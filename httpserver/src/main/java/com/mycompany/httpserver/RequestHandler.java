package com.mycompany.httpserver;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author Emily Noreña Cardozo
 */

public class RequestHandler {
    private final Socket clientSocket;
    private final FileHandler fileHandler;
    private final ApiHandler apiHandler;

    public RequestHandler(Socket clientSocket, FileHandler fileHandler, ApiHandler apiHandler) {
        this.clientSocket = clientSocket;
        this.fileHandler = fileHandler;
        this.apiHandler = apiHandler;
    }

    public RequestHandler(Socket clientSocket, String staticFilesPath) {
        this(clientSocket, new FileHandler(staticFilesPath), new ApiHandler());
    }

    public void handleRequest() throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             OutputStream rawOut = clientSocket.getOutputStream()) {

            String requestLine = in.readLine();
            if (requestLine == null) return;

            String[] parts = requestLine.split(" ");
            String method = parts[0];
            String fullPath = parts[1];

            String path = fullPath.split("\\?")[0];
            Map<String, String> queryParams = parseQueryParams(fullPath);

            Map<String, String> headers = new HashMap<>();
            String line;
            while ((line = in.readLine()) != null && !line.isEmpty()) {
                String[] header = line.split(":", 2);
                if (header.length == 2) {
                    headers.put(header[0].trim(), header[1].trim());
                }
            }
            if (path.startsWith("/api/")) {
                String response = apiHandler.handleApiRequest(method, path, queryParams, in);
                sendResponse(out, response, "application/json");
            } else {
                fileHandler.serveFile(path, out, rawOut);
            }
        }
    }

    private void sendResponse(PrintWriter out, String content, String contentType) {
        out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: " + contentType);
        out.println("Content-Length: " + content.length());
        out.println();
        out.println(content);
    }

    private Map<String, String> parseQueryParams(String fullPath) {
        Map<String, String> params = new HashMap<>();
        if (fullPath.contains("?")) {
            String query = fullPath.split("\\?")[1];
            for (String param : query.split("&")) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    params.put(keyValue[0], keyValue[1]);
                }
            }
        }
        return params;
    }
}