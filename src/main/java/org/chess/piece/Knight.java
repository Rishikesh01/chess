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

import static org.chess.board.Move.AttackMove;
import static org.chess.board.Move.MajorMove;

/**
 * @author Rishikesh
 * @project chess
 */
public class Knight extends Piece {

    private static final int[] CANDIDATE_MOVE_COORDINATE = {-17, -15, -6, 6, 15, 17};

    public Knight(final int piecePosition, final Color pieceColor) {
        super(piecePosition, pieceColor,PieceType.KNIGHT,true);
    }

    public Knight(final int piecePosition, final Color pieceColor,final boolean isFirstMove) {
        super(piecePosition, pieceColor,PieceType.KNIGHT,isFirstMove);
    }

    private static boolean isFirstColumnExclusion(final int currentPos, final int offset) {
        return BoardUtils.FIRST_COLUMN[currentPos] && (offset == -17 || offset == -10 || offset == 6 || offset == 15);
    }

    private static boolean isSecondColumnExclusion(final int currentPos, final int offset) {
        return BoardUtils.SECOND_COLUMN[currentPos] && (offset == -10 || offset == 6);
    }

    private static boolean isSeventhColumnExclusion(final int currentPos, final int offset) {
        return BoardUtils.SEVENTH_COLUMN[currentPos] || (offset == -6 || offset == 10);
    }

    private static boolean isEightColumnExclusion(final int currentPos, final int offset) {
        return BoardUtils.EIGHTH_COLUMN[currentPos] && (offset == -15 || offset == -6 || offset == 10 || offset == 17);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int currentOffSet : CANDIDATE_MOVE_COORDINATE) {
            if (isFirstColumnExclusion(this.piecePosition, currentOffSet) || isSecondColumnExclusion(this.piecePosition, currentOffSet)
                    || isSeventhColumnExclusion(this.piecePosition, currentOffSet) || isEightColumnExclusion(this.piecePosition, currentOffSet)) {
                continue;
            }
            int candidateDestCoordinate = this.piecePosition + currentOffSet;

            if (BoardUtils.isValidTileCoordinate(candidateDestCoordinate)) {
                final Tile destCoordinate = board.getTile(candidateDestCoordinate);
                if (destCoordinate.isTileEmpty()) {
                    legalMoves.add(new MajorMove(board, this, candidateDestCoordinate));
                } else {
                    final Piece pieceDest = destCoordinate.getPiece();
                    final Color color = pieceDest.getPieceColor();
                    if (this.pieceColor != color) {
                        legalMoves.add(new AttackMove(board, this, candidateDestCoordinate, pieceDest));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);

    }

    @Override
    public Piece movePiece(Move move) {
        return new Knight(move.getDestinationCoordinate(), move.getMovedPiece().getPieceColor());
    }

}
