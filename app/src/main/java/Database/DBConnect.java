package Database;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static android.content.Context.MODE_PRIVATE;

public class DBConnect{
    private String address = "";
    private String database = "";
    private String user = "";
    private String password = "";
    private Connection connection = null;
    private static DBConnect instance = null;

    private DBConnect() {
        DBConnect();
    }

    private void DBConnect() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    String server = "jdbc:mysql://" + address + "/" + database + "?user=" + user + "&password=" + password;
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
            DBConnect();
        return connection;
    }

    public static DBConnect getInstance() {
        if(instance == null)
            instance = new DBConnect();
        return instance;
    }

    public void newConnection(String address, String database, String user, String password) {
        this.address = address;
        this.database = database;
        this.user = user;
        this.password = password;
        if (connection != null)
            closeConnection();
        DBConnect();
    }

    public void closeConnection() {
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
