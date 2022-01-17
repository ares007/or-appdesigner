package com.visualdesigner.controller;

import com.sun.net.httpserver.HttpExchange;
import com.visualdesigner.api.DBConnection;
import com.visualdesigner.api.ResponseNumber;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Controller class for widget.
 */
public class WidgetController {

    private Connection conn;

    /**
     * Constructor.
     */
    public WidgetController() {
        DBConnection dbConnection = new DBConnection();
        conn = dbConnection.connect();
    }

    /**
     * returns the widget with relational information from the database.
     * @param exchange
     * @param pageId
     * @return
     * @throws SQLException
     * @throws IOException
     */
    public JSONArray get(HttpExchange exchange, int pageId) throws SQLException, IOException {
        JSONArray pageArray = new JSONArray();
        JSONObject pageObject = new JSONObject();
        JSONArray widgetArray = new JSONArray();

        PreparedStatement pageStatement = conn.prepareStatement(
                "SELECT * FROM page WHERE id=?");

        PreparedStatement widgetStatement = conn.prepareStatement(
                "SELECT * FROM widgets WHERE page_id=?");

        PreparedStatement valueStatement = conn.prepareStatement(
                "SELECT * FROM widget_values WHERE widget_id=?");

        pageStatement.setInt(1, pageId); // set the value for id
        widgetStatement.setInt(1, pageId); // set the value for widget_id

        try {
            ResultSet pageResult = pageStatement.executeQuery();
            ResultSetMetaData pageMetaData = pageResult.getMetaData();

            // get the data from page
            while (pageResult.next()) {
                int pageNumOfColumns = pageMetaData.getColumnCount();
                for (int i = 1; i <= pageNumOfColumns; i++) {
                    String pageColName = pageMetaData.getColumnName(i);
                    pageObject.put(pageColName, pageResult.getObject(pageColName));
                }
                pageArray.put(pageObject);
            }

            ResultSet widgetResult = widgetStatement.executeQuery();
            ResultSetMetaData widgetMetaData = widgetResult.getMetaData();

            // get the data from widgets
            while (widgetResult.next()) {
                JSONArray valueArray = new JSONArray(); // value array
                JSONObject widgetObject = new JSONObject(); // widget object

                valueStatement.setInt(1, widgetResult.getInt("id")); // set the value for widget_id

                int widgetNumOfColumns = widgetMetaData.getColumnCount();
                for (int j = 1; j <= widgetNumOfColumns; j++) {
                    String widgetColName = widgetMetaData.getColumnName(j);
                    widgetObject.put(widgetColName, widgetResult.getObject(widgetColName));
                }
                widgetArray.put(widgetObject);

                ResultSet valueResult = valueStatement.executeQuery();
                ResultSetMetaData valueMetaData = valueResult.getMetaData();

                // get the data from widget_values
                while (valueResult.next()) {
                    JSONObject valueObject = new JSONObject(); // value object
                    int valueNumOfColumns = valueMetaData.getColumnCount();

                    for (int y = 1; y <= valueNumOfColumns; y++) {
                        String valueColName = valueMetaData.getColumnName(y);
                        valueObject.put(valueColName, valueResult.getObject(valueColName));
                    }
                    valueArray.put(valueObject);
                }
                widgetObject.put("values", valueArray); // put the widget values Array inside the widgetObject

            }
            pageObject.put("widgets", widgetArray); // put the widgetArray inside the pageObject
            conn.close();

        } catch (SQLException ex) {
            pageArray.put(ex);
            exchange.sendResponseHeaders(ResponseNumber.NOTALLOWED.getValues(), -1); // 405 Not allowed

        }
        return pageArray;
    }
}
