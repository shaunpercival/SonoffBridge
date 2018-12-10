/**
 * Created by bearingpoint on 27/11/18.
 */



window.onload = init;


var socket = new WebSocket("wss://localhost:9443/sonoffwebsockets/actions");


socket.onmessage = onMessage;

function onMessage(event) {
    var device = JSON.parse(event.data);
    if (device.action === "add") {
        printDeviceElement(device);
    }
    if (device.action === "remove") {
        document.getElementById(device.id).remove();
        //device.parentNode.removeChild(device);
    }
    if (device.action === "toggle") {
        var node = document.getElementById(device.id);
        var statusText = node.children[2];
        if (device.status === "On") {
            statusText.innerHTML = "Status: " + device.status + " (<a href=\"#\" OnClick=toggleDevice(" + device.id + ")>Turn off</a>)";
        } else if (device.status === "Off") {
            statusText.innerHTML = "Status: " + device.status + " (<a href=\"#\" OnClick=toggleDevice(" + device.id + ")>Turn on</a>)";
        }
    }
}

function registerDevice(name, type, description, deviceId, romVersion, model, apiKey) {
    var DeviceAction = {
        action: "register",
        name: name,
        type: type,
        id: null,
        deviceid: deviceId,
        apikey: apiKey,
        romVersion: romVersion,
        model: model,
        description: description
    };
    socket.send(JSON.stringify(DeviceAction));

    if ( device == null){
        device = new Device();
        device.setAPIKey(getJsonVariable(jsonMessage,"apikey"));
        device.setDeviceId("deviceid");
    }
    device.setVersion(getJsonVariable(jsonMessage,"romVersion"));
    device.setModel(getJsonVariable(jsonMessage,"model"));
    sessionHandler.handleSonoffRegisterRequest(session,device);


}

function removeDevice(element) {
    var id = element;
    var DeviceAction = {
        action: "remove",
        id: id
    };
    socket.send(JSON.stringify(DeviceAction));
}

function toggleDevice(element) {
    var id = element;
    var DeviceAction = {
        action: "toggle",
        id: id
    };
    socket.send(JSON.stringify(DeviceAction));
}

function printDeviceElement(device) {
    var content = document.getElementById("content");

    var deviceDiv = document.createElement("div");
    deviceDiv.setAttribute("id", device.id);
    deviceDiv.setAttribute("class", "device " + device.type);
    content.appendChild(deviceDiv);

    var deviceName = document.createElement("span");
    deviceName.setAttribute("class", "deviceName");
    deviceName.innerHTML = device.name;
    deviceDiv.appendChild(deviceName);

    var deviceId = document.createElement("span");
    deviceId.setAttribute("class", "deviceOther");
    deviceId.innerHTML = device.deviceid;
    deviceId.appendChild(deviceId);

    //var deviceName = document.createElement("span");
    //deviceName.setAttribute("class", "deviceName");
    //deviceName.innerHTML = device.name;
    //deviceDiv.appendChild(deviceName);
    //
    //var deviceName = document.createElement("span");
    //deviceName.setAttribute("class", "deviceName");
    //deviceName.innerHTML = device.name;
    //deviceDiv.appendChild(deviceName);
    //
    //type: type,
    //    id: null,
    //    deviceid: deviceId,
    //    apikey: apiKey,
    //    romVersion: romVersion,
    //    model: model,
    //    description: description

    var deviceType = document.createElement("span");
    deviceType.innerHTML = "<b>Type:</b> " + device.type;
    deviceDiv.appendChild(deviceType);

    var deviceStatus = document.createElement("span");
    if (device.status === "On") {
        deviceStatus.innerHTML = "<b>Status:</b> " + device.status + " (<a href=\"#\" OnClick=toggleDevice(" + device.id + ")>Turn off</a>)";
    } else if (device.status === "Off") {
        deviceStatus.innerHTML = "<b>Status:</b> " + device.status + " (<a href=\"#\" OnClick=toggleDevice(" + device.id + ")>Turn on</a>)";
        //deviceDiv.setAttribute("class", "device off");
    }
    deviceDiv.appendChild(deviceStatus);

    var deviceDescription = document.createElement("span");
    deviceDescription.innerHTML = "<b>Comments:</b> " + device.description;
    deviceDiv.appendChild(deviceDescription);

    var removeDevice = document.createElement("span");
    removeDevice.setAttribute("class", "removeDevice");
    removeDevice.innerHTML = "<a href=\"#\" OnClick=removeDevice(" + device.id + ")>Remove device</a>";
    deviceDiv.appendChild(removeDevice);
}

function showForm() {
    document.getElementById("addDeviceForm").style.display = '';
}

function hideForm() {
    document.getElementById("addDeviceForm").style.display = "none";
}

function formSubmit() {
    var form = document.getElementById("addDeviceForm");
    var name = form.elements["device_name"].value;
    var type = form.elements["device_type"].value;
    var deviceId =form.elements["deviceId"].value;
    var apikey =form.elements["apikey"].value;
    var romVersion =form.elements["romVersion"].value;
    var model =form.elements["model"].value;
    var description = form.elements["device_description"].value;
    hideForm();
    document.getElementById("addDeviceForm").reset();
    registerDevice(name, type, description, deviceId, romVersion, model, apikey)
}

function init() {
    hideForm();
}