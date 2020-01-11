package com.chessy.engine.pieces;

import java.util.Collection;

import com.chess.engine.common.Alliance;
import com.chessy.engine.board.Board;
import com.chessy.engine.board.Move;

public abstract class Piece {
	protected final int piecePostion;
	protected final Alliance pieceAlliance;
	protected final boolean isFirstMove;
	
	Piece(final int piecePostion,final Alliance pieceAlliance){
		this.piecePostion = piecePostion;
		this.pieceAlliance = pieceAlliance;
		//TODO 
		this.isFirstMove = false;
	}
	
	public int getPiecePostion() {
		return this.piecePostion;
	}

	public Alliance getPieceAlliance() {
		return this.pieceAlliance;
	}

	public boolean isFirstMove() {
		return this.isFirstMove;
	}

	public abstract Collection<Move> calculateLegalMoves(final Board board);
	
	
	public enum PieceType{
		PAWN("P"),
		ROOK("R"),
		KNIGHT("N"),
		BISHOP("B"),
		QUEEN("Q"),
		KING("K");
		
		String pieceName;
		
		PieceType(String pieceName) {
			this.pieceName = pieceName;
		}
		
		@Override
		public String toString() {
			return this.pieceName.toString();
		}
		
	}
	
	
}
