package Database.MySQL;

public abstract class PersonCmd {
    public static String SELECTALL = "SELECT * FROM PERSON";
    public static String SELECTALLUSERS = "SELECT * FROM PERSON WHERE RIGHTS = 'OWN' OR RIGHTS = 'REN' OR RIGHTS = 'USR'";
    public static String SELECTBYRIGHTS = "SELECT * FROM PERSON WHERE RIGHTS = ?";
    public static String SELECTBYID = "SELECT * FROM PERSON WHERE ID = ?";

    public static String INSERT = "INSERT INTO PERSON VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static String UPDATE = "UPDATE PERSON SET NAME = ?, COMPANY_NAME = ?, DATE_OF_BIRTH = ?, RIGHTS = ?, EMAIL = ?," +
            "NFC_UID = ?, TASK = ?, PERSON_ID = ? WHERE ID = ?";

    public static String DELETE = "DELETE FROM PERSON WHERE ID = ?";
}
