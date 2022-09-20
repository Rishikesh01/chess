package org.chess.gui;

import org.chess.board.BoardUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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

    public ChessBoard() {
        this.gameFrame = new JFrame("JChess");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar chessBoardMenuBar = createChessBoardMenuBar();
        this.gameFrame.setJMenuBar(chessBoardMenuBar);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.gameFrame.setVisible(true);
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
    }

    private class TilePanel extends JPanel {
        private final int tileId;

        public TilePanel(final BoardPanel boardPanel, final int tileId) {
            super(new GridLayout());
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL_DEMENTION);
            assignTileColor();
            validate();
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
    }


}
