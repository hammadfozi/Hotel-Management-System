function verifyCapacity() {
    var check = document.getElementById('capacity').value;
    var maxCapacity = 6;
    if (Number(check) < 0) {
        document.getElementById("capacity").style.borderColor = 'red';
        document.getElementById("capacity").value = null;
    } else if (Number(check) > maxCapacity) {
        document.getElementById("capacity").value = maxCapacity;
    } else {
        document.getElementById("capacity").style.borderColor = 'green';
    }
}

function verifyRoomName() {
    var xmlhttp = new XMLHttpRequest();
    var name = document.forms["roomForm"]["name"].value;
    var url = "/admin/room/check?name=" + name;
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            if (xmlhttp.responseText == "Available") {
                document.getElementById("name").style.borderColor = 'green';
                document.getElementById("name").style.color = 'green';
                document.getElementById("nameerror").innerHTML = "";
            } else {
                document.getElementById("name").style.borderColor = "red";
                document.getElementById("nameerror").innerHTML = document.getElementById("name").value + " is already in use";
                document.getElementById("name").value = null;
            }
        }

    };
    try {
        xmlhttp.open("GET", url, true);
        xmlhttp.send();
    } catch (e) {
        //alert("Unable to connect to server");
    }
}

function verifyBedCount() {
    var check = document.getElementById('bed').value;
    var totalBeds = 3;
    if (Number(check) < 0) {
        document.getElementById("bed").style.borderColor = 'red';
        document.getElementById("bed").value = null;
    } else if (Number(check) > totalBeds) {
        document.getElementById("bed").value = totalBeds;
    } else {
        document.getElementById("bed").style.borderColor = 'green';
    }
}

function verifyPrice() {
    var check = document.getElementById('price').value;
    var minPrice = 1099;
    var maxPrice = 2999;
    if (Number(check) < minPrice) {
        document.getElementById("price").style.borderColor = 'red';
        document.getElementById("price").value = minPrice;
    } else if (Number(check) > maxPrice) {
        document.getElementById("price").value = maxPrice;
    } else {
        document.getElementById("price").style.borderColor = 'green';
    }
}