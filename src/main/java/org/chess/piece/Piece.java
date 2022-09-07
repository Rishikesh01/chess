package org.chess.piece;

import org.chess.board.Board;
import org.chess.board.Move;

import java.util.List;

/**
 * @author Rishikesh
 * @project chess
 */
public abstract class Piece {
    protected final int piecePosition;
    protected final Color pieceColor;

     Piece(final int piecePosition,final Color pieceColor){
        this.pieceColor=pieceColor;
        this.piecePosition = piecePosition;
    }

    public abstract List<Move> calculateLegalMoves(final Board board);

    public Color getPieceColor(){
        return this.pieceColor;
    }




}
