package com.hit.processes;

import java.util.List;
import java.util.logging.Level;

import com.hit.memoryunits.MemoryManagementUnit;
import com.hit.memoryunits.Page;
import com.hit.util.MMULogger;

public class Process implements Runnable {

	int _id;
	MemoryManagementUnit _mmu;
	ProcessCycles _processCycles;
	
	public Process(int id, MemoryManagementUnit mmu, ProcessCycles processCycles)
	{
		_id = id;
		_mmu = mmu;
		_processCycles = processCycles;
	}
	
	@Override
	public void run() {
		for(ProcessCycle cycle : _processCycles.getProcessCycles())
		{
			List<Long> pages = cycle.getPages();
			Long[] pageArr = pages.toArray(new Long[pages.size()]);
			try
			{
				synchronized (_mmu.getClass()) {
					Page[] mmuPages = _mmu.getPages(pageArr);
					int i = 0;
					for(Page<byte[]> mmuPage : mmuPages)
					{
						mmuPage.setContent(cycle.getData().get(i++));
					}
				}
			}
			catch (Exception e)
			{
				MMULogger.getInstance().write(e.getMessage(), Level.SEVERE);
			}
			try {
				Thread.sleep(cycle.getSleepMs());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				MMULogger.getInstance().write("Process run failed", Level.SEVERE);
			}
		}
		
	}

	public int getId()
	{
		return _id;
	}
	
	public void setId(int id)
	{
		_id = id;
	}
}
