package Database;

import java.util.Date;

public class Person {
    /* Local variables */
    private int id;
    private String name;
    private String companyName;
    private Date dateOfBirth;
    private String rights;
    private String email;
    private String nfcUid;
    private String task;
    private int superiorId;

    /* ID of person */
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    /* ********** */

    /* Name of person */
    public String getName(){
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    /* ********** */

    /* Name of company */
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    /* ********** */

    /* Birthdate of person */
    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    /* ********** */

    /* Rights of person */
    public String getRights() {
        return rights;
    }
    public void setRights(String rights) {
        this.rights = rights;
    }
    /* ********** */

    /* Email of person/ */
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    /* ********** */

    /* NFC UID of person */
    public String getNfcUid() {
        return nfcUid;
    }
    public void setNfcUid(String nfcUid) {
        this.nfcUid = nfcUid;
    }
    /* ********** */

    /* Task of company */
    public String getTask() {
        return task;
    }
    public void setTask(String task) {
        this.task = task;
    }
    /* ********** */

    /* ID od superiorId person */
    public int getSuperiorId() {
        return superiorId;
    }
    public void setSuperiorId(int superiorId) {
        this.superiorId = superiorId;
    }
    /* ********** */
}
