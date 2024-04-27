$(document).ready(function () {
    var resizeDiv = document.getElementById("resize");
    var horizontalResizeDiv = document.getElementById("horizontalResize");

    var outDiv = $("#out");
    var verticalResize = $("#resize");
    var horizontalResize = $("#horizontalResize");
    var bottomDiv = $("#bottom");
    var menuDiv = $("#menu");
    var varsDiv = $("#vars");
    var codeDiv = $(".code");

    var unlock = false;
    var horizontalUnlock = false;

    $(document).mousemove(function (e) {
        if (unlock) {
            var posX = getCoords(resizeDiv).left;
            var diffX = e.pageX - posX;

            var outWidth = outDiv.width();
            var varsWidth = varsDiv.width();

            if (posX < e.pageX && outWidth > 100)
                varsDiv.css("width", varsWidth + diffX);
            if (posX > e.pageX && varsWidth > 100)
                varsDiv.css("width", varsWidth + diffX);

            outDiv.css("width", bottomDiv.width() - varsDiv.width());
        }

        if (horizontalUnlock) {
            var posY = getCoords(horizontalResizeDiv).top;
            var diffY = e.pageY - posY;

            var codeHeight = codeDiv.height();
            var bottomHeight = bottomDiv.height();
            if (posY < e.pageY && bottomHeight > 100)
                codeDiv.css("height", codeDiv.height() + diffY);
            if (posY > e.pageY && codeHeight > 100)
                codeDiv.css("height", codeDiv.height() + diffY);

            bottomDiv.css("height", menuDiv.height() - (codeDiv.height() + 5));
        }
    });

    verticalResize.mousedown(function (e) {
        unlock = true;
    });

    horizontalResize.mousedown(function (e) {
        horizontalUnlock = true;
    });

    $(document).mousedown(function (e) {

    });

    $(document).mouseup(function (e) {
        unlock = false;
        horizontalUnlock = false;
    });

    function getCoords(element) {
        var box = element.getBoundingClientRect();

        return {
            top: box.top + pageYOffset,
            left: box.left + pageXOffset
        };
    }
    localStorage.setItem()

});