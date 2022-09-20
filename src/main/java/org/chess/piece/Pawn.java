package org.chess.piece;

import com.google.common.collect.ImmutableList;
import org.chess.Color;
import org.chess.board.Board;
import org.chess.board.BoardUtils;
import org.chess.board.Move;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rishikesh
 * @project chess
 */
public class Pawn extends Piece {
    public static int[] CANDIDATE_MOVES_COORDINATES = {8, 16};

    public Pawn(int piecePosition, Color pieceColor) {
        super(piecePosition, pieceColor,PieceType.PAWN);
    }

    @Override
    public List<Move> calculateLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for (final int offset : CANDIDATE_MOVES_COORDINATES) {
            int candidateDestCoordinate = this.piecePosition + (this.pieceColor.getDirection() * offset);
            if (!BoardUtils.isValidTileCoordinate(candidateDestCoordinate)) continue;
            if (offset == 8 && board.getTile(candidateDestCoordinate).isTileEmpty()) {
                legalMoves.add(new Move.MajorMove(board, this, candidateDestCoordinate));
            } else if (offset == 16 && this.isFirstMove() &&
                    (BoardUtils.SECOND_ROW[this.piecePosition] && this.pieceColor.isBlack()) ||
                    (BoardUtils.SEVENTH_ROW[this.piecePosition] && this.pieceColor.isWhite())) {
                final int behindCandidateDest = this.piecePosition + (this.pieceColor.getDirection()
                        * BoardUtils.ROW_SIZE);
                if (board.getTile(behindCandidateDest).isTileEmpty() && board.getTile(candidateDestCoordinate).isTileEmpty())
                    legalMoves.add(new Move.MajorMove(board, this, candidateDestCoordinate));

                else if (offset == 7 &&
                        !((BoardUtils.EIGHT_COLUMN[this.piecePosition] && this.pieceColor.isWhite() ||
                                (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceColor.isBlack())))) {
                    if (!board.getTile(candidateDestCoordinate).isTileEmpty()) {
                        final Piece pieceOnCandidate = board.getTile(candidateDestCoordinate).getPiece();
                        if (this.pieceColor != pieceOnCandidate.getPieceColor()) {
                            //TODO more to do here
                            legalMoves.add(new Move.MajorMove(board, this, candidateDestCoordinate));
                        }
                    }
                } else if (offset == 9 &&
                        !((BoardUtils.EIGHT_COLUMN[this.piecePosition] && this.pieceColor.isWhite() ||
                                (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceColor.isBlack())))) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestCoordinate).getPiece();
                    if (this.pieceColor != pieceOnCandidate.getPieceColor()) {
                        //TODO more to do here
                        legalMoves.add(new Move.MajorMove(board, this, candidateDestCoordinate));

                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Pawn movePiece(Move move) {
        return new Pawn(move.getDestinationCoordinate(), move.getMovedPiece().getPieceColor());

    }
}
