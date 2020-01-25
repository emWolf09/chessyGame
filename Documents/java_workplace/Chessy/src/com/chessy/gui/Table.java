package com.chessy.gui;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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
    
    private BoardDirection boardDirection;
    private boolean highlightMoveFlag;
    
    private TakenPiecePanel takenPiecePanel;
    private GameHistoryPanel gameHistoryPanel;
    private MoveLog moveLog;
	
	//TODO later make it singeltion
	public Table(){
		
		this.gameFrame = new JFrame("Lets Chessy");
		this.gameFrame.setLayout(new BorderLayout());
		this.boardDirection = BoardDirection.NORMAL;
		MenuBar menuBar = new MenuBar();
		final JMenuBar tableMenuBar = menuBar.createTableMenuBar();
		this.highlightMoveFlag = true;
		gameFrame.setJMenuBar(tableMenuBar);
		this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
		chessBoard = Board.createStandardBoard();
		this.boardPanel = new BoardPanel();
		this.moveLog = new MoveLog();
		this.gameHistoryPanel = new GameHistoryPanel();
		this.takenPiecePanel = new TakenPiecePanel();
		this.gameFrame.add(takenPiecePanel,BorderLayout.WEST);
		this.gameFrame.add(this.boardPanel,BorderLayout.CENTER);
		this.gameFrame.add(this.gameHistoryPanel,BorderLayout.EAST);
		this.gameFrame.setVisible(true);
	}

	
	
	public enum BoardDirection{
		
		NORMAL {
			@Override
			List<TilePanel> traverse(List<TilePanel> boardTiles) {
				return boardTiles;
			}

			@Override
			BoardDirection opposite() {
				return FLIPPED;
			}
		}
		,FLIPPED {
			@Override
			List<TilePanel> traverse(List<TilePanel> boardTiles) {
				List<TilePanel> newList = new ArrayList<>(boardTiles);
				Collections.reverse(newList);
				return newList;
			}

			@Override
			BoardDirection opposite() {
				return NORMAL;
			}
		};
		
		
		abstract List<TilePanel> traverse(final List<TilePanel> boardTiles);
		abstract BoardDirection opposite();
		
		
	}
	
	
	
	@SuppressWarnings("serial")
	public class BoardPanel extends JPanel{
		
		List<TilePanel> boardTiles;
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
			for(final TilePanel tilePanel : boardDirection.traverse(boardTiles)){
				tilePanel.drawTile(chessBoard);
				add(tilePanel);
			}
			validate();
			repaint();
		}
	}

	
	
	
	public static class MoveLog{
		private final List<Move> moves;
		
		public MoveLog(){
			this.moves = new ArrayList<>();
		}

		public List<Move> getMoves() {
			return moves;
		}
		
		
		public void addMove(final Move m){
			this.moves.add(m);
		}
		
		public int size(){
			return this.moves.size();
		}
		
		public void clear(){
			this.moves.clear();
		}
		

		public boolean remove(final Move m){
			return this.moves.remove(m);
		}
		
		public Move remove(final int index){
			return this.moves.remove(index);
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
								moveLog.addMove(move); 
							}else System.out.println("move can not be played on this destination tile Try again");
							sourceTile = null;
							destinationTile = null;
							humanMovedPiece = null;
						}
						
						SwingUtilities.invokeLater(new Runnable() {
							
							@Override
							public void run() {
								gameHistoryPanel.redo(chessBoard,moveLog);
								takenPiecePanel.redo(moveLog);
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
			highlightLegals(chessBoard);
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
		
		private void highlightLegals(final Board board){
            if(highlightMoveFlag){
                for(Move move : peiceLegalMoves(board)) {
                    if(move.getDestinationCordinate()==this.tileId){
                        try {
                            add(new JLabel(new ImageIcon(ImageIO.read(new File("sprites/green_dot.png")))));
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
		}
		
		private Collection<Move> peiceLegalMoves(final  Board board){
            if(humanMovedPiece !=null && humanMovedPiece.getPieceAlliance()==board.getCurrentPlayer().getAlliance()){
                return humanMovedPiece.calculateLegalMoves(chessBoard);
            }
            return Collections.emptyList();
        }

	}
	
	
	
	
	
	
	public class MenuBar {
		public  JMenuBar createTableMenuBar() {
			JMenuBar tableMenuBar = new JMenuBar(); 
			
			tableMenuBar.add(createFileMenu());
			tableMenuBar.add(createExitMenu());
			tableMenuBar.add(createPreferenceMenu());
			return tableMenuBar;
			
		}

		private  JMenu createPreferenceMenu() {
			final JMenu preferencesMenu = new JMenu("Preferences");
			final JMenuItem flipBoard = new JMenuItem("Flip Board");
			flipBoard.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					boardDirection = boardDirection.opposite();
					boardPanel.drawBoard(chessBoard);
				}
			});
			preferencesMenu.add(flipBoard);
			preferencesMenu.addSeparator();
			final JCheckBoxMenuItem highlightMove = new JCheckBoxMenuItem("Highlight Legal Move",true);
			highlightMove.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					highlightMoveFlag = highlightMove.isSelected();
				}
			});
			preferencesMenu.add(highlightMove);
			return preferencesMenu;
		}

		//EXIT Menu
		private  JMenu createExitMenu() {
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
		private  JMenu createFileMenu() {
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
	
	
}
