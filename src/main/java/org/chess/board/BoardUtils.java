package org.chess.board;

/**
 * @author Rishikesh
 * @project chess
 */
public class BoardUtils {
    public static final int BOARD_SIZE = 64;
    public static final int ROW_SIZE = 8;
    public static final boolean[] FIRST_COLUMN = initColumn(0);
    public static final boolean[] SECOND_COLUMN = initColumn(1);
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    public static final boolean[] EIGHT_COLUMN = initColumn(7);

    public static final boolean[] SECOND_ROW = null;

    public static final boolean[] SEVENTH_ROW = null;

    private BoardUtils() {
        throw new RuntimeException("You can create this obj");
    }

    public static boolean isValidTileCoordinate(final int candidateDestCoordinate) {
        return candidateDestCoordinate >= 0 && candidateDestCoordinate < BOARD_SIZE;
    }

    private static boolean[] initColumn(int colNumber) {
        final boolean[] col = new boolean[BOARD_SIZE];
        while (colNumber < BOARD_SIZE) {
            col[colNumber] = true;
            colNumber += ROW_SIZE;
        }
        return col;
    }


}
