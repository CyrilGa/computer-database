//On load
$(function() {
    // Default: hide edit mode
    $(".editMode").hide();
    
    // Click on "selectall" box
    $("#selectall").click(function () {
        $('.cb').prop('checked', this.checked);
    });

    // Click on a checkbox
    $(".cb").click(function() {
        if ($(".cb").length == $(".cb:checked").length) {
            $("#selectall").prop("checked", true);
        } else {
            $("#selectall").prop("checked", false);
        }
        if ($(".cb:checked").length != 0) {
            $("#deleteSelected").enable();
        } else {
            $("#deleteSelected").disable();
        }
    });

    // Delete dashboard msg after 3 sec
    setTimeout(
    		  function() 
    		  {
    			 $("#dashboardMsg").hide();
    			 $("#errorMsg").hide();
    		  }, 5000);
    
    // Hide secondSearch
    $("#secondSearch").hide();
    $("#secondDiv").hide();
    
    if ($("#companyName").val().length > 0) {
    	$.fn.showSecondSearch();
    };

});


// Function setCheckboxValues
(function ( $ ) {

    $.fn.setCheckboxValues = function(formFieldName, checkboxFieldName) {

        var str = $('.' + checkboxFieldName + ':checked').map(function() {
            return this.value;
        }).get().join();
        
        $(this).attr('value',str);
        
        return this;
    };

}( jQuery ));

// Function toggleEditMode
(function ( $ ) {

    $.fn.toggleEditMode = function() {
        if($(".editMode").is(":visible")) {
            $(".editMode").hide();
            $("#editComputer").text(edit);
        }
        else {
            $(".editMode").show();
            $("#editComputer").text(view);
        }
        return this;
    };

}( jQuery ));


// Function delete selected: Asks for confirmation to delete selected computers, then submits it to the deleteForm
(function ( $ ) {
    $.fn.deleteSelected = function() {
        if (confirm(alertMsg)) { 
            $('#deleteForm input[name=selection]').setCheckboxValues('selection','cb');
            $('#deleteForm').submit();
        }
    };
}( jQuery ));



//Event handling
//Onkeydown
$(document).keydown(function(e) {

    switch (e.keyCode) {
        //DEL key
        case 46:
            if($(".editMode").is(":visible") && $(".cb:checked").length != 0) {
                $.fn.deleteSelected();
            }   
            break;
        //Y key (Y will switch to edit mode)
        case 89:
        	if(e.ctrlKey) {
        		$.fn.toggleEditMode();
        	}
            break;
    }
});


(function ( $ ) {

    $.fn.resetSearch = function() {
    	
    	$("#searchForm input[name=computerName]").val("")
    	$("#searchForm input[name=companyName]").val("")
    	$('#searchForm').submit();
        
    };

}( jQuery ));


(function ( $ ) {

    $.fn.showSecondSearch = function() {
    	
    	if($("#companyName").is(":visible")) {
    	    $("#secondDiv").hide();
            $("#companyName").hide();
        }
        else {
            $("#secondDiv").show();
            $("#companyName").show();
            }
    	$("#spanSecondSearch").toggleClass("glyphicon glyphicon-minus-sign").toggleClass("glyphicon glyphicon-plus-sign");;
    };

}( jQuery ));