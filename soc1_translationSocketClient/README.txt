There is only one option for running this project, as it relies on standard input: you must use the command line.

For code revision and editing, import the project into Eclipse.

To run the project, open a command console and execute the command:
.\gradlew run.

By default, the socket client sends requests to port 9500.

If the server is running on a different port, you can easily change the client port with the following command:

.\gradlew run -Parg1=6000 

(Here, 6000 represents the port on which the socket server operates).

