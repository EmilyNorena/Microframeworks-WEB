package com.mycompany.httpserver;
import java.io.*;
import java.nio.file.*;

/**
 *
 * @author Emily Noreña Cardozo
 */

public class FileHandler {
    private final Path basePath;

    public FileHandler(String baseDirectory) {
        this.basePath = Paths.get(baseDirectory).toAbsolutePath().normalize();
    }

    public void serveFile(String path, PrintWriter out, OutputStream rawOut) throws IOException {
        try {
            if (path.equals("/")) {
                path = "/index.html"; // Default
            }

            Path filePath = resolveSafePath(path);

            // Verificar si el archivo existe y es legible
            if (!Files.exists(filePath) || !Files.isReadable(filePath)) {
                handle404Error(out, rawOut);
                return;
            }

            byte[] content = Files.readAllBytes(filePath);
            String contentType = getContentType(path);

            sendSuccessResponse(out, rawOut, contentType, content);

        } catch (SecurityException e) {
            handle403Error(out);
        } catch (IOException e) {
            handle404Error(out, rawOut);
        }
    }

    private void handle404Error(PrintWriter out, OutputStream rawOut) {
        try {
            // Cambiar la resolución de la ruta del 404.html
            Path errorPagePath = basePath.resolve("404.html").normalize();

            if (Files.exists(errorPagePath) && Files.isReadable(errorPagePath)) {
                byte[] errorContent = Files.readAllBytes(errorPagePath);

                out.print("HTTP/1.1 404 Not Found\r\n");
                out.print("Content-Type: text/html\r\n");
                out.print("Content-Length: " + errorContent.length + "\r\n");
                out.print("\r\n");
                out.flush();
                rawOut.write(errorContent);
                rawOut.flush();
            } else {
                sendPlainText404(out);
            }
        } catch (Exception e) {
            sendPlainText404(out);
        }
    }

    private void sendSuccessResponse(PrintWriter out, OutputStream rawOut,
                                     String contentType, byte[] content) throws IOException {
        out.print("HTTP/1.1 200 OK\r\n");
        out.print("Content-Type: " + contentType + "\r\n");
        out.print("Content-Length: " + content.length + "\r\n");
        out.print("\r\n");
        out.flush();
        rawOut.write(content);
        rawOut.flush();
    }

    private void sendPlainText404(PrintWriter out) {
        out.print("HTTP/1.1 404 Not Found\r\n");
        out.print("Content-Type: text/plain\r\n");
        out.print("\r\n");
        out.print("404 Not Found - The requested resource was not found");
        out.flush();
    }

    private void handle403Error(PrintWriter out) {
        out.print("HTTP/1.1 403 Forbidden\r\n");
        out.print("Content-Type: text/plain\r\n");
        out.print("\r\n");
        out.print("403 Forbidden - Access denied");
        out.flush();
    }

    private Path resolveSafePath(String requestPath) throws SecurityException {
        Path resolved = basePath.resolve(requestPath.substring(1)).normalize();
        if (!resolved.startsWith(basePath)) {
            throw new SecurityException("Directory traversal attempt");
        }
        return resolved;
    }

    private String getContentType(String path) {
        if (path.endsWith(".html")) {
            return "text/html";
        } else if (path.endsWith(".css")) {
            return "text/css";
        } else if (path.endsWith(".js")) {
            return "application/javascript";
        } else if (path.endsWith(".jpg") || path.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (path.endsWith(".png")) {
            return "image/png";
        } else if (path.endsWith(".gif")) {
            return "image/gif";
        } else {
            return "text/plain";
        }
    }
}