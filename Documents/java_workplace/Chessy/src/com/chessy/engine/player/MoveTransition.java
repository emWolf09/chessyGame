package com.chessy.engine.player;

import com.chessy.engine.board.Board;
import com.chessy.engine.board.Move;

public class MoveTransition {
	private final Board transitionBoard;
	private final Move move;
	private final MoveStatus moveStatus;
	
	
	public MoveTransition(Board transitionBoard, Move move, MoveStatus moveStatus) {
		this.transitionBoard = transitionBoard;
		this.move = move;
		this.moveStatus = moveStatus;
	}


	public MoveStatus getMoveStatus() {
		return this.moveStatus;
	}
	
	
}
