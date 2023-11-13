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
            String nome = "";
            System.out.println("conexao de "+ socket.getRemoteSocketAddress().toString());
            while (!socket.isClosed() && (char) ret != '§') {
                ret = socket.getInputStream().read();
                nome += (char) ret;
                if ((char) ret == '§') {
                nome = nome.replace("§", "");
                nome = nome.replace("Â", "");
                nome = nome.replace("Hey, ", "");
                System.out.println(nome);
                SocketsList.getInstance().addSocket(nome, socket.getInetAddress().toString());
                }
            }
            socket.getOutputStream()
                .write((SocketsList.getInstance().getListaSockets()+"§").getBytes());
            socket.getOutputStream().flush();
            SocketsList.getInstance().removeSocket(socket.getInetAddress().toString());
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}