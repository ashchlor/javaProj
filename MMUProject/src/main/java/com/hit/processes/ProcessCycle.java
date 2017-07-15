package com.hit.processes;

import java.util.List;

public class ProcessCycle {

	List<Long> pages;
	int sleepMs;
	List<byte[]> data;
	
	public ProcessCycle(List<Long> pages,
            int sleepMs,
            List<byte[]> data)
	{
		this.pages = pages;
		this.sleepMs = sleepMs;
		this.data = data;
	}
	
	public List<byte[]> getData()
	{
		return this.data;
	}
	
	public void setData(List<byte[]> data)
	{
		this.data = data;
	}
	
	public int getSleepMs()
	{
		return this.sleepMs;
	}
	
	public void setSleepMs(int sleepMs)
	{
		this.sleepMs = sleepMs;
	}
	
	public List<Long> getPages()
	{
		return this.pages;
	}
	
	public void setPages(List<Long> pages)
	{
		this.pages = pages;
	}
}
