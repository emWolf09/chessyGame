package com.chessy.engine.player;

import java.util.Collection;

import com.chessy.engine.board.Board;
import com.chessy.engine.board.Move;
import com.chessy.engine.pieces.Piece;

public class BlackPlayer extends Player{

	public BlackPlayer(Board board, Collection<Move> blackStandardLegalMove, Collection<Move> opponentMove) {
		super(board, blackStandardLegalMove, opponentMove);
	}

	@Override
	protected Collection<Piece> getActivePieces() {
		return this.board.getBlackPieces();
	}

}
