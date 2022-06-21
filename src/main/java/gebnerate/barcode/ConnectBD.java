package gebnerate.barcode;

import java.sql.*;

public class ConnectBD {
    Connection con;
    private String query;
    public String result = "";

    public static void main(String[] args) {
    }

    public void open() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:src/main/resources/gebnerate/barcode/bd/barcode.db");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String query(String query) throws SQLException {

        Statement statement = con.createStatement();

        if (query != null && query.length() != 0) {
            switch (query.charAt(0)) {

                case 's':
                    try {
                        ResultSet res = statement.executeQuery(query);
                        while (res.next()) {
                            result += res.getString("id") +
                                    "|" + res.getString("barcode") + "|"
                                    + res.getString("type") + ";";


                        }
                    } catch (Exception e) {
                        return e.getMessage();
                    }
                    break;

                default:
                    try {
                        statement.executeUpdate(query);
                    } catch (Exception e) {
                        return e.getMessage();
                    }

                    break;
            }
        } else {
            return "Empty query.";
        }
        statement.close();
        if (result != null && result.length() != 0) {
            query = result;
        } else {
            query = "noresultata";
        }
        return query;
    }

    public void close() throws SQLException {
        con.close();
    }
}


