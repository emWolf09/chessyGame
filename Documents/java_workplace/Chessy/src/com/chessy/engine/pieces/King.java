package com.chessy.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chessy.engine.board.AttackMove;
import com.chessy.engine.board.Board;
import com.chessy.engine.board.BoardUtil;
import com.chessy.engine.board.Move;
import com.chessy.engine.board.Tile;
import com.chessy.engine.common.Alliance;
import com.google.common.collect.ImmutableList;

public class King extends Piece{

	private static final int []LEGAL_MOVES = {-8,-1-9,-7,1,9,8,7};
	public King(Alliance pieceAlliance,int piecePostion) {
		super(piecePostion, pieceAlliance,PieceType.KING);
	}
	
	@Override
	public String toString() {
		return Piece.PieceType.KING.toString();
	}

	@Override
	public Collection<Move> calculateLegalMoves(Board board) {
		int destinationCordinate;
		List<Move> legalMovelist = new ArrayList<>();
		for(int i : LEGAL_MOVES) {
			destinationCordinate = this.piecePostion+i;
			
			if(BoardUtil.isValidTileCordinate(destinationCordinate)) {
				if(isFirstColumnExclusion(this.piecePostion,i) ||isEighthColumnExclusion(this.piecePostion,i))
	                continue;
				
				Tile destinationTile = board.getTile(destinationCordinate);
				if(destinationTile.isTileOccupied()) {
					Piece destinationTilePiece = destinationTile.getPiece();
					Alliance alliance = destinationTilePiece.getPieceAlliance();
					if(alliance!=this.pieceAlliance) {
						legalMovelist.add(new AttackMove(board,this,destinationCordinate,destinationTilePiece)); 
					}
				}else {
					legalMovelist.add(new Move.MajorMove(board, this, destinationCordinate));
				}
				
			}
		}
		return ImmutableList.copyOf(legalMovelist);
	}


    private static boolean isFirstColumnExclusion(final int pos, final int off) {
        return BoardUtil.FIRST_COLUMN[pos] && ((off == -9) ||
                (off== -1) || (off== 7));
    }

    private static boolean isEighthColumnExclusion(final int pos, final int off) {
        return BoardUtil.EIGHTH_COLUMN[pos] && ((off == -7) || (off == 1)|| (off == 9));
    }
    
    @Override
	public King movePiece(Move move) {
		return new King(move.getMovedPiece().getPieceAlliance(), move.getDestinationCordinate());
	}

}
