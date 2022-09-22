package org.chess.piece;

import com.google.common.collect.ImmutableList;
import org.chess.Color;
import org.chess.board.Board;
import org.chess.board.BoardUtils;
import org.chess.board.Move;
import org.chess.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Rishikesh
 * @project chess
 */
public class King extends Piece {
    public final static int[] CANDIDATE_MOVES_COORDINATE = {-9, -8, -1, 1, 7, 8, 9};

    public King(int piecePosition, Color pieceColor) {
        super(piecePosition, pieceColor, PieceType.KING,true);
    }

    public King(int piecePosition, Color pieceColor,final boolean isFirstMove) {
        super(piecePosition, pieceColor, PieceType.KING,isFirstMove);
    }

    private static boolean isFirstColumnExclusion(final int currentPos, final int offset) {
        return BoardUtils.FIRST_COLUMN[currentPos] && (offset == -9 || offset == -1 || offset == 7);
    }

    private static boolean isEightColumnExclusion(final int currentPos, final int offset) {
        return BoardUtils.EIGHTH_COLUMN[currentPos] && (offset == -7 || offset == 1 || offset == 9);
    }


    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for (final int offSet : CANDIDATE_MOVES_COORDINATE) {
            final int candidateDestCoordinate = this.piecePosition + offSet;

            if (isFirstColumnExclusion(this.piecePosition, offSet) || isFirstColumnExclusion(this.piecePosition, offSet)) {
                continue;
            }
            if (BoardUtils.isValidTileCoordinate(candidateDestCoordinate)) {
                final Tile destCoordinate = board.getTile(candidateDestCoordinate);
                if (destCoordinate.isTileEmpty()) {
                    legalMoves.add(new Move.MajorMove(board, this, candidateDestCoordinate));
                } else {
                    final Piece pieceDest = destCoordinate.getPiece();
                    final Color color = pieceDest.getPieceColor();
                    if (this.pieceColor != color) {
                        legalMoves.add(new Move.AttackMove(board, this, candidateDestCoordinate, pieceDest));
                    }
                }


            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public King movePiece(Move move) {
        return new King(move.getDestinationCoordinate(), move.getMovedPiece().getPieceColor());

    }
}
