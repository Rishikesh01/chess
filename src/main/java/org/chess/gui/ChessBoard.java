package org.chess.gui;

import org.chess.board.Board;
import org.chess.board.BoardUtils;
import org.chess.board.Move;
import org.chess.board.Tile;
import org.chess.piece.Piece;
import org.chess.player.MoveTransition;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

/**
 * @author Rishikesh
 * @project chess
 */
public class ChessBoard {
    private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
    private static final Dimension BOARDPANEL_DIMENTION = new Dimension(400, 350);
    private static final Dimension TILE_PANEL_DEMENTION = new Dimension(10, 10);
    private final Color lightTileColor = Color.decode("#FFFACD");
    private final Color darkTileColor = Color.decode("#593E1A");
    private final BoardPanel boardPanel;
    private final JFrame gameFrame;
    private Board board;
    private Tile srcTile;
    private Tile destTile;
    private Piece humanMovedPiece;

    public ChessBoard() {
        this.gameFrame = new JFrame("JChess");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar chessBoardMenuBar = createChessBoardMenuBar();
        this.gameFrame.setJMenuBar(chessBoardMenuBar);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.gameFrame.setVisible(true);
        this.board = Board.standardBoard();
        this.boardPanel = new BoardPanel();
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
    }

    private JMenuBar createChessBoardMenuBar() {
        final JMenuBar chessBoardMenuBar = new JMenuBar();
        chessBoardMenuBar.add(createFileMenu());
        return chessBoardMenuBar;
    }

    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");
        final JMenuItem openPGN = new JMenuItem("Load PGN");
        openPGN.addActionListener((actionEvent) -> {
            System.out.println("Open the PGN");
        });
        fileMenu.add(openPGN);

        final JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener((actionEvent -> System.exit(0)));
        fileMenu.add(exitMenuItem);
        return fileMenu;
    }

    private class BoardPanel extends JPanel {
        final java.util.List<TilePanel> boardTiles;

        public BoardPanel() {
            super(new GridLayout(8, 8));
            this.boardTiles = new ArrayList<>();
            for (int i = 0; i < BoardUtils.BOARD_SIZE; i++) {
                final TilePanel tilePanel = new TilePanel(this, i);
                this.boardTiles.add(tilePanel);
                add(tilePanel);
            }
            setPreferredSize(BOARDPANEL_DIMENTION);
            validate();

        }

        public void drawBoard(final Board board) {
            removeAll();
            for (final TilePanel tilePanel : boardTiles) {
                tilePanel.drawTile(board);
                add(tilePanel);
            }
            validate();
            repaint();
        }
    }

    private class TilePanel extends JPanel {
        private final int tileId;

        public TilePanel(final BoardPanel boardPanel, final int tileId) {
            super(new GridLayout());
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL_DEMENTION);
            assignTileColor();
            assignTilePieceIcon(board);
            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                    if (isRightMouseButton(mouseEvent)) {
                        srcTile = null;
                        destTile = null;
                        humanMovedPiece = null;
                    } else if (isLeftMouseButton(mouseEvent)) {
                        if (srcTile == null) {
                            srcTile = board.getTile(tileId);
                            humanMovedPiece = srcTile.getPiece();
                            if (humanMovedPiece == null) {
                                srcTile = null;
                            }
                        } else {
                            destTile = board.getTile(tileId);
                            final Move move = Move.MoveFactory.createMove(board, srcTile.getTitleCoordinate(), destTile.getTitleCoordinate());
                            final MoveTransition transition = board.currentPlayer().makeMove(move);

                            if (transition.getMoveStatus().isDone()) {
                                board = transition.getTransitionBoard();
                            }
                            srcTile = null;
                            destTile = null;
                            humanMovedPiece = null;
                        }
                        SwingUtilities.invokeLater(() -> boardPanel.drawBoard(board));
                    }
                }

                @Override
                public void mousePressed(MouseEvent mouseEvent) {

                }

                @Override
                public void mouseReleased(MouseEvent mouseEvent) {

                }

                @Override
                public void mouseEntered(MouseEvent mouseEvent) {

                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {

                }
            });
            validate();
        }

        private void assignTilePieceIcon(final Board board) {
            this.removeAll();
            if (!board.getTile(this.tileId).isTileEmpty()) {
                try {
                    final BufferedImage image = ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream
                            (board
                                    .getTile(this.tileId)
                                    .getPiece()
                                    .getPieceColor()
                                    .toString()
                                    .charAt(0)
                                    + board
                                    .getTile(this.tileId)
                                    .getPiece()
                                    .getPieceType()
                                    .toString()
                                    + ".png"
                            )));
                    add(new JLabel(new ImageIcon(image)));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        private void assignTileColor() {
            if (BoardUtils.FIRST_ROW[this.tileId] ||
                    BoardUtils.THIRD_ROW[this.tileId] ||
                    BoardUtils.FIFTH_ROW[this.tileId] ||
                    BoardUtils.SEVENTH_ROW[this.tileId]) {
                setBackground(this.tileId % 2 == 0 ? lightTileColor : darkTileColor);
            }
            if (BoardUtils.SECOND_ROW[this.tileId] ||
                    BoardUtils.FOURTH_ROW[this.tileId] ||
                    BoardUtils.SIXTH_ROW[this.tileId] ||
                    BoardUtils.EIGHT_ROW[this.tileId]
            ) {
                setBackground(this.tileId % 2 != 0 ? lightTileColor : darkTileColor);
            }

        }

        public void drawTile(final Board board) {
            assignTileColor();
            assignTilePieceIcon(board);
            validate();
            repaint();
        }
    }


}
