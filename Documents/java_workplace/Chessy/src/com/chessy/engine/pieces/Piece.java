package com.chessy.engine.pieces;

import java.util.Collection;

import com.chess.engine.common.Alliance;
import com.chessy.engine.board.Board;
import com.chessy.engine.board.Move;

public abstract class Piece {
	protected final int piecePostion;
	protected final Alliance pieceAlliance;
	
	Piece(final int piecePostion,final Alliance pieceAlliance){
		this.piecePostion = piecePostion;
		this.pieceAlliance = pieceAlliance;
	}
	
	public int getPiecePostion() {
		return piecePostion;
	}

	public Alliance getPieceAlliance() {
		return pieceAlliance;
	}

	public abstract Collection<Move> calculateLegalMoves(final Board board);
	
}
