package org.chess.piece;

import com.google.common.collect.ImmutableList;
import org.chess.board.Board;
import org.chess.board.BoardUtils;
import org.chess.board.Move;
import org.chess.board.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rishikesh
 * @project chess
 */
public class Queen extends Piece {

    private final static int[] CANDIDATE_MOVES_BISHOP = {-9, -8, -7, -1, 1, 7, 8, 9};


    Queen(int piecePosition, Color pieceColor) {
        super(piecePosition, pieceColor);
    }

    private static boolean isFirstColumnExclusion(final int currentPos, final int offset) {
        return BoardUtils.FIRST_COLUMN[currentPos] && (offset == -9 || offset == 7 || offset == -1);
    }

    private static boolean isEightColumnExclusion(final int currentPos, final int offset) {
        return BoardUtils.EIGHT_COLUMN[currentPos] && (offset == -7 || offset == 9 || offset == 1);
    }

    @Override
    public List<Move> calculateLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        int candidateDestCoordinate = this.piecePosition;
        for (final int offSet : CANDIDATE_MOVES_BISHOP) {

            while (BoardUtils.isValidTileCoordinate(candidateDestCoordinate)) {
                candidateDestCoordinate += offSet;
                if (isEightColumnExclusion(candidateDestCoordinate, offSet) | isFirstColumnExclusion(candidateDestCoordinate, offSet))
                    break;
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
                        break;
                    }

                }

            }
        }
        return ImmutableList.copyOf(legalMoves);
    }
}
