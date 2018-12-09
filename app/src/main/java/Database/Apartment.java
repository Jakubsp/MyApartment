package Database;

public class Apartment {
    /* Local variables */
    private int id;
    private int rooms;
    private int usableArea;
    private int floor;
    private String psswd;
    private int tenantId;

    /* ID of apartment */
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    /* ********** */

    /* Number of rooms */
    public int getRooms() {
        return rooms;
    }
    public void setRooms(int rooms) {
        this.rooms = rooms;
    }
    /* ********** */

    /* Total usable area of apartment */
    public int getUsableArea() {
        return usableArea;
    }
    public void setUsableArea(int usableArea) {
        this.usableArea = usableArea;
    }
    /* ********** */

    /* Floor where is apartment located */
    public int getFloor() {
        return floor;
    }
    public void setFloor(int floor) {
        this.floor = floor;
    }
    /* ********** */

    /* Password of the apartment */
    public String getPsswd() {
        return psswd;
    }
    public void setPsswd(String psswd) {
        this.psswd = psswd;
    }
    /* ********** */

    /* Current tenantId of apartment */
    public int getTenantId() {
        return tenantId;
    }
    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }
    /* ********** */
}
