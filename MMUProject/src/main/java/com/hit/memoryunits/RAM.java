package com.hit.memoryunits;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RAM {

	private int _initialCapacity;
	Map<Long,Page> _pagesMap;
	
	public RAM(int InitialCapacity) {
		_initialCapacity = InitialCapacity;
		_pagesMap = new HashMap<>(_initialCapacity);
	}
	
	public void addPage(Page<byte[]> addPage) {
		_pagesMap.put(addPage.getId(), addPage);
	}
	
	public Boolean isRamFull()
	{
		return _pagesMap.size() == _initialCapacity;
	}
	
	public void addPages(Page<byte[]>[] addPages) {
		for(Page page : addPages)
		{
			_pagesMap.put(page.getId(), page);
		}
	}
	
	public Page<byte[]> getPage(long pageId) 
	{
		if(_pagesMap.containsKey(pageId)){
			return _pagesMap.get(pageId);
		}
		else
		{
			return null;
		}
	}
	
	public Map<Long,Page> getPages() 
	{
		return _pagesMap;
	}
	
	@SuppressWarnings("unchecked")
	public Page<byte[]>[] getPages(long[] pageIds) 
	{
		ArrayList<Page<byte[]>> lst = new ArrayList<Page<byte[]>>();
		for(int i = 0; i < pageIds.length; i++)
		{
			Page<byte[]> pg = getPage(pageIds[i]);
			lst.add(pg);
		}
			
		return (Page<byte[]>[]) lst.toArray();
	}
	
	public void removePage(Page<byte[]> removePage){
		if(_pagesMap.containsKey(removePage.getId()))
			_pagesMap.remove(removePage.getId());
	}
	
	public void removePages(Page<byte[]>[] removePages){
		for(Page<byte[]> page : removePages)
		{
			removePage(page);
		}
	}
	
	public int getInitialCapacity() 
	{
		return _initialCapacity;
	}
	
	public void setInitialCapacity(int initialCapacity)
	{
		_initialCapacity = initialCapacity;
	}
}
