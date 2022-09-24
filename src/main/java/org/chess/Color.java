package org.chess;

import org.chess.board.BoardUtils;
import org.chess.player.Player;

/**
 * @author Rishikesh
 * @project chess
 */
public enum Color {
    BLACK {
        @Override
        public int getDirection() {
            return 1;
        }

        @Override
        public int getOppositeDirection() {
            return -1;
        }

        @Override
        public boolean isWhite() {
            return false;
        }

        @Override
        public boolean isBlack() {
            return true;
        }

        @Override
        public boolean isPawnPromotionSquare(int pos) {
            return BoardUtils.FIRST_ROW[pos];
        }

        @Override
        public Player choosePlayer(Player whitePlayer, Player blackPlayer) {
            return blackPlayer;
        }
    },
    WHITE {
        @Override
        public int getDirection() {
            return -1;
        }

        @Override
        public int getOppositeDirection() {
            return 1;
        }

        @Override
        public boolean isWhite() {
            return true;
        }

        @Override
        public boolean isBlack() {
            return false;
        }

        @Override
        public boolean isPawnPromotionSquare(int pos) {
            return BoardUtils.EIGHT_ROW[pos];
        }

        @Override
        public Player choosePlayer(Player whitePlayer, Player blackPlayer) {
            return whitePlayer;
        }
    };

    public abstract int getDirection();

    public abstract int getOppositeDirection();

    public abstract boolean isWhite();

    public abstract boolean isBlack();

    public abstract boolean isPawnPromotionSquare(int pos);


    public abstract Player choosePlayer(Player whitePlayer, Player blackPlayer);
}
