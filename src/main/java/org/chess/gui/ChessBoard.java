package org.chess.gui;

import javax.swing.*;
import java.awt.*;

/**
 * @author Rishikesh
 * @project chess
 */
public class ChessBoard {
    private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(640,640);
    private final JFrame gameFrame;
    public ChessBoard(){
        this.gameFrame =  new JFrame("JChess");
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.gameFrame.setVisible(true);
    }
}
