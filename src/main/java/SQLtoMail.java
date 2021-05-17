import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;

public class SQLtoMail {

    public static void main(String[] args) {

        Connection conn = null;
        int count = 0;
        String message = "";

        Dotenv env = Dotenv.load();
        try {
            String dbURL = env.get("SQL_URL") ;
            String user = env.get("SQL_USER");
            String pass = env.get("SQL_KEY");
            conn = DriverManager.getConnection(dbURL, user, pass);

            String sql = env.get("SQL_BASE_LIST_SELECT");
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                count++;
                String data = resultSet.getString("DateLastSeen");
                String id = resultSet.getString("XyzmoId");
                System.out.println(id + " - " + data + " - " + count);
                message += (id + " - " + data + " - " + count + "\n");
            }
            if (!resultSet.next()) {
                Email.send(message);
                // message = "";
            }

            if (conn != null) {
                    DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
                    System.out.println("Driver name: " + dm.getDriverName());
                    System.out.println("Driver version: " + dm.getDriverVersion());
                    System.out.println("Product name: " + dm.getDatabaseProductName());
                    System.out.println("Product version: " + dm.getDatabaseProductVersion());
                conn.close();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}

