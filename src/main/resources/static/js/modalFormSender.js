$('document').ready(function ($) {
    $('#createExamButton').on('click', function (event) {
        console.log("here");
        event.preventDefault();
        var href = $(this).attr('href');
        $.get(href, function (exam, status) {
            $('#title').val(exam.title);
            $('#password').val(exam.password);
        });
        console.log("here2");
        $('#createColloquiumModal').modal();
        console.log("here3");
    });
});

