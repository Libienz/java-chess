package chess.repository;

import chess.dto.PieceDto;
import chess.dto.PiecesDto;
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

    public void savePieces(PiecesDto pieces) {
        try (Connection connection = connectionManager.getConnection()) {
            pieces.getPieces().forEach(piece -> savePiece(piece, connection));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void savePiece(PieceDto pieceDto, Connection connection) {
        String query = String.format("INSERT INTO %s (position, team, type) VALUES (?, ?, ?)", TABLE_NAME);

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, pieceDto.getPosition());
            pstmt.setString(2, pieceDto.getTeam());
            pstmt.setString(3, pieceDto.getType());
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<List<PieceDto>> findPieces() {
        String query = String.format("SELECT * FROM %s", TABLE_NAME);

        List<PieceDto> result = new ArrayList<>();

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet resultSet = pstmt.executeQuery()) {

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String position = resultSet.getString("position");
                String team = resultSet.getString("team");
                String type = resultSet.getString("type");

                result.add(new PieceDto(position, team, type));
            }
            if (!result.isEmpty()) {
                return Optional.of(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void deleteAll() {
        String query = String.format("DELETE FROM %s", TABLE_NAME);

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
