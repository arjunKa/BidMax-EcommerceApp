Docker:
---------------------------------------------------------------------------
Docker image is on my hub: https://hub.docker.com/r/arjunka/bezos-app
Use the latest image.
You can run my build using: docker run --rm -it -p 8080:8080 --name bezos-app arjunka/bezos-app
When running the image in a container, make sure you go to the url: http://localhost:8080/Bezos/
---------------------------------------------------------------------------
Eclipse:
---------------------------------------------------------------------------
You can also import project into eclipse as a WAR file. Make sure Apache tomcat 10.1v is the target runtime, and all web libraries are unselected on the "WAR Import: Web libraries" page, so that they will be added in project folder automatically. This is a maven project.

NOTE:
All test postman scripts are in folder: TestScripts
Report is titled: EECS4413_Asg3_document.pdf