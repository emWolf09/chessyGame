package com.chessy.engine.board;


import com.chessy.engine.board.Board.Builder;
import com.chessy.engine.pieces.Piece;
import com.chessy.engine.pieces.Rook;

abstract public class CastleMove extends Move{
	
	protected final Rook castleRook;
	protected final int castleRookStart;
	protected final int castleRookDestination;
	
	public CastleMove(Board board, Piece movedPiece, int destinationCordinate,final Rook castleRook,
			final int castleRookDestination,final int castleRookStart) {
		super(board, movedPiece, destinationCordinate);
		this.castleRook = castleRook;
		this.castleRookDestination  = castleRookDestination;
		this.castleRookStart = castleRookStart;
	}
	
	
	public Rook getCastleRook() {
		return castleRook;
	}

	@Override
	public boolean isCastlingMove() {return true;}
	
	@Override
	public Board execute() {
		final Builder builder = new Builder();
		for(final Piece piece:this.board.getCurrentPlayer().getActivePieces()) {
			if(!piece.equals(this.movedPiece) && !piece.equals(this.castleRook)) {
				builder.setPiece(piece);
			}
		}
		for(final Piece piece : this.board.getCurrentPlayer().getOpponent().getActivePieces()) {
			builder.setPiece(piece);
		}
		builder.setPiece(this.movedPiece.movePiece(this));
		builder.setPiece(new Rook(this.castleRook.getPieceAlliance(),this.castleRookDestination));
		//TODO look into rook set first move false condition
		builder.setNextMoveMaker(this.board.getCurrentPlayer().getOpponent().getAlliance());
		return builder.build();
	}
	
	public static class KingSideCastleMove extends CastleMove{
		public KingSideCastleMove(final Board board,final Piece movedPiece,final int destinationCordinate,final Rook castleRook,
									final int castleRookDestination,final int castleRookStart) {
			super(board, movedPiece, destinationCordinate,castleRook,castleRookDestination,castleRookStart);
		}		
		@Override
		public String toString() {
			return "O-O";
		}
	}

	public static class QueenSideCastleMove extends CastleMove{
		public QueenSideCastleMove(final Board board,final Piece movedPiece,final int destinationCordinate,final Rook castleRook,
									final int castleRookDestination,final int castleRookStart) {
			super(board, movedPiece, destinationCordinate,castleRook,castleRookDestination,castleRookStart);
		}
		
		@Override
		public String toString() {
			return "O-O-O";
		}
	}
}


