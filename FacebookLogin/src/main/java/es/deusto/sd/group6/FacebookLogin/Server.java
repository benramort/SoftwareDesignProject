package es.deusto.sd.group6.FacebookLogin;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
	public static void main(String[] args) {
		if (args.length < 1) {
			System.err.println("Port number not specified");
			System.exit(1);
		}
		
		int port = Integer.parseInt(args[0]);
		
		UserStorage.getInstance().addUser("user1", "password1");
		UserStorage.getInstance().addUser("user2", "password2");
		UserStorage.getInstance().addUser("user3", "password3");
		
		try (ServerSocket serverSocket = new ServerSocket(port)) {
		
		System.out.println("Server started on port " + port);
			while(true) {
				new LoginService(serverSocket.accept());
				System.out.println("New client connected");
			}
			
		} catch (IOException ex) {
			System.err.println("Error while creating the server socket: " + ex.getMessage());
		}
		
	}
}
