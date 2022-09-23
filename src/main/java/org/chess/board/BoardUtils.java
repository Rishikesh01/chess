package org.chess.board;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rishikesh
 * @project chess
 */
public class BoardUtils {
    public static final int BOARD_SIZE = 64;
    public static final int ROW_SIZE = 8;
    public static final boolean[] FIRST_COLUMN = initColumn(0);
    public static final boolean[] SECOND_COLUMN = initColumn(1);
    public static final boolean[] THIRD_COLUMN = initColumn(2);
    public static final boolean[] FOURTH_COLUMN = initColumn(3);
    public static final boolean[] FIFTH_COLUMN = initColumn(4);
    public static final boolean[] SIXTH_COLUMN = initColumn(5);
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    public static final boolean[] EIGHTH_COLUMN = initColumn(7);

    public static final boolean[] FIRST_ROW = initRow(56);
    public static final boolean[] SECOND_ROW = initRow(48);
    public static final boolean[] THIRD_ROW = initRow(40);
    public static final boolean[] FOURTH_ROW = initRow(32);
    public static final boolean[] FIFTH_ROW = initRow(24);
    public static final boolean[] SIXTH_ROW = initRow(16);
    public static final boolean[] SEVENTH_ROW = initRow(8);
    public static final boolean[] EIGHT_ROW = initRow(0);
    private static final List<String> ALGEBRAIC_NOTATION = initAlegbricNotation();
    private static final Map<String, Integer> POSITION_TO_COORDINATE = initPositionToCoordinateMap();

    private BoardUtils() {
        throw new RuntimeException("You can create this obj");
    }

    private static List<String> initAlegbricNotation() {
        return ImmutableList.copyOf(Arrays.asList(
                "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8",
                "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
                "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
                "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
                "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
                "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
                "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
                "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1"));
    }

    private static Map<String, Integer> initPositionToCoordinateMap() {
        final Map<String, Integer> positionToCoordinate = new HashMap<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            positionToCoordinate.put(ALGEBRAIC_NOTATION.get(i), i);
        }
        return ImmutableMap.copyOf(positionToCoordinate);
    }

    private static boolean[] initRow(int i) {
        final boolean[] row = new boolean[BOARD_SIZE];
        do {
            row[i] = true;
            i++;
        } while (i % ROW_SIZE != 0);

        return row;
    }

    public static boolean isValidTileCoordinate(final int candidateDestCoordinate) {
        return candidateDestCoordinate >= 0 && candidateDestCoordinate < BOARD_SIZE;
    }

    private static boolean[] initColumn(int colNumber) {
        final boolean[] col = new boolean[BOARD_SIZE];
        do {
            col[colNumber] = true;
            colNumber += ROW_SIZE;
        } while (colNumber < BOARD_SIZE);
        return col;
    }


    public static String getPostionAtCoordinate(int destCoordinate) {
        return ALGEBRAIC_NOTATION.get(destCoordinate);
    }

    public static int getCoordinateAtPostion(String postion) {
        return POSITION_TO_COORDINATE.get(postion);
    }
}
