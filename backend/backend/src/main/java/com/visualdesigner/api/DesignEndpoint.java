package com.visualdesigner.api;

import com.sun.net.httpserver.HttpExchange;
import com.visualdesigner.controller.DesignController;
import java.io.IOException;
import java.sql.SQLException;
import org.json.JSONArray;

/**
 * this class handles the specific requests for the design endpoint.
 */
public class DesignEndpoint {

    /**
     * @param exchange
     * @throws IOException
     * @throws SQLException
     */
    public void handleRequest(HttpExchange exchange) throws IOException, SQLException {

        DesignController designController = new DesignController();
        Endpoint endpoint = new Endpoint();

        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();

        if ("GET".equals(method) && path.matches("/api/design/([^/]+)")) {
            final int pathNumber = 3;
            int id = endpoint.getIdFromPath(exchange, pathNumber);

            JSONArray responseText = designController.get(id);

            if (responseText.length() > 0) {
                endpoint.sendResponse(exchange, responseText);
            } else {
                exchange.sendResponseHeaders(ResponseNumber.NOTFOUND.getValues(), -1); // 404 Not Found
            }
        } else if ("PUT".equals(method)) {
            System.out.println(exchange);
        } else if ("POST".equals(method)) {
            designController.post(exchange);
            exchange.sendResponseHeaders(ResponseNumber.OK.getValues(), -1); // 200 Ok
        } else if ("DELETE".equals(method)) {

            System.out.println(path.substring(path.lastIndexOf('/') + 1));

            if (path.matches("/api/design/([^/]+)")) {

                int id = Integer.parseInt(path.substring(path.indexOf('/') + 1));
                System.out.println("Deleted design with ID:" + id);
                exchange.sendResponseHeaders(ResponseNumber.OK.getValues(), -1); // 200 Ok
            }
        }
        if ("OPTIONS".equals(method)) {
            exchange.sendResponseHeaders(ResponseNumber.NOCONTENT.getValues(), -1);
        } else {
            exchange.sendResponseHeaders(ResponseNumber.UNAUTHORIZED.getValues(), -1); // 401 Unauthorized
        }
    }
}
