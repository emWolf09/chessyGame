package com.chessy.engine.board;

import com.chessy.engine.pieces.Piece;

public abstract class Move {
	
	final Board board;
	final Piece movedPiece;
	final int destinationCordinate;
	
	private Move(Board board, Piece movedPiece, int destinationCordinate) {
		super();
		this.board = board;
		this.movedPiece = movedPiece;
		this.destinationCordinate = destinationCordinate;
	}
	
	
	public static class MajorMove extends Move{
		public MajorMove(Board board, Piece movedPiece, int destinationCordinate) {
			super(board, movedPiece, destinationCordinate);
		}	
	}
	
	public static class AttackMove extends Move{
		final Piece attackedPiece;
		public AttackMove(Board board, Piece movedPiece, int destinationCordinate,Piece attackedPiece) {
			super(board, movedPiece, destinationCordinate);
			this.attackedPiece = attackedPiece;
		}	
	}
	
	
	
}
