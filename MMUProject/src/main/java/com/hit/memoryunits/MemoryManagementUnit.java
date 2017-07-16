package com.hit.memoryunits;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;

import com.hit.algorithm.IAlgoCache;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class MemoryManagementUnit extends Observable {
	
	private IAlgoCache<Long,Long> _algo;
	private RAM _ram;
	private int _numOfPageFautls = 0;
	private int _numOfPageReplacements = 0;
	
	public MemoryManagementUnit(int ramCapacity, IAlgoCache<Long,Long> algo) {
		// TODO Auto-generated constructor stub
		_ram = new RAM(ramCapacity);
		_algo = algo;
	}
	
	
	public Page<byte[]>[] getPages(Long[] pageIds) throws FileNotFoundException, ClassNotFoundException, IOException
	{
		ArrayList<Page<byte[]>> pages = new ArrayList<Page<byte[]>>();
		int pageIdx = 0; // Index for pagesToRemoveFromRam
		List<Long> pagesToRemoveFromRam = _algo.putElement((List<Long>)Arrays.asList(pageIds), (List<Long>)Arrays.asList(pageIds));
		
		for(int i = 0; i < pageIds.length; i++)
		{
			Page<byte[]> pg = _ram.getPage(pageIds[i]);
			if(pg == null)
			{
				if(_ram.isRamFull() == false)
				{
					pg = HardDisk.getInstace().pageFault(pageIds[i]);
					_numOfPageFautls++;
					setChanged();
					notifyObservers("PF:" + Integer.toString(_numOfPageFautls));
				}
				else
				{				
					Page<byte[]> pageToHd = _ram.getPage(pagesToRemoveFromRam.get(pageIdx));
					pageIdx++;
					pg= HardDisk.getInstace().pageReplacement(pageToHd, pageIds[i]);
					_numOfPageReplacements++;
					setChanged();
					notifyObservers("PR:" + Integer.toString(_numOfPageReplacements));
					_ram.removePage(pageToHd);
				}
				_ram.addPage(pg);
			}
			pages.add(pg);
		}
		
		setChanged();
		notifyObservers(_ram.getPages());
				
		Page<byte[]> pagesArr[] = new Page[pages.size()];
		for(int i = 0; i < pages.size(); i++)
			pagesArr[i] = pages.get(i);
			
		return pagesArr;
	}
}
