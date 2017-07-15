package com.hit.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Observable;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class MMUView extends Observable implements View {

	int ramSize = 6; //TODO Omer
	JFrame frame = new JFrame("MMU View");
	JTable table = new JTable();
	JLabel pg = new JLabel("Page Fault Amount");
	JTextField txtPf = new JTextField();
	JLabel pr = new JLabel("Page Replacement Amount");
	JTextField txtPr = new JTextField();
	JLabel lblProcesses = new JLabel("Processes");
	JList<String> lstProcesses = new JList<>();
	JButton btnPlay = new JButton("Play");
	JButton btnPlayAll = new JButton("Play All");
	public MMUView() {
		// TODO Auto-generated constructor stub
	}
	
	public void setPageFaultAmount(int PfAmount)
	{
		txtPf.setText(Integer.toString(PfAmount));
	}
	
	public void setPageReplacementAmount(int PrAmount)
	{
		txtPr.setText(Integer.toString(PrAmount));
	}
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(900, 500);;
		
		Integer[] row = new Integer[ramSize];
		DefaultTableModel tab_model = new DefaultTableModel(6, 0);
		table.setEnabled(false);
		table.setRowHeight(20);
		table.setBounds(10, 20, 600, 240);

		for (int i = 0; i < ramSize; i++) {
			row[i] = 0;
			tab_model.addColumn(i);
		}
		for (int j = 0; j < 6; j++) {
			tab_model.insertRow(j, row);
		}
		table.setModel(tab_model);

		
		txtPf.setBounds(800, 100, 50, 20);
		pg.setBounds(620, 100, 200, 20);
		
		txtPr.setBounds(800, 125, 50, 20);
		pr.setBounds(620, 120, 200, 20);
		
		lblProcesses.setBounds(620, 250, 200, 20);
		lstProcesses.setBounds(620, 270, 270, 230);
		
		btnPlay.setBounds(10, 350, 100, 30);
		btnPlayAll.setBounds(140,350, 100, 30);
		btnPlayAll.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setChanged();
				notifyObservers("PA"); //Play all
				
			}
		});
		
		btnPlay.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				List<String> prc = lstProcesses.getSelectedValuesList();
				
				setChanged();
				notifyObservers(prc);
				
			}
		});
		
		frame.add(btnPlay);
		frame.add(btnPlayAll);
		frame.add(lblProcesses);
		frame.add(lstProcesses);
		frame.add(txtPr);		
		frame.add(pr);
		frame.add(txtPf);		
		frame.add(pg);
		frame.add(table);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setVisible(true);
	}
	
	public void SetProcesses(List<String> processes)
	{
		DefaultListModel<String> modelProName = new DefaultListModel<>();
		
		for(String prc : processes)
			modelProName.addElement(prc);
		
		lstProcesses.setModel(modelProName);
	}

}
