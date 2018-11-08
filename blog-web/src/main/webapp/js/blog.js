var editedPost;
var postsHolder = [];
	
function init() {
	var posting = $.get('blog-web/posts', null, null, 'json')
	.done(function(data, status, xhr) {
		loadPosts(data, status, xhr);
	})
	.fail(function(data) {
		var errorMsg = "<article class=\"message is-danger\"><div class=\"message-header\"><p>Error</p></div>"
		  + "<div class=\"message-body\">"
		  + "Can not connect to sever."
		  + "</div></article>";
		  
		$('#posts-container').append(errorMsg);
	});
	
	$( "#publish-button" ).click(function () {
		if (editedPost === undefined) {
			$.ajax({
				  type: "POST",
				  url: 'blog-web/posts',
				  data: JSON.stringify({'title': $( "#submit-post-form" ).find( "input[name='title']" ).val(), content: $( "#submit-post-form" ).find( "textarea[name='content']" ).val() }),
				  statusCode: {
				        201: loadNewPost,
				        405: warnInvalidData
				    },
				  contentType: 'application/json'
				});
		} else {
			$.ajax({
				  type: "PUT",
				  url: 'blog-web/posts',
				  data: JSON.stringify({'id': editedPost.id, 'title': $( "#submit-post-form" ).find( "input[name='title']" ).val(), content: $( "#submit-post-form" ).find( "textarea[name='content']" ).val() }),
				  statusCode: {
				        201: updatePost,
				        405: warnInvalidData
				    },
				  contentType: 'application/json'
				});
		}
	});
	
}

function loadSinglePosts(entry, status, xhr) {
	postsHolder[entry.id] = entry;
	var row = "<article class=\"message is-info\" name=\"post_" + entry.id + "\" onmouseover=\"showOperations(" + entry.id + ")\" onmouseout=\"hideOperations(" + entry.id + ")\">"
	+ "<div class=\"message-header\">"
	+ "<p name=\"post_title\">" + entry.title + "</p><div name=\"post_operations_" + entry.id + "\" style=\"visibility:hidden\">"
	+ "<i class=\"material-icons\" style=\"cursor: pointer; color:white; margin-right:5px;\" onclick=\"editPost(" + entry.id + ")\">mode_edit</i>"
	+ "<i class=\"material-icons\" style=\"cursor: pointer; color:white\" onclick=\"deletePost(" + entry.id + ")\">cancel</i>"
	+ "</div></div>"
	+  "<div class=\"message-body\" name=\"post_content\">"
	+   entry.content
	+  "</div>"
	+"</article>";
	$('#posts-container').append(row);
}

function loadPosts(data, status, xhr) {
	data.forEach(function(entry) {
		loadSinglePosts(entry);
	});
}

function warnInvalidData() {
	$( "#warning_msg" ).html("All fields has to be filled");
}

function updatePost(data, status, xhr) {
	editedPost = undefined;
	$( "#submit-post-form" ).find( "input[name='title']" ).val("");
	$( "#submit-post-form" ).find( "textarea[name='content']" ).val("");
	$( "#warning_msg" ).html("");
	$( "#publish-button" ).html("Publish post");
	
	$.get(xhr.getResponseHeader("Location"), null, null, 'json')
	.done(function(data, status, xhr) {
		postsHolder[data.id] = data;
		$("article[name='post_" + data.id + "']").find( "p[name='post_title']" ).html(data.title);; 
		$("article[name='post_" + data.id + "']").find( "div[name='post_content']" ).html(data.content); 
	});
}

function loadNewPost(data, status, xhr) {
	$( "#submit-post-form" ).find( "input[name='title']" ).val("");
	$( "#submit-post-form" ).find( "textarea[name='content']" ).val("");
	$( "#warning_msg" ).html("");
	
	$.get(xhr.getResponseHeader("Location"), null, null, 'json')
		.done(function(data, status, xhr) {
			loadSinglePosts(data, status, xhr);
		});
}

function showOperations(postId) {
	$("article[name='post_" + postId + "']").find( "div[name='post_operations_" + postId + "']" ).css('visibility', 'visible');
}

function hideOperations(postId) {
	$("article[name='post_" + postId + "']").find( "div[name='post_operations_" + postId + "']" ).css('visibility', 'hidden');
}

function editPost(postId) {
	topFunction();
	editedPost = postsHolder[postId];
	$( "#submit-post-form" ).find( "input[name='title']" ).val(postsHolder[postId].title);
	$( "#submit-post-form" ).find( "textarea[name='content']" ).val(postsHolder[postId].content);
	$( "#publish-button" ).html("Update post");
}

function deletePost(postId) {
	var jqxhr = $.ajax({
	    url: 'blog-web/posts/' + postId,
	    type: 'DELETE',
	    dataType: 'text'
	  });
	
	jqxhr.done(function(data, status, xhr) {
		delete postsHolder[postId];
		$("article[name='post_" + postId + "']").remove();
	});
	jqxhr.fail(function(data, status, xhr) {
		if (status == 404) {
			delete postsHolder[postId];
		 	$("article[name='post_" + postId + "']").remove();
		}
	});
}

function topFunction() {
    document.body.scrollTop = 0; // For Safari
    document.documentElement.scrollTop = 0; // For Chrome, Firefox, IE and Opera
} 