$(document).ready(function() {
    
    $('input').on('blur keyup', function() {
        if ($("#myform").valid()) {
            $('#submit').prop('disabled', false);  
        } else {
            $('#submit').prop('disabled', 'disabled');
        }
    });
    
    $("#myform").validate({
        rules: {
            name: {
                required: true,
                minlength: 4
            }
        }
    });

});