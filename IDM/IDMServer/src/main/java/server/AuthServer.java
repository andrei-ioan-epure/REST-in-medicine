package server;

import db.repository.UserRepository;
import db.repository.UserRepositoryImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import service.AuthServiceImpl;
import service.UserServiceImpl;


public class AuthServer {
    private final int port;
    private final Server server;

    public AuthServer(int port) {
        this.port = port;
        this.server = ServerBuilder.forPort(port)
                .addService(new AuthServiceImpl())
                .addService(new UserServiceImpl())
                .build();
    }

    public void start() throws Exception {
        UserRepository userRepository = new UserRepositoryImpl();
        userRepository.createTable();
        server.start();
        System.out.println("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            AuthServer.this.stop();
            System.out.println("Server shut down");
        }));
        server.awaitTermination();
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }


}