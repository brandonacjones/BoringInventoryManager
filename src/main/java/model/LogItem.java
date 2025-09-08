package model;

import java.sql.Time;
import java.sql.Timestamp;

public class LogItem {
    private String created_at;
    private String type;
    private String user;
    private String description;

    public LogItem() {};

    public LogItem(String timestamp, String type, String user, String description) {
        this.created_at = created_at;
        this.type = type;
        this.user = user;
        this.description = description;
    }

    public String getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
