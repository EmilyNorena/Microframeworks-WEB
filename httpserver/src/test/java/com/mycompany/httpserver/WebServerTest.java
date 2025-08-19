package com.mycompany.httpserver;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import com.mycompany.httpserver.WebServer;

/**
 *
 * @author Emily Noreña Cardozo
 */

public class WebServerTest {

    private static final String URL = "http://localhost:35000/";
    private static WebServer server;
    private static Thread serverThread;

    @BeforeAll
    public static void setUp() {
        try {
            server = new WebServer();
            serverThread = new Thread(() -> {
                try {
                    server.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            serverThread.start();
            Thread.sleep(1000);
            System.out.println("Servidor iniciado");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldLoadStaticFileHtml() throws Exception {
        String file = "index.html";
        testFileRequest(file, 200);
    }

    @Test
    public void notShouldLoadStaticFileHtml() throws Exception {
        String file = "nonexistent.html";
        testFileRequest(file, 404);
    }

    @Test
    public void shouldLoadStaticFileCss() throws Exception {
        String file = "styles.css";
        testFileRequest(file, 200);
    }

    @Test
    public void notShouldLoadStaticFileCss() throws Exception {
        String file = "nonexistent.css";
        testFileRequest(file, 404);
    }

    @Test
    public void shouldLoadStaticFileJs() throws Exception {
        String file = "script.js";
        testFileRequest(file, 200);
    }

    @Test
    public void notShouldLoadStaticFileJs() throws Exception {
        String file = "nonexistent.js";
        testFileRequest(file, 404);
    }

    @Test
    public void shouldLoadStaticImagePNG() throws Exception {
        String file = "img.png";
        testFileRequest(file, 200);
    }

    @Test
    public void shouldLoadStaticImageJPG() throws Exception {
        String file = "koala.jpg";
        testFileRequest(file, 200);
    }

    @Test
    public void notShouldLoadStaticImagePNG() throws Exception {
        String file = "nonexistent.png";
        testFileRequest(file, 404);
    }

    @Test
    public void notShouldLoadStaticImageJPG() throws Exception {
        String file = "nonexistent.jpg";
        testFileRequest(file, 404);
    }

    @Test
    public void shouldLoadRestGet() throws Exception {
        String endpoint = "api/hello?name=test";
        testEndpointRequest(endpoint, 200);
    }

    @Test
    public void shouldLoadRestPost() throws Exception {
        String endpoint = "api/hellopost";
        testPostRequest(endpoint, "name=test", 200);
    }

    private void testFileRequest(String file, int expectedCode) {
        try {
            URL requestUrl = new URL(URL + file);
            HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            assertEquals(expectedCode, responseCode);
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testEndpointRequest(String endpoint, int expectedCode) {
        try {
            URL requestUrl = new URL(URL + endpoint);
            HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            assertEquals(expectedCode, responseCode);
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testPostRequest(String endpoint, String params, int expectedCode) {
        try {
            URL requestUrl = new URL(URL + endpoint);
            HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.getOutputStream().write(params.getBytes());
            int responseCode = connection.getResponseCode();
            assertEquals(expectedCode, responseCode);
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    public static void tearDown() {
        try {
            server.stop();
            serverThread.join();
            System.out.println("Server close");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}