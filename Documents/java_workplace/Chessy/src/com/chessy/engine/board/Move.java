package com.chessy.engine.board;

import com.chessy.engine.board.Board.Builder;
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
	
	public int getDestinationCordinate() {
		return this.destinationCordinate;
	}
	
	public abstract Board execute();
	
	public static class MajorMove extends Move{
		public MajorMove(Board board, Piece movedPiece, int destinationCordinate) {
			super(board, movedPiece, destinationCordinate);
		}

		@Override
		public Board execute() {
			final Builder builder = new Builder();
			for(final Piece piece:this.board.getCurrentPlayer().getActivePieces()) {
				//TODO hashcode and equals for pieces
				if(!piece.equals(this.movedPiece)) {
					builder.setPiece(piece);
				}
			}
			for(final Piece piece : this.board.getCurrentPlayer().getOpponent().getActivePieces()) {
				builder.setPiece(piece);
			}
			//TODO add moved piece to moved destination
			builder.setPiece(null);
			builder.setNextMoveMaker(this.board.getCurrentPlayer().getOpponent().getAlliance());
			return builder.build();
		}	
	}
	
	public static class AttackMove extends Move{
		final Piece attackedPiece;
		public AttackMove(Board board, Piece movedPiece, int destinationCordinate,Piece attackedPiece) {
			super(board, movedPiece, destinationCordinate);
			this.attackedPiece = attackedPiece;
		}
		@Override
		public Board execute() {
			// TODO Auto-generated method stub
			return null;
		}	
	}

	
	
}
