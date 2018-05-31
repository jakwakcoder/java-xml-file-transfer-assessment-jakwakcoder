package client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class FileMetaData {

	private String username;
	private String date;
	private String fileName;
	private String fileContents;
	

	public FileMetaData(){}
	
	public FileMetaData(String username, String date, String fileName, String fileContents) {
		this.username=username;
		this.date=date;
		this.fileName=fileName;
		this.fileContents=fileContents;
	}
	
	//getters and setters
	public String getUsername(){return username;}
	public void setUsername(String username){this.username = username;}
	public String getDateFormat(){return date;}
	public void setDateFormat(String dateFormat){this.date = dateFormat;}
	public String getFileName(){return fileName;}
	public void setFileName(String fileName){this.fileName = fileName;}
	public String getFileContents(){return fileContents;}
	public void setFileContents(String fileContents){this.fileContents = fileContents;}


	

}
