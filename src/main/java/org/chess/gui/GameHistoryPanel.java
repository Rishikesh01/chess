package org.chess.gui;

import org.chess.board.Board;
import org.chess.board.Move;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rishikesh
 * @project chess
 */
public class GameHistoryPanel extends JPanel {

    private static final Dimension HISTORY_PANEL_DIMENSTION = new Dimension(100, 400);
    private final DataModel model;
    private final JScrollPane scrollPane;

    public GameHistoryPanel() {
        this.setLayout(new BorderLayout());
        this.model = new DataModel();
        final JTable table = new JTable(model);
        table.setRowHeight(15);
        this.scrollPane = new JScrollPane(table);
        scrollPane.setColumnHeaderView(table.getTableHeader());
        scrollPane.setPreferredSize(HISTORY_PANEL_DIMENSTION);
        this.add(scrollPane, BorderLayout.CENTER);
        this.setVisible(true);
    }

    void redo(final Board board, final ChessBoard.MoveLog moveLog) {
        int currentRow = 0;
        this.model.clear();
        for (final Move move : moveLog.getMoves()) {
            final String moveText = move.toString();
            if (move.getMovedPiece().getPieceColor().isWhite()) {
                this.model.setValueAt(moveText, currentRow, 0);

            } else if (move.getMovedPiece().getPieceColor().isBlack()) {
                this.model.setValueAt(moveText, currentRow, 1);
                currentRow++;
            }
        }
        if (moveLog.getMoves().size() > 0) {
            final Move move = moveLog.getMoves().get(moveLog.size() - 1);
            final String moveText = move.toString();

            if (move.getMovedPiece().getPieceColor().isWhite()) {
                this.model.setValueAt(moveText + calcuateCheckAndCheckMate(board), currentRow - 1, 1);
            }
        }
        final JScrollBar vertical = scrollPane.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    }

    private String calcuateCheckAndCheckMate(Board board) {
        if (board.currentPlayer().isInCheckMate()) {
            return "#";
        } else if (board.currentPlayer().isInCheck()) {
            return "+";
        }
        return "";
    }

    private static class DataModel extends DefaultTableModel {
        private final List<Row> values;
        private final String[] NAMES = {"White", "BLACK"};


        public DataModel() {
            this.values = new ArrayList<>();
        }

        public void clear() {
            this.values.clear();
            setRowCount(0);
        }

        @Override
        public int getRowCount() {
            if(this.values == null)
                return  0;
            return this.values.size();
        }

        @Override
        public int getColumnCount() {
            return NAMES.length;
        }

        @Override
        public Object getValueAt(final int row, final int column) {
            final Row currentRow = this.values.get(row);
            if (column == 0)
                return currentRow.getWhiteMoves();
            if (column == 1)
                return currentRow.getBlackMoves();
            return null;
        }

        @Override
        public void setValueAt(final Object value, final int row, final int column) {
            final Row currentRow;
            if (this.values.size() <= row) {
                currentRow = new Row();
                this.values.add(currentRow);
            } else {
                currentRow = this.values.get(row);
            }
            if (column == 0) {
                currentRow.setWhiteMoves((String) value);
            } else if (column == 1) {
                currentRow.setBlackMoves((String) value);
                fireTableCellUpdated(row, column);
            }
        }

        @Override
        public Class<?> getColumnClass(final int column) {
            return Move.class;
        }

        @Override
        public String getColumnName(final int column) {
            return NAMES[column];
        }

    }

    private static class Row {
        private String whiteMoves;
        private String blackMoves;


        public String getWhiteMoves() {
            return this.whiteMoves;
        }

        public void setWhiteMoves(String whiteMoves) {
            this.whiteMoves = whiteMoves;
        }

        public String getBlackMoves() {
            return this.blackMoves;
        }

        public void setBlackMoves(String blackMoves) {
            this.blackMoves = blackMoves;
        }
    }
}
