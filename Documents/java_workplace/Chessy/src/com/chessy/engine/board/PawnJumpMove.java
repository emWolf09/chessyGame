package com.chessy.engine.board;

import java.util.Collection;

import com.chessy.engine.board.Board.Builder;
import com.chessy.engine.pieces.Pawn;
import com.chessy.engine.pieces.Piece;

public class PawnJumpMove extends Move {
	public PawnJumpMove(final Board board,final Piece movedPiece,final int destinationCordinate) {
		super(board, movedPiece, destinationCordinate);
	}
	
	@Override
	public Board execute() {
		final Builder builder = new Builder();
		Collection<Piece> pieces = this.board.getCurrentPlayer().getActivePieces();
		for(Piece piece : pieces) {
			if(piece!=this.movedPiece) {
				builder.setPiece(piece);
			}
		}
		
		pieces = this.board.getCurrentPlayer().getOpponent().getActivePieces();
		for(Piece piece : pieces) {
			builder.setPiece(piece);
		}
		final Pawn movedPawn = (Pawn)this.movedPiece.movePiece(this);
		builder.setPiece(movedPawn);
		builder.setEnpassantPawn(movedPawn);
		builder.setNextMoveMaker(this.board.getCurrentPlayer().getOpponent().getAlliance());
		return builder.build();
	}
	
	@Override
    public String toString() {
        return BoardUtil.getPositionAtCordinate(this.destinationCordinate);
    }
}
