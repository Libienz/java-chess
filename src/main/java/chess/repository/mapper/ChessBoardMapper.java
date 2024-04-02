package chess.repository.mapper;

import chess.domain.board.ChessBoard;
import chess.domain.piece.Piece;
import chess.domain.piece.Team;
import chess.domain.position.Position;
import chess.dto.PieceDto;
import chess.dto.PiecesDto;
import java.util.HashMap;
import java.util.Map;

public class ChessBoardMapper {
    private final PieceMapper pieceMapper;
    private final PositionMapper positionMapper;
    private final TeamMapper teamMapper;

    public ChessBoardMapper(PieceMapper pieceMapper, PositionMapper positionMapper, TeamMapper teamMapper) {
        this.pieceMapper = pieceMapper;
        this.positionMapper = positionMapper;
        this.teamMapper = teamMapper;
    }

    public ChessBoard mapToBoard(PiecesDto piecePlacements) {
        Map<Position, Piece> positionPiece = new HashMap<>();

        for (PieceDto pieceDto : piecePlacements.getPieces()) {
            Position position = positionMapper.mapToPosition(pieceDto.getPosition());
            Team team = teamMapper.mapToTeam(pieceDto.getTeam());
            Piece piece = pieceMapper.mapToPiece(pieceDto.getType(), team);

            positionPiece.put(position, piece);
        }

        return new ChessBoard(positionPiece);
    }
}
