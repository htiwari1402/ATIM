function login()
{
	var userName = $('#username').val();
	var password = $('#password').val();
	/*$.ajax({
	    type: 'POST',
	    url: '/form/',
	    data: '{"username":userName}', // or JSON.stringify ({name: 'jonas'}),
	    success: function(data) { alert('data: ' + data); },
	    contentType: "application/json",
	    dataType: 'json'
	});*/
	$.post(
			"auth?username="+userName+"&password="+password,
			function(responseText)
			{
				if(parseInt(responseText) == 1)
					{
						window.location.href ="migration";
					}
				else
					{
						$('#error').html("Invalid username and password !!!");
					}
			}
			);
	
}