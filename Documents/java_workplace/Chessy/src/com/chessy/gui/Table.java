package com.chessy.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JMenuBar;


public class Table {
	
	private final JFrame gameFrame;
	private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(Constants.OUTER_FRAME_SIZE,Constants.OUTER_FRAME_SIZE); //600*600
	
	private final BoardPanel boardPanel;
	//TODO later make it singeltion
	public Table(){
		
		this.gameFrame = new JFrame("Lets Chessy");
		this.gameFrame.setLayout(new BorderLayout());
		final JMenuBar tableMenuBar = MenuBar.createTableMenuBar();
		gameFrame.setJMenuBar(tableMenuBar);
		this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
		this.boardPanel = new BoardPanel();
		this.gameFrame.add(this.boardPanel,BorderLayout.CENTER);
		
		
		
		
		this.gameFrame.setVisible(true);
	}


	
}
