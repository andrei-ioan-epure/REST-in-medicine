
import com.idm.proto.Auth;
import com.idm.proto.AuthServiceGrpc;
import com.idm.proto.UserServiceGrpc;
import db.repository.UserRepositoryImpl;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;


public class Main {

    public static void main(String[] args) {

        ManagedChannel channel=ManagedChannelBuilder.forAddress("localhost",50051).usePlaintext().build();

//        UserRepositoryImpl userRepository=new UserRepositoryImpl();
//        userRepository.createTable();
        UserServiceGrpc.UserServiceBlockingStub userStub=UserServiceGrpc.newBlockingStub(channel);
        Auth.CreateUserRequest createUserRequest= Auth.CreateUserRequest.newBuilder()
                .setUsername("user")
                .setRole("patient")
                .setPassword("pass").build();
        Auth.CreateUserResponse createUserResponse=userStub.createUser(createUserRequest);
        System.out.println(createUserResponse.getSuccess());

        AuthServiceGrpc.AuthServiceBlockingStub authServiceBlockingStub= AuthServiceGrpc.newBlockingStub(channel);
        Auth.AuthenticationRequest authenticationRequest= Auth.AuthenticationRequest.newBuilder()
                .setUsername("user")
                .setPassword("pass")
                .build();
        Auth.AuthenticationResponse authenticationResponse=authServiceBlockingStub.authenticateUser(authenticationRequest);
        System.out.println(authenticationResponse.getToken());

    }
}
