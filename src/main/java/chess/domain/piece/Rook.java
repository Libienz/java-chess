package chess.domain.piece;

import chess.domain.position.Position;

public class Rook implements Piece {
    @Override
    public boolean canMoveTo(Position target) {
        //TODO: 룩 움직임 전략 구현 필요
        return false;
    }
}