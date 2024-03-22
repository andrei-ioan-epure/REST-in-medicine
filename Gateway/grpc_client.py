import grpc
from fastapi import HTTPException, status
from pydantic import BaseModel

import auth_pb2
import auth_pb2_grpc


class AuthenticationResponseModel(BaseModel):
    token: str


class AuthenticationRequestModel(BaseModel):
    username: str
    password: str


class ValidateRequestModel(BaseModel):
    token: str


class ValidateResponseModel(BaseModel):
    sub: str
    role: str


class DestroyTokenRequestModel(BaseModel):
    token: str


class DestroyTokenResponseModel(BaseModel):
    message: str


class RegisterRequestModel(BaseModel):
    username: str
    password: str
    role: str


class RegisterResponseModel(BaseModel):
    success: str
    id: str


def destroy_token_grpc(request: DestroyTokenRequestModel, grpc_server_address: str):
    protobuf_request = auth_pb2.DestroyTokenRequest(
        token=request.token
    )
    with grpc.insecure_channel(grpc_server_address) as channel:
        stub = auth_pb2_grpc.AuthServiceStub(channel)

        try:
            destroy_response = stub.DestroyToken(protobuf_request)
            return {"message": destroy_response.message}

        except grpc._channel._InactiveRpcError as e:
            status_code = status.HTTP_500_INTERNAL_SERVER_ERROR
            raise HTTPException(status_code=status_code, detail="Destroy token failed")


def authenticate_user_grpc(request: AuthenticationRequestModel, grpc_server_address: str):
    protobuf_request = auth_pb2.AuthenticationRequest(
        username=request.username,
        password=request.password,
    )
    with grpc.insecure_channel(grpc_server_address) as channel:
        stub = auth_pb2_grpc.AuthServiceStub(channel)

        try:
            auth_response = stub.AuthenticateUser(protobuf_request)
            return {"token": auth_response.token}

        except grpc._channel._InactiveRpcError as e:
            status_code = status.HTTP_401_UNAUTHORIZED if "Invalid credentials" in str(
                e) else status.HTTP_500_INTERNAL_SERVER_ERROR
            raise HTTPException(status_code=status_code, detail="Authentication failed")

        # finally:
        #     channel.close()


def register_user_grpc(request: RegisterRequestModel, grpc_server_address: str):
    protobuf_request = auth_pb2.CreateUserRequest(
        username=request.username,
        password=request.password,
        role=request.role,
    )
    with grpc.insecure_channel(grpc_server_address) as channel:
        stub = auth_pb2_grpc.UserServiceStub(channel)

        try:
            auth_response = stub.createUser(protobuf_request)
            print("auth_response:", auth_response)
            print("auth_response.success:", auth_response.success)

            if "False" in auth_response.success:
                if "already exists" in auth_response.success:
                    raise HTTPException(
                        status_code=status.HTTP_409_CONFLICT,
                        detail="Username already exists",
                        headers={"content-type": "application/json"},
                    )

            print(f'Creare cu succes id={auth_response.id}')
            return {"success": auth_response.success, "id": str(auth_response.id)}

            # print(RegisterResponseModel(auth_response.success, auth_response.id))
            # return RegisterResponseModel(auth_response.success, auth_response.id)

        except grpc._channel._InactiveRpcError as e:
            status_code = status.HTTP_401_UNAUTHORIZED if "Invalid credentials" in str(
                e) else status.HTTP_500_INTERNAL_SERVER_ERROR
            raise HTTPException(status_code=status_code, detail="Register failed")

        # finally:
        #     channel.close()


def validate_token_grpc(request: ValidateRequestModel, grpc_server_address: str):
    protobuf_request = auth_pb2.TokenValidationRequest(
        token=request.token
    )
    with grpc.insecure_channel(grpc_server_address) as channel:
        try:
            stub = auth_pb2_grpc.AuthServiceStub(channel)
            response = stub.ValidateToken(protobuf_request)

            return ValidateResponseModel(sub=response.sub, role=response.role)

        except grpc._channel._InactiveRpcError as e:
            status_code = status.HTTP_401_UNAUTHORIZED if "Invalid token" in str(
                e) else status.HTTP_500_INTERNAL_SERVER_ERROR
            raise HTTPException(status_code=status_code, detail="Authentication failed")

        # finally:
        #     channel.close()
