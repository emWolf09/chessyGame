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
	
	public Piece getMovedPiece() {
		return this.movedPiece;
	}
	
	public int getDestinationCordinate() {
		return this.destinationCordinate;
	}
	
	
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
		builder.setPiece(this.movedPiece.movePiece(this));
		builder.setNextMoveMaker(this.board.getCurrentPlayer().getOpponent().getAlliance());
		return builder.build();
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
		@Override
		public Board execute() {
			// TODO Auto-generated method stub
			return null;
		}	
	}

	public static class PawnMove extends Move{
		public PawnMove(final Board board,final Piece movedPiece,final int destinationCordinate) {
			super(board, movedPiece, destinationCordinate);
		}
	}

	public static class PawnAttackMove extends AttackMove{
		final Piece attackedPiece;
		public PawnAttackMove(Board board, Piece movedPiece, int destinationCordinate,Piece attackedPiece) {
			super(board, movedPiece, destinationCordinate,attackedPiece);
			this.attackedPiece = attackedPiece;
		}
		@Override
		public Board execute() {
			// TODO Auto-generated method stub
			return null;
		}	
	}
	
	
	public static class PawnEnPassantAttackMove extends PawnAttackMove{
		final Piece attackedPiece;
		public PawnEnPassantAttackMove(Board board, Piece movedPiece, int destinationCordinate,Piece attackedPiece) {
			super(board, movedPiece, destinationCordinate,attackedPiece);
			this.attackedPiece = attackedPiece;
		}
		@Override
		public Board execute() {
			// TODO Auto-generated method stub
			return null;
		}	
	}
	
	public static class PawnJumpMove extends Move{
		public PawnJumpMove(final Board board,final Piece movedPiece,final int destinationCordinate) {
			super(board, movedPiece, destinationCordinate);
		}
	}
	
	/*
	 * castle move abstract class
	 */
	static abstract class CastleMove extends Move{
		public CastleMove(Board board, Piece movedPiece, int destinationCordinate) {
			super(board, movedPiece, destinationCordinate);
		}
	}
	public static class KingSideCastleMove extends CastleMove{
		public KingSideCastleMove(final Board board,final Piece movedPiece,final int destinationCordinate) {
			super(board, movedPiece, destinationCordinate);
		}
	}
	
	public static class QueenSideCastleMove extends CastleMove{
		public QueenSideCastleMove(final Board board,final Piece movedPiece,final int destinationCordinate) {
			super(board, movedPiece, destinationCordinate);
		}
	}
	
	public static class NullMove extends Move{
		public NullMove(final Board board,final Piece movedPiece,final int destinationCordinate) {
			super(board, movedPiece, destinationCordinate);
		}
	}
}
