package com.hit.processes;

import java.util.List;

public class ProcessCycles {

	List<ProcessCycle> processCycles;
	
	ProcessCycles(List<ProcessCycle> processCycles) 
	{
		this.processCycles = processCycles;
	}
	
	public List<ProcessCycle> getProcessCycles()
	{
		return processCycles;
	}
	
	public void setProcessCycles(List<ProcessCycle> cycles)
	{
		processCycles = cycles;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(ProcessCycle cycle : processCycles)
		{
			sb.append("Sleep MS:" + cycle.getSleepMs() + " Data:" + cycle.getData() + "\n");
		}

		return sb.toString();
	}
}
