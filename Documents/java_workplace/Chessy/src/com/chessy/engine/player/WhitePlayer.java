package com.chessy.engine.player;

import java.util.Collection;

import com.chess.engine.common.Alliance;
import com.chessy.engine.board.Board;
import com.chessy.engine.board.Move;
import com.chessy.engine.pieces.Piece;

public class WhitePlayer extends Player{

	public WhitePlayer(Board board, Collection<Move> whiteStandardLegalMove, Collection<Move> blackStandardLegalMove) {
		super(board, whiteStandardLegalMove, blackStandardLegalMove);
	}

	@Override
	public Collection<Piece> getActivePieces() {
		return this.board.getWhitePieces();
	}

	@Override
	public Alliance getAlliance() {
		return Alliance.WHITE;
	}

	@Override
	public Player getOpponent() {
		return this.board.getBlackPlayer();
	}

}
