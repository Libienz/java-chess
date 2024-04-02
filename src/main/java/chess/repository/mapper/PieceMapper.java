package chess.repository.mapper;

import chess.domain.piece.Bishop;
import chess.domain.piece.King;
import chess.domain.piece.Knight;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.piece.Queen;
import chess.domain.piece.Rook;
import chess.domain.piece.Team;

class PieceMapper {
    Piece mapToPiece(String pieceTypeValue, Team team) {
        if ("pawn".equals(pieceTypeValue)) {
            return new Pawn(team);
        }
        if ("knight".equals(pieceTypeValue)) {
            return new Knight(team);
        }
        if ("bishop".equals(pieceTypeValue)) {
            return new Bishop(team);
        }
        if ("rook".equals(pieceTypeValue)) {
            return new Rook(team);
        }
        if ("queen".equals(pieceTypeValue)) {
            return new Queen(team);
        }
        if ("king".equals(pieceTypeValue)) {
            return new King(team);
        }
        throw new IllegalArgumentException("Invalid piece type: " + pieceTypeValue);
    }
}
