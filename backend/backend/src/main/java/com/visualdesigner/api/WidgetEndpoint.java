package com.visualdesigner.api;

import com.sun.net.httpserver.HttpExchange;
import com.visualdesigner.controller.WidgetController;
import java.io.IOException;
import java.sql.SQLException;
import org.json.JSONArray;

/**
 * this class handles the specific requests for the widgets endpoint.
 */
public class WidgetEndpoint {

    /**
     * @param exchange
     * @throws IOException
     * @throws SQLException
     */
    public void handleRequest(HttpExchange exchange) throws IOException, SQLException {

        WidgetController widgetController = new WidgetController();
        Endpoint endpoint = new Endpoint();

        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();

        if ("GET".equals(method) && path.matches("/api/widget/([^/]+)")) {
            final int pathNumber = 3;
            int id = endpoint.getIdFromPath(exchange, pathNumber);

            JSONArray responseText = widgetController.get(exchange, id);

            if (responseText.length() > 0) {
                endpoint.sendResponse(exchange, responseText);
            } else {
                exchange.sendResponseHeaders(ResponseNumber.NOTFOUND.getValues(), -1); // 404 Not Found
            }
        } else {
            exchange.sendResponseHeaders(ResponseNumber.UNAUTHORIZED.getValues(), -1); // 401 Unauthorized
        }
    }
}
