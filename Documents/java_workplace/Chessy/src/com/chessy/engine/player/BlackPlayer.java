package com.chessy.engine.player;

import java.util.ArrayList;
import java.util.Collection;

import com.chessy.engine.board.Board;
import com.chessy.engine.board.Move;
import com.chessy.engine.board.Tile;
import com.chessy.engine.common.Alliance;
import com.chessy.engine.pieces.Piece;
import com.google.common.collect.ImmutableList;

public class BlackPlayer extends Player{

	public BlackPlayer(Board board, Collection<Move> blackStandardLegalMove, Collection<Move> opponentMove) {
		super(board, blackStandardLegalMove, opponentMove);
	}

	@Override
	public Collection<Piece> getActivePieces() {
		return this.board.getBlackPieces();
	}

	@Override
	public Alliance getAlliance() {
		return Alliance.BLACK;
	}

	@Override
	public Player getOpponent() {
		return this.board.getWhitePlayer();
	}
	
	@Override
	protected Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentLegals) {
		Collection<Move> kingCastles = new ArrayList<Move>();
		
		if(this.playerKing.isFirstMove() && !isInCheck()) {
			//blacks king side castle
			if(this.board.getTile(5).isTileOccupied()==false && this.board.getTile(6).isTileOccupied()==false) {
				final Tile rookPositionTile = this.board.getTile(7);
				
				if(rookPositionTile.isTileOccupied()) {
					Piece piece = rookPositionTile.getPiece();
					if(piece.getPieceAlliance()==this.getAlliance() && piece.getPieceType().isRook() && piece.isFirstMove()) {
						if(Player.calculateAttackOnTile(6, opponentLegals).isEmpty()) {
							//TODO create a castle move and add it
							kingCastles.add(null);
						}
					}
				}
			}
			
			
			//blacks queen side castle
			if(this.board.getTile(1).isTileOccupied()==false && this.board.getTile(2).isTileOccupied()==false
					&& this.board.getTile(3).isTileOccupied()==false) {
				final Tile rookPositionTile = this.board.getTile(0);
				
				if(rookPositionTile.isTileOccupied()) {
					Piece piece = rookPositionTile.getPiece();
					if(piece.getPieceAlliance()==this.getAlliance() && piece.getPieceType().isRook() && piece.isFirstMove()) {
						//TODO create a castle move and add it
						kingCastles.add(null);
					}
				}
			}
			
		}
		return ImmutableList.copyOf(kingCastles);
	}

}
