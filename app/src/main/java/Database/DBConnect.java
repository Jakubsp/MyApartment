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
    private Connection connection = null;
    private static DBConnect instance = null;

    private DBConnect() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    connection = DriverManager.getConnection(ConfigurationManager.getInstance().getServer());
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

    public void newConnection() {
        if (connection != null)
            closeConnection();
        new DBConnect();
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
