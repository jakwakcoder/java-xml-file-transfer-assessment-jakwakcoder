package server;

import java.net.ServerSocket;
import java.net.Socket;


public class MyServer {

	public static void main(String[] args) {

		int portNumber = 13663;
		try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
			serverSocket.setReuseAddress(true);
			

			while (true) {
				Socket clientSocket = serverSocket.accept();
				System.out.println("Accepted connection from : " + clientSocket);
				new Thread(new ClientHandler(clientSocket)).start();
			}

		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		}

	}

}
