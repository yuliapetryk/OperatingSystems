package clients;

import functions.Functions;
import org.newsclub.net.unix.AFUNIXSocket;
import org.newsclub.net.unix.AFUNIXSocketAddress;

import java.io.*;
import java.util.Optional;

public class Client {
    private final File socketFile = new File(new File(System.getProperty("java.io.tmpdir")), "junixsocket-test.sock");
    private Integer x;

    public Client(String typeOfFunction) {
        readMessage();
        Optional<Double> result =  Optional.empty();
        switch (typeOfFunction) {
            case "F":
                result = Functions.functionF(x);
                break;
            case "G":
                result = Functions.functionG(x);
                break;
        }
        sendMessage(result, typeOfFunction);
    }

    public void readMessage() {
        try (AFUNIXSocket socket = AFUNIXSocket.newInstance()) {
            socket.connect( AFUNIXSocketAddress.of(socketFile));
            System.out.println("Connected to server");

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String serverResponse = reader.readLine();
                System.out.println("Received number from server: " + serverResponse);
                this.x=Integer.valueOf(serverResponse);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendMessage(Optional<Double> result, String typeOfFunction){
        String resultStr = result.map(integer -> typeOfFunction + integer).orElseGet(() -> typeOfFunction + "hard fail");

        try (AFUNIXSocket socket = AFUNIXSocket.newInstance()) {
            socket.connect( AFUNIXSocketAddress.of(socketFile));
            try (OutputStream outputStream = socket.getOutputStream()) {
                String numberToSend = resultStr;
                outputStream.write(numberToSend.getBytes());
                System.out.println("Sent result to server: " + numberToSend);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
