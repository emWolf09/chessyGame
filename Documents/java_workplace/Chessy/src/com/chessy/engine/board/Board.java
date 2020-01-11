package com.chessy.engine.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.chess.engine.common.Alliance;
import com.chessy.engine.constants.Constants;
import com.chessy.engine.pieces.Piece;
import com.google.common.collect.ImmutableList;

public class Board {
	
	private final List<Tile> gameBoard;
	
	private Board(Builder builder) {
		this.gameBoard = createGameBoard(builder);
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
		return null;
	}
	
	public static class Builder{
		
		Map<Integer,Piece> boardConfig;
		Alliance nextMoveMakerAlliance;
		
		public Builder() {

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
	}
	
}
