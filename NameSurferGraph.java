/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas
	implements NameSurferConstants, ComponentListener {

	/**
	* Creates a new NameSurferGraph object that displays the data.
	*/
	public NameSurferGraph() 
	{
		counter=0;
		addComponentListener(this);
		
		
	}
	//that draws the grid 
	private void drawGrid()
	{
		drawHorizontalLines();
		drawVerticalLines();
		showDecadeLabels();
	}
	private void drawVerticalLines()
	{
		double space=getWidth()/NDECADES;
		int start=0;
		int end=getHeight();
		for(int i=0;i<(NDECADES+1);i++)
		{
			add(new GLine(space*i,start,space*i,end));
		}
			
	}
	private void drawHorizontalLines()
	{
	   add(new GLine(0,GRAPH_MARGIN_SIZE,getWidth(),GRAPH_MARGIN_SIZE));
	   add(new GLine(0,getHeight()-GRAPH_MARGIN_SIZE,getWidth(),getHeight()-GRAPH_MARGIN_SIZE));
			
	}
	private void showDecadeLabels()
	{
		double space=getWidth()/NDECADES;
		for(int i=0;i<NDECADES;i++)
		{
			int decade=START_DECADE+(i*10);
			GLabel temp=new GLabel(""+decade);
			temp.setFont("Courier-16");
			add(temp,(i*space),getHeight());
		}
	}

	private void drawEntry(NameSurferEntry entry,Color c)
	{
		double space=getWidth()/NDECADES;
		double lineOfGrid=getHeight()-(2*GRAPH_MARGIN_SIZE);
		String label;
		String lastLabel;
		for(int i=0;i<NDECADES-1;i++)
		{
			double startPoint;
			double endPoint;
			if(entry.getRank((i*10)+START_DECADE)==0&&entry.getRank(((i+1)*10)+START_DECADE)==0)
			{
				startPoint=getHeight()-(2*GRAPH_MARGIN_SIZE);
				endPoint=getHeight()-(2*GRAPH_MARGIN_SIZE);
				label=entry.getName()+"*";
				lastLabel=entry.getName()+"*";
			}
			else if(entry.getRank((i*10)+START_DECADE)==0&&entry.getRank(((i+1)*10)+START_DECADE)!=0)
			{
				startPoint=getHeight()-(2*GRAPH_MARGIN_SIZE);
				endPoint=(lineOfGrid*(entry.getRank(((i+1)*10)+START_DECADE)))/MAX_RANK;
				label=entry.getName()+"*";
				lastLabel=entry.getName()+entry.getRank(((i+1)*10)+START_DECADE);
				
			}
			else if(entry.getRank((i*10)+START_DECADE)!=0&&entry.getRank(((i+1)*10)+START_DECADE)==0)
			{
				startPoint=(lineOfGrid*(entry.getRank((i*10)+START_DECADE)))/MAX_RANK;
				endPoint=getHeight()-(2*GRAPH_MARGIN_SIZE);
				label=entry.getName()+entry.getRank((i*10)+START_DECADE);
				lastLabel=entry.getName()+"*";
				
			}
			else
			{
				startPoint=(lineOfGrid*(entry.getRank((i*10)+START_DECADE)))/MAX_RANK;
				endPoint=(lineOfGrid*(entry.getRank(((i+1)*10)+START_DECADE)))/MAX_RANK;
				label=entry.getName()+entry.getRank((i*10)+START_DECADE);
				lastLabel=entry.getName()+entry.getRank(((i+1)*10)+START_DECADE);
			}
			GLine temp=new GLine(space*i,GRAPH_MARGIN_SIZE+startPoint,(space*i)+space,GRAPH_MARGIN_SIZE+endPoint);
			GLabel temp2=new GLabel(label,space*i,GRAPH_MARGIN_SIZE+startPoint);
			temp.setColor(c);
			temp2.setColor(c);
			add(temp);
			add(temp2);
			if(i==(NDECADES-2))
			{
				GLabel temp3=new GLabel(lastLabel,(space*i)+space,GRAPH_MARGIN_SIZE+endPoint);
				temp3.setColor(c);
				add(temp3);
			}
		}
	}
	
	private Color chooseColour()
	{
		Color colour;
		switch(counter)
		{
			case 0:colour= Color.MAGENTA;
			 		break;
			case 1:colour= Color.RED;
					break;
			case 2:colour= Color.GREEN;
					break;
			default:colour= Color.BLUE;
		}
		if(counter==3)
		{counter=-1;}
		counter++;
		return colour;
	}
	/**
	* Clears the list of name surfer entries stored inside this class.
	*/
	public void clear() 
	{
	 list.clear();
	 update();
	}
	
	/* Method: addEntry(entry) */
	/**
	* Adds a new NameSurferEntry to the list of entries on the display.
	* Note that this method does not actually draw the graph, but
	* simply stores the entry; the graph is drawn by calling update.
	*/
	public void addEntry(NameSurferEntry entry) 
	{
		list.add(entry);
		update();
	}
	public void addLabel()
	{
		temp=new GLabel("Not Popular , soory :D ",getWidth()/4,getHeight()/2);
		temp.setFont("Courier-48");
		temp.setColor(Color.RED);
		add(temp);
		
	}
	private void displayAllEnteries()
	{
		
		for(int i=0;i< list.size();i++)
		{
			Color c=chooseColour();
			drawEntry(list.get(i),c);
		}
		counter=0;
	}
	
	
	
	/**
	* Updates the display image by deleting all the graphical objects
	* from the canvas and then reassembling the display according to
	* the list of entries. Your application must call update after
	* calling either clear or addEntry; update is also called whenever
	* the size of the canvas changes.
	*/
	public void update() 
	{
	 removeAll();
	 drawGrid();
	 displayAllEnteries();
	}
	
	
	
	
	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
	/*instance variables*/
	ArrayList<NameSurferEntry> list=new ArrayList<NameSurferEntry>();
	int counter; //for choosing the colour
	GLabel temp;
}
