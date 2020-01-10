package com.chessy.engine.pieces;

import java.util.ArrayList;
import java.util.List;


import com.chess.engine.common.Alliance;
import com.chessy.engine.board.Board;
import com.chessy.engine.board.BoardUtil;
import com.chessy.engine.board.Move;
import com.chessy.engine.board.Tile;
import com.google.common.collect.ImmutableList;

public final class Knight extends Piece{
	
	private static final int []LEGAL_MOVES = {-17, -15, -10, -6, 6, 10, 15, 17};
	
	Knight(final Alliance pieceAlliance,final int piecePosition){
		super(piecePosition, pieceAlliance);
	}
	@Override
	public List<Move> calculateLegalMoves(Board board) {
		
		int destinationCordinate;
		List<Move> legalMovelist = new ArrayList<>();
		
		for(int i : LEGAL_MOVES) {
			destinationCordinate = this.piecePostion+i;
			
			if(BoardUtil.isValidTileCordinate(destinationCordinate)) {
				
				/*if not valid move for current position*/
				if(isFirstColumnExclusion(this.piecePostion,i)||isSecondColumnExclusion(this.piecePostion,i)
                        ||isSeventhColumnExclusion(this.piecePostion,i)||isEighthColumnExclusion(this.piecePostion,i))
                {
                    continue;
                }
				
				
				Tile destinationTile = board.getTile(destinationCordinate);
				if(destinationTile.isTileOccupied()) {
					Piece destinationTilePiece = destinationTile.getPiece();
					Alliance alliance = destinationTilePiece.getPieceAlliance();
					if(alliance!=this.pieceAlliance) {
						//TODO here add a move with given parameter
						legalMovelist.add(new Move()); 
					}
				}else {
					//TODO add here right move
					legalMovelist.add(new Move());
				}
				
			}
		}
		return ImmutableList.copyOf(legalMovelist);
	}
	
	private static boolean isFirstColumnExclusion(final int pos, final int off) {
        return BoardUtil.FIRST_COLUMN[pos] && ((off == -17) ||
                (off== -10) || (off== 6) || (off == 15));
    }
	

    private static boolean isSecondColumnExclusion(final int pos, final int off) {
        return BoardUtil.SECOND_COLUMN[pos] && ((off == -10) || (off == 6));
    }

    private static boolean isSeventhColumnExclusion(final int pos, final int off) {
        return BoardUtil.SEVENTH_COLUMN[pos] && ((off == -6) || (off == 10));
    }

    private static boolean isEighthColumnExclusion(final int pos, final int off) {
        return BoardUtil.EIGHTH_COLUMN[pos] && ((off == -15) || (off == -6) ||
                (off == 10) || (off == 17));

    }

}
