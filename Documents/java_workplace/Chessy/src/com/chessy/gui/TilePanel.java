package com.chessy.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.chessy.engine.board.Board;
import com.chessy.engine.board.BoardUtil;
import com.chessy.engine.board.Tile;
import com.chessy.engine.pieces.Piece;


@SuppressWarnings("serial")
public class TilePanel extends JPanel{
	private static final Dimension TILE_PANEL_DIMENSION = new Dimension(Constants.TILE_PANEL_WIDTH,Constants.TILE_PANEL_WIDTH);
	private final int tileId;
	
	private Color lightTileColor = Color.decode("#FFFACD");
    private Color darkTileColor = Color.decode("#593E1A");
    private static String defaultPeiceImagesPath = Constants.SPRITES_PATH;
	
	public TilePanel(final BoardPanel boardPanel,final int tileId) {
		super(new GridBagLayout());
		this.tileId = tileId;
		setPreferredSize(TILE_PANEL_DIMENSION);
		assignTileColor();
		validate();
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
