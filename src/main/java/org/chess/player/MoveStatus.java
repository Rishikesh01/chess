package org.chess.player;

/**
 * @author Rishikesh
 * @project chess
 */
public enum MoveStatus {
    DONE {
        @Override
        public boolean isDone() {
            return true;
        }
    },

    ILLEGAL {
        @Override
        public boolean isDone() {
            return false;
        }
    }, IN_CHECK {
        @Override
        public boolean isDone() {
            return false;
        }
    };


    public abstract boolean isDone();
    }
