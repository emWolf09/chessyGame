package com.chessy.engine.board;


import com.chessy.engine.pieces.Piece;
import com.chessy.engine.pieces.Rook;

abstract public class CastleMove extends Move{
	public CastleMove(Board board, Piece movedPiece, int destinationCordinate) {
		super(board, movedPiece, destinationCordinate);
	}
	
	public static class KingSideCastleMove extends CastleMove{
		protected final Rook castleRook;
		protected final int castleRookStart;
		protected final int castleRookDestination;
		
		
		public KingSideCastleMove(final Board board,final Piece movedPiece,final int destinationCordinate,final Rook castleRook,
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
		
		
	}

	public static class QueenSideCastleMove extends CastleMove{
		
		protected final Rook castleRook;
		protected final int castleRookStart;
		protected final int castleRookDestination;
		
		
		public QueenSideCastleMove(final Board board,final Piece movedPiece,final int destinationCordinate,final Rook castleRook,
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
	}
}


