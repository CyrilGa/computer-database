$(document).ready(function() {

	$('body').on('blur keyup click keydown', function() {
		if ($("#validForm").valid()) {
			$('#btnSubmit').prop('disabled', false);
		} else {
			$('#btnSubmit').prop('disabled', 'disabled');
		}
	});

	// add validation
	$("#validForm").validate({
		rules : {
			computerName: {
				required: true
			},
			introducedDate: {
				required: function(element){
					return $("#introducedTime").val().length > 0;
		        }
			},
			introducedTime: {
				required: function(element){
					return $("#introducedDate").val().length > 0;
		        }
			},
			discontinuedDate: {
				required: function(element){
					return $("#discontinuedTime").val().length > 0;
		        }
			},
			discontinuedTime: {
				required: function(element){
					return $("#discontinuedDate").val().length > 0;
		        }
			},
			companyName: {
				required: true
			}
		}
	});

});