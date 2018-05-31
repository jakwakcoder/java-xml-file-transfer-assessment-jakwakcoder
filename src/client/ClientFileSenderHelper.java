package client;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

public class ClientFileSenderHelper implements Runnable {
String host;
int port;
String filename;


	public ClientFileSenderHelper(String host, int port, String filename) {
		this.host=host;
		this.port=port;
		this.filename=filename;
	}


	@Override
	public void run() {
		try (Socket socket = new Socket(host, port);
				// write to
				PrintWriter writeToServer = new PrintWriter(socket.getOutputStream(), true);
				//read line of results from server
				BufferedReader resultsFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {

			byte[] fileContents=null;
			//
			try {

				String filePath = "ClientFiles\\"+filename;
				File myFile = new File(filePath);
				fileContents= new byte[(int) myFile.length()];
		        Path path = Paths.get(filePath);
		        fileContents = Files.readAllBytes(path);
			} catch (Exception e) {
				e.getMessage();
				e.printStackTrace();
			}
			//
			
			String encoded = Base64.getEncoder().encodeToString(fileContents);
			//
	        FileMetaData metaDataObject = new FileMetaData("Jason Kwak", new SimpleDateFormat("yyyy-MM-dd").format(new Date()), filename, encoded);
			//
			
	        //marshaltime
	        //
			JAXBContext context = JAXBContext.newInstance(FileMetaData.class);
			Marshaller marshaller = context.createMarshaller();

			//sucessfully made a XML in string form from a file.
			StringWriter sw = new StringWriter();
			marshaller.marshal(metaDataObject, sw);
			String xmlString = sw.toString();

			String line="";

			Thread.sleep(3000);
			
			//print line to server
			writeToServer.println(xmlString);

			//replace String line with server results
			line = resultsFromServer.readLine();
			//print out new String line
			System.out.println(line);

		writeToServer.flush();

		} catch (Exception e) {
			System.out.println("Exception Message: " + e.getMessage());
			e.printStackTrace();
		}

	}

}
