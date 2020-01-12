package com.chessy.engine.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chessy.engine.board.Board;
import com.chessy.engine.board.Move;
import com.chessy.engine.pieces.King;
import com.chessy.engine.pieces.Piece;

public abstract class Player {
	protected final Board board;
	protected final King playerKing;
	protected final Collection<Move> legalMove;
	public Player(final Board board,final Collection<Move> legalMove,final Collection<Move> opponentMove) {
		this.board = board;
		this.legalMove = legalMove;
		this.playerKing = establishKing();
	}
	protected King establishKing() {
		List<Piece> pieceList = new ArrayList<Piece>(getActivePieces());
		for(Piece piece : pieceList) {
			if(piece.getPieceType().isKing()) {
				return (King)piece;
			}
		}
		
		throw new RuntimeException("Failed to establish king ::A player without no king is invalid");
	}
	protected abstract Collection<Piece> getActivePieces();
	
	
}
