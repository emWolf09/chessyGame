package com.chessy.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar {
	
	public static JMenuBar createTableMenuBar() {
		JMenuBar tableMenuBar = new JMenuBar(); 
		
		tableMenuBar.add(createFileMenu());
		tableMenuBar.add(createExitMenu());
		
		return tableMenuBar;
		
	}

	//EXIT Menu
	private static JMenu createExitMenu() {
		final JMenu exitMenu = new JMenu("Exit");
		final JMenuItem exit = new JMenuItem("click here! Bye");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Game Closed by user");
				System.exit(0);
			}
		});
		
		exitMenu.add(exit);
		
		return exitMenu;
	}

	//FILE Menu
	private static JMenu createFileMenu() {
		final JMenu fileMenu = new JMenu("File");
		
		//load saved game option
		final JMenuItem openPGN = new JMenuItem("Load saved games");
		openPGN.addActionListener(
				new ActionListener() {	
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("Tried To open saved game :: XXXXX");
						System.out.println("x::x::x::option to load saved game Coming Soonx::x::x::");
					}
				}
		);
		fileMenu.add(openPGN);
		
		return fileMenu; 
	}
}
