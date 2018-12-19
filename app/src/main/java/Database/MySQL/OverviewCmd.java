package Database.MySQL;

public abstract class OverviewCmd {
    public static String SELECTALL = "SELECT * FROM OVERVIEW";
    public static String SELECTNEWESTBYAPARTMENTID = "SELECT * FROM OVERVIEW WHERE APARTMENT_ID = ? ORDER BY DATE DESC LIMIT 1";
}
