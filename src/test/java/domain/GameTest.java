package domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import org.easymock.EasyMock;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {
    //    Board board;
    @Test
    public void startNewGame_prepareBoard(){
        Player player1=EasyMock.createMock(Player.class);
        Player player2=EasyMock.createMock(Player.class);
        Board board=EasyMock.createMock(Board.class);
        EasyMock.expect(player1.getName()).andStubReturn("p1");
//        EasyMock.replay(player1);
        EasyMock.expect(player2.getName()).andStubReturn("p2");
//        EasyMock.replay(player2);
        board.initBoard();
        EasyMock.expectLastCall();
        EasyMock.replay(board);
//        List<String> names = List.of("ROOK");
//        EasyMock.expect(board.initBoard()).andStubReturn(names);
        GameStatus status=GameStatus.WHITE_TURN;
        List<Move> moveHistory=new ArrayList<>();
        Move lastMove=null;
        int halfMoveClock=0;
        Game game=new Game(board, status, moveHistory, lastMove, halfMoveClock);
//        Player white = EasyMock.createMock(Player.class);
//        Player black = EasyMock.createMock(Player.class);
        player1.setColor(Color.WHITE);
        EasyMock.expectLastCall();
        EasyMock.replay(player1);
        player2.setColor(Color.BLACK);
        EasyMock.expectLastCall();
        EasyMock.replay(player2);
        game.startNewGame(player1,player2);
        EasyMock.verify(player1,player2,board);

//        Board board=game.getBoard();
//        assertPiece(board, 0, 0, PieceType.ROOK, PieceColor.BLACK);
//        assertPiece(board, 0, 1, PieceType.KNIGHT, PieceColor.BLACK);
//        assertPiece(board, 0, 2, PieceType.BISHOP, PieceColor.BLACK);
//        assertPiece(board, 0, 3, PieceType.QUEEN, PieceColor.BLACK);
//        assertPiece(board, 0, 4, PieceType.KING, PieceColor.BLACK);
//
//        for (int col = 0; col < 8; col++) {
//            assertPiece(board, 1, col, PieceType.PAWN, PieceColor.BLACK);
//            assertPiece(board, 6, col, PieceType.PAWN, PieceColor.WHITE);
//        }
//
//        for (int row = 2; row <= 5; row++) {
//            for (int col = 0; col < 8; col++) {
//                assertNull(board.getPieceAt(new Location(row, col)));
//            }
//        }
//
//        assertPiece(board, 7, 0, PieceType.ROOK, PieceColor.WHITE);
//        assertPiece(board, 7, 1, PieceType.KNIGHT, PieceColor.WHITE);
//        assertPiece(board, 7, 2, PieceType.BISHOP, PieceColor.WHITE);
//        assertPiece(board, 7, 3, PieceType.QUEEN, PieceColor.WHITE);
//        assertPiece(board, 7, 4, PieceType.KING, PieceColor.WHITE);
    }

//    private void assertPiece(Board board, int row, int col,
//                             PieceType expectedType,
//                             PieceColor expectedColor) {
//        Piece piece = board.getPieceAt(new Location(row, col));
//
//        assertNotNull(piece);
//        assertEquals(expectedType, piece.getType());
//        assertEquals(expectedColor, piece.getColor());
//        assertFalse(piece.hasMoved());
//    }

}
