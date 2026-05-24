package domain;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class Game {
    Board board;
    Player white;
    Player black;
    Player currentPlayer;
    GameStatus status;
    List<Move> moveHistory;
    Move lastMove;
    int halfMoveClock;
    Map<String, Integer> positionHistory;
    public Game(Board board,GameStatus status, List<Move> moveHistory, Move lastMove, int halfMoveClock){
        this.board=board;
        this.status=status;
        this.moveHistory=moveHistory;
        this.lastMove=lastMove;
        this.halfMoveClock=halfMoveClock;
    }
    public void startNewGame(Player p1, Player p2) {
        if (!p1.getName().equals( p2.getName())) {
            this.white = p1;
            this.black = p2;
            white.setColor(Color.WHITE);
            black.setColor(Color.BLACK);
            currentPlayer = white;
            //        board.setPiece(new Location(0, 0), new Piece(PieceType.ROOK, PieceColor.BLACK));
            //        board.setPiece(new Location(0, 1), new Piece(PieceType.KNIGHT, PieceColor.BLACK));
            //        board.setPiece(new Location(0, 2), new Piece(PieceType.BISHOP, PieceColor.BLACK));
            //        board.setPiece(new Location(0, 3), new Piece(PieceType.QUEEN, PieceColor.BLACK));
            //        board.setPiece(new Location(0, 4), new Piece(PieceType.KING, PieceColor.BLACK));
            //        board.setPiece(new Location(0, 5), new Piece(PieceType.BISHOP, PieceColor.BLACK));
            //        board.setPiece(new Location(0, 6), new Piece(PieceType.KNIGHT, PieceColor.BLACK));
            //        board.setPiece(new Location(0, 7), new Piece(PieceType.ROOK, PieceColor.BLACK));
            board.initBoard();
            //        for (int col = 0; col < 8; col++) {
            //            board.setPiece(new Location(1, col), new Piece(PieceType.PAWN, PieceColor.BLACK));
            //        }
            //
            //        // White pieces
            //        for (int col = 0; col < 8; col++) {
            //            board.setPiece(new Location(6, col), new Piece(PieceType.PAWN, PieceColor.WHITE));
            //        }
            //
            //        board.setPiece(new Location(7, 0), new Piece(PieceType.ROOK, PieceColor.WHITE));
            //        board.setPiece(new Location(7, 1), new Piece(PieceType.KNIGHT, PieceColor.WHITE));
            //        board.setPiece(new Location(7, 2), new Piece(PieceType.BISHOP, PieceColor.WHITE));
            //        board.setPiece(new Location(7, 3), new Piece(PieceType.QUEEN, PieceColor.WHITE));
            //        board.setPiece(new Location(7, 4), new Piece(PieceType.KING, PieceColor.WHITE));
            //        board.setPiece(new Location(7, 5), new Piece(PieceType.BISHOP, PieceColor.WHITE));
            //        board.setPiece(new Location(7, 6), new Piece(PieceType.KNIGHT, PieceColor.WHITE));
            //        board.setPiece(new Location(7, 7), new Piece(PieceType.ROOK, PieceColor.WHITE));
            //    }
        }
    }

}
