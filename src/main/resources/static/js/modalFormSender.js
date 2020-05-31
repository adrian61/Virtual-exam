$('document').ready(function ($) {
    $('#createExamButton').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $.get(href, function (exam, status) {
            $('#title').val(exam.title);
            $('#password').val(exam.password);
        });
        $('#createColloquiumModal').modal();
    });
});

