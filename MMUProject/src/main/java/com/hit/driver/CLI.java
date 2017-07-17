package com.hit.driver;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.hit.algorithm.IAlgoCache;
import com.hit.algorithm.LFUAlgoCacheImpl;
import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.algorithm.SecondChanceAlgoCacheImpl;
import com.hit.memoryunits.MemoryManagementUnit;
import com.hit.processes.Process;
import com.hit.processes.ProcessCycles;
import com.hit.processes.RunConfiguration;
import com.hit.util.MMULogger;

public class CLI  extends Observable implements Runnable {

	MMULogger _logger;
	InputStream _in;
	OutputStream _out;
	
	
	public CLI(InputStream in, OutputStream out){
		_in = in;
		_out = out;
		_logger = MMULogger.getInstance();
	}
	
	
	
	
	public void write(String string) throws IOException
	{
		_out.write(string.getBytes());
	}




	@Override
	public void run() {
		String inputText = "";
		BufferedReader now = new BufferedReader(new InputStreamReader(_in));
		try {
			write("Please enter start\n");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			_logger.write(e1.toString(), Level.SEVERE);
			
		}
		
		while(inputText != "stop")
		{
			
			try {
				inputText = now.readLine();
				if(inputText.equals("start"))
				{
					write("please enter required algorithm and RAM capacity\n");
					inputText = now.readLine();
					String[] spl = inputText.split(" ");
					if(spl.length == 3)
					{
						ArrayList<String> configuration = new ArrayList<>();
						configuration.add(spl[0]);
						configuration.add(spl[1]);
						configuration.add(spl[2]);
						setChanged();
						notifyObservers(configuration);
					}
					else
					{
						write("Invalid input\n");
					}
				}
				else if(!inputText.equals("stop"))
				{
					write("Invalid command");
				}
			} catch (IOException e) {
				_logger.write("Error has occured. Please try again.\n", Level.SEVERE);
				e.printStackTrace();
			}
		}
		
	}
	
	
}
