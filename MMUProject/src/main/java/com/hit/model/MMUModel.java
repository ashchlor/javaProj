package com.hit.model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
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
import com.hit.memoryunits.MemoryManagementUnit;
import com.hit.memoryunits.Page;
import com.hit.processes.Process;
import com.hit.processes.ProcessCycles;
import com.hit.processes.RunConfiguration;
import com.hit.util.MMULogger;

public class MMUModel extends Observable implements Model, Observer{
	MMULogger _logger;
	public int numProcesses;
	public int ramCapacity;
	private List<Process> _processes;
	private static final String CONFIG_FILE_NAME = "src/main/resources/Configuration/Configuration.json";
	
	public MMUModel() {
		_logger = MMULogger.getInstance();
	}
	
	public void setConfiguration(List<String> commands) throws JsonIOException, JsonSyntaxException, FileNotFoundException
	{
		IAlgoCache<Long, Long> algo = null;
		int capacity = Integer.parseInt(commands.get(1));
		switch(commands.get(0))
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
		mmu.addObserver(this);
		RunConfiguration runConfig = ReadConfiguraionFile();
		List<ProcessCycles> processCycles = runConfig.getProcessCycles();
		_processes = createProcesses(processCycles, mmu);
		_logger.write("PN:" + _processes.size(),Level.INFO);
		
		List<String> prcIds = new ArrayList<String>();
		for(Process p : _processes)
			prcIds.add(Integer.toString(p.getId()));
		
		setChanged();
		notifyObservers("RS:"+ capacity);
		
		setChanged();
		notifyObservers(prcIds);
	}
	
	private List<Process> createProcesses(List<ProcessCycles> cycles, MemoryManagementUnit mmu)
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
	
	private RunConfiguration ReadConfiguraionFile() throws JsonIOException, JsonSyntaxException, FileNotFoundException
	{
		return new Gson().fromJson(new JsonReader(new FileReader(CONFIG_FILE_NAME)), RunConfiguration.class);
	}
	
	public void runProcesses(List<String> prcId)
	{
		ExecutorService exec = Executors.newCachedThreadPool(); 
		for(Process prc : _processes)
		{
			if(prcId.contains(Integer.toString(prc.getId())))
				exec.execute(prc);
		}
		
		exec.shutdown();
	}
	
	public void runAllProcesses()
	{
		ExecutorService exec = Executors.newCachedThreadPool(); 
		for(Process prc : _processes)
		{
			exec.execute(prc);
		}
		
		exec.shutdown();
		
	}
		
	@Override
	public void start() {
		// TODO Auto-generated method stub
	
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg0 instanceof MemoryManagementUnit)
		{
			if(arg1 instanceof Page[])
			{
				setChanged();
				notifyObservers(arg1);
			}
			else if(arg1 instanceof String)
			{
				setChanged();
				notifyObservers(arg1);
			}
		}
	}

}
