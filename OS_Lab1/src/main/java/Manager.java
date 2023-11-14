import java.io.*;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

import org.newsclub.net.unix.AFUNIXServerSocket;
import org.newsclub.net.unix.AFUNIXSocketAddress;

public class Manager {
    private static  Integer x;
    private static Executor executor;
    private static Process processF;
    private static Process processG;
    private static AFUNIXServerSocket server;

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
        File socketFile = new File(new File(System.getProperty("java.io.tmpdir")), "junixsocket-test.sock");
        try (AFUNIXServerSocket serverSocket = AFUNIXServerSocket.newInstance()) {
            serverSocket.bind( AFUNIXSocketAddress.of(socketFile));
            System.out.println("Server is running. Waiting for connections...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected");

                try (OutputStream outputStream = clientSocket.getOutputStream()) {
                    outputStream.write(Integer.toString(x).getBytes());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}



