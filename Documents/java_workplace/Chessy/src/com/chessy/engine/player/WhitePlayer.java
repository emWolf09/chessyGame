package com.chessy.engine.player;

import java.util.Collection;

import com.chessy.engine.board.Board;
import com.chessy.engine.board.Move;
import com.chessy.engine.pieces.Piece;

public class WhitePlayer extends Player{

	public WhitePlayer(Board board, Collection<Move> whiteStandardLegalMove, Collection<Move> blackStandardLegalMove) {
		super(board, whiteStandardLegalMove, blackStandardLegalMove);
	}

	@Override
	protected Collection<Piece> getActivePieces() {
		return this.board.getWhitePieces();
	}

}
