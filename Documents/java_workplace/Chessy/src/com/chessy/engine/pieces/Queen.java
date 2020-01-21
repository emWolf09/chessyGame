package com.chessy.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chessy.engine.board.Board;
import com.chessy.engine.board.BoardUtil;
import com.chessy.engine.board.Move;
import com.chessy.engine.board.Tile;
import com.chessy.engine.board.Move.AttackMove;
import com.chessy.engine.common.Alliance;
import com.google.common.collect.ImmutableList;

public class Queen extends Piece{
	private static final int []LEGAL_MOVES = {-9,-8,-7,-1,1,7,8,9};

	public Queen(final Alliance pieceAlliance,final int piecePosition) {
		super(piecePosition,pieceAlliance,PieceType.QUEEN);
	}

	
	@Override
	public String toString() {
		return Piece.PieceType.QUEEN.toString();
	}
	
	@Override
	public Collection<Move> calculateLegalMoves(Board board) {
		int destinationCordinate;
		List<Move> legalMovelist = new ArrayList<>();
		
		for(int offset : LEGAL_MOVES) {
			destinationCordinate = this.piecePostion+offset;
			if(checkExclusion(this.piecePostion,offset))continue;
			while(BoardUtil.isValidTileCordinate(destinationCordinate)) {
				if(checkExclusion(destinationCordinate,offset))break;
				Tile destinationTile = board.getTile(destinationCordinate);
				if(destinationTile.isTileOccupied()) {
					//tile has a piece
					Piece destinationPiece = destinationTile.getPiece();
					if(destinationPiece.getPieceAlliance()!=this.getPieceAlliance()) {
						legalMovelist.add(new AttackMove(board,this,this.getPiecePostion(),destinationPiece));
					}
					break;//since can not jump a piece in a move
				}else {
					legalMovelist.add(new Move.MajorMove(board,this,this.getPiecePostion()));
				}
				destinationCordinate+=offset;
			}
		}
		return ImmutableList.copyOf(legalMovelist);
	}
	
	private static boolean checkExclusion(int pos,int off) {
		return isFirstColumnExclusion(pos, off)||isEighthColumnExclusion(pos, off);
	}
	private static boolean isFirstColumnExclusion(final int pos, final int off) {
        return BoardUtil.FIRST_COLUMN[pos] && ((off == -9) || (off== 7)|| (off == -1));
    }
    private static boolean isEighthColumnExclusion(final int pos, final int off) {
        return BoardUtil.EIGHTH_COLUMN[pos] && ((off == -7) || (off== 9)|| (off == 1));
    }
    
    @Override
	public Queen movePiece(Move move) {
		return new Queen(move.getMovedPiece().getPieceAlliance(), move.getDestinationCordinate());
	}
	
}
