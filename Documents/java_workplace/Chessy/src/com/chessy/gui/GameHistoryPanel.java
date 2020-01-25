package com.chessy.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.chessy.engine.board.Board;
import com.chessy.engine.board.Move;
import com.chessy.gui.Table.MoveLog;


@SuppressWarnings("serial")
public class GameHistoryPanel extends JPanel {
	private  DataModel model;
	private  JScrollPane scrollPane;
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
	
	public void redo(final Board board,final MoveLog  moveHistory){
		int currentRow = 0;
        this.model.clear();
        for (final Move move : moveHistory.getMoves()) {
            final String moveText = move.toString();
            if (move.getMovedPiece().getPieceAlliance().isWhite()) {
                this.model.setValueAt(moveText, currentRow, 0);
            }
            else if (move.getMovedPiece().getPieceAlliance().isBlack()) {
                this.model.setValueAt(moveText, currentRow, 1);
                currentRow++;
            }
        }

        if(moveHistory.getMoves().size() > 0) {
            final Move lastMove = moveHistory.getMoves().get(moveHistory.size() - 1);
            final String moveText = lastMove.toString();
            if (lastMove.getMovedPiece().getPieceAlliance().isWhite()) {
                this.model.setValueAt(moveText + calculateCheckAndCheckMateHash(board), currentRow, 0);
            }
            else if (lastMove.getMovedPiece().getPieceAlliance().isBlack()) {
                this.model.setValueAt(moveText + calculateCheckAndCheckMateHash(board), currentRow - 1, 1);
            }
        }
        final JScrollBar vertical = scrollPane.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
	}
	
	private static String calculateCheckAndCheckMateHash(final Board board) {
        if(board.getCurrentPlayer().isInCheckMate()) {
            return "#";
        } else if(board.getCurrentPlayer().isInCheck()) {
            return "+";
        }
        return "";
    }
	
	
	private static class DataModel extends DefaultTableModel{
		private final List<Row> value;
		private final String[] NAMES = {"White","Black"};
		
		@Override
		public String toString() {
			return super.toString();
		}
		public DataModel() {
			this.value = new ArrayList<>();
		}
		
		public void clear() {
			this.value.clear();
			setRowCount(0); 
		}
		@Override
		public void setRowCount(int rowCount) {
			super.setRowCount(rowCount);
		}
		
		@Override
		public int getRowCount() {
			if(this.value==null)return 0;
			return this.value.size();
		}
		
		@Override
		public int getColumnCount() {
			return NAMES.length;
		}
		
		@Override
		public Object getValueAt(final int row,final int column) {
			final Row curreRow = value.get(row);
			if(column==0)return  curreRow.getWhiteMove();
			else if(column==1)return curreRow.getBlackMove();
			else return null;
		}
		
		@Override
		public void setValueAt(Object aValue, int row, int column) {
			Row currentRow;
			if(this.value.size()<=row) { 
				currentRow = new Row();;
				this.value.add(currentRow);
			}else {
				currentRow = this.value.get(row);
			}
			if(column==0) {
				currentRow.setWhiteMove((String)aValue);
//				fireTableCellUpdated(row, row);
				fireTableDataChanged();
			}else if(column==1) {
				currentRow.setBlackMove((String)aValue);
				fireTableDataChanged();
//				fireTableCellUpdated(row, row);
			}
			
			
		}
		
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			return Move.class;
		}
		
		@Override
		public String getColumnName(int column) {
			return NAMES[column];
		}
	}
	
	private static class Row {
		private String whiteMove;
		private String blackMove;
		
		Row(){
			
		}
		
		public String getWhiteMove() {
			return whiteMove;
		}
		public void setWhiteMove(String whiteMove) {
			this.whiteMove = whiteMove;
		}
		public String getBlackMove() {
			return blackMove;
		}
		public void setBlackMove(String blackMove) {
			this.blackMove = blackMove;
		}
	}
}
