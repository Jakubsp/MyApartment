package Database;

public class ConfigurationManager {

    private String address;
    private String database;
    private String user;
    private String password;
    private String databaseType;

    private static ConfigurationManager instance;

    private ConfigurationManager() {

    }

    public static ConfigurationManager getInstance() {
        if (instance == null)
            instance = new ConfigurationManager();
        return instance;
    }

    public void updateDatabaseType(String type) {
        this.databaseType = type;
    }

    public void updateParameters(String address, String database, String user, String password) {
        this.address = address;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    public String getDatabaseType() {
        return this.databaseType;
    }

    public String getServer() {
        return "jdbc:mysql://" + address + "/" + database + "?user=" + user + "&password=" + password;
    }
}
