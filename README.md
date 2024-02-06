Docker Installation:
---------------------------------------------------------------------------
Docker image is on my hub: https://hub.docker.com/r/arjunka/bezos-app
Use the latest image.
You can run my build using: docker run --rm -it -p 8080:8080 --name bezos-app arjunka/bezos-app
When running the image in a container, make sure you go to the url (to access site): http://localhost:8080/Bezos/
---------------------------------------------------------------------------
Eclipse Installation:
---------------------------------------------------------------------------
You can also import project into eclipse as a WAR file. 
The file is in root directory as Bezos.war.
Make sure Apache tomcat 10.1v is the target runtime, and all web libraries are unselected on the "WAR Import: Web libraries" page, so that they will be added in project folder automatically. This is a maven, dynamic web project project.
---------------------------------------------------------------------------
Zip File Eclipse Installation:
---------------------------------------------------------------------------
The project is also stored as a ZIP file. 
The file is in root directory as Bezos.zip. Inside is the "Bezos" project folder and "Servers" folder which contains the TomCat Server configuration.
The project is a Maven dynamic web project.

Video Demo:
https://www.youtube.com/watch?v=cfCOQieQubs

--------------------------------
Postman Scripts are in TestScripts/ folder