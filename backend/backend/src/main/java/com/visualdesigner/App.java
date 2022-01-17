package com.visualdesigner;

import com.sun.net.httpserver.HttpServer;
import com.visualdesigner.api.Endpoint;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * @param args The arguments of the program.
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        HttpServer server = null;

        int port = Integer.parseInt(System.getenv("HTTP_PORT").replace("\"", ""));

        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        final int maxThreads = 10;
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(maxThreads);

        if (server != null) {
            server.createContext("/api/", new Endpoint());
            server.setExecutor(threadPoolExecutor);
            server.start();
            System.out.println("server started on http://www.localhost:" + port);
        } else {
            System.out.println("server could not be started");
        }

    }
}
