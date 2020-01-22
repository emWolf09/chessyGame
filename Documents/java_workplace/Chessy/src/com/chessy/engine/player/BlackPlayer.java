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

public class BlackPlayer extends Player{

	public BlackPlayer(final Board board,final Collection<Move> blackStandardLegalMove,final Collection<Move> opponentMove) {
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
	protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals,final Collection<Move> opponentLegals) {
		Collection<Move> kingCastles = new ArrayList<Move>();
		
		if(this.playerKing.isFirstMove() && !isInCheck()) {
			//blacks king side castle
			if(this.board.getTile(5).isTileOccupied()==false && this.board.getTile(6).isTileOccupied()==false) {
				final Tile rookPositionTile = this.board.getTile(7);
				
				if(rookPositionTile.isTileOccupied()) {
					Piece piece = rookPositionTile.getPiece();
					if(piece.getPieceAlliance()==this.getAlliance() && piece.getPieceType().isRook() && piece.isFirstMove()) {
						if(Player.calculateAttackOnTile(6, opponentLegals).isEmpty()) {
							kingCastles.add(new KingSideCastleMove(board, 
											this.playerKing, 6, 
											(Rook)piece, 
											5, //dest
											7)); //start
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
						if(Player.calculateAttackOnTile(2,opponentLegals).isEmpty() && Player.calculateAttackOnTile(3,opponentLegals).isEmpty()) {
							kingCastles.add(new QueenSideCastleMove(board, 
									this.playerKing, 2, 
									(Rook)piece, 
									3, //dest
									0));//start
						}
					}
				}
			}
			
		}
		return ImmutableList.copyOf(kingCastles);
	}

}
