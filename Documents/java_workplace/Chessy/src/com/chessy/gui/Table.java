package com.chessy.gui;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.chessy.engine.board.Board;
import com.chessy.engine.board.BoardUtil;
import com.chessy.engine.board.Move;
import com.chessy.engine.board.Tile;
import com.chessy.engine.board.Move.MoveFactory;
import com.chessy.engine.pieces.Piece;
import com.chessy.engine.player.MoveTransition;


public class Table {
	
	private final JFrame gameFrame;
	private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(Constants.OUTER_FRAME_SIZE,Constants.OUTER_FRAME_SIZE); //600*600
	private Board chessBoard;
	private final BoardPanel boardPanel;
	
	private Tile sourceTile;
    private Tile destinationTile;
    private Piece humanMovedPiece;
    
	
	//TODO later make it singeltion
	public Table(){
		
		this.gameFrame = new JFrame("Lets Chessy");
		this.gameFrame.setLayout(new BorderLayout());
		final JMenuBar tableMenuBar = MenuBar.createTableMenuBar();
		gameFrame.setJMenuBar(tableMenuBar);
		this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
		chessBoard = Board.createStandardBoard();
		this.boardPanel = new BoardPanel();
		this.gameFrame.add(this.boardPanel,BorderLayout.CENTER);
		
		
		
		
		this.gameFrame.setVisible(true);
	}

	
	
	
	@SuppressWarnings("serial")
	public class BoardPanel extends JPanel{
		
		final List<TilePanel> boardTiles;
		private final Dimension BOARD_PANEL_DIMENSION = new Dimension(Constants.BOARD_PANEL_WIDTH, Constants.BOARD_PANEL_HEIGHT);
		
		public BoardPanel(){
			super(new GridLayout(Constants.ROWS, Constants.COLUMNS));
			boardTiles = new ArrayList<>();
			for(int i = 0;i<Constants.NUM_TILES;i++){
				final TilePanel tile = new TilePanel(this, i);
				boardTiles.add(tile);
				
				//add tile component to the root jpanel component
				add(tile);
			}
			setPreferredSize(BOARD_PANEL_DIMENSION);
			validate();
		}

		public void drawBoard(Board chessBoard) {
			removeAll();
			for(final TilePanel tilePanel : boardTiles){
				tilePanel.drawTile(chessBoard);
				add(tilePanel);
			}
			validate();
			repaint();
		}
	}

	
	
	
	
	@SuppressWarnings("serial")
	public class TilePanel extends JPanel{
		private  final Dimension TILE_PANEL_DIMENSION = new Dimension(Constants.TILE_PANEL_WIDTH,Constants.TILE_PANEL_WIDTH);
		private final int tileId;
		
		private Color lightTileColor = Color.decode("#FFFACD");
	    private Color darkTileColor = Color.decode("#593E1A");
	    private String defaultPeiceImagesPath = Constants.SPRITES_PATH;
		
	    
		public TilePanel(final BoardPanel boardPanel,final int tileId) {
			super(new GridBagLayout());
			this.tileId = tileId;
			setPreferredSize(TILE_PANEL_DIMENSION);
			assignTileColor();
			assignPieceIconOnTile(chessBoard);
			
			addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(isRightMouseButton(e)){
						//cancel any selection they may have
						sourceTile=null;
						destinationTile=null;
						humanMovedPiece = null;
						System.out.println("move selection cleared");
					}else if(isLeftMouseButton(e)){
						if(sourceTile==null){
							//first clicks
							sourceTile = chessBoard.getTile(tileId);
							humanMovedPiece = sourceTile.getPiece();
							if(humanMovedPiece==null){
								System.out.println("selected tile is empty");
								sourceTile = null; //clicked on empty tilr
							}else System.out.println("selected piece is "+humanMovedPiece.getPieceType().toString());
						}else {
							/*
							 * second click 
							 * -> can be the same tile or different tile
							 * -> can be clicking on the destination tile or selecting another piece as a source pieee
							 */
							destinationTile = chessBoard.getTile(tileId);
							//TODO
							final Move move = MoveFactory.createMove(chessBoard, sourceTile.getTileCordinate(), destinationTile.getTileCordinate());
							final MoveTransition transition = chessBoard.getCurrentPlayer().makeMove(move);
							if(transition.getMoveStatus().isDone()){
								chessBoard = transition.getTransitionBoard();
								System.out.println("moved played by "+chessBoard.getCurrentPlayer().getOpponent().getAlliance().toString());
								//TODo add move to add log
							}else System.out.println("move can not be played on this destination tile Try again");
							sourceTile = null;
							destinationTile = null;
							humanMovedPiece = null;
						}
						
						SwingUtilities.invokeLater(new Runnable() {
							
							@Override
							public void run() {
								boardPanel.drawBoard(chessBoard);
							}
						});
					}
				}
				@Override
				public void mouseReleased(MouseEvent e) {
					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
				}
				
				
			});
			
			validate();
		}

		public void drawTile(Board chessBoard) {
			assignTileColor();
			assignPieceIconOnTile(chessBoard);
			validate();
			repaint();
		}

		private void assignTileColor() {
			int i = this.tileId;
	        
			if(BoardUtil.EIGTH_RANK[i] || BoardUtil.SIXTH_RANK[i] || BoardUtil.FOURTH_RANK[i] ||BoardUtil.SECOND_RANK[i]){
	            setBackground(this.tileId%2==0?lightTileColor:darkTileColor);
	        }else if(BoardUtil.SEVENTH_RANK[i]|| BoardUtil.FIFTH_RANK[i] || BoardUtil.THIRD_RANK[i] || BoardUtil.FIRST_RANK[i] ){
	            setBackground(this.tileId%2!=0?lightTileColor:darkTileColor);
	        }
	    
		}
		
		
		private void assignPieceIconOnTile(final Board board){
			this.removeAll();
			Tile tile = board.getTile(this.tileId);
			if(tile.isTileOccupied()==true){
	            try {
	            	Piece pieceOnTile = tile.getPiece();
	                final BufferedImage image =
	                ImageIO.read(new File(defaultPeiceImagesPath+((pieceOnTile.getPieceAlliance().isWhite())?"white":"black") + 
	                		pieceOnTile.toString()+".png"));
	                add(new JLabel(new ImageIcon(image)));
	            }
	            catch (IOException e){
	                e.printStackTrace();
	            }
	        }
		}
	}
	
	
	
	
	
}
