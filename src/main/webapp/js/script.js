// $(document).ready(function () {
//     var p = $("#vars");
//     var d = $("#out");
//     var r = $("#resize");
//     var c = $("#container");
//
//     var curr_width = p.width();
//     var unlock = false;
//
//     $(document).mousemove(function (e) {
//         var change = curr_width + (p.width() - curr_width);
//
//         if (unlock) {
//             if (change > 50) {
//                 p.css("width", change);
//                 d.css("margin-left", change);
//             } else {
//                 p.css("width", 51);
//             }
//         }
//     });
//
//     r.mousedown(function (e) {
//         curr_width = p.width();
//         unlock = true;
//         r.css("background-color", "rgba(0, 0, 0, 0.2)");
//     });
//
//     $(document).mousedown(function (e) {
//         if (unlock) {
//             e.preventDefault();
//         }
//     });
//
//     $(document).mouseup(function (e) {
//         unlock = false;
//         r.css("background-color", "rgba(0, 0, 0, 0.1)");
//     });
// });