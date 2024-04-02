package chess.repository;

import chess.domain.board.ChessBoard;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import chess.dto.PieceDto;
import chess.dto.PiecesDto;
import chess.repository.mapper.ChessBoardMapper;
import chess.repository.mapper.PieceMapper;
import chess.repository.mapper.PositionMapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PieceRepository {
    private static final String TABLE_NAME = "pieces";

    private final ConnectionManager connectionManager;

    public PieceRepository(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void saveChessBoard(ChessBoard chessBoard) {
        try (Connection connection = connectionManager.getConnection()) {
            chessBoard.getBoard().keySet()
                    .forEach(position -> savePiece(chessBoard.findPieceByPosition(position), position, connection));
        } catch (SQLException e) {
            throw new RuntimeException("기물 저장 과정 중 오류 발생");
        }
    }

    private void savePiece(Piece piece, Position position, Connection connection) {
        String query = String.format("INSERT INTO %s (position, team, type) VALUES (?, ?, ?)", TABLE_NAME);

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, PositionMapper.mapPositionToValue(position));
            pstmt.setString(2, PieceMapper.mapPieceTeamToValue(piece));
            pstmt.setString(3, PieceMapper.mapPieceTypeToValue(piece));
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException("기물 저장 과정 중 오류 발생");
        }
    }

    public Optional<ChessBoard> findChessBoard() {
        String query = String.format("SELECT * FROM %s", TABLE_NAME);

        List<PieceDto> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet resultSet = pstmt.executeQuery()) {

            while (resultSet.next()) {
                String position = resultSet.getString("position");
                String team = resultSet.getString("team");
                String type = resultSet.getString("type");
                result.add(new PieceDto(position, team, type));
            }
            if (result.isEmpty()) {
                return Optional.empty();
            }
            ChessBoard chessBoard = ChessBoardMapper.mapToBoard(new PiecesDto(result));
            return Optional.of(chessBoard);
        } catch (SQLException e) {
            throw new RuntimeException("보드 조회 과정 중 오류 발생");
        }
    }

    public void deleteAll() {
        String query = String.format("DELETE FROM %s", TABLE_NAME);

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("기물 조회 과정 중 오류 발생");
        }
    }
}
