package clients;

import org.newsclub.net.unix.AFUNIXSocket;
import org.newsclub.net.unix.AFUNIXSocketAddress;

import java.io.*;


public class Client {
    private final File socketFile = new File(new File(System.getProperty("java.io.tmpdir")), "junixsocket-test.sock");
    private Integer x;

    public Client(String funcType) {
        readMessage();
    }

    public void readMessage() {
        try (AFUNIXSocket socket = AFUNIXSocket.newInstance()) {
            socket.connect( AFUNIXSocketAddress.of(socketFile));
            System.out.println("Connected to server");

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String serverResponse = reader.readLine();
                System.out.println("Received number from server: " + serverResponse);
                this.x=Integer.valueOf(serverResponse);
                System.out.println(x);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
