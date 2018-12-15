package Database;

public class ConfigurationManager {

    private String address;
    private String database;
    private String user;
    private String password;
    private DatabaseType databaseType;
    private String jsonFileName;

    private static ConfigurationManager instance;

    private ConfigurationManager() {

    }

    public static ConfigurationManager getInstance() {
        if (instance == null)
            instance = new ConfigurationManager();
        return instance;
    }

    public void updateDatabaseType(DatabaseType type) {
        this.databaseType = type;
    }

    public void updateParameters(String address, String database, String user, String password) {
        this.address = address;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    public void updateJsonFileName(String name) {
        this.jsonFileName = name;
    }

    public DatabaseType getDatabaseType() {
        return this.databaseType;
    }

    public String getJsonFileName() {
        return this.jsonFileName;
    }

    public String getServer() {
        return "jdbc:mysql://" + address + "/" + database + "?user=" + user + "&password=" + password;
    }
}
