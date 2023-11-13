import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HUBSocket {
    static ServerSocket serverSck;
    static BufferedReader in;

    private static void run() {
        try {
            serverSck = new ServerSocket(4444);
            System.out.println("Aguardando Conexão");
            while(true) {
                Socket sckt = serverSck.accept();
                new SocketThread(sckt).start();
                SocketsList.getInstance().addSocket(sckt);
                System.out.println(
                        SocketsList.getInstance().getListaSockets()
                );
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        HUBSocket.run();
    }
}
