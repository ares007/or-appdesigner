package com.visualdesigner.controller;

import com.sun.net.httpserver.HttpExchange;
import com.visualdesigner.api.DBConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Controller class for design.
 */
public class DesignController {

    // SQL helper queries
    private static final String INSERT_DESIGN =
        "INSERT INTO design(id, name, display_device, safe_space, display_safe_space) VALUES (?, ?, ?, ?, ?) ";
    private static final String INSERT_PAGE =
        "INSERT INTO page(design_id, name, is_homepage, in_navigation)VALUES ( ?, ?, ?, ?)";

    // Split up this query because of the 120 character line limit
    private static final String SQL_SPLIT_WIDGET_QUERY =
        "INSERT INTO widgets(id, label, page_id, position_x, position_y, height, width, asset_type, widget_type";
    private static final String SQL_SPLIT_WIDGET_VALUES = "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    // Split up this query because of the 120 character line limit
    private static final String SQL_SPLIT_WIDGET_VALUE_QUERY =
        "INSERT INTO widget_values(asset_name, asset_id, attribute_name, time, value, measurement, widget_id) ";
    private static final String SQL_SPLIT_WIDGET_VALUE_VALUES = "VALUES (?, ?, ?, ?, ?, ?, ?)";

    // Merging the split query into one again
    private static final String INSERT_WIDGET = SQL_SPLIT_WIDGET_QUERY + SQL_SPLIT_WIDGET_VALUES;
    private static final String INSERT_VALUE = SQL_SPLIT_WIDGET_VALUE_QUERY + SQL_SPLIT_WIDGET_VALUE_VALUES;

    private Connection conn;

    /**
     * Constructor.
     */
    public DesignController() {
        DBConnection dbConnection = new DBConnection();
        conn = dbConnection.connect();
    }

    /**
     * returns the design from the database.
     * @param designId
     * @return
     * @throws SQLException
     */
    public JSONArray get(int designId) throws SQLException {

        PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM design WHERE id=?");

        stmt.setInt(1, designId);

        JSONArray arr = new JSONArray();

        try (
                ResultSet result = stmt.executeQuery();) {
            ResultSetMetaData rsmd = result.getMetaData();

            while (result.next()) {
                int numColumns = rsmd.getColumnCount();
                JSONObject obj = new JSONObject();
                for (int i = 1; i <= numColumns; i++) {
                    String columnName = rsmd.getColumnName(i);
                    obj.put(columnName, result.getObject(columnName));
                }
                arr.put(obj);
            }
            conn.close();

        } catch (SQLException ex) {
            arr.put(ex);
        }
        return arr;
    }

    /**
     * Preparedstatement to post a new design to the database.
     * @param exchange
     * @throws IOException
     * @throws SQLException
     */
    public void postOld(HttpExchange exchange) throws IOException, SQLException {

        BufferedReader httpInput = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), "UTF-8"));

        JSONTokener tokener = new JSONTokener(httpInput);
        JSONObject json = new JSONObject(tokener);

        String name = json.getString("name");
        String displayDevice = json.getString("display_device");
        int safeSpace = json.getInt("safe_space");
        Boolean displaySafeSpace = json.getBoolean("display_safe_space");

        try {
            String sql = "INSERT INTO design(name, display_device, safe_space, display_safe_space) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            ControllerHelper.setValues(stmt, name, displayDevice, safeSpace, displaySafeSpace);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Post for testing purposes only, truncates the database and fills it with the
     * posted data.
     * This is without many security checks.
     * This can and will break the database when faulty data is supplied.
     * @param exchange
     * @throws IOException
     * @throws SQLException
     */
    public void post(HttpExchange exchange) throws IOException, SQLException {
        int designId = 0;
        int pageId = 0;
        int widgetId = 0;

        try {
            String[] tables = {"design", "page", "widgets", "widget_values"};
            // first truncate all the tables
            for (String table : tables) {
                System.out.println("truncated: " + table);
                String sql = "TRUNCATE TABLE " + table;
                Statement statement = conn.createStatement();
                statement.executeUpdate(sql);
            }

            BufferedReader httpInput = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), "UTF-8"));

            JSONTokener tokener = new JSONTokener(httpInput);
            JSONObject json = new JSONObject(tokener);

            // prepare statement
            PreparedStatement stmt = conn.prepareStatement(INSERT_DESIGN);

            // prepare values
            designId = json.getInt("id");
            String name = json.getString("name");
            String displayDevice = json.getString("display_device");
            int safeSpace = json.getInt("safe_space");
            Boolean displaySafeSpace = json.getBoolean("display_safe_space");

            ControllerHelper.setValues(stmt, designId, name, displayDevice, safeSpace, displaySafeSpace);

            // update design table
            stmt.executeUpdate();
            System.out.println("inserted: design");

            // prepare statement
            PreparedStatement stmt2 = conn.prepareStatement(INSERT_PAGE);

            // prepare values
            JSONObject page = json.getJSONObject("page");

            String pageName = page.getString("name");
            Boolean isHomepage = page.getBoolean("is_homepage");
            Boolean inNavigation = page.getBoolean("in_navigation");

            ControllerHelper.setValues(stmt2, designId, pageName, isHomepage, inNavigation);

            // update page table
            stmt2.executeUpdate();
            System.out.println("inserted: page");

            JSONArray widgets = json.getJSONArray("widgets");

            pageId = page.getInt("id");

            for (int i = 0; i < widgets.length(); i++) {
                JSONObject widget = widgets.getJSONObject(i);

                JSONObject element = widget.getJSONObject("element");

                // prepare statement
                PreparedStatement stmt3 = conn.prepareStatement(INSERT_WIDGET);

                // prepare values
                widgetId = widget.getInt("id");
                String text = element.getString("text");
                int posX = widget.getInt("positionX");
                int posY = widget.getInt("positionY");
                int height = widget.getInt("height");
                int width = widget.getInt("width");
                String assetType = element.getString("assetType");
                int widgetType = element.getInt("widgetType");

                ControllerHelper.setValues(stmt3, widgetId, text, pageId, posX, posY, height, width, assetType,
                        widgetType);

                // update widgets table
                stmt3.executeUpdate();
                System.out.println("inserted: widget");

                JSONArray values = element.getJSONArray("values");

                for (int j = 0; j < values.length(); j++) {
                    JSONObject value = values.getJSONObject(j);

                    // prepare statement
                    PreparedStatement stmt4 = conn.prepareStatement(INSERT_VALUE);

                    // prepare values
                    String assetName = value.getString("assetName");
                    String assetId = value.getString("assetId");
                    String attributeName = value.getString("attributeName");
                    String time = value.getString("time");
                    String assetValue = value.getString("value");
                    String measurement = value.getString("measurement");

                    ControllerHelper.setValues(stmt4, assetName, assetId, attributeName, time, assetValue, measurement,
                            widgetId);

                    // update values
                    stmt4.executeUpdate();
                    System.out.println("inserted: value");

                }
            }
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
