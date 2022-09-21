package org.chess.board;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import org.chess.Color;
import org.chess.piece.*;
import org.chess.player.BlackPlayer;
import org.chess.player.Player;
import org.chess.player.WhitePlayer;

import java.util.*;

/**
 * @author Rishikesh
 * @project chess
 */
public class Board {

    private final List<Tile> gameBoard;
    private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieces;

    private final WhitePlayer whitePlayer;

    private final BlackPlayer blackPlayer;
    private final Player currentPlayer;

    private Board(Builder builder) {
        this.gameBoard = createGameBoard(builder);
        this.whitePieces = calculateActivePieces(this.gameBoard, Color.WHITE);
        this.blackPieces = calculateActivePieces(this.gameBoard, Color.BLACK);
        final Collection<Move> whiteLegalMoves = calculateLegalMoves(this.whitePieces);
        final Collection<Move> blackLegalMoves = calculateLegalMoves(this.blackPieces);
        this.whitePlayer = new WhitePlayer(this, whiteLegalMoves, blackLegalMoves);
        this.blackPlayer = new BlackPlayer(this,whiteLegalMoves,blackLegalMoves);
        this.currentPlayer = builder.nextMoveMaker.choosePlayer(this.whitePlayer, this.blackPlayer);

    }

    private static Collection<Piece> calculateActivePieces(List<Tile> gameBoard, Color color) {
        List<Piece> activePieces = new ArrayList<>();
        for (final Tile tile : gameBoard) {
            if (!tile.isTileEmpty()) {
                final Piece piece = tile.getPiece();
                if (piece.getPieceColor() == color) {
                    activePieces.add(piece);
                }
            }
        }

        return ImmutableList.copyOf(activePieces);
    }

    private static List<Tile> createGameBoard(Builder builder) {
        final Tile[] tiles = new Tile[BoardUtils.BOARD_SIZE];
        for (int i = 0; i < BoardUtils.BOARD_SIZE; i++) {
            tiles[i] = Tile.createTile(i, builder.boardMap.get(i));
        }
        return ImmutableList.copyOf(tiles);
    }

    public static Board standardBoard() {
        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new Rook(0, Color.BLACK));
        builder.setPiece(new Knight(1, Color.BLACK));
        builder.setPiece(new Bishop(2, Color.BLACK));
        builder.setPiece(new Queen(3, Color.BLACK));
        builder.setPiece(new King(4, Color.BLACK));
        builder.setPiece(new Bishop(5, Color.BLACK));
        builder.setPiece(new Knight(6, Color.BLACK));
        builder.setPiece(new Rook(7, Color.BLACK));
        builder.setPiece(new Pawn(8, Color.BLACK));
        builder.setPiece(new Pawn(9, Color.BLACK));
        builder.setPiece(new Pawn(10, Color.BLACK));
        builder.setPiece(new Pawn(11, Color.BLACK));
        builder.setPiece(new Pawn(12, Color.BLACK));
        builder.setPiece(new Pawn(13, Color.BLACK));
        builder.setPiece(new Pawn(14, Color.BLACK));
        builder.setPiece(new Pawn(15, Color.BLACK));
        // White Layout
        builder.setPiece(new Pawn(48, Color.WHITE));
        builder.setPiece(new Pawn(49, Color.WHITE));
        builder.setPiece(new Pawn(50, Color.WHITE));
        builder.setPiece(new Pawn(51, Color.WHITE));
        builder.setPiece(new Pawn(52, Color.WHITE));
        builder.setPiece(new Pawn(53, Color.WHITE));
        builder.setPiece(new Pawn(54, Color.WHITE));
        builder.setPiece(new Pawn(55, Color.WHITE));
        builder.setPiece(new Rook(56, Color.WHITE));
        builder.setPiece(new Knight(57, Color.WHITE));
        builder.setPiece(new Bishop(58, Color.WHITE));
        builder.setPiece(new Queen(59, Color.WHITE));
        builder.setPiece(new King(60, Color.WHITE));
        builder.setPiece(new Bishop(61, Color.WHITE));
        builder.setPiece(new Knight(62, Color.WHITE));
        builder.setPiece(new Rook(63, Color.WHITE));
        //white to move
        builder.setMoveMaker(Color.WHITE);
        //build the board
        return builder.build();
    }

    public Player currentPlayer() {
        return this.currentPlayer;
    }

    private Collection<Move> calculateLegalMoves(final Collection<Piece> pieces) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final Piece piece : pieces) {
            legalMoves.addAll(piece.calculateLegalMoves(this));
        }
        return ImmutableList.copyOf(legalMoves);
    }

    public Tile getTile(final int tileCoordinate) {
        return gameBoard.get(tileCoordinate);
    }

    public Collection<Piece> getBlackPieces() {
        return this.blackPieces;
    }

    public Collection<Piece> getWhitePieces() {
        return this.whitePieces;
    }

    public Player whitePlayer() {
        return this.whitePlayer;
    }

    public Player blackPlayer() {
        return this.blackPlayer;
    }

    public Iterable<Move> getAllLegalMoves() {
        return Iterables.unmodifiableIterable(Iterables.concat(this.whitePlayer.getLegalMoves(), this.blackPlayer.getLegalMoves()));
    }


    public static class Builder {
        Map<Integer, Piece> boardMap;
        Color nextMoveMaker;
        Pawn pawnEnPassant;

        public Builder() {
            this.boardMap = new HashMap<>();
        }

        public Builder setPiece(final Piece piece) {
            this.boardMap.put(piece.getPiecePosition(), piece);
            return this;
        }

        public Builder setMoveMaker(final Color nextMoveMaker) {
            this.nextMoveMaker = nextMoveMaker;
            return this;
        }

        public Board build() {
            return new Board(this);
        }

        public void setEnPassantPawn(Pawn pawnEnPassant) {
            this.pawnEnPassant = pawnEnPassant;
        }
    }
}
