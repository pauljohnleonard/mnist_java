package uk.ac.bath.ai.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import uk.ac.bath.ai.util.Data;


/**
 * 
 * Writes a set of Data into files in a directory.
 * 
 * @author pjl
 *
 */
public class DataWriter {
	
	
	
	public DataWriter(){
		
		
	}
	
	/*
	 * datas is set of data
	 * dir directory to save them to
	 * base is a base name for the file    e.g.  base_i.data
	 * 
	 */
	public void write(DataSource datas,File dir,String base) throws Exception{
		
		if (! dir.exists()) throw new IOException("please create "+dir.getAbsolutePath());

		
		for (int i=0;i<datas.getSize();i++){
			File filename = new File(dir,base+"_"+i+".data");
			FileOutputStream fos = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			Data d=datas.getData(i);
			out.writeObject(d);
			out.close();			
		}
		
		File filename = new File(dir,"dimensionIn.data");
		FileOutputStream fos = new FileOutputStream(filename);
		ObjectOutputStream out = new ObjectOutputStream(fos);
		out.writeObject(datas.getInDimension());
		out.close();
		
		filename = new File(dir,"dimensionOut.data");
		fos = new FileOutputStream(filename);
		out = new ObjectOutputStream(fos);
		out.writeObject(datas.getOutDimension());
		out.close();
		
		
	}

}
