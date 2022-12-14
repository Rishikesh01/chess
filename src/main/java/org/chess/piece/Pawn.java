package org.chess.piece;

import com.google.common.collect.ImmutableList;
import org.chess.Color;
import org.chess.board.Board;
import org.chess.board.BoardUtils;
import org.chess.board.Move;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Rishikesh
 * @project chess
 */
public class Pawn extends Piece {
    public static int[] CANDIDATE_MOVES_COORDINATES = {8, 16, 7, 9};

    public Pawn(int piecePosition, Color pieceColor) {
        super(piecePosition, pieceColor, PieceType.PAWN, true);
    }

    public Pawn(int piecePosition, Color pieceColor, boolean isFirstMove) {
        super(piecePosition, pieceColor, PieceType.PAWN, isFirstMove);
    }


    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for (final int offset : CANDIDATE_MOVES_COORDINATES) {
            int candidateDestCoordinate = this.piecePosition + (this.pieceColor.getDirection() * offset);

            if (!BoardUtils.isValidTileCoordinate(candidateDestCoordinate)) {
                continue;
            }
            if (offset == 8 && board.getTile(candidateDestCoordinate).isTileEmpty()) {
                if (this.pieceColor.isPawnPromotionSquare(candidateDestCoordinate)) {
                    legalMoves.add(new Move.PawnPromotion(new Move.PawnMove(board, this, candidateDestCoordinate)));
                } else {
                    legalMoves.add(new Move.PawnMove(board, this, candidateDestCoordinate));
                }
            } else if (offset == 16 && this.isFirstMove() &&
                    ((BoardUtils.SECOND_ROW[this.piecePosition] && this.pieceColor.isWhite()) ||
                            (BoardUtils.SEVENTH_ROW[this.piecePosition] && this.pieceColor.isBlack()))) {

                final int behindCandidateDest = this.piecePosition + (this.pieceColor.getDirection() * BoardUtils.ROW_SIZE);

                if (board.getTile(behindCandidateDest).isTileEmpty() && board.getTile(candidateDestCoordinate).isTileEmpty()) {
                    legalMoves.add(new Move.PawnJump(board, this, candidateDestCoordinate));
                }
            } else if (offset == 7 &&
                    !((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceColor.isWhite() ||
                            (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceColor.isBlack())))) {

                if (!board.getTile(candidateDestCoordinate).isTileEmpty()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestCoordinate).getPiece();
                    if (this.pieceColor != pieceOnCandidate.getPieceColor()) {
                        if (this.pieceColor.isPawnPromotionSquare(candidateDestCoordinate)) {
                            legalMoves.add(new Move.PawnPromotion(new Move.PawnAttackMove(board, this, candidateDestCoordinate, pieceOnCandidate)));
                        } else {
                            legalMoves.add(new Move.PawnAttackMove(board, this, candidateDestCoordinate, pieceOnCandidate));
                        }
                    }
                } else if (board.getEnPassantPawn() != null) {
                    if (board.getEnPassantPawn().getPiecePosition() == (this.piecePosition + (this.getPieceColor().getOppositeDirection()))) {
                        final Piece pieceOnCandidate = board.getEnPassantPawn();
                        if (this.pieceColor != pieceOnCandidate.getPieceColor()) {
                            legalMoves.add(new Move.PawnPassEntAttackMove(board, this, candidateDestCoordinate, pieceOnCandidate));
                        }
                    }
                }
            } else if (offset == 9 &&
                    !((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceColor.isWhite() || //checks piece on 1st column is black
                            (BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceColor.isBlack())))) {
                if (!board.getTile(candidateDestCoordinate).isTileEmpty()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestCoordinate).getPiece();
                    if (this.pieceColor != pieceOnCandidate.getPieceColor()) {
                        if (this.pieceColor.isPawnPromotionSquare(candidateDestCoordinate)) {
                            legalMoves.add(new Move.PawnPromotion(new Move.PawnAttackMove(board, this, candidateDestCoordinate, pieceOnCandidate)));
                        } else {
                            legalMoves.add(new Move.PawnAttackMove(board, this, candidateDestCoordinate, pieceOnCandidate));
                        }
                    }
                } else if (board.getEnPassantPawn() != null) {
                    if (board.getEnPassantPawn().getPiecePosition() == (this.piecePosition - (this.getPieceColor().getOppositeDirection()))) {
                        final Piece pieceOnCandidate = board.getEnPassantPawn();
                        if (this.pieceColor != pieceOnCandidate.getPieceColor()) {
                            legalMoves.add(new Move.PawnPassEntAttackMove(board, this, candidateDestCoordinate, pieceOnCandidate));
                        }
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

    public Piece getPromotionPiece() {
        return new Queen(this.piecePosition, this.pieceColor, false);
    }
}

