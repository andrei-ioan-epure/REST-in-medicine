import server.AuthServer;

public class Main {

    public static void main(String[] args) throws Exception {

        int port = 50051;
        AuthServer authServer = new AuthServer(port);
        authServer.start();

    }
}