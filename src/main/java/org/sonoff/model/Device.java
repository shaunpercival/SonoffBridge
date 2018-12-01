package org.sonoff.model;

public class Device {

    private int id;
    private String name;
    private String status;
    private String type;
    private String description;

    // Sonoff
    private String apiKey;
    private String deviceId;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }




    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    private String deviceid;

    public String getAPIKey() {
        return apiKey;
    }

    public void setAPIKey(String APIKey) {
        this.apiKey = APIKey;
    }




    public Device() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }



    public void setId(int id) {
        this.id = id;
        System.out.println("setId " + id);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}