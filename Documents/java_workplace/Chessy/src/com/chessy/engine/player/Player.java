package com.chessy.engine.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chessy.engine.board.Board;
import com.chessy.engine.board.Move;
import com.chessy.engine.common.Alliance;
import com.chessy.engine.pieces.King;
import com.chessy.engine.pieces.Piece;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

public abstract class Player {
	protected final Board board;
	protected final King playerKing;
	protected final Collection<Move> legalMove;
	private final boolean isInCheck;
	
	public Player(final Board board,final Collection<Move> legalMove,final Collection<Move> opponentMove) {
		this.board = board;
		this.playerKing = establishKing();
		this.isInCheck = !Player.calculateAttackOnTile(this.playerKing.getPiecePostion(),opponentMove).isEmpty();
		this.legalMove = ImmutableList.copyOf(Iterables.concat(legalMove,calculateKingCastles(legalMove, opponentMove)));
	}
	
	//calculate all those attacks on a given destination tile
	public static Collection<Move> calculateAttackOnTile(int piecePosition,Collection<Move> moves){
		List<Move> attackMove = new ArrayList<>();
		for(Move currentMove:moves) {
			if(currentMove.getDestinationCordinate()==piecePosition)attackMove.add(currentMove);
		}
		
		return ImmutableList.copyOf(attackMove);
	}
	
	public Collection<Move> getLegalMoves(){
		return this.legalMove;
	}
	
	public King getPlayerKing() {
		return this.playerKing;
	}
	
	private King establishKing() {
		List<Piece> pieceList = new ArrayList<Piece>(getActivePieces());
		for(Piece piece : pieceList) {
			if(piece.getPieceType().isKing()) {
				return (King)piece;
			}
		}
		
		throw new RuntimeException("Failed to establish king ::A player without no king is invalid");
	}
	
	public boolean isMoveLegal(Move move) {
		return this.legalMove.contains(move);
	}
	
	public MoveTransition makeMove(Move move) {
		if(!isMoveLegal(move)) {
			return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
		}
		
		final Board transitionBoard = move.execute();
		final Collection<Move> kingAttacks = Player.calculateAttackOnTile(transitionBoard.getCurrentPlayer().getOpponent().getPlayerKing().getPiecePostion()
				, transitionBoard.getCurrentPlayer().getLegalMoves());
		if(!kingAttacks.isEmpty()) {
			return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
		}
		return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
	}
	
	public boolean isInCheck() {
		return this.isInCheck;
	}
	
	public boolean isInCheckMate() {
		return this.isInCheck && !hasEscapeMove();
	}
	
	protected  boolean hasEscapeMove() {
		for(Move move:legalMove) {
			MoveTransition transition = makeMove(move);
			if(transition.getMoveStatus().isDone())return true;
		}
		return false;
	}

	public boolean isInStaleMate() {
		return !isInCheck && !hasEscapeMove();
	}
	
	public boolean isCastled() {
		//TODO
		return false;
	}
	
	public abstract Collection<Piece> getActivePieces();
	public abstract Alliance getAlliance();
	public abstract Player getOpponent();
	protected abstract Collection<Move> calculateKingCastles(Collection<Move> playerLegals,Collection<Move> opponentLegals);
	
	
	
}
