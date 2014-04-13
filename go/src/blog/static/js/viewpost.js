(function($) {
    $(document).ready(function(){
	$('#addcomment').click(addComment)
    });
    
    function addComment(e){
	e.preventDefault();
	$('#addcomment').button('loading');
	var data = {
	    "comment": $('#commentbody').val()

	};
	$.ajax({
	    type:"POST",
	    dataType: "json",
	    url: $('#commentform').attr("mydata"),
	    data: data,
	    success: success
	});
	//$.post($('#commentform').attr("mydata"), data, success);
    }
    function success(data){
	$('#addcomment').button('reset');
	$('#commentbody').val("");
	$('#commentlist').append($("<div>").addClass("row").
				 append($("<p>").text("comment: " + data.Body)));
    }
})(jQuery);
