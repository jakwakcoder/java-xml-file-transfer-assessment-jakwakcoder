package server;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.Socket;
import java.util.Base64;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import client.FileMetaData;

public class ClientHandler implements Runnable {
	Socket clientSocket;

	public ClientHandler(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	@Override
	public void run() {
    	try (	InputStream is = clientSocket.getInputStream();
    			// write to
    			PrintWriter writeToClient = new PrintWriter(clientSocket.getOutputStream(), true);
    			BufferedReader resultsFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));){

    		//String from client
			String line=resultsFromClient.readLine();
		
			//unmarshal this String into an object
			FileMetaData File1= unmarshal(line);
			String usernameReceived = File1.getUsername();
			String fileDate = File1.getDateFormat();
			String filename = File1.getFileName();
			byte[] fileContents = Base64.getDecoder().decode(File1.getFileContents());

			//directory to put serverFiles
			String directoryToPutFile = "ServerFiles\\"+usernameReceived+"\\"+fileDate;
			new File(directoryToPutFile).mkdirs();
			
            //RECIEVING FILE-------------------------------------------------------------------------
          File test = new File(directoryToPutFile+"\\"+filename);
          //create the file
          test.createNewFile();
          //make fileOutputStream from created file
          FileOutputStream fos = new FileOutputStream(test);
          //make bufferedOutputStream from fileOutputStream
          BufferedOutputStream out = new BufferedOutputStream(fos);
          
          //from byteArray, send bytes to the fileOutputStream
    	  out.write(fileContents, 0, fileContents.length);
    		out.flush();
			
			//write Successful message to client
			writeToClient.println("Got Your Files =D");
			
			//for server to know
		System.out.println(usernameReceived+" sent "+filename);
			writeToClient.flush();
			
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
	  public static FileMetaData unmarshal(String xml) throws JAXBException {
		  JAXBContext jaxbContext = JAXBContext.newInstance(FileMetaData.class);
		  Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		  StringReader reader = new StringReader(xml);
		  FileMetaData recievedObject = (FileMetaData) unmarshaller.unmarshal(reader);
		return recievedObject;
		  }

}
