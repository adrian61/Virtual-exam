$(document).ready(function () {
    let maxWidth = 0;
    const target = $(".equal-length");

    target.each(function () {
        var currentWidth = parseInt($(this).css("width"));
        console.log(currentWidth)
        if(currentWidth > maxWidth) {
            maxWidth = currentWidth;
        }
    });
    console.log(maxWidth);
    target.each(function () {
        $(this).css("width", maxWidth + 1 + "px");
    })
})