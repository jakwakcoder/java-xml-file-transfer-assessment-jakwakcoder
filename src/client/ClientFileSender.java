package client;

import java.io.File;

public class ClientFileSender {
	public static void main(String[] args) {
		
		String host = "localhost";
		int port = 13663;
		
//		FileArray of all the clients files
	    String [] listFilesArray = listFiles("ClientFiles").split(" ");
		
		for(int i =0; i<listFilesArray.length; i++) {
			new Thread(new ClientFileSenderHelper(host, port, listFilesArray[i])).start();
		}
	}
	
    public static String listFiles(String directoryName){
    	String allFiles = "";
        File directory = new File(directoryName);
        //get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList){
            if (file.isFile()){
            	allFiles +=file.getName()+" ";
           //     System.out.println(file.getName());
            }
        }
        return allFiles.substring(0, allFiles.length()-1);
    }
    

	
}
