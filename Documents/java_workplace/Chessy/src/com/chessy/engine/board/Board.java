package com.chessy.engine.board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.chessy.engine.common.Alliance;
import com.chessy.engine.constants.Constants;
import com.chessy.engine.pieces.Pawn;
import com.chessy.engine.pieces.Piece;
import com.chessy.engine.player.BlackPlayer;
import com.chessy.engine.player.Player;
import com.chessy.engine.player.WhitePlayer;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;


public class Board {
	
	private final List<Tile> gameBoard;
	private final Collection<Piece> whitePieces;
	private final Collection<Piece> blackPieces;
	private final WhitePlayer whitePlayer;
	private final BlackPlayer blackPlayer;
	private final Player currentPlayer;
	private final Pawn enPassantPawn;
	private Board(Builder builder) {
		
		this.gameBoard = createGameBoard(builder);
		this.whitePieces = calculateActivePiece(this.gameBoard,Alliance.WHITE);
		this.blackPieces = calculateActivePiece(this.gameBoard,Alliance.BLACK);
		final Collection<Move> whiteStandardLegalMove = calculateLegalMove(this.whitePieces);
		final Collection<Move> blackStandardLegalMove = calculateLegalMove(this.blackPieces);
		this.whitePlayer = new WhitePlayer(this,whiteStandardLegalMove,blackStandardLegalMove);
		this.blackPlayer = new BlackPlayer(this,blackStandardLegalMove,whiteStandardLegalMove);
		
		this.currentPlayer = builder.nextMoveMakerAlliance.choosePlayer(this.whitePlayer,this.blackPlayer);
		this.enPassantPawn = builder.enPassantPawn;
	}
	
	@Override
	public String toString() {
		StringBuilder boardString = new StringBuilder("");
		for(int i = 0;i<Constants.TILES_SIZE;i++) {
			String tileString = this.gameBoard.get(i).toString();
			boardString.append(String.format("%3s",tileString));
			if((i+1)%Constants.NUM_TILES_PER_ROW==0) {
				boardString.append("\n");
			}
		}
		
		return boardString.toString();
	}
	
	
	public Player getCurrentPlayer() {
		return this.currentPlayer;
	}
	
	public Collection<Piece> getWhitePieces() {
		return this.whitePieces;
	}

	public Collection<Piece> getBlackPieces() {
		return this.blackPieces;
	}
	
	public Pawn getEnPassantPawn() {
		return this.enPassantPawn;
	}
	
	public Iterable<Move> getAllLegalMoves(){
		/*
		 * copied code here merge both moves
		 */
		return Iterables.unmodifiableIterable(
					Iterables.concat(this.whitePlayer.getLegalMoves(),this.blackPlayer.getLegalMoves())
				);
	}
	
	private Collection<Move> calculateLegalMove(Collection<Piece> activePieces) {
		final List<Move> legalMoves = new ArrayList<Move>();
		for(Piece piece : activePieces) {
			legalMoves.addAll(piece.calculateLegalMoves(this));
		}
		return ImmutableList.copyOf(legalMoves);
	}

	private static Collection<Piece> calculateActivePiece(final List<Tile> gameBoard,final Alliance currentAlliance) {
		List<Piece> acticePieces = new ArrayList<Piece>();
		for(int i = 0;i<Constants.TILES_SIZE;i++) {
			Piece pieceOnTile = gameBoard.get(i).getPiece();
			if(pieceOnTile!=null && pieceOnTile.getPieceAlliance()==currentAlliance) {
				acticePieces.add(pieceOnTile);
			}
		}
		return ImmutableList.copyOf(acticePieces);
	}

	public static Board createStandardBoard() {
		return StandardBoard.createStandardBoard().build();
	}
	
	private List<Tile> createGameBoard(Builder builder) {
		List<Tile> tiles = new ArrayList<>(Constants.TILES_SIZE);
		for(int i = 0;i<Constants.TILES_SIZE;i++) {
			Tile tile = Tile.createTile(i, builder.boardConfig.get(i));
			tiles.add(i,tile);
		}
		return ImmutableList.copyOf(tiles);
	}

	
	
	public Tile getTile(int cordinate) {
		return gameBoard.get(cordinate);
	}
	
	public static class Builder{
		
		Map<Integer,Piece> boardConfig;
		Alliance nextMoveMakerAlliance;
		private Pawn enPassantPawn;
		public Builder() {
			this.boardConfig = new HashMap<Integer, Piece>();
		}
		
		public Builder setPiece(final Piece piece) {
			this.boardConfig.put(piece.getPiecePostion(),piece);
			return this;
		}
		
		public Builder setNextMoveMaker(final Alliance nextMoveMakeAlliance) {
			this.nextMoveMakerAlliance = nextMoveMakeAlliance;
			return this;
		}
		
		public Board build() {
			return new Board(this);
		}

		public void setEnpassantPawn(Pawn movedPawn) {
			this.enPassantPawn = movedPawn;
			
		}
	}

	public Player getWhitePlayer() {
		return this.whitePlayer;
	}
	
	public Player getBlackPlayer() {
		return this.blackPlayer;
	}
}
