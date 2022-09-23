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

    private final static int[] CANDIDATE_MOVE_COORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17};

    public Knight(final int piecePosition,final Color color) {
        super(piecePosition, color,PieceType.KNIGHT, true);
    }

    public Knight(final int piecePosition,final Color color,final boolean isFirstMove) {
        super(piecePosition, color,PieceType.KNIGHT, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {  //Cycles each item in an array of possible moves for Knight
            final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset; //Adds possible move to current position
            if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) { //Checks if target position is <64

                if (isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) ||  //checks if current position is on the edge of board
                        isSecondColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                        isSeventhColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                        isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)) {
                    continue;
                }
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate); // If target destination is valid assigns it to variable
                if (candidateDestinationTile.isTileEmpty()) { //checks if tile is empty
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                } else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Color pieceColor = pieceAtDestination.getPieceColor(); //Checks target piece alliance
                    if (this.pieceColor != pieceColor) {
                        legalMoves.add(new Move.MajorAttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Knight movePiece(Move move) {
        return new Knight(move.getDestinationCoordinate(),move.getMovedPiece().getPieceColor());
    }

    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -17 || candidateOffset == -10 ||
                candidateOffset == 6 || candidateOffset == 15);
    }


    private static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.SECOND_COLUMN[currentPosition] && (candidateOffset == -10 || candidateOffset == 6);
    }

    private static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.SEVENTH_COLUMN[currentPosition] && (candidateOffset == -6 || candidateOffset == 10);

    }

    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -15 || candidateOffset == -6 ||
                candidateOffset == 10 || candidateOffset == 17);
    }

}
