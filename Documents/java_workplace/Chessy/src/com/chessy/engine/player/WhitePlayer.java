package com.chessy.engine.player;

import java.util.ArrayList;
import java.util.Collection;

import com.chessy.engine.board.Board;
import com.chessy.engine.board.Move;
import com.chessy.engine.board.Tile;
import com.chessy.engine.common.Alliance;
import com.chessy.engine.pieces.Piece;
import com.google.common.collect.ImmutableList;

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

	@Override
	protected Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentLegals) {
		Collection<Move> kingCastles = new ArrayList<Move>();
		
		if(this.playerKing.isFirstMove() && !isInCheck()) {
			//whites king side castle
			if(this.board.getTile(61).isTileOccupied()==false && this.board.getTile(62).isTileOccupied()==false) {
				final Tile rookPositionTile = this.board.getTile(63);
				
				if(rookPositionTile.isTileOccupied()) {
					Piece piece = rookPositionTile.getPiece();
					if(piece.getPieceAlliance()==this.getAlliance() && piece.getPieceType().isRook() && piece.isFirstMove()) {
						if(Player.calculateAttackOnTile(62, opponentLegals).isEmpty()) {
							//TODO create a castle move and add it
							kingCastles.add(null);
						}
					}
				}
			}
			
			
			//white queen side castle
			if(this.board.getTile(59).isTileOccupied()==false && this.board.getTile(58).isTileOccupied()==false
					&& this.board.getTile(57).isTileOccupied()==false) {
				final Tile rookPositionTile = this.board.getTile(56);
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
