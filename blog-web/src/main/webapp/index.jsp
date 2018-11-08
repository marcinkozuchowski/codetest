<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Blog page sandbox!</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bulma/0.7.2/css/bulma.min.css">
    <script defer src="https://use.fontawesome.com/releases/v5.3.1/js/all.js"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<script src="js/jquery-3.3.1.js"></script>
	<script src="js/blog.js"></script>
  </head>
  <body onload="init();">
	<section class="section">
    <div class="container is-fluid">
      
		<div class="tile is-ancestor">
		  <div class="tile is-parent is-vertical is-3">			
			  <div class="tile is-child is-vertical">
				<article class="is-child notification" id="submit-post-form">
				  <div class="field">
						<label class="label">Title</label>
						<div class="control">
							<input class="input" type="text" placeholder="" name="title" maxlength="255">
						</div>
					</div>
					<div class="field">
					  <label class="label">Message</label>
					  <div class="control">
						<textarea class="textarea" placeholder="" name="content"></textarea>
					  </div>
					</div>
					<div style="margin: 10px">
						<button class="button is-link is-outlined is-fullwidth" id="publish-button">
						  Publish post
						</button>
					</div>
					<div class="field is-grouped is-grouped-centered">
						<p id="warning_msg" class="help is-danger"></p>
					</div>
				</article>			 
			</div>
		  </div>
		  <div class="tile is-parent is-vertical">
			<section class="section" style="padding:0px;" id="posts-container">
				
			</section>
		</div>
		
    </div>
  </section>
  </body>
</html>

