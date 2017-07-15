package com.hit.processes;

import java.util.List;

public class RunConfiguration {
	
	List<ProcessCycles> processesCycles;
	
	RunConfiguration(List<ProcessCycles> processesCycles) 
	{
		this.processesCycles = processesCycles;
	}
	
	public List<ProcessCycles> getProcessCycles()
	{
		return processesCycles;
	}
	
	public void setProcessCycles(List<ProcessCycles> cycles)
	{
		processesCycles = cycles;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(ProcessCycles cycle : processesCycles)
		{
			sb.append(cycle.toString());
		}

		return sb.toString();
	}
}
