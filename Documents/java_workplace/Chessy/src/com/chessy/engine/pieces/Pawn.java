package com.chessy.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import com.chess.engine.common.Alliance;
import com.chessy.engine.board.Board;
import com.chessy.engine.board.BoardUtil;
import com.chessy.engine.board.Move;
import com.chessy.engine.board.Tile;
import com.google.common.collect.ImmutableList;

public class Pawn extends Piece{
	
	private static final int []LEGAL_MOVES = {8,16,7,9};
	
	public Pawn(final Alliance pieceAlliance,final int piecePostion) {
		super(piecePostion, pieceAlliance);
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
		int destinationCordinate;
		List<Move> legalMovelist = new ArrayList<>();
		
		for(int offset:LEGAL_MOVES) {
			destinationCordinate = this.piecePostion+this.pieceAlliance.getDirection()*offset;
			
			if(BoardUtil.isValidTileCordinate(destinationCordinate)==false)continue;
			
			if(offset==8 && board.getTile(destinationCordinate).isTileOccupied()==false) {
				
				//TODO two case here 1. Non promotion move or promotion move
				legalMovelist.add(new Move.MajorMove(board,this,destinationCordinate));
			}else if(offset==16 && this.isFirstMove() && (
					(this.pieceAlliance.isBlack() && BoardUtil.SECOND_ROW[this.piecePostion])||
					(this.pieceAlliance.isWhite() && BoardUtil.SEVENTH_ROW[this.piecePostion]))) 
			{
				boolean canJump = false;
				Tile tileBehindDesination = board.getTile(this.piecePostion+this.pieceAlliance.getDirection()*8);
				if(!tileBehindDesination.isTileOccupied() && !board.getTile(destinationCordinate).isTileOccupied())canJump = true;
				
				if(canJump) {
					legalMovelist.add(new  Move.MajorMove(board,this,destinationCordinate));
				}
			}else if(offset==7 && 
					!((this.pieceAlliance.isWhite()&&
					BoardUtil.EIGHTH_COLUMN[this.piecePostion])||
							(this.pieceAlliance.isBlack() && BoardUtil.FIRST_COLUMN[this.piecePostion]))) {
				
				//check piece on attacking destination
				Piece destinationPiece = board.getTile(destinationCordinate).getPiece();
				if(destinationPiece.pieceAlliance!=this.pieceAlliance) {
					//TODO promotion or non promotion
					legalMovelist.add(new Move.MajorMove(board,this,destinationCordinate));
				}
				
					
			}else if(offset==9 && 
					!((this.pieceAlliance.isWhite()&&
					BoardUtil.FIRST_COLUMN[this.piecePostion])||
							(this.pieceAlliance.isBlack() && BoardUtil.EIGHTH_COLUMN[this.piecePostion]))) {
				
				//check piece on attacking destination
				Piece destinationPiece = board.getTile(destinationCordinate).getPiece();
				if(destinationPiece.pieceAlliance!=this.pieceAlliance) {
					//TODO promotion or non promotion
					legalMovelist.add(new Move.MajorMove(board,this,destinationCordinate));
				}
			}
		}
		
		return ImmutableList.copyOf(legalMovelist);
	}

}
