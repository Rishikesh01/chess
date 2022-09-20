package org.chess.piece;

/**
 * @author Rishikesh
 * @project chess
 */
public enum PieceType {
    PAWN("P"){
        @Override
        public boolean isKing() {
            return false;
        }

        @Override
        public boolean isRook() {
            return false;
        }
    },
    KNIGHT("H"){
        @Override
        public boolean isKing() {
            return false;
        }

        @Override
        public boolean isRook() {
            return false;
        }
    },
    BISHOP("B"){
        @Override
        public boolean isKing() {
            return false;
        }

        @Override
        public boolean isRook() {
            return false;
        }
    },
    ROOK("R"){
        @Override
        public boolean isKing() {
            return false;
        }

        @Override
        public boolean isRook() {
           return  true;
        }
    },
    QUEEN("Q"){
        @Override
        public boolean isKing() {
            return false;
        }

        @Override
        public boolean isRook() {
            return false;
        }
    },
    KING("K"){
        @Override
        public boolean isKing() {
            return true;
        }

        @Override
        public boolean isRook() {
            return false;
        }
    };

    private final String pieceName;

    PieceType(
            final String pieceName) {
        this.pieceName = pieceName;
    }


    @Override
    public String toString() {
        return this.pieceName;
    }

    public abstract boolean isKing();

    public abstract boolean isRook();
}
