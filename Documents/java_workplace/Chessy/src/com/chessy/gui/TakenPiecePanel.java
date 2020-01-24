package com.chessy.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import com.chessy.engine.board.Move;
import com.chessy.engine.common.Alliance;
import com.chessy.engine.pieces.Piece;
import com.chessy.gui.Table.MoveLog;

public class TakenPiecePanel extends JPanel {
	
	private final JPanel northPanel,southPanel;
	
	private static final EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);
	private String defaultPeiceImagesPath = Constants.SPRITES_PATH;
	private static final Color PANEL_COLOR = Color.decode("0xFDFE6");
	private static Dimension TAKEN_PIECES_DIM = new Dimension(40,80);
	
	public TakenPiecePanel(){
		super(new BorderLayout());
		setBackground(PANEL_COLOR);
		this.northPanel = new JPanel(new GridLayout(8,2));
		this.southPanel = new JPanel(new GridLayout(8,2));
		this.northPanel.setBackground(PANEL_COLOR);
		this.southPanel.setBackground(PANEL_COLOR);
		this.add(this.northPanel,BorderLayout.NORTH);
		this.add(this.southPanel,BorderLayout.SOUTH);
		setPreferredSize(TAKEN_PIECES_DIM);
	}
	
	public void redo(final MoveLog moveLog){
		southPanel.removeAll();
		northPanel.removeAll();
		
		final List<Piece> whiteTakenPiece = new ArrayList<>();
		final List<Piece> blackTakenPiece = new ArrayList<>();
		
		
		for(final Move move: moveLog.getMoves()){
			if(move.isAttack()){
				final Piece takenPiece = move.getAttackPiece();
				if(takenPiece.getPieceAlliance().isWhite()){
					whiteTakenPiece.add(takenPiece);
				}else if(takenPiece.getPieceAlliance().isBlack())blackTakenPiece.add(takenPiece);
				else{
					throw new RuntimeException("redoing log redo funtion in takenPiecePanel class");
				}
			}
			
			
		}
		
		Collections.sort(whiteTakenPiece,(Piece p1,Piece p2)->Integer.compare(p1.getPieceValue(),p2.getPieceValue()));
		Collections.sort(blackTakenPiece,(Piece p1,Piece p2)->Integer.compare(p1.getPieceValue(),p2.getPieceValue()));
		
		
		for(final Piece piece : whiteTakenPiece){
			
			try {
				 final BufferedImage image =
	                ImageIO.read(new File(defaultPeiceImagesPath+((piece.getPieceAlliance().isWhite())?"white":"black") + 
	                		piece.toString()+".png"));
	                add(new JLabel(new ImageIcon(image)));
	                
	                final ImageIcon icon = new ImageIcon(image);
	                //TODO correct JLabel
	                final JLabel imageLable = new JLabel("label1");
	                southPanel.add(imageLable);
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		for(final Piece piece : blackTakenPiece){
					
			try {
				 final BufferedImage image =
	                ImageIO.read(new File(defaultPeiceImagesPath+((piece.getPieceAlliance().isWhite())?"white":"black") + 
	                		piece.toString()+".png"));
	                add(new JLabel(new ImageIcon(image)));
	                
	                final ImageIcon icon = new ImageIcon(image);
	                //TODO
	                final JLabel imageLable = new JLabel("label1");
	                northPanel.add(imageLable);
			}catch (Exception e) {
				e.printStackTrace();
			}
					
		}
		
		validate();
	}
}
