package com.chessy.engine.board;

import java.util.HashMap;
import java.util.Map;

import com.chessy.engine.constants.Constants;
import com.chessy.engine.pieces.Piece;
import com.google.common.collect.ImmutableMap;

public abstract class Tile {
	protected final int tileCordinate; // 0 to 63
	
	//create all possible empty tiles for upfront and return it whenever required to save unwanted object creation memory 
	private static final Map<Integer,EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();
	
	private Tile(int cordinate){
		this.tileCordinate = cordinate;
	}
	
	//static method to create all emptytiles
	private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
		final Map<Integer,EmptyTile> emptyTileMap = new HashMap<>();
		for(int i=0;i<Constants.TILES_SIZE;i++) {
			emptyTileMap.put(i, new EmptyTile(i));
		}
		return ImmutableMap.copyOf(emptyTileMap);
	}
	
	//abstract methods 
	public abstract boolean isTileOccupied();
	public abstract Piece getPiece();
	
	//factory method for tile creation
	public static Tile createTile(final int cordinate,final Piece piece) {
		if(piece==null) {
			return EMPTY_TILES_CACHE.get(cordinate);
		}else return new OccupiedTile(cordinate, piece);
	}
	
	
	
	/*
	 * There can be two types of tiles 1. empty tile and 2. occupied tile
	 * We give to subclasses of tile as above
	 */
	
	public static final class EmptyTile extends Tile{
		
		private EmptyTile(final int cordinate) {
			super(cordinate);
		}
		
		@Override
		public String toString() {
			return "-";
		}
		@Override
		public boolean isTileOccupied() {
			return false;
		}

		@Override
		public Piece getPiece() {
			return null;
		}
		
	}
	
	public static final class OccupiedTile extends Tile{
		private final Piece pieceOnTile;
		
		private OccupiedTile(int cordinate,Piece piece) {
			super(cordinate);
			this.pieceOnTile = piece;
		}
		
		@Override
		public String toString() {
			if(this.getPiece().getPieceAlliance().isBlack())
			return this.getPiece().toString().toLowerCase();	
			else return this.getPiece().toString();
		}
		@Override
		public boolean isTileOccupied() {
			return true;
		}

		@Override
		public Piece getPiece() {
			return this.pieceOnTile;
		}
		
	}
	
}
