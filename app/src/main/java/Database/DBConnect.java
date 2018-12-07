package Database;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect extends Activity {
    private String address;
    private String database;
    private String user;
    private String password;
    private String server = "jdbc:mysql://" + address + "/" + database + "?user=" + user + "&password=" + password;
    private Connection connection = null;
    private static DBConnect instance = null;

    private DBConnect() {
        if (address == null)
            RefreshValues();

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

    public void RefreshValues() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("DBinitials", MODE_PRIVATE);
        address = sharedPreferences.getString("address", "127.0.0.1");
        database = sharedPreferences.getString("database", "apartments");
        user = sharedPreferences.getString("user", "admin");
        password = sharedPreferences.getString("password", "password");
    }

    public boolean testConnection(String address, String database, String user, String password) {
        this.address = address;
        this.database = database;
        this.user = user;
        this.password = password;

        new DBConnect();

        if (connection == null) {
            RefreshValues();
            return false;
        }

        RefreshValues();
        return true;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
