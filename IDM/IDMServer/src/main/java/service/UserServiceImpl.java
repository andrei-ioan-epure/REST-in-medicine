package service;

import com.idm.proto.Auth;
import com.idm.proto.UserServiceGrpc;
import db.model.User;
import db.repository.UserRepository;
import db.repository.UserRepositoryImpl;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {

    private final UserRepository userRepository=new UserRepositoryImpl();


    @Override
    public void createUser(Auth.CreateUserRequest request, StreamObserver<Auth.CreateUserResponse> responseObserver) {
        try {

            List<String> invalidFields = getInvalidFields(request);
            if (invalidFields.isEmpty()) {
               Long id= userRepository.createUser(new User(1L, request.getUsername(),
                        request.getPassword(), request.getRole()));

                responseObserver.onNext(Auth.CreateUserResponse.newBuilder().setSuccess("True").setId(id).build());
            } else {
                responseObserver.onNext(Auth.CreateUserResponse.newBuilder().setSuccess("False : "+invalidFields.get(0)).setId(-1).build());
            }

        } catch (Exception e) {
            responseObserver.onNext(Auth.CreateUserResponse.newBuilder().setSuccess("False").build());
        } finally {
            responseObserver.onCompleted();
        }
    }


    private List<String> getInvalidFields(Auth.CreateUserRequest request) {
        List<String> invalidFields = new ArrayList<>();

        if(userRepository.findByUsername(request.getUsername()) != null) {
            invalidFields.add("Username already exists");
        }
        if (request.getUsername().isEmpty()) {
            invalidFields.add("Username cannot be empty");
        }
        if (request.getPassword().isEmpty()) {
            invalidFields.add("Password cannot be empty");
        }
        if (!isValidRole(request.getRole())) {
            invalidFields.add("Invalid role");
        }

        return invalidFields;
    }


    private boolean isValidRole(String userRole) {
        String[] roles = {"patient", "physician", "admin"};

        for (String role : roles) {
            if (role.equals(userRole)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public void getUserById(Auth.GetUserRequest request, StreamObserver<Auth.GetUserResponse> responseObserver) {
        try {
            User user = userRepository.findById(request.getId());

            if (user != null) {
                responseObserver.onNext(Auth.GetUserResponse.newBuilder()
                        .setUsername(user.getUsername())
                        .setPassword(user.getPassword())
                        .setRole(user.getRole())
                        .build());
            } else {
                responseObserver.onNext(Auth.GetUserResponse.newBuilder().build());
            }
        } catch (Exception e) {
            responseObserver.onNext(Auth.GetUserResponse.newBuilder().build());
        } finally {
            responseObserver.onCompleted();
        }    }

    @Override
    public void editUser(Auth.EditUserRequest request, StreamObserver<Auth.EditUserResponse> responseObserver) {
        try {
            User user = userRepository.findById(request.getId());

            if (user != null) {
                if (!request.getUsername().isEmpty()) {
                    user.setUsername(request.getUsername());
                }

                if (!request.getPassword().isEmpty()) {
                    user.setPassword(request.getPassword());
                }

                if (!request.getRole().isEmpty()) {
                    user.setRole(request.getRole());
                }

                userRepository.updateUser(user.getId(), user);

                responseObserver.onNext(Auth.EditUserResponse.newBuilder().setSuccess("Success").build());
            } else {
                responseObserver.onNext(Auth.EditUserResponse.newBuilder().setSuccess("User not found").build());
            }
        } catch (Exception e) {
            responseObserver.onNext(Auth.EditUserResponse.newBuilder().setSuccess("Failed").build());
        } finally {
            responseObserver.onCompleted();
        }
    }

    @Override
    public void deleteUser(Auth.DeleteUserRequest request, StreamObserver<Auth.DeleteUserResponse> responseObserver) {
        try {
            User user = userRepository.findById(request.getId());

            if (user != null) {
                userRepository.deleteUser(user.getId());

                responseObserver.onNext(Auth.DeleteUserResponse.newBuilder().setSuccess("Success").build());
            } else {
                responseObserver.onNext(Auth.DeleteUserResponse.newBuilder().setSuccess("User not found").build());
            }
        } catch (Exception e) {
            responseObserver.onNext(Auth.DeleteUserResponse.newBuilder().setSuccess("Failed").build());
        } finally {
            responseObserver.onCompleted();
        }
    }
}
