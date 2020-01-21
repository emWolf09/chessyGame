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

public class Rook extends Piece{
	
	private static final int []LEGAL_MOVES = {-1,-8,1,8};
	
	public Rook(Alliance pieceAlliance,int piecePosition){
		super(piecePosition, pieceAlliance,PieceType.ROOK);
	}
	
	@Override
	public String toString() {
		return Piece.PieceType.ROOK.toString();
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
        return BoardUtil.FIRST_COLUMN[pos] && ((off == -1));
    }
    private static boolean isEighthColumnExclusion(final int pos, final int off) {
        return BoardUtil.EIGHTH_COLUMN[pos] && ((off == 1));
    }
    
    @Override
	public Rook movePiece(Move move) {
		return new Rook(move.getMovedPiece().getPieceAlliance(), move.getDestinationCordinate());
	}

}
