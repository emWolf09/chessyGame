package com.chessy.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BoardPanel extends JPanel{
	
	final List<TilePanel> boardTiles;
	private static final Dimension BOARD_PANEL_DIMENSION = new Dimension(Constants.BOARD_PANEL_WIDTH, Constants.BOARD_PANEL_HEIGHT);
	
	public BoardPanel(){
		super(new GridLayout(Constants.ROWS, Constants.COLUMNS));
		boardTiles = new ArrayList<>();
		for(int i = 0;i<Constants.NUM_TILES;i++){
			final TilePanel tile = new TilePanel(this,i);
			boardTiles.add(tile);
			
			//add tile component to the root jpanel component
			add(tile);
		}
		setPreferredSize(BOARD_PANEL_DIMENSION);
		validate();
	}
}
