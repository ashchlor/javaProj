package com.hit.driver;

import com.hit.controller.MMUController;
import com.hit.model.MMUModel;
import com.hit.view.LoginView;
import com.hit.view.MMUView;

public class MMUDriver {

	
	public static void main(String[] args)
	{	
		
		LoginView login = new LoginView();
		
		
		
		
		CLI cli = new CLI(System.in, System.out);
		MMUModel model = new MMUModel();
		MMUView view = new MMUView();
		MMUController controller = new MMUController(model,view, login);
		login.addObserver(controller);
		model.addObserver(controller);
		view.addObserver(controller);
		cli.addObserver(controller);	
		new Thread(cli).start();
	}	
}
