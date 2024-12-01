package es.deusto.sd.group6.strava.external;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;

import es.deusto.sd.group6.strava.entity.AccountType;
import es.deusto.sd.group6.strava.entity.User;

public class FacebookServiceGateway implements ILoginServiceGateway{
	
	private String serverIP;
	private int serverPort;
	private static String DELIMITER = ":";
	
	public FacebookServiceGateway () {
		serverIP = "127.0.0.1";
		serverPort = 8082;
	}
	@Override
    public boolean validateUser(String email, String password) {
		String message = email+DELIMITER+password;
		String result = null;
		StringTokenizer tokenizer = null;
		
		try (Socket socket = new Socket(serverIP, serverPort);
			//Streams to send and receive information are created from the Socket
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
			
			//Send request (one String) to the server
			out.writeUTF(message);
			System.out.println(" - Sending data to '" + socket.getInetAddress().getHostAddress() + ":" + socket.getPort() + "' -> '" + message + "'");
			
			//Read response (one String) from the server
			result = in.readUTF();			
			System.out.println(" - Getting result from '" + socket.getInetAddress().getHostAddress() + ":" + socket.getPort() + "' -> '" + result + "'");
			tokenizer = new StringTokenizer(result, DELIMITER);

		} catch (UnknownHostException e) {
			System.err.println("# Trans. SocketClient: Socket error: " + e.getMessage());	
		} catch (EOFException e) {
			System.err.println("# Trans. SocketClient: EOF error: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("# Trans. SocketClient: IO error: " + e.getMessage());
		}		
		return (tokenizer.nextToken().equals("true")) ? true : false;
	}
	
}
