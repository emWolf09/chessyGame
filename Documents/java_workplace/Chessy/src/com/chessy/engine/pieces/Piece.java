package com.chessy.engine.pieces;

import java.util.Collection;

import com.chess.engine.common.Alliance;
import com.chessy.engine.board.Board;
import com.chessy.engine.board.Move;

public abstract class Piece {
	protected final int piecePostion;
	protected final Alliance pieceAlliance;
	protected final boolean isFirstMove;
	protected final PieceType pieceType;
	Piece(final int piecePostion,final Alliance pieceAlliance,final PieceType pieceType){
		this.piecePostion = piecePostion;
		this.pieceAlliance = pieceAlliance;
		//TODO 
		this.isFirstMove = false;
		this.pieceType = pieceType;
	}
	
	public int getPiecePostion() {
		return this.piecePostion;
	}
	
	public PieceType getPieceType() {
		return this.pieceType;
	}

	public Alliance getPieceAlliance() {
		return this.pieceAlliance;
	}

	public boolean isFirstMove() {
		return this.isFirstMove;
	}

	public abstract Collection<Move> calculateLegalMoves(final Board board);
	
	
	public enum PieceType{
		PAWN("P") {
			@Override
			public boolean isKing() {
				return false;
			}
		},
		ROOK("R") {
			@Override
			public boolean isKing() {
				return false;
			}
		},
		KNIGHT("N") {
			@Override
			public boolean isKing() {
				return false;
			}
		},
		BISHOP("B") {
			@Override
			public boolean isKing() {
				return false;
			}
		},
		QUEEN("Q") {
			@Override
			public boolean isKing() {
				return false;
			}
		},
		KING("K") {
			@Override
			public boolean isKing() {
				return true;
			}
		};
		
		String pieceName;
		
		PieceType(String pieceName) {
			this.pieceName = pieceName;
		}
		
		@Override
		public String toString() {
			return this.pieceName.toString();
		}
		
		public abstract boolean isKing();
		
	}
	
	
}
