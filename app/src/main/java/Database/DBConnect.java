package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    private String database = "192.168.77.73/apartments";
    private String user = "jakub@spilacek.cz";
    private String password = "";
    private String server = "jdbc:mysql://" + database + "?user=" + user + "&password=" + password;
    private Connection connection = null;
    private static DBConnect instance = null;

    private DBConnect() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    connection = DriverManager.getConnection(server);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        if (connection == null)
            new DBConnect();
        return connection;
    }

    public static DBConnect getInstance() {
        if(instance == null)
            instance = new DBConnect();
        return instance;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
