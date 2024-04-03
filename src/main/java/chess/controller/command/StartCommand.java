package chess.controller.command;

import chess.service.ChessGameService;
import chess.service.dto.BoardDto;
import chess.view.OutputView;

public class StartCommand implements Command {
    @Override
    public ExecuteResult execute(ChessGameService chessGameService, OutputView outputView) {
        BoardDto boardDto = chessGameService.startChessGame();
        outputView.printChessBoardMessage(boardDto);
        return new ExecuteResult(true, true);
    }
}
