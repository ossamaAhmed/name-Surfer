/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {

/* Method: init() */
/**
 * This method has the responsibility for reading in the data base
 * and initializing the interactors at the bottom of the window.
 */
	public void init() 
	{  
		//creates the database 
		loadDB();
		//intialising text field and buttons
		textField=new JTextField(25);
		graphB=new JButton ("Graph");
		clearB=new JButton("Clear");
		//adding the interactors to show up
		add(new JLabel("Name"),SOUTH);
		add(textField,SOUTH);
		add(graphB,SOUTH);
		add(clearB,SOUTH);
//		creates a graph showing to the window
		graph=new NameSurferGraph();
		add(graph);
		//adding the listeners
		textField.addActionListener(this);
		addActionListeners();
	}
	/*debugging issues*/
   /*public void run()
   {
	  
   }*/
/* Method: actionPerformed(e) */
/**
 * This class is responsible for detecting when the buttons are
 * clicked, so you will have to define a method to respond to
 * button actions.
 */
   private void loadDB()
   {
	   db=new NameSurferDataBase(NAMES_DATA_FILE);
   }
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==textField||e.getSource()==graphB)
		{
			NameSurferEntry required=db.findEntry(textField.getText());
			if(required !=null)
			{
				graph.addEntry(db.findEntry(textField.getText()));
			}
			else
			{
				graph.addLabel();
			}
			textField.setText("");
		}
		if(e.getSource()==clearB)
		{
			graph.clear();
		}
			
	}
/*intsance variables*/
	private JTextField textField;
	private JButton graphB;
	private JButton clearB;
	private NameSurferDataBase db;
	private NameSurferGraph graph;
}
