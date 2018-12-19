package Database;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static android.content.Context.MODE_PRIVATE;

public class DBConnect{
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
                    String server = ConfigurationManager.getInstance().getServer();
                    connection = DriverManager.getConnection(server);
                    server = ConfigurationManager.getInstance().getServer();
                } catch (SQLException e) {
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
        try {
            if (connection == null || connection.isClosed()){
                DBConnect();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        DBConnect();
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
