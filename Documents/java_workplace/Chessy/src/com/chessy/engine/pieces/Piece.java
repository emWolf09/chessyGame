package com.chessy.engine.pieces;

import java.util.Collection;

import com.chessy.engine.board.Board;
import com.chessy.engine.board.Move;
import com.chessy.engine.common.Alliance;

public abstract class Piece {
	protected final int piecePostion;
	protected final Alliance pieceAlliance;
	protected final boolean isFirstMove;
	protected final PieceType pieceType;
	//cached hash code
	private final int hashCode;
	Piece(final int piecePostion,final Alliance pieceAlliance,final PieceType pieceType){
		this.piecePostion = piecePostion;
		this.pieceAlliance = pieceAlliance;
		//TODO 
		this.isFirstMove = false;
		this.pieceType = pieceType;
		this.hashCode = computeHashCode();
	}
	
	private int computeHashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isFirstMove ? 1 : 0);
		result = prime * result + pieceAlliance.hashCode();
		result = prime * result + piecePostion;
		result = prime * result + pieceType.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this==obj)return true;
		
		if(!(obj instanceof Piece))return false;
		
		final Piece otherPiece = (Piece)obj;
		return this.pieceAlliance==otherPiece.pieceAlliance&&piecePostion==otherPiece.piecePostion&&
				pieceType==otherPiece.pieceType&&isFirstMove==otherPiece.isFirstMove;
	} 
	
	@Override
	public int hashCode() {
		return this.hashCode;
	}

	public abstract Piece movePiece(final Move move);
	
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

			@Override
			public boolean isRook() {
				return false;
			}
		},
		ROOK("R") {
			@Override
			public boolean isKing() {
				return false;
			}
			@Override
			public boolean isRook() {
				return true;
			}
		},
		KNIGHT("N") {
			@Override
			public boolean isKing() {
				return false;
			}
			@Override
			public boolean isRook() {
				return false;
			}
		},
		BISHOP("B") {
			@Override
			public boolean isKing() {
				return false;
			}
			@Override
			public boolean isRook() {
				return false;
			}
		},
		QUEEN("Q") {
			@Override
			public boolean isKing() {
				return false;
			}
			@Override
			public boolean isRook() {
				return false;
			}
		},
		KING("K") {
			@Override
			public boolean isKing() {
				return true;
			}
			@Override
			public boolean isRook() {
				return false;
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

		public abstract boolean isRook();
		
	}
	
	
}
