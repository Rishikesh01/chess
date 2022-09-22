package org.chess.piece;

/**
 * @author Rishikesh
 * @project chess
 */
public enum PieceType {
    PAWN("P",1){
        @Override
        public boolean isKing() {
            return false;
        }

        @Override
        public boolean isRook() {
            return false;
        }
    },
    KNIGHT("H",3){
        @Override
        public boolean isKing() {
            return false;
        }

        @Override
        public boolean isRook() {
            return false;
        }
    },
    BISHOP("B",3){
        @Override
        public boolean isKing() {
            return false;
        }

        @Override
        public boolean isRook() {
            return false;
        }
    },
    ROOK("R",5){
        @Override
        public boolean isKing() {
            return false;
        }

        @Override
        public boolean isRook() {
           return  true;
        }
    },
    QUEEN("Q",9){
        @Override
        public boolean isKing() {
            return false;
        }

        @Override
        public boolean isRook() {
            return false;
        }
    },
    KING("K",10){
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
    private final int pieceValue;

    PieceType(
            final String pieceName, int pieceValue) {
        this.pieceName = pieceName;
        this.pieceValue = pieceValue;
    }


    @Override
    public String toString() {
        return this.pieceName;
    }

    public abstract boolean isKing();

    public int getPieceValue(){
        return  this.pieceValue;
    }

    public abstract boolean isRook();
}
