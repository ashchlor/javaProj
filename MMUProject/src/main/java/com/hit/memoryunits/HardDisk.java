package com.hit.memoryunits;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;

import com.hit.util.MMULogger;

public class HardDisk {
	
	static final String DEFAULT_FILE_NAME = "src/main/resources/harddisk/HD.txt";
	private static HardDisk _instance;
	private static final int HD_SIZE = 5000;
	private MMULogger _logger;
	private static HashMap<Long, Page<byte[]>> _pagesMap = new HashMap<>(HD_SIZE);;
	
	private HardDisk() {
		_logger = MMULogger.getInstance(); 
	}
	
	public static HardDisk getInstace() throws FileNotFoundException, IOException
	{
		if(_instance == null)
		{
			writeHd();
			_instance = new HardDisk();
		}
		
		return _instance;
	}
	
	
	private static void writeHd() throws java.io.FileNotFoundException,  java.io.IOException{
		for(int i=1;i<100;i++)
		{
			
			Page pg = new Page<byte[]>(i, "ff".getBytes());
			_pagesMap.put((long)i,pg);
		}
		BufferedWriter wr = new BufferedWriter( new FileWriter(DEFAULT_FILE_NAME));
		wr.write(_pagesMap.toString());
		wr.close();
	}
	

	public Page<byte[]> pageFault(long pageId) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		BufferedWriter wr = null;
		try
		{
			_logger.write("PF:" + pageId, Level.INFO);
			wr = new BufferedWriter( new FileWriter(DEFAULT_FILE_NAME));
			wr.write(_pagesMap.toString());
			
			if(_pagesMap.containsKey(pageId))
			{
				return _pagesMap.get(pageId);
			}
		}
		
		catch(Exception e)
		{
			MMULogger.getInstance().write("Failed to write pages to HD file", Level.SEVERE);
		}
		finally
		{
			wr.close();
		}
		
		
		return null;
	}
	
	public Page<byte[]> pageReplacement(Page<byte[]> moveToHdPage,long moveToRamId) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		_logger.write("PR:MTH " + moveToHdPage.getId() + " MTR " + moveToRamId, Level.INFO);
		_pagesMap.put(moveToHdPage.getId(), moveToHdPage);
		
		return pageFault(moveToRamId);
	}
	
}
