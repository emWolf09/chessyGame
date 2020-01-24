package com.chessy.engine.board;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chessy.engine.constants.Constants;

public class BoardUtil {
	
	public static final boolean[] SEVENTH_COLUMN = {
            false,false,false,false,false,false,true,false,
            false,false,false,false,false,false,true,false,
            false,false,false,false,false,false,true,false,
            false,false,false,false,false,false,true,false,
            false,false,false,false,false,false,true,false,
            false,false,false,false,false,false,true,false,
            false,false,false,false,false,false,true,false,
            false,false,false,false,false,false,true,false
    };
    public static final boolean[] EIGHTH_COLUMN = {
            false,false,false,false,false,false,false,true,
            false,false,false,false,false,false,false,true,
            false,false,false,false,false,false,false,true,
            false,false,false,false,false,false,false,true,
            false,false,false,false,false,false,false,true,
            false,false,false,false,false,false,false,true,
            false,false,false,false,false,false,false,true,
            false,false,false,false,false,false,false,true
    };
    public static final boolean[] SIXTH_COLUMN = {
            false,false,false,false,false,true,false,false,
            false,false,false,false,false,true,false,false,
            false,false,false,false,false,true,false,false,
            false,false,false,false,false,true,false,false,
            false,false,false,false,false,true,false,false,
            false,false,false,false,false,true,false,false,
            false,false,false,false,false,true,false,false,
            false,false,false,false,false,true,false,false
    };
    public static final boolean[] FIFTH_COLUMN = {
            false,false,false,false,true,false,false,false,
            false,false,false,false,true,false,false,false,
            false,false,false,false,true,false,false,false,
            false,false,false,false,true,false,false,false,
            false,false,false,false,true,false,false,false,
            false,false,false,false,true,false,false,false,
            false,false,false,false,true,false,false,false,
            false,false,false,false,true,false,false,false
    };

    public static final boolean[] SECOND_COLUMN = {
            false,true,false,false,false,false,false,false,
            false,true,false,false,false,false,false,false,
            false,true,false,false,false,false,false,false,
            false,true,false,false,false,false,false,false,
            false,true,false,false,false,false,false,false,
            false,true,false,false,false,false,false,false,
            false,true,false,false,false,false,false,false,
            false,true,false,false,false,false,false,false
    };
    public static final boolean[] FIRST_COLUMN = {
            true,false,false,false,false,false,false,false,
            true,false,false,false,false,false,false,false,
            true,false,false,false,false,false,false,false,
            true,false,false,false,false,false,false,false,
            true,false,false,false,false,false,false,false,
            true,false,false,false,false,false,false,false,
            true,false,false,false,false,false,false,false,
            true,false,false,false,false,false,false,false
    };
    
    
  //remaining colomn and rows
    public static final boolean[] FOURTH_COLUMN = initColumn(3);
    public static final boolean[] THIRD_COLUMN = initColumn(2);
    
    
    
    public static final boolean[] SEVENTH_RANK = {
            false,false,false,false,false,false,false,false,
            true,true,true,true,true,true,true,true,
            false,false,false,false,false,false,false,false,
            false,false,false,false,false,false,false,false,
            false,false,false,false,false,false,false,false,
            false,false,false,false,false,false,false,false,
            false,false,false,false,false,false,false,false,
            false,false,false,false,false,false,false,false
    };
    public static final boolean[] SECOND_RANK = {
            false,false,false,false,false,false,false,false,
            false,false,false,false,false,false,false,false,
            false,false,false,false,false,false,false,false,
            false,false,false,false,false,false,false,false,
            false,false,false,false,false,false,false,false,
            false,false,false,false,false,false,false,false,
            true,true,true,true,true,true,true,true,
            false,false,false,false,false,false,false,false
    };
	
    /*
     * bottom rank is First Rank and keep go on upside
     */
    
    public static final boolean[] EIGTH_RANK = initRow(0);
    public static final boolean[] SIXTH_RANK = initRow(16);
    public static final boolean[] FIFTH_RANK = initRow(24);
    public static final boolean[] FOURTH_RANK = initRow(32);
    public static final boolean[] THIRD_RANK = initRow(40);
    public static final boolean[] FIRST_RANK = initRow(56);

	private BoardUtil() {
		throw new RuntimeException("BoardUtil class can not be instanciated");
	}
	
	public static boolean isValidTileCordinate(int cordinate) {
		if(cordinate>=0 && cordinate<Constants.TILES_SIZE)return true;
		return false;
	}

	//fucntion to create boolean of colomn
    private static boolean[] initColumn(int columnNumber) {
        final boolean[] column = new boolean[Constants.TILES_SIZE];
        for(int i = 0; i < column.length; i++) {
            column[i] = false;
        }
        do {
            column[columnNumber] = true;
            columnNumber += Constants.NUM_TILES_PER_ROW;
        } while(columnNumber < Constants.TILES_SIZE);
        return column;
    }

    //remaining rows for function
    private static boolean[] initRow(int rowNumber) {
        final boolean[] row = new boolean[Constants.TILES_SIZE];
        for(int i = 0; i < row.length; i++) {
            row[i] = false;
        }
        do {
            row[rowNumber] = true;
            rowNumber++;
        } while(rowNumber % Constants.NUM_TILES_PER_ROW != 0);
        return row;
    }

    public static int getCordinateAtPosition(final String position) {
    	return POSITION_TO_COORDINATE.get(position);
	}
    
	public static String getPositionAtCordinate(final int cordinate) {
		return ALGEBRAIC_NOTATION.get(cordinate);
	}
	
	
	public final static List<String> ALGEBRAIC_NOTATION = initializeAlgebraicNotation();
	public final static Map<String, Integer> POSITION_TO_COORDINATE = initializePositionToCoordinateMap();
	
	private static Map<String, Integer> initializePositionToCoordinateMap() {
        final Map<String, Integer> positionToCoordinate = new HashMap<>();
        for (int i = Constants.START_TILE_INDEX; i < Constants.TILES_SIZE; i++) {
            positionToCoordinate.put(ALGEBRAIC_NOTATION.get(i), i);
        }
        return Collections.unmodifiableMap(positionToCoordinate);
    }

    private static List<String> initializeAlgebraicNotation() {
        return Collections.unmodifiableList(Arrays.asList(
                "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8",
                "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
                "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
                "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
                "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
                "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
                "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
                "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1"));
    }
	
}
