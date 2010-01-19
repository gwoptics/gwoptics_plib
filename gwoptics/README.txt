
Steps to get gwoptics library source code setup up in eclipse.

	- Ensure JDK 1.5 was installed
	- Open eclipse and set the workspace to the gwoptics_p5lib trunk folder 
	- Add JDK 1.5 to the list of installed JRE's in eclipse found from the menu bar in Window->Preferences->Java->installed JREs
	- Then select New->Java project from right clicking the package explorer view. Don’t add project name.
	- Set the Contents section to 'Create project from existing source' and choose the gwoptics folder in trunk. Eclipse will automatically name project 'gwoptics'
	- Choose to use JDK 1.5 as project specific JRE.
	- Click 'Next'. Ensure src, examples, libs and resources folders have been included. Remove 'resource/code' source code folder if present, as its not needed.
	- Choose 'Libraries' tab and check that core.jar and guicomponents.jar from the libs folder are included, if not add them.
	- Click finish.
	- From the package explorer, find the resources folder and make a copy of build-base.xml and rename to build.xml. Open this new build file and edit the section
		<!-- Alter this property with the location of the output directory required -->
			<property name="outputDir" location=""/>
	- Add build.xml to your Ant view to build library
