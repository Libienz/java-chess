package chess.repository.mapper;

import chess.domain.piece.Team;

public class TeamMapper {

    Team mapToTeam(String value) {
        if (value.equals("WHITE")) {
            return Team.WHITE;
        }
        return Team.BLACK;
    }
}
