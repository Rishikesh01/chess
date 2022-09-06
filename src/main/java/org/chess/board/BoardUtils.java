package org.chess.board;

/**
 * @author Rishikesh
 * @project chess
 */
public class BoardUtils {
    public static final boolean[] FIRST_COLUMN = initColumn(0);
    public static final boolean[] SECOND_COLUMN = initColumn(1);
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    public static final boolean[] EIGHT_COLUMN = initColumn(7);

    private BoardUtils() {
        throw new RuntimeException("You can create this obj");
    }

    public static boolean isValidTileCoordinate(final int candidateDestCoordinate) {
        return candidateDestCoordinate >= 0 && candidateDestCoordinate < 64;
    }

    private static boolean[] initColumn( int colNumber) {
        final boolean[] col = new boolean[64];
        while (colNumber < 64) {
            col[colNumber] = true;
            colNumber += 8;
        }
        return col;
    }
}
