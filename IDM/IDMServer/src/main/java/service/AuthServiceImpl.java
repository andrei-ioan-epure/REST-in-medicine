package service;

import com.idm.proto.Auth;
import com.idm.proto.Auth.AuthenticationRequest;
import com.idm.proto.Auth.AuthenticationResponse;
import com.idm.proto.Auth.TokenValidationRequest;
import com.idm.proto.Auth.TokenValidationResponse;
import com.idm.proto.AuthServiceGrpc;
import db.model.BlacklistItem;
import db.model.User;
import db.repository.BlacklistRepository;
import db.repository.BlacklistRepositoryImpl;
import db.repository.UserRepository;
import db.repository.UserRepositoryImpl;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.mindrot.jbcrypt.BCrypt;

import java.time.Instant;
import java.util.*;

public class AuthServiceImpl extends AuthServiceGrpc.AuthServiceImplBase {

    private static final String SECRET_KEY = "3ff2e1h57aefb8bdf1542850d663n007d620e40520b5715dc83f4a921d36ce9ce47d0d13c5d85f2b0ff8318d2877eec2f63b931bd47417a81a538327af927da3e";
    private final UserRepository userRepository=new UserRepositoryImpl();
    private final BlacklistRepository blacklistRepository = new BlacklistRepositoryImpl();

    @Override
    public void authenticateUser(AuthenticationRequest request, StreamObserver<AuthenticationResponse> responseObserver) {
        User user=this.userRepository.findByUsername(request.getUsername());
        AuthenticationResponse response;
        if(user !=null  && BCrypt.checkpw(request.getPassword(), user.getPassword())) {
             response=AuthenticationResponse
                            .newBuilder()
                            .setToken(generateToken(user))
                            .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        else{

            String errorMessage = "Invalid credentials";
            responseObserver.onError(Status.INVALID_ARGUMENT.withDescription(errorMessage).asRuntimeException());
        }
    }

    @Override
    public void validateToken(TokenValidationRequest request, StreamObserver<TokenValidationResponse> responseObserver) {
        String token = request.getToken();

        try {
           Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            if (isTokenValid(claims)) {

                if(!isTokenExpired(claims)) {
                    String userRole = claims.get("role", String.class);
                    String userId = claims.get("sub", String.class);

                    TokenValidationResponse response = TokenValidationResponse.newBuilder()
                            .setSub(userId)
                            .setRole(userRole)
                            .setMessage("Success")
                            .build();
                    responseObserver.onNext(response);

                }
                else
                {
                    TokenValidationResponse response = TokenValidationResponse.newBuilder()
                            .setMessage("Expired token")
                            .build();
                    responseObserver.onNext(response);
                  }

            } else {
                TokenValidationResponse response = TokenValidationResponse.newBuilder()
                        .setMessage("Invalid token")
                        .build();
                responseObserver.onNext(response);
             }

        } catch (Exception e) {
            TokenValidationResponse response = TokenValidationResponse.newBuilder()
                    .setMessage("Invalid token")
                    .build();
            responseObserver.onNext(response);

        }finally {
            responseObserver.onCompleted();
        }

    }

    @Override
    public void destroyToken(Auth.DestroyTokenRequest request, StreamObserver<Auth.DestroyTokenResponse> responseObserver) {
        String token = request.getToken();

        try {
            blacklistRepository.addToBlacklist(new BlacklistItem(token,Instant.now()));

            Auth.DestroyTokenResponse response = Auth.DestroyTokenResponse.newBuilder()
                    .setMessage("Token added to blacklist")
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
           // }
        } catch (Exception e) {
            blacklistRepository.addToBlacklist(new BlacklistItem(token,Instant.now()));

            Auth.DestroyTokenResponse response = Auth.DestroyTokenResponse.newBuilder()
                    .setMessage("Token added to blacklist")
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }

    private boolean isTokenValid(Claims claims) {
        System.out.println(!isTokenInBlacklist(claims.getId()));
        return !isTokenInBlacklist(claims.getId());
    }
    private boolean isTokenInBlacklist(String tokenId) {
        return blacklistRepository.isTokenBlacklisted(tokenId);
    }

    private boolean isTokenExpired(Claims claims) {
        return !Instant.now().isBefore(claims.getExpiration().toInstant());
    }

    private String generateToken(User user)  {

        Claims claims = Jwts.claims().setSubject(user.getId().toString());
        claims.put("role", user.getRole());

        String issuer = "http://127.0.0.1:8000/authenticate";

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(issuer)
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) //valid o ora
                .setId(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}