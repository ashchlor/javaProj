package com.hit.util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class MMULogger {

	public final static String DEFAULT_FILE_NAME = "log.txt";
	private FileHandler handler;
	private static MMULogger instance = null;
	
	private MMULogger() {
		try {
			handler = new FileHandler(DEFAULT_FILE_NAME);
			handler.setFormatter(new OnlyMessageFormatter());
		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static MMULogger getInstance()  {
		if (instance == null) {
			instance = new MMULogger();
		}
		return instance;
	}
	
	public synchronized void write(String command, Level level){
		LogRecord log = new LogRecord(level, command + "\n");
		handler.publish(log);
	}
	
	public class OnlyMessageFormatter extends Formatter
	{
		public OnlyMessageFormatter()
		{
			super();
		}
		
		@Override
		public String format(final LogRecord record){
			return record.getMessage();
		}
	}
	
	
}
