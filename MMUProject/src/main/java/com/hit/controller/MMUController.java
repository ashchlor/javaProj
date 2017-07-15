package com.hit.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;

import com.hit.model.MMUModel;
import com.hit.model.Model;
import com.hit.util.MMULogger;
import com.hit.view.MMUView;
import com.hit.view.View;

public class MMUController implements Observer  {

	MMUModel _model;
	MMUView _view;
	public MMUController(MMUModel model, MMUView view) {
		_model = model;
		_view = view;
		 
		_model.addObserver(this);
		_view.addObserver(this);
		
		_view.start();
		_model.start();
		
	}
	
	
	@Override
	public void update(Observable arg0, Object arg1) {
		String[] commands = null;
		
		if(arg1.getClass().equals(String.class))
		{
		String command = arg1.toString();
		commands = command.split(":");
		if(commands.length != 2)
			{
			MMULogger.getInstance().write("Invalid command to controller", Level.SEVERE);
			return;
			}
		}
		
		if(arg0.getClass().equals(MMUModel.class)) //Model to View
		{		
			if(arg1.getClass().equals(ArrayList.class)) // List of processes
			{
				List<String> lst = (ArrayList<String>)arg1;
				_view.SetProcesses(lst);
			}
			else
			{
				switch(commands[0]){
				case "PF":
					_view.setPageFaultAmount(Integer.parseInt(commands[1]));
					break;
				case "PR":
					_view.setPageReplacementAmount(Integer.parseInt(commands[1]));
					break;
				}
			}
		}
		else if(arg0.getClass().equals(MMUView.class)) //View to Model
		{
			if(arg1.getClass().equals(ArrayList.class)) // List of processes
			{
				List<String> lst = (ArrayList<String>)arg1;
				
			}
			else
			{
				switch(commands[0])
				{
				case "P": //Play specific
					break;
				case "PA": //Play All
					break;
				}
			}
		}
		System.out.print("Test");
	}

}
