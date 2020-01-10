package com.chessy.engine.board;

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
    public static final boolean[] SECOND_ROW = {
            false,false,false,false,false,false,false,false,
            true,true,true,true,true,true,true,true,
            false,false,false,false,false,false,false,false,
            false,false,false,false,false,false,false,false,
            false,false,false,false,false,false,false,false,
            false,false,false,false,false,false,false,false,
            false,false,false,false,false,false,false,false,
            false,false,false,false,false,false,false,false
    };
    public static final boolean[] SEVENTH_ROW = {
            false,false,false,false,false,false,false,false,
            false,false,false,false,false,false,false,false,
            false,false,false,false,false,false,false,false,
            false,false,false,false,false,false,false,false,
            false,false,false,false,false,false,false,false,
            false,false,false,false,false,false,false,false,
            true,true,true,true,true,true,true,true,
            false,false,false,false,false,false,false,false
    };
	
    //remaining colomn and rows
    public static final boolean[] FOURTH_COLUMN = initColumn(3);
    public static final boolean[] THIRD_COLUMN = initColumn(2);
    public static final boolean[] FIRST_ROW = initRow(0);
    public static final boolean[] THIRD_ROW = initRow(16);
    public static final boolean[] FOURTH_ROW = initRow(24);
    public static final boolean[] FIFTH_ROW = initRow(32);
    public static final boolean[] SIXTH_ROW = initRow(40);
    public static final boolean[] EIGHTH_ROW = initRow(56);

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
}
