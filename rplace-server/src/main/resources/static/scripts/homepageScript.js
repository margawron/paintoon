

function resizeIframe(obj) {
    obj.style.height = "0"; // <---- Dunno how it works but it does resize correctly
    obj.style.height = obj.contentWindow.document.documentElement.scrollHeight + 'px';
}

function getCursorPosition(canvas,event) {
    var rect = canvas.getBoundingClientRect(), // abs. size of element
        scaleX = canvas.width / rect.width,    // relationship bitmap vs. element for X
        scaleY = canvas.height / rect.height;  // relationship bitmap vs. element for Y

    return {
        x: (event.clientX - rect.left) * scaleX,   // scale mouse coordinates after they have
        y: (event.clientY - rect.top) * scaleY     // been adjusted to be relative to element
    }
}

function sendPixelToServer(canvas, event) {
    var position = getCursorPosition(canvas, event);
    var serverMessage = {
        "x": position.x,
        "y": position.y,
        "red": 255,
        "green": 0,
        "blue": 0
    }
    const other_params = {
        headers : { "content-type" : "application/json; charset=UTF-8" },
        body : JSON.stringify(serverMessage),
        method : "POST",
        mode : "cors"
    };
    fetch("/image/set_pixel", other_params)
        .then(function (response) {
            if(response.ok){
                $('#messages').html('<div id="messagediv" class="alert alert-success margin-0-auto" role="alert">\n' +
                    '  Pixel set!\n' +
                    '</div>')
                setTimeout(function () {
                    $('#messagediv').remove();
                }, 10000);
            } else {
                $('#messages').html('<div id="messagediv" class="alert alert-warning margin-0-auto" role="alert">\n' +
                    '  Something went wrong\n' +
                    '</div>')
                setTimeout(function () {
                    $('#messagediv').remove();
                }, 10000);
            }
        }).then(function (e) {
        loadImage(canvas);
    })
        .catch(function (error) {
        console.log(error);
    });
}

function loadImage(canvas) {
    var ctx = canvas.getContext('2d');
    var image = new Image;
    image.onload = function(){
        canvas.width = image.width;
        canvas.height = image.height;
        canvas.style.height = (image.height > 600  ? image.height : 600) + "px";
        canvas.style.width = (image.width > 600 ? image.width : 600) + "px";
        ctx.imageSmoothingEnabled = false;
        ctx.drawImage(image,0,0, image.width, image.height,
                            0,0, canvas.width, canvas.height);
    };
    var timestamp = new Date().getTime();
    image.src = "./image" + "?_=" + timestamp;
}

function initPage() {
    var canvas = document.getElementById("canvas");
    canvas.addEventListener('mousedown', function (e){
        sendPixelToServer(canvas, e);
    });
    loadImage(canvas);
}