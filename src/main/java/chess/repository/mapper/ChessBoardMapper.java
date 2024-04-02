package chess.repository.mapper;

import chess.domain.board.ChessBoard;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import chess.dto.PieceDto;
import chess.dto.PiecesDto;
import java.util.HashMap;
import java.util.Map;

public class ChessBoardMapper {

    public static ChessBoard mapToBoard(PiecesDto piecePlacements) {
        Map<Position, Piece> positionPiece = new HashMap<>();

        for (PieceDto pieceDto : piecePlacements.getPieces()) {
            Position position = PositionMapper.mapToPosition(pieceDto.getPosition());
            Piece piece = PieceMapper.mapValueToPiece(pieceDto.getType(), pieceDto.getTeam());
            positionPiece.put(position, piece);
        }

        return new ChessBoard(positionPiece);
    }
}
