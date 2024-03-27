package chess.domain.piece;

import chess.domain.board.ChessBoard;
import chess.domain.position.Position;

public class Knight extends Piece {
    private static final int L_SHAPE_SQUARED_DISTANCE = 5;
    private static final double SCORE_VALUE = 2.5;

    public Knight(Team team) {
        super(team);
    }

    @Override
    boolean canNotMoveByItsOwnInPassing(Position start, Position destination) {
        return start.squaredDistanceWith(destination) != L_SHAPE_SQUARED_DISTANCE;
    }

    @Override
    boolean canNotMoveByBoardStatus(Position start, Position destination, ChessBoard chessBoard) {
        return false;
    }

    @Override
    Score score() {
        return new Score(SCORE_VALUE);
    }
}
