package com.chessy.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.chessy.engine.board.Board;
import com.chessy.gui.Table.MoveLog;

public class GameHistoryPanel extends JPanel {
	private final DataModel model;
	private final JScrollPane scrollPane;
	private final Dimension HISTORY_PANEL_DIM = new Dimension(100,400);
	
	
	public GameHistoryPanel() {
		this.setLayout(new BorderLayout());
		this.model = new DataModel();
		final JTable table = new JTable(model);
		table.setRowHeight(15);
		this.scrollPane = new JScrollPane(table);
		scrollPane.setColumnHeaderView(table.getTableHeader());
		scrollPane.setPreferredSize(HISTORY_PANEL_DIM);
		this.add(scrollPane,BorderLayout.CENTER);
		this.setVisible(true);
	}
	
	public void redo(final Board board,final MoveLog moveLog){
		
	}
	
	private static class DataModel extends DefaultTableModel{
		
	}
}
