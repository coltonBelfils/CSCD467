import java.net.Socket;

public class ClientInfo {
    private Socket socket;
    private int clientNumber;

    public ClientInfo(Socket socket, int clientNumber) {
        this.socket = socket;
        this.clientNumber = clientNumber;
    }

    public Socket getSocket() {
        return socket;
    }

    public int getClientNumber() {
        return clientNumber;
    }
}
