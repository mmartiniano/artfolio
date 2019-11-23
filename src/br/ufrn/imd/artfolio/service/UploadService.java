package br.ufrn.imd.artfolio.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import javax.servlet.http.Part;
import org.apache.commons.io.FilenameUtils;


		
public class UploadService implements Serializable {
	
	private Part source;
	
	public UploadService(Part source) {
		this.source = source;
	}

	
	public String uploadFile(String path, String rename) {
		try (InputStream input = source.getInputStream()) {
			 String fileName = source.getSubmittedFileName();
			 String format = FilenameUtils.getExtension(fileName);
			 
			 String newName = rename + "." + format;
			 
			 File previousFile = new File(path, newName);
			 
			 if(format.equals("png") || format.equals("jpg") || format.equals("jpeg")) {
				 if(previousFile.exists()) previousFile.delete();
				 Files.copy(input, new File(path, newName).toPath());
				 return rename + "." + format;
			 } else {
				 return null;
			 }
			 
	     }
	     catch (IOException e) {
	        return null;
	     }
	 }

	public Part getSource() {
		return source;
	}

	public void setSource(Part source) {
		this.source = source;
	}
}
