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

    // var languageVal = document.getElementById("language").value;
    // var baseFilledText = document.getElementsByClassName("CodeMirror-line");
    //
    // if (languageVal === 'Java') {
    //     baseFilledText[0].innerText = 'import java.util.Scanner;';
    // }
    // if (languageVal === 'C') {
    //     baseFilledText[0].innerText = '#define _CRT_SECURE_NO_WARNINGS';
    // }

    // $("select").each(function () {
    //     var originalValue = $(this).val();
    //
    //     $(this).change(function () {
    //         var newOriginalValue = $(this).val();
    //
    //         if (newOriginalValue !== originalValue) {
    //             originalValue = newOriginalValue;
    //
    //             if (newOriginalValue === 'Java') {
    //
    //                 //$(".CodeMirror-code")[0].innerHTML = "<div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 21px;\">1</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\"><span class=\"cm-keyword\">import</span> <span class=\"cm-def\">java</span>.<span class=\"cm-variable\">util</span>.<span class=\"cm-property\">Scanner</span>;</span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 21px;\">2</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\"><span cm-text=\"\">&#8203;</span></span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 21px;\">3</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\"><span class=\"cm-variable\">public</span> <span class=\"cm-keyword\">class</span> <span class=\"cm-def\">ExecutionTest</span></span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 21px;\">4</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\">{</span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 21px;\">5</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\"><span class=\"cm-tab\" role=\"presentation\" cm-text=\"\t\">    </span><span class=\"cm-property\">public</span> <span class=\"cm-def\">static</span> <span class=\"cm-property\">void</span> <span class=\"cm-def\">main</span>(<span class=\"cm-def\">String</span>[] <span class=\"cm-property\">args</span>)</span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 21px;\">6</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\"><span class=\"cm-tab\" role=\"presentation\" cm-text=\"\t\">    </span>{</span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 21px;\">7</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\"><span class=\"cm-tab\" role=\"presentation\" cm-text=\"\t\">    </span><span class=\"cm-tab\" role=\"presentation\" cm-text=\"\t\">    </span><span class=\"cm-variable\">Scanner</span> <span class=\"cm-variable\">scanner</span> <span class=\"cm-operator\">=</span> <span class=\"cm-keyword\">new</span> <span class=\"cm-variable\">Scanner</span>(<span class=\"cm-variable\">System</span>.<span class=\"cm-property\">in</span>);</span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 21px;\">8</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\"><span class=\"cm-tab\" role=\"presentation\" cm-text=\"\t\">    </span><span class=\"cm-tab\" role=\"presentation\" cm-text=\"\t\">    </span><span class=\"cm-variable\">System</span>.<span class=\"cm-property\">out</span>.<span class=\"cm-property\">println</span>(<span class=\"cm-variable\">scanner</span>.<span class=\"cm-property\">nextInt</span>());</span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 21px;\">9</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\"><span class=\"cm-tab\" role=\"presentation\" cm-text=\"\t\">    </span><span class=\"cm-tab\" role=\"presentation\" cm-text=\"\t\">    </span><span class=\"cm-variable\">System</span>.<span class=\"cm-property\">out</span>.<span class=\"cm-property\">println</span>(<span class=\"cm-string\">\"first answer\"</span>);</span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 21px;\">10</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\"><span class=\"cm-tab\" role=\"presentation\" cm-text=\"\t\">    </span><span class=\"cm-tab\" role=\"presentation\" cm-text=\"\t\">    </span><span class=\"cm-variable\">System</span>.<span class=\"cm-property\">out</span>.<span class=\"cm-property\">println</span>(<span class=\"cm-variable\">scanner</span>.<span class=\"cm-property\">nextInt</span>());</span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 21px;\">11</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\"><span class=\"cm-tab\" role=\"presentation\" cm-text=\"\t\">    </span><span class=\"cm-tab\" role=\"presentation\" cm-text=\"\t\">    </span><span class=\"cm-variable\">System</span>.<span class=\"cm-property\">out</span>.<span class=\"cm-property\">println</span>(<span class=\"cm-string\">\"second answer\"</span>);</span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 21px;\">12</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\"><span class=\"cm-tab\" role=\"presentation\" cm-text=\"\t\">    </span>}</span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 21px;\">13</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\">}</span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 21px;\">14</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\"><span cm-text=\"\">&#8203;</span></span></pre></div>";
    //                 // baseFilledText[0].innerText = 'import java.util.Scanner;';
    //                 // baseFilledText.push('\t');
    //                 // //baseFilledText[1].innerText = ;
    //                 // baseFilledText[2].innerText = 'public class ExecutionTest';
    //                 // baseFilledText[3].innerText = '{';
    //                 // baseFilledText[4].innerText = '\tpublic static void main(String[] args)';
    //                 // baseFilledText[5].innerText = '\t{';
    //                 // baseFilledText[6].innerText = '\t\tScanner scanner = new Scanner(System.in);';
    //                 // baseFilledText[7].innerText = '\t\tSystem.out.println(scanner.nextInt());';
    //                 // baseFilledText[8].innerText = '\t\tSystem.out.println("first answer");';
    //                 // baseFilledText[9].innerText = '\t\tSystem.out.println(scanner.nextInt());';
    //                 // baseFilledText[10].innerText = '\t\tSystem.out.println("second answer");';
    //                 // baseFilledText[11].innerText = '\t}';
    //                 // baseFilledText[12].innerText = '}';
    //             }
    //             if (newOriginalValue === 'C') {
    //                 //baseFilledText[0].innerText = '#define _CRT_SECURE_NO_WARNINGS';
    //             }
    //         }
    //     });
    // });
});