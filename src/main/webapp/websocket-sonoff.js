/**
 * Created by bearingpoint on 27/11/18.
 */



window.onload = init;


var socket = new WebSocket("wss://192.168.1.180:9443/sonoffwebsockets/actions");


socket.onmessage = onMessage;

function onMessage(event) {
    var device = JSON.parse(event.data);

    if (device.action === "add") {
        printDeviceElement(device);
    }else if (device.action === "remove") {
        if (document.getElementById(device.deviceid) != null ) {
            document.getElementById(device.deviceid).remove();
        }else{
            console.log("Div content was null");
        }
        //device.parentNode.removeChild(device);
    }else if (device.action === "toggle") {
        var node = document.getElementById(device.deviceid);
        var statusText = node.children[2];
        if (device.status === "On") {
            statusText.innerHTML = "Status: " + device.status + " (<a href=\"#\" OnClick=toggleDevice(" + device.deviceid + ")>Turn off</a>)";
        } else if (device.status === "Off") {
            statusText.innerHTML = "Status: " + device.status + " (<a href=\"#\" OnClick=toggleDevice(" + device.deviceid + ")>Turn on</a>)";
        }
    }else{
        printDeviceStats(device);
    }
}

function registerDevice(name, type, description, deviceid, romVersion, model, apiKey) {
    var DeviceAction = {
        action: "register",
        name: name,
        type: type,
        id: deviceid,
        deviceid: deviceid,
        apikey: apiKey,
        romVersion: romVersion,
        model: model,
        description: description
    };
    socket.send(JSON.stringify(DeviceAction));

}

function removeDevice(element) {
    var id = element;
    var DeviceAction = {
        action: "remove",
        deviceid: id
    };
    socket.send(JSON.stringify(DeviceAction));
}

function toggleDevice(element) {
    var id = element;
    var DeviceAction = {
        action: "toggle",
        deviceid: id
    };
    socket.send(JSON.stringify(DeviceAction));
}

function printDeviceStats(device) {
    var stats = document.getElementById("stats");
    console.log("WS Response: apikey: "  + device.apikey + " deviceid:" + device.deviceid + " error:" + device.error + " IP: " + device.IP);
    stats.innerHTML = "WS: apikey: "  + device.apikey + " deviceid:" + device.deviceid + " error:" + device.error  + " IP: " + device.IP ;


}


function printDiv(deviceDiv,className,innerHtmlValue){
    var div = document.createElement("span");
    div.setAttribute("class", className);
    div.innerHTML = innerHtmlValue;
    deviceDiv.appendChild(div);
}

function printDeviceElement(device) {
    var content = document.getElementById("content");

    var deviceDiv = document.createElement("div");
    deviceDiv.setAttribute("id", device.deviceid);
    deviceDiv.setAttribute("class", "device " + device.type);
    content.appendChild(deviceDiv);

    printDiv(deviceDiv,"deviceName",device.name);
    printDiv(deviceDiv,"deviceOther",device.model);
    printDiv(deviceDiv,"deviceOther",device.apikey);
    printDiv(deviceDiv,"deviceOther",device.deviceid);
    printDiv(deviceDiv,"deviceOther",device.romVersion);
    printDiv(deviceDiv,"deviceOther",device.type);


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
    var deviceid =form.elements["deviceid"].value;
    var apikey =form.elements["apikey"].value;
    var romVersion =form.elements["romVersion"].value;
    var model =form.elements["model"].value;
    var description = form.elements["device_description"].value;
    hideForm();
    document.getElementById("addDeviceForm").reset();
    registerDevice(name, type, description, deviceid, romVersion, model, apikey)
}


function formRefresh(){
    var PageACtion = {
        action: "refresh"
    };
    socket.send(JSON.stringify(PageACtion));
}

function init() {
    hideForm();
}