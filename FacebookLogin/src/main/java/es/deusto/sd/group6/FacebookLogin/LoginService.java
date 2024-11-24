package es.deusto.sd.group6.FacebookLogin;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class LoginService extends Thread {
	
	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;
	private Socket socket;
	
	public LoginService(Socket socket) {
		try {
			this.socket = socket;
			this.dataInputStream = new DataInputStream(socket.getInputStream());
			this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
			this.start();
		} catch (IOException ex) {
			System.err.println("Error while creating the data input stream: " + ex.getMessage());
		}		
	}
	
	@Override
	public void run() {
		try {
			String message = dataInputStream.readUTF();
			System.out.println("Received message: " + message);
			String response = checkUser(message);
			dataOutputStream.writeUTF(response);
		} catch (IOException e) {
			System.err.println("Error while reading the message: " + e.getMessage());
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				System.err.println("Error while closing the socket: " + e.getMessage());
			}
		}
	}
	
	private String checkUser(String message) {
		String username = message.split(":")[0];
		String password = message.split(":")[1];
		String realPassword = UserStorage.getInstance().getPassword(username);
		if (realPassword == null)
			return "not found";
		else if (password.equals(UserStorage.getInstance().getPassword(username)))
			return "true";
		else
			return "false";
	}

}
