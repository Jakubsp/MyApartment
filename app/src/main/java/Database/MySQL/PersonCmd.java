package Database.MySQL;

public abstract class PersonCmd {
    public static String SELECTALL = "SELECT * FROM PERSON";
    public static String SELECTBYRIGHTS = "SELECT * FROM PERSON WHERE RIGHTS = ?";
    public static String SELECTBYID = "SELECT * FROM PERSON WHERE ID = ?";

    public static String INSERT = "INSERT INTO PERSON VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static String DELETE = "DELETE FROM PERSON WHERE ID = ?";
}