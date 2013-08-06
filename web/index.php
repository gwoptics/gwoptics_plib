<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<!--

The design has been created from a freely available design template:

Design by Free CSS Templates
http://www.freecsstemplates.org
Released for free under a Creative Commons Attribution 2.5 License

Title      : Emporium
Version    : 1.0
Released   : 20090222
Description: A two-column, fixed-width and lightweight template ideal for 1024x768 resolutions. Suitable for blogs and small websites.

-->
<?php set_include_path( get_include_path() . PATH_SEPARATOR . $_SERVER['DOCUMENT_ROOT'] ); ?>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>gwoptics : processing : gwoptics library</title>
<meta name="keywords" content="processing.org, library, gravitational waves, optics, simulations" />
<meta name="description" content="a library for the programming environment processing" />
<link rel="shortcut icon" href="favicon.ico"  type="image/x-icon" />
<link href="/css/p5.css" rel="stylesheet" type="text/css" />

</head>
<body>



<!-- include header -->
<? include("include/header.php") ?>
<!-- start page -->
<div id="page">
	<!-- start content -->
	<div id="content900">			
	<div id="p5_container">

		<!--
		<div id="p5_header">
			<h1>##yourLibrary##</h1>
		</div>
		
		<div id="p5_menu" class="p5_clear">
			<ul>
				<li><a href="#about">About</a> \ </li>
				<li><a href="#download">Download</a> \ </li>
				<li><a href="#download">Installation</a> \ </li>
				<li><a href="#examples">Examples</a> \ </li>
				<li><a href="reference/" target="_blank">Documentation</a></li>
			</ul>
		</div>

		-->

				<!-- <li><a href="#demos">Demos</a> \ </li> -->				
				<!-- <li><a href="#misc">Misc</a> \ </li> -->
				<!-- <li><a href="#images">Images</a> \ </li> -->

		
<!--		<div id="p5_content" class="p5_clear"> -->
			<div id="p5_content"> 
			
			<div id="p5_about">
			<h2>##yourLibrary##</h2>
			<p>
			A library by <a href="##yourLink##">##author##</a> for the programming environment <a href="http://www.processing.org" target="_blank">processing</a>. Last update, ##date##.
			</p>
			<p>
				This library provides a set of tools for drawing graphs in 2D or 3D. We have developed the library
				in order to be able to use processing for simple scientific simulations or data displays.
			  While processing itself makes the graphical output very easy, a good library for plotting
				functions or data was missing. So far, the content of the library directly reflect the need
				of our research group, but we hope that it might be useful for many other people. Any comment,
				feedback or suggestions are very welcome.
				</p>
			<p>
				You can find examples of what we are doing with processing in our
				<a href="http://www.gwoptics.org/processing/">list of processing sketches</a>.
				</p>

			</div>
			
			<!-- use the images/screenshots section. Activate the link to the misc section in the menu above. -->
			<div id="p5_images">
							<img src="gwoptics_p5lib_screenshot.png" alt="gwoptics library screenshot" />
			</div>
					
			
			
			<div id="p5_download" class="p5_clear">
			<h2>Download</h2>
			<p>
			Download ##yourLibrary## version ##versionNumber## in 
			<a href="./download/##yourLibrary##.zip">.zip format</a>.
			</p>
			<h2>Installation</h2>
			<p>
			Unzip and put the extracted ##yourLibrary## folder into the libraries folder of your processing sketches.
				Documentation and examples are included in the ##yourLibrary## folder.
			</p>
			</div>	
			
			
			<div id="p5_resources">
			<p><strong>Keywords</strong> ##keywords##</p>
			<p><strong>Documentation</strong> Have a look at the online documentation <a href="reference/" target="_blank">here</a>.
				A copy of the documentation is included in the .zip file as well.</p>		

				<p><strong>Source</strong>
		The source code of the library is included in the .zip file.
				<!-- The source code of ##yourLibrary## is available at <a href="##source:url##">##source:host##</a>, and its repository can be
				browsed <a href="##source:repository##" target="_blank">here</a>.
				-->
				</p>

			</div>
			
			<div id="p5_examples" class="p5_clear">
			<h2>Examples</h2>
			<p>Find a list of examples in the current distribution of ##yourLibrary##, or have a look at their
      source code by following the links below.</p>
			<ul>
			##examples##
			</ul>
			</div>
			
			
			<div id="p5_info">
			<h2>Tested</h2>
			<p>
			<!-- on which platform has the library been tested? -->
			<strong>Platform</strong> ##tested:platform##
			
			<!-- which processing version did you use for testing your library? -->
			<br /><strong>Processing</strong> ##tested:processingVersion##
			
			<!-- does your library depend on any other library or framework? -->
			<br /><strong>Dependencies</strong> ##tested:dependencies##
			</p>
			</div>
		
			
			<!-- use the demos section for a list of applets run in a browser. -->
			<!--
			<div id="p5_demos" class="p5_clear">
				<h2>demos</h2>
				<p>
				find a list of online applet demos below.
				
				<ul>
					<li><a href="./applets/demo/index.html">demo</a></li>
				</ul>
				</p>
			</div>
			-->
			
			<!-- use the misc section for other relevant information. Activate the link to the misc section in the menu above. -->
			<!--
			<div id="p5_misc" class="p5_clear">
				<p></p>
			</div>
			-->
			
			
			<br class="p5_clear" />
		</div>
		
		<div id="p5_footer">
			<p>by ##author##, ##date##.</p>
		</div>
	</div>

			</div>
	<!-- end content -->

<!-- include footer -->
	<? include("include/footer.php") ?>

</div>
<!-- end page -->
</body>
</html>