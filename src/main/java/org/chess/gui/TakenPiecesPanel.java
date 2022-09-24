package org.chess.gui;

import com.google.common.primitives.Ints;
import org.chess.board.Move;
import org.chess.piece.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Rishikesh
 * @project chess
 */
public class TakenPiecesPanel extends JPanel {
    private static final Color PANEL_COLOR = Color.decode("0xFDF6");
    private static final Dimension TAKE_PIECES_DETENTION = new Dimension(40, 80);
    private static final EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);
    private final JPanel northPanel;
    private final JPanel southPanel;

    public TakenPiecesPanel() {
        super(new BorderLayout());
        setBackground(PANEL_COLOR);
        setBorder(PANEL_BORDER);
        this.northPanel = new JPanel(new GridLayout(8, 2));
        this.southPanel = new JPanel(new GridLayout(8, 2));
        this.northPanel.setBackground(PANEL_COLOR);
        this.southPanel.setBackground(PANEL_COLOR);
        this.add(this.northPanel, BorderLayout.NORTH);
        this.add(this.southPanel, BorderLayout.SOUTH);
        setPreferredSize(TAKE_PIECES_DETENTION);
    }

    public void redo(final ChessBoard.MoveLog moveLog) {
        this.southPanel.removeAll();
        this.northPanel.removeAll();

        final List<Piece> whiteTakenPieces = new ArrayList<>();
        final List<Piece> blackTakenPieces = new ArrayList<>();
        for (final Move move : moveLog.getMoves()) {
            if (move.isAttack()) {
                final Piece takenPiece = move.getAttackedPiece();
                if (takenPiece.getPieceColor().isWhite()) {
                    whiteTakenPieces.add(takenPiece);
                } else if (takenPiece.getPieceColor().isBlack()) {
                    blackTakenPieces.add(takenPiece);
                }
            }
        }

        whiteTakenPieces.sort((piece, t1) -> Ints.compare(piece.getPieceValue(), t1.getPieceValue()));
        blackTakenPieces.sort((piece, t1) -> Ints.compare(piece.getPieceValue(), t1.getPieceValue()));
        for (Piece takenPiece : whiteTakenPieces) {
            try {
                final BufferedImage image = ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream
                        (takenPiece
                                .getPieceColor()
                                .toString()
                                .charAt(0)
                                + takenPiece
                                .getPieceType()
                                .toString()
                                + ".png")));
                final ImageIcon icon = new ImageIcon(image);
                final JLabel imageLabel = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(
                        icon.getIconWidth() - 15, icon.getIconWidth() - 15, Image.SCALE_SMOOTH)));
                this.southPanel.add(imageLabel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (Piece takenPiece : blackTakenPieces) {
            try {
                final BufferedImage image = ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream
                        (takenPiece
                                .getPieceColor()
                                .toString()
                                .charAt(0)
                                + takenPiece
                                .getPieceType()
                                .toString()
                                + ".png")));
                final ImageIcon icon = new ImageIcon(image);
                final JLabel imageLabel = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(
                        icon.getIconWidth() - 15, icon.getIconWidth() - 15, Image.SCALE_SMOOTH)));
                this.northPanel.add(imageLabel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        validate();
    }
}
