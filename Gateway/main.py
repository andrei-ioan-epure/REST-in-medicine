import requests
from fastapi import FastAPI, Request, Header
from fastapi.middleware.cors import CORSMiddleware
from starlette.responses import JSONResponse

from grpc_client import *

app = FastAPI()

app.add_middleware(CORSMiddleware, allow_origins=["*"], allow_credentials=True,
                   allow_methods=["*"], allow_headers=["*"], )

patient_service_url = "http://localhost:8086/api/medical_office/patients"
physician_service_url = "http://localhost:8087/api/medical_office/physicians"
appointment_service_url = "http://localhost:8088/api/medical_office/appointments"
consultation_service_url = "http://localhost:8089/api/medical_office/consultations"
grpc_server_address = "localhost:50051"


@app.get("/url-list-from-request")
async def get_all_urls_from_request(request: Request):
    url_list = [
        {"path": route.path, "name": route.name} for route in request.app.routes
    ]
    return url_list


@app.post("/destroyToken", response_model=DestroyTokenResponseModel)
async def destroy_token(request: DestroyTokenRequestModel):
    return destroy_token_grpc(request, grpc_server_address)


@app.post("/authenticate", response_model=AuthenticationResponseModel)
async def authenticate_user(request: AuthenticationRequestModel):
    return authenticate_user_grpc(request, grpc_server_address)


@app.post("/register", response_model=RegisterResponseModel)
async def register_user(request: RegisterRequestModel):
    return register_user_grpc(request, grpc_server_address)


@app.post("/validate", response_model=ValidateResponseModel)
async def validate_token(request: ValidateRequestModel):
    response = validate_token_grpc(request, grpc_server_address)
    return response


async def forward_request(method: str, url: str, headers: dict, params: dict = None, json_data: dict = None):
    try:
        if method == "GET":
            response = requests.get(url, headers=headers, params=params)
        elif method == "POST":
            response = requests.post(url, headers=headers, params=params, json=json_data)
        elif method == "PUT":
            response = requests.put(url, headers=headers, params=params, json=json_data)
        elif method == "DELETE":
            response = requests.delete(url, headers=headers, params=params)
        else:
            raise HTTPException(status_code=405, detail="Method Not Allowed")

        if response.content:
            return JSONResponse(content=response.json(), status_code=response.status_code)
        else:
            return JSONResponse(content=None, status_code=response.status_code)

    except requests.exceptions.RequestException as e:

        error_message = f"Error {method} request to {url}: {e}"
        return JSONResponse(content={"error": error_message}, status_code=500)


@app.api_route("/patients/{path:path}", methods=["GET", "POST", "PUT", "DELETE"])
async def patients(path: str, request: Request, authorization: str = Header(None)):
    return await handle_request(path, request, authorization, patient_service_url)


@app.api_route("/physicians/{path:path}", methods=["GET", "POST", "PUT", "DELETE"])
async def physicians(path: str, request: Request, authorization: str = Header(None)):
    return await handle_request(path, request, authorization, physician_service_url)


@app.api_route("/appointments/{path:path}", methods=["GET", "POST", "PUT", "DELETE"])
async def appointments(path: str, request: Request, authorization: str = Header(None)):
    return await handle_request(path, request, authorization, appointment_service_url)


@app.api_route("/consultations/{path:path}", methods=["GET", "POST", "PUT", "DELETE"])
async def consultations(path: str, request: Request, authorization: str = Header(None)):
    return await handle_request(path, request, authorization, consultation_service_url)


async def handle_request(path: str, request: Request, authorization: str, base_url: str):
    headers_to_forward = dict(request.headers)
    try:
        if authorization is None:
            raise HTTPException(status_code=401, detail="Authentication header missing")

        print("Authorization ",authorization)
        token = authorization.split(" ")[1] if authorization.startswith("Bearer ") else None
        if token is None or token == "null":
            raise HTTPException(status_code=401, detail="Bearer token missing")

        print(authorization)
        requestValidation = ValidateRequestModel(token=token)
        validation_response = validate_token_grpc(requestValidation, grpc_server_address)

    except HTTPException as e:
        raise HTTPException(status_code=403, detail=e.detail)

    url = f"{base_url}/{path}" if path else f"{base_url}"
    print(f'url ={url}')
    if request.method in ["GET", "DELETE"]:
        return await forward_request(request.method, url, headers_to_forward, params=dict(request.query_params))
    elif request.method in ["POST", "PUT"]:
        json_body = await request.json()
        return await forward_request(request.method, url, headers_to_forward, json_data=json_body)
    else:
        raise HTTPException(status_code=405, detail="Method Not Allowed")
