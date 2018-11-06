
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Blog page sandbox</title>

<script src="js/jquery-3.3.1.min.js"></script>
<link rel="stylesheet" type="text/css" media="all" href="css/styles.css">
<script>
</script>

</head>

<body>

<div class="form-style-6">
<h1>Write a new post</h1>
<form id = "submitPostForm">
<input type="text" name="title" placeholder="Post title" />
<textarea name="content" placeholder="Post content"></textarea>
<input type="button" value="Publish" id="publishButton"/>
</form>
</div>
        
<script type="text/javascript">
	
	var posting = $.get('service/posts', null, null, 'json');
	console.log(posting);
	
	$( "#publishButton" ).click(function () {
		
		$.ajax({
			  type: "POST",
			  url: 'service/posts',
			  data: JSON.stringify({'title': $( "#submitPostForm" ).find( "input[name='title']" ).val(), content: $( "#submitPostForm" ).find( "textarea[name='content']" ).val() }),
			  success: function( data ) {
				  alert( "Data Loaded: " + data );
				},
				contentType: 'application/json',
				dataType: 'json'
			});
	});

</script>
</body>

</html>