

function resizeIframe(obj) {
    obj.style.height = "0"; // <---- Dunno how it works but it does resize correctly
    obj.style.height = obj.contentWindow.document.documentElement.scrollHeight + 'px';
}

function getCursorPosition(canvas,event) {
    var x, y;

    canoffset = $(canvas).offset();
    x = event.clientX + document.body.scrollLeft + document.documentElement.scrollLeft - Math.floor(canoffset.left);
    y = event.clientY + document.body.scrollTop + document.documentElement.scrollTop - Math.floor(canoffset.top) + 1;

    return {
        "x":x,
        "y":y
    };
}

function sendPixelToServer(canvas, event) {
    var position = getCursorPosition(canvas, event);
    console.log(position);
}

function initCanvas() {
    var canvas = document.getElementById("canvas");
    canvas.addEventListener('mousedown', function (e){
        sendPixelToServer(canvas, e);
    });

    var ctx = canvas.getContext('2d');
    var image = new Image;
    image.onload = function(){
        canvas.style.height = image.height + "px";
        canvas.style.width = image.width + "px";
        ctx.drawImage(image,0,0);
        console.log("draw image");
    };
    image.src = "./image";

}