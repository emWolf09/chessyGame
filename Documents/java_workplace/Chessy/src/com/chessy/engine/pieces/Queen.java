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

public class Queen extends Piece{
	private static final int []LEGAL_MOVES = {-9,-8,-7,-1,1,7,8,9};

	public Queen(final Alliance pieceAlliance,final int piecePosition) {
		super(piecePosition,pieceAlliance,PieceType.QUEEN,true);
	}
	
	public Queen(final Alliance pieceAlliance,final int piecePosition,final boolean isFirstMove) {
		super(piecePosition,pieceAlliance,PieceType.QUEEN,isFirstMove);
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
			destinationCordinate = this.piecePostion;
			boolean flagValidCordinate = true;
			while(flagValidCordinate) {
				if(checkExclusion(destinationCordinate,offset))break;
				destinationCordinate+=offset;
				flagValidCordinate = BoardUtil.isValidTileCordinate(destinationCordinate);
				if(flagValidCordinate){
					Tile destinationTile = board.getTile(destinationCordinate);
					if(destinationTile.isTileOccupied()) {
						//tile has a piece
						Piece destinationPiece = destinationTile.getPiece();
						if(destinationPiece.getPieceAlliance()!=this.getPieceAlliance()) {
							legalMovelist.add(new AttackMove(board,this,destinationCordinate,destinationPiece));
						}
						break;//since can not jump a piece in a move
					}else {
						legalMovelist.add(new Move.MajorMove(board,this,destinationCordinate));
					}
				}
			}
		}
		return ImmutableList.copyOf(legalMovelist);
	}
	
	//current is position ---->pos
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
