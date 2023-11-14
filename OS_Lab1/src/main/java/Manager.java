import java.io.*;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import org.newsclub.net.unix.AFUNIXServerSocket;
import org.newsclub.net.unix.AFUNIXSocketAddress;

public class Manager {
    private static  Integer x;

    private static int clientsToWaitFor = 2;

    public Manager() throws IOException, InterruptedException, TimeoutException {
        Scanner sc = new Scanner(System.in);
        int x = 0;
        System.out.println("Please, enter x: ");
        if (sc.hasNextInt()) {
            x = sc.nextInt();
        }
        sc.nextLine();
        this.x = x;

        runManager();
    }

    public static void runManager() throws IOException, InterruptedException, TimeoutException {
        ArrayList<String> receivedValues = new ArrayList<>();
        File socketFile = new File(new File(System.getProperty("java.io.tmpdir")), "junixsocket-test.sock");
        try (AFUNIXServerSocket serverSocket = AFUNIXServerSocket.newInstance()) {
            serverSocket.bind( AFUNIXSocketAddress.of(socketFile));
            System.out.println("Server is running. Waiting for connections...");

            while (clientsToWaitFor > 0) {
                Socket clientSocket = serverSocket.accept();

                try (OutputStream outputStream = clientSocket.getOutputStream()) {
                    outputStream.write(Integer.toString(x).getBytes());

                } catch (IOException e) {
                    e.printStackTrace();
                }
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                    String clientMessage = reader.readLine();
                    if (clientMessage!=null) {
                        receivedValues.add(clientMessage);
                        clientsToWaitFor--;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        parseResult(receivedValues);
    }

   private static void parseResult(List<String> array) {
       String elemF = array.get(0);
       String elemG = array.get(1);

       if (elemF.charAt(1) == 'c') {
           System.out.println("Critical failure of function F");

       } else if (elemG.charAt(1) == 'c') {
           System.out.println("Critical failure of function G");

       } else {
           double resultF = Double.parseDouble(elemF.substring(1));
           double resultG = Double.parseDouble(elemG.substring(1));
           double result = resultG + resultF;

           System.out.println("The result of a binary operation = " + result);
       }
   }

}



