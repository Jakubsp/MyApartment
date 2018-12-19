package Database;

import java.util.Date;

public class Overview {
    /* Local variables */
    private int id;
    private Date date;
    private int water;
    private int gas;
    private int electricity;
    private double avgTemp;
    private int apartmentId;

    /* ID of current overview */
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    /* ********** */

    /* Datetime of overview */
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    /* ********** */

    /* m3 of water used */
    public int getWater() {
        return water;
    }
    public void setWater(int water) {
        this.water = water;
    }
    /* ********** */

    /* m3 of gas used */
    public int getGas() {
        return gas;
    }
    public void setGas(int gas) {
        this.gas = gas;
    }
    /* ********** */

    /* kwh of electricity used */
    public int getElectricity() {
        return electricity;
    }
    public void setElectricity(int electricity) {
        this.electricity = electricity;
    }
    /* ********** */

    /* Average temperature of apartment */
    public double getAvgTemp() {
        return avgTemp;
    }
    public void setAvgTemp(double avgTemp) {
        this.avgTemp = avgTemp;
    }
    /* ********** */

    /* ID of apartment */
    public int getApartmentId() {
        return apartmentId;
    }
    public void setApartmentId(int apartmentId) {
        this.apartmentId = apartmentId;
    }
    /* ********** */
}
