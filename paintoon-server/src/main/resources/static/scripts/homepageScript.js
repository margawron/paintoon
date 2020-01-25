var stompClient = null;
var canvas = null;
var context = null;

var redColorPicker = null;
var greenColorPicker = null;
var blueColorPicker = null;
var colorToBeSet = null;
var activeSessionHistory = null;

function resizeIframe(obj) {
    obj.style.height = "0"; // <---- Dunno how it works but it does resize correctly
    obj.style.height = obj.contentWindow.document.documentElement.scrollHeight + 'px';
}

function initPage() {
    canvas = document.getElementById("canvas");
    canvas.addEventListener('mousedown', function (e) {
        sendPixelToServer(canvas, e);
    });
    context = canvas.getContext('2d');
    setupWebSocketConnection();
    loadImage(canvas);
    activeSessionHistory = [];
    $(document).ready(function () {
        redColorPicker = $('#redComponent');
        greenColorPicker = $('#greenComponent');
        blueColorPicker = $('#blueComponent');
        colorToBeSet = JSON.parse(localStorage.getItem("colors"));
        if (colorToBeSet == null) {
            colorToBeSet = ["ff", "00", "ff"];
        }
        redColorPicker.val(colorToBeSet[0]);
        greenColorPicker.val(colorToBeSet[1]);
        blueColorPicker.val(colorToBeSet[2]);
        redColorPicker.on("change paste keyup", function () {
            onColorPickerChange();
        });
        greenColorPicker.on("change paste keyup", function () {
            onColorPickerChange();
        });
        blueColorPicker.on("change paste keyup", function () {
            onColorPickerChange();
        });

        setColor(colorToBeSet[0], colorToBeSet[1], colorToBeSet[2]);
    })

}

function sendPixelToServer(canvas, event) {
    var position = getCursorPosition(canvas, event);
    var serverMessage = {
        "x": position.x,
        "y": position.y,
        "red": parseInt(colorToBeSet[0], 16),
        "green": parseInt(colorToBeSet[1], 16),
        "blue": parseInt(colorToBeSet[2], 16)
    };
    const other_params = {
        headers: {"content-type": "application/json; charset=UTF-8"},
        body: JSON.stringify(serverMessage),
        method: "POST",
        mode: "cors"
    };
    fetch("/image/set_pixel", other_params)
        .then(function (response) {
            if (response.ok) {
                showMessage("Pixel set!", true);
            } else {
                showMessage("Something went wrong", false);
                console.log(response);
            }
        }).then(function (e) {
    })
        .catch(function (error) {
            console.log(error);
        });
}

function getCursorPosition(canvas, event) {
    var rect = canvas.getBoundingClientRect(), // abs. size of element
        scaleX = canvas.width / rect.width,    // relationship bitmap vs. element for X
        scaleY = canvas.height / rect.height;  // relationship bitmap vs. element for Y

    return {
        x: (event.clientX - rect.left) * scaleX,   // scale mouse coordinates after they have
        y: (event.clientY - rect.top) * scaleY     // been adjusted to be relative to element
    };
}

function showMessage(message, isSuccess) {
    $('#messages').html('<div id="messagediv" class="alert ' + (isSuccess ? "alert-success" : "alert-warning") + ' margin-0-auto" role="alert">\n' +
        '  ' + message + '\n' +
        '</div>');
    setTimeout(function () {
        $('#messagediv').remove();
    }, 10000);
}

function setupWebSocketConnection() {
    var socket = new SockJS("/websocket");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected ' + frame);
        stompClient.subscribe('/topic/broadcast', function (payload) {
            processPayload(JSON.parse(payload.body));
        });
    });
}

function processPayload(payload) {
    /**
     * Payload model
     * {
     *     username: String
     *     x: number,
     *     y: number,
     *     red: number,
     *     green: number,
     *     blue: number
     * }
     */
    reloadHistory(payload);
    putPixel(canvas.getContext('2d'),
        payload.x, payload.y,
        payload.red, payload.green, payload.blue);
}

function putPixel(ctx, x, y, r, g, b) {
    var id = ctx.createImageData(1, 1);
    var d = id.data;
    d[0] = r;
    d[1] = g;
    d[2] = b;
    d[3] = 255;
    ctx.putImageData(id, x, y);
}

function reloadHistory(payload) {
    var historyFrame = $("#historyFrame");
    if (activeSessionHistory.length >= 17) {
        activeSessionHistory.pop();
        // Needs double remove (for <span> tag and for <br /> tag)
        historyFrame.contents().last().remove();
        historyFrame.contents().last().remove();
    }
    activeSessionHistory.unshift(payload);
    var hexedColor = '#' + hex(payload.red) + hex(payload.green) + hex(payload.blue);
    console.log(historyFrame.contents());
    historyFrame.prepend(
        '<span style="color:' + hexedColor + '">' + payload.username + ': ' + payload.x + 'px ' + payload.y + 'px</span><br />'
    );
}

function hex(color) {
    return padZero(color.toString(16));
}


function loadImage(canvas) {
    var ctx = canvas.getContext('2d');
    var image = new Image;
    image.onload = function () {
        canvas.width = image.width;
        canvas.height = image.height;
        canvas.style.height = (image.height > 600 ? image.height : 600) + "px";
        canvas.style.width = (image.width > 600 ? image.width : 600) + "px";
        ctx.imageSmoothingEnabled = false;
        ctx.drawImage(image, 0, 0, image.width, image.height,
            0, 0, canvas.width, canvas.height);
    };
    var timestamp = new Date().getTime();
    image.src = "./image" + "?_=" + timestamp;
}

function onColorPickerChange() {
    var red = redColorPicker.val().length === 2 ? redColorPicker.val() : "ff";
    var green = greenColorPicker.val().length === 2 ? greenColorPicker.val() : "ff";
    var blue = blueColorPicker.val().length === 2 ? blueColorPicker.val() : "ff";
    setColor(red, green, blue);
}

function setColor(red, green, blue) {
    var bodyColor = red + green + blue;
    var r = (255 - parseInt(red, 16)).toString(16),
        g = (255 - parseInt(green, 16)).toString(16),
        b = (255 - parseInt(blue, 16)).toString(16);
    var textColor = padZero(r) + padZero(g) + padZero(b);
    colorToBeSet = [red, green, blue];
    localStorage.setItem("colors", JSON.stringify(colorToBeSet));
    $('body').css({
        'background-color': '#' + bodyColor,
        'color': '#' + textColor
    });
}

function padZero(str, len) {
    len = len || 2;
    var zeros = new Array(len).join('0');
    return (zeros + str).slice(-len);
}