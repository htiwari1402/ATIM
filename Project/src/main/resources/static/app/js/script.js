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

function migrate()
{
	var mig_opt = $('#mig_opt').val();
	var src_domain = $('#src_domain').va();
	var tgt_domain = $('#tgt_domain').val();
	var src_repo = $('#src_repo').val();
	var tgt_repo = $('#tgt_repo').val();
	var dep_group = $('#dep_group').val();
	var emailGroup = $('#emailGroup').val();
}

function addEmail()
{
	var textval = $('#emailGroup').val();
	var currEmail=$('#email').val();
	textval = textval+','+currEmail;
	$('#emailGroup').val(textval);
}

$(document).ready(function(){
	$.post("getRepo",function(responseText)
			{
				$('#src_repo').html(responseText);
				$('#tgt_repo').html(responseText);
			});
	$.post("getDom",function(responseText)
			{
				$('#src_domain').html(responseText);
				$('#tgt_domain').html(responseText);
			});
	$.post("get_tkt_no",function(responseText)
			{
				$('#tkt_no_show').html(responseText);
				$('#tkt_no').val(responseText);
			});
});

function migrate()
{
	var control_param_text = $('#control_param_text').val();
	var postData = {
						"control_param_text":control_param_text
					};
	$.post("genXML",postData,function(response)
			{
				if(parseInt(response) == 1)
					{
						$('#mig_form').submit();
					}
			});
}