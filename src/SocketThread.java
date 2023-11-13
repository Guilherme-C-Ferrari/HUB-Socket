import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class SocketThread extends Thread{
    Socket socket;
    BufferedReader in;
    
    public SocketThread(Socket sckt) {
        this.socket = sckt;
    }
    public void run() {
        try {
            in = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()));
            int ret = 0;
            System.out.println("conexao de "+ socket.getRemoteSocketAddress().toString());
            while(!socket.isClosed() && ret >= 0){
                ret = socket.getInputStream().read();
                socket.getOutputStream()
                        .write((SocketsList.getInstance().getListaSockets()+"§").getBytes());
                socket.getOutputStream().flush();
            }
            socket.close();
            SocketsList.getInstance().removeSocket(socket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}