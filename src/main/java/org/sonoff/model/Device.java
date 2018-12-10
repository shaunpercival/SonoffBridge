package org.sonoff.model;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.spi.JsonProvider;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Device {

    private int id = 0;
    private String name = "<not assigned>";
    private String status = "<not assigned>";
    private String type = "<not assigned>";
    private String description= "<not assigned>" ;

    // Sonoff
    private String apiKey = "<not assigned>";
    private String deviceId= "<not assigned>";
    private String version= "<not assigned>";
    private String model= "<not assigned>";

    private List<DeviceParams> deviceParamsList = new ArrayList<DeviceParams>();

    public List<DeviceParams> getDeviceParamsList() {
        return deviceParamsList;
    }

    public void putDeviceParam(DeviceParams deviceParams) {
        deviceParamsList.add(deviceParams);
    }


    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void setDeviceParamsList(List<DeviceParams> deviceParamsList) {
        this.deviceParamsList = deviceParamsList;
    }

    // mETA DATA
    private Date lastUpdated = new Date();

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }



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




    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", apiKey='" + apiKey + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", version='" + version + '\'' +
                ", model='" + model + '\'' +
                '}';
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