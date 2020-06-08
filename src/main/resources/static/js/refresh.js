window.setInterval(function () {
    var model = {title: $("#exam-name").html()};
    $.get("time-left", model, function (fragment) { // get from controller
        $("#time-left").replaceWith(fragment); // update snippet of page
    });
}, 1000);
