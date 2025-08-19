package com.mycompany.httpserver;
import java.net.*;
import java.io.*;

/**
 *
 * @author Emily Noreña Cardozo
 */

public class WebServer {
    private static final int port = 35000;
    private ServerSocket serverSocket;
    private boolean running;
    private String staticFilesPath = "src/main/java/com/mycompany/public";
    private FileHandler fileHandler;
    private ApiHandler apiHandler;
    private volatile boolean ready = false;

    public static void main(String[] args) throws IOException {
        new WebServer().start();
    }

    public WebServer() {
        this.fileHandler = new FileHandler(staticFilesPath);
        this.apiHandler = new ApiHandler();
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        running = true;
        System.out.println("Server started on port " + port);
        while (running) {
            try {
                Socket clientSocket = serverSocket.accept();
                handleClient(clientSocket);
            } catch (IOException e) {
                if (running) {
                    System.err.println("Error accepting connection: " + e.getMessage());
                }
            }
        }
    }

    private void handleClient(Socket clientSocket) {
        try {
            RequestHandler handler = new RequestHandler(clientSocket, fileHandler, apiHandler);
            handler.handleRequest();
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }

    public void stop() {
        running = false;
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error stopping server: " + e.getMessage());
        }
    }
}