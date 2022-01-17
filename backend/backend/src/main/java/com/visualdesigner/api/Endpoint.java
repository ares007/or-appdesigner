package com.visualdesigner.api;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;

/**
 * this class handles and routes HTTP requests.
 */
public class Endpoint implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        // set all the headers on every request for now
        // ToDo: set the right headers for each request
        final Headers headers = exchange.getResponseHeaders();
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        headers.set("Access-Control-Allow-Headers", "*");

        DesignEndpoint designEndpoint = new DesignEndpoint();
        WidgetEndpoint widgetEndpoint = new WidgetEndpoint();

        try {
            String path = exchange.getRequestURI().getPath();
            if (path.startsWith("/api/design")) {
                designEndpoint.handleRequest(exchange);
            } else if (path.startsWith("/api/widget")) {
                widgetEndpoint.handleRequest(exchange);
            } else {
                exchange.sendResponseHeaders(ResponseNumber.UNAUTHORIZED.getValues(), -1); // 401 Unauthorized
            }
        } catch (SQLException e) {
            exchange.sendResponseHeaders(ResponseNumber.INTERNALERROR.getValues(), 0); // 500 Internal Server Error
        }
        exchange.close();
    }

    /**
     * @param exchange
     * @param responseText
     * @throws IOException
     */
    public void sendResponse(HttpExchange exchange, JSONArray responseText) throws IOException {
        final Headers headers = exchange.getResponseHeaders();
        exchange.sendResponseHeaders(ResponseNumber.OK.getValues(), responseText.toString().getBytes().length);
        OutputStream output = exchange.getResponseBody();
        output.write(responseText.toString().getBytes());
        headers.set("Content-Type", String.format("application/json; charset=%s", "StandardCharsets.UTF_8"));
        headers.set("Access-Control-Allow-Origin", "*");
        output.flush();
    }

    /**
     * @param exchange
     * @param index
     * @return
     * @throws IOException
     */
    public int getIdFromPath(HttpExchange exchange, int index) throws IOException {
        int id = 0;
        String path = exchange.getRequestURI().getPath();

        String[] urlSplited = path.split("/");
        String dept = urlSplited[index];
        Pattern pattern = Pattern.compile("^-?[0-9]+$");
        Matcher matcher = pattern.matcher(dept);

        if (matcher.matches()) {
            id = Integer.parseInt(dept);
        } else {
            exchange.sendResponseHeaders(ResponseNumber.NOTFOUND.getValues(), -1); // 404
        }
        return id;
    }
}
