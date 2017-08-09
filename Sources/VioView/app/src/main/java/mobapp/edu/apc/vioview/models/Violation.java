package mobapp.edu.apc.vioview.models;

/**
 * Created by Caranto on 11/30/2016.
 */

public class Violation {

    private String date;
    private String description;
    private String issuedBy;
    private String type;

    public Violation() {
    }

    public Violation(String date, String description, String issuedBy, String type) {
        this.date = date;
        this.description = description;
        this.issuedBy = issuedBy;
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
