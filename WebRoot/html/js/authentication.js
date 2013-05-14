(function($) {
	$.fn.isLogin = function(apId) {
		var CurrentUser;
		$.ajax({
					type : "POST",
					url : "/user/valid/",
					dataType : "json",
					async : false,
					success : function(responseText) {
						if (responseText.success == false) {
							$(document.body).empty();
							Msg.Alert(responseText.message, 300, 100,
									function() {
										window.location.href = responseText.root+'?apId='+apId;
									});
						} else {
							CurrentUser= responseText;
						}
					}
				});
	 return CurrentUser;
	}
})(jQuery);