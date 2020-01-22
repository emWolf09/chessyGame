package com.chessy.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import com.chessy.engine.board.BoardUtil;


@SuppressWarnings("serial")
public class TilePanel extends JPanel{
	private static final Dimension TILE_PANEL_DIMENSION = new Dimension(Constants.TILE_PANEL_WIDTH,Constants.TILE_PANEL_WIDTH);
	private final int tileId;
	
	private Color lightTileColor = Color.decode("#FFFACD");
    private Color darkTileColor = Color.decode("#593E1A");
 
	
	public TilePanel(final BoardPanel boardPanel,final int tileId) {
		super(new GridBagLayout());
		this.tileId = tileId;
		setPreferredSize(TILE_PANEL_DIMENSION);
		assignTileColor();
		validate();
	}

	private void assignTileColor() {
		int i = this.tileId;
        if(BoardUtil.EIGTH_RANK[i] || BoardUtil.SIXTH_RANK[i] || BoardUtil.FOURTH_RANK[i] ||BoardUtil.SECOND_RANK[i])
           {
                setBackground(this.tileId%2==0?lightTileColor:darkTileColor);
            }
        else if(BoardUtil.SEVENTH_RANK[i]|| BoardUtil.FIFTH_RANK[i] || BoardUtil.THIRD_RANK[i] || BoardUtil.FIRST_RANK[i] )
          {
               setBackground(this.tileId%2!=0?lightTileColor:darkTileColor);
            }

    }
}
