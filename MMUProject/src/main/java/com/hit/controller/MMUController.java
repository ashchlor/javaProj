package com.hit.controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.hit.driver.CLI;
import com.hit.memoryunits.Page;
import com.hit.model.MMUClient;
import com.hit.model.MMUModel;
import com.hit.model.Model;
import com.hit.util.MMULogger;
import com.hit.view.LoginView;
import com.hit.view.MMUView;
import com.hit.view.View;

public class MMUController implements Observer  {

	MMUModel _model;
	MMUView _view;
	LoginView _loginView;
	MMUClient _client;
	
	public MMUController(MMUModel model, MMUView view, LoginView login) {
		_model = model;
		_view = view;
		_loginView = login;
		_client = MMUClient.getInstance();
		_client.addObserver(this);
	}
	
	
	@Override
	public void update(Observable arg0, Object arg1) {
		String command = arg1.toString();
		String[] commands = command.split(":");
		
		if(arg0 instanceof LoginView) //Login To Client
		{
			if(commands.length != 3) return;
			
			MMUClient.getInstance().SendLoginDetails(commands[0], commands[1], commands[2]);
			try {
				_model.setConfiguration();
			} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
				MMULogger.getInstance().write(e.toString(), Level.SEVERE);
			}
			_loginView.HideLoginView();
			_view.start();
		}
		
		
		if(arg0 instanceof MMUModel) //Model to View
		{		
			if(arg1 instanceof ArrayList) // List of processes
			{
				List<String> lst = (ArrayList<String>)arg1;
				_view.SetProcesses(lst);
			}
			else if(arg1 instanceof Page[])
			{
				Page[] pages = (Page[])arg1;
				_view.setRamData(pages);
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
				case "RS": //Ram Size
					_view.ramSize = Integer.parseInt(commands[1]);
					break;
				}
			}
		}
		else if(arg0 instanceof MMUView) //View to Model
		{
			if(arg1 instanceof ArrayList) // List of processes
			{
				@SuppressWarnings("unchecked")
				List<String> lst = (ArrayList<String>)arg1;
				_model.runProcesses(lst);
			}
			else
			{
				switch(commands[0])
				{
				case "P": //Play specific
					break;
				case "PA": //Play All
					_model.runAllProcesses();
					break;
				}
			}
		}
		else if(arg0 instanceof MMUClient)
		{
			String str = arg1.toString();
			_model.SetConfigurationFile(str);
		}
		else if(arg0 instanceof CLI) //CLI to model
		{
			if(arg1 instanceof ArrayList) // List of processes
			{
				@SuppressWarnings("unchecked")
				List<String> lst = (ArrayList<String>)arg1;
				_model.configuration = lst;
				if(lst.get(2).toUpperCase().equals("REMOTE"))
				{
					_client.InitClient();
					_loginView.start();
				}
				else
				{
					try {
						_model.setConfiguration();
					} catch (Exception e) {
						MMULogger.getInstance().write(e.toString(), Level.SEVERE);
					}
					_view.start();
				}
			}
		}
	}

}
