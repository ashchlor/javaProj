package com.hit.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.hit.processes.Process;

public class MMUModel extends Observable implements Model{
	
	public int numProcesses;
	public int ramCapacity;
	private List<Process> _configuration;
	
	public MMUModel() {
		//TODO Auto-generated constructor stub
	}
	
	public void setConfiguration(List<Process> configuration)
	{
		_configuration = configuration;
		List<String> prcIds = new ArrayList<String>();
		for(Process p : configuration)
			prcIds.add(Integer.toString(p.getId()));
		
		setChanged();
		notifyObservers(prcIds);
	}
	
	    void runProcesses(List<Integer> prcId)
	{
		ExecutorService exec = Executors.newCachedThreadPool(); 
		for(Process prc : _configuration)
		{
			if(prcId.contains(prc.getId()))
				exec.execute(prc);
		}
		
		exec.shutdown();
	}
	
	private void runAllProcesses()
	{
		ExecutorService exec = Executors.newCachedThreadPool(); 
		for(Process prc : _configuration)
		{
			exec.execute(prc);
		}
		
		exec.shutdown();
	}
	
	public void Play(List<Integer> ids){
		runProcesses(ids);
	}
	
	public void PlayAll(){
		runAllProcesses();
	}
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		setChanged();
		notifyObservers("PF:0");
		
	}

}
