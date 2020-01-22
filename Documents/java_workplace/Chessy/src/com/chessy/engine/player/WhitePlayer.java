package com.chessy.engine.player;

import java.util.ArrayList;
import java.util.Collection;

import com.chessy.engine.board.Board;
import static com.chessy.engine.board.CastleMove.*;
import com.chessy.engine.board.Move;
import com.chessy.engine.board.Tile;
import com.chessy.engine.common.Alliance;
import com.chessy.engine.pieces.Piece;
import com.chessy.engine.pieces.Rook;
import com.google.common.collect.ImmutableList;

public class WhitePlayer extends Player{

	public WhitePlayer(final Board board,final Collection<Move> whiteStandardLegalMove,final Collection<Move> blackStandardLegalMove) {
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
	protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals, final Collection<Move> opponentLegals) {
		Collection<Move> kingCastles = new ArrayList<Move>();
		
		if(this.playerKing.isFirstMove() && !isInCheck()) {
			//whites king side castle
			if(this.board.getTile(61).isTileOccupied()==false && this.board.getTile(62).isTileOccupied()==false) {
				final Tile rookPositionTile = this.board.getTile(63);
				
				if(rookPositionTile.isTileOccupied()) {
					Piece piece = rookPositionTile.getPiece();
					if(piece.getPieceAlliance()==this.getAlliance() && piece.getPieceType().isRook() && piece.isFirstMove()) {
						if(Player.calculateAttackOnTile(62, opponentLegals).isEmpty() && Player.calculateAttackOnTile(61, opponentLegals).isEmpty()) {
							kingCastles.add(new KingSideCastleMove(board,
														this.playerKing, 62, (Rook)piece, 
														61,//destination
														63));//start
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
					if(piece.getPieceAlliance()==this.getAlliance() && piece.getPieceType().isRook() && piece.isFirstMove()
							&& Player.calculateAttackOnTile(58, opponentLegals).isEmpty()
							&& Player.calculateAttackOnTile(59, opponentLegals).isEmpty()
							) {
						//TODO create a castle move and add it
						kingCastles.add(new QueenSideCastleMove(board,
										this.playerKing, 58, 
										(Rook)piece,
										59, //destination
										56)//start
										);
					}
				}
			}
			
		}
		return ImmutableList.copyOf(kingCastles);
	}

}
