package com.hit.driver;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.hit.algorithm.IAlgoCache;
import com.hit.algorithm.LFUAlgoCacheImpl;
import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.algorithm.SecondChanceAlgoCacheImpl;
import com.hit.controller.Controller;
import com.hit.controller.MMUController;
import com.hit.memoryunits.MemoryManagementUnit;
import com.hit.model.MMUModel;
import com.hit.processes.Process;
import com.hit.processes.ProcessCycles;
import com.hit.processes.RunConfiguration;
import com.hit.util.MMULogger;
import com.hit.view.MMUView;

public class MMUDriver {

	private static MMULogger _logger;
	private static final String CONFIG_FILE_NAME = "src/main/resources/Configuration/Configuration.json";
	public static void main(String[] args)
	{
		_logger = MMULogger.getInstance();
		
	
		CLI cli = new CLI(System.in, System.out);
		new Thread(cli).start();
	}
	
	public static void Start(String[] command) throws JsonIOException, JsonSyntaxException, FileNotFoundException
	{
		IAlgoCache<Long, Long> algo = null;
		int capacity = Integer.parseInt(command[1]);
		switch(command[0])
		{
			case "LRU":
			{
				algo = new LRUAlgoCacheImpl<>(capacity);
				break;
			}
			case "LFU":
			{
				algo = new LFUAlgoCacheImpl<>(capacity);
				break;
			}
			case "SS":
			{
				algo = new SecondChanceAlgoCacheImpl<>(capacity);
				break;
			}
			default:
				return;
		}
		_logger.write("RC:" + capacity, Level.INFO);
		MemoryManagementUnit mmu = new MemoryManagementUnit(capacity, algo);
		RunConfiguration runConfig = ReadConfiguraionFile();
		List<ProcessCycles> processCycles = runConfig.getProcessCycles();
		List<Process> processes = createProcesses(processCycles, mmu);
		_logger.write("PN:" + processes.size(),Level.INFO);
		MMUModel mdl = new MMUModel();
		MMUController controller = new MMUController(mdl,new MMUView());
		mdl.setConfiguration(processes);
		//runProcesses(processes);
	}
	
	private static void runProcesses(List<Process> processes)
	{
		ExecutorService exec = Executors.newCachedThreadPool(); 
		for(Process prc : processes)
		{
			exec.execute(prc);
		}
		
		exec.shutdown();
	}
	
	private static List<Process> createProcesses(List<ProcessCycles> cycles, MemoryManagementUnit mmu)
	{
		int i = 0;
		List<Process> processes = new ArrayList<Process>();
		for(ProcessCycles pCycles : cycles)
		{
			Process p = new Process(i++, mmu, pCycles);
			processes.add(p);
		}
		
		return processes;
	}
	
	private static RunConfiguration ReadConfiguraionFile() throws JsonIOException, JsonSyntaxException, FileNotFoundException
	{
		return new Gson().fromJson(new JsonReader(new FileReader(CONFIG_FILE_NAME)), RunConfiguration.class);
	}
}
