- **TC1: startNewGame_prepareBoard** ()
- **State of the system:** player1=P1，player2=p2
- **Expected output:** a board with two player, initiate all data
- **Implemented:*implemented*

- **TC2: startNewGame_sameInput** ()
- **State of the system:** player1=p1，player2=p1
- **Expected output:** throw Error shows need two different player
- **Implemented:*implemented*

- **TC3: makeMove_validMove** ()
- **State of the system:** piece=Rook, valid move, halfMoveClock=0, from (7,0) to (0,7)
- **Expected output:** valid, halfMoveClock=1, last move equal this move, add move to movehistory and positionhistory
- **Implemented:*implemented*
-
- **TC4: makeMove_InCheck_white** ()
- **State of the system:** piece=Rook, from (7,0) to (0,0) to check, white turn, halfMoveClock=0
- **Expected output:** CHECK, halfMoveClock=1, last move equal this move, add move to movehistory and positionhistory
- **Implemented:*implemented*
-
- **TC5: makeMove_Checkmate_black** ()
- **State of the system:** piece=knight, from (0,0) to (7,0) to checkmate, black turn, halfMoveClock=0
- **Expected output:** CHECKMATE, halfMoveClock=1, last move equal this move, add move to movehistory and positionhistory
- **Implemented:*implemented*

- **TC6: makeMove_Stalemate_white** ()
- **State of the system:** piece=knight, from (0,0) to (7,7) to stalemate, white turn, halfMoveClock=1
- **Expected output:** Stalemate, halfMoveClock=1, last move equal this move, add move to movehistory and positionhistory
- **Implemented:*implemented*

- **TC7: makeMove_sameColorCapture_white** ()
- **State of the system:** piece=knight, white turn, from (7,7) to (0,7)
- **Expected output:** INVALID_SAME_COLOR_CAPTURE, last move is null
- **Implemented:*implemented*

- **TC8: makeMove_emptySource_white** ()
- **State of the system:**  white turn
- **Expected output:** INVALID_EMPTY_SOURCE, from (7,7) to (0,7), last move is null
- **Implemented:*implemented*

- **TC9: makeMove_wrongTurn_white** ()
- **State of the system:**  piece=Bishop, from (7,7) to (1,7), white turn, black move
- **Expected output:** INVALID_EMPTY_SOURCE, last move is null
- **Implemented:*implemented*

- **TC10: makeMove_capture_white** ()
- **State of the system:** piece=knight, from (7,7) to (0,7),white turn, halfMoveClock=99
- **Expected output:** VALID, halfMoveClock=100, last move equal this move, add move to movehistory and positionhistory
- **Implemented:*implemented*

- **TC11: makeMove_outOfBound_black** ()
- **State of the system:** black turn, from (0,-1) to (0,0)
- **Expected output:** out of bound error
- **Implemented:*implemented*

- **TC12: makeMove_outOfBound_white** ()
- **State of the system:** black turn, from (7,7) to (8,0)
- **Expected output:** out of bound error
- **Implemented:*implemented*

- **TC13: makeMove_illegalMove_black** ()
- **State of the system:** piece=knight, black turn, from (7,1) to (1,0)
- **Expected output:** illegal move error
- **Implemented:*implemented*

- **TC14: makeMove_threefoldRepetition_white** ()
- **State of the system:** piece=QUEEN, white turn, from (7,1) to (1,0)
- **Expected output:** draw
- **Implemented:*implemented*

- **TC15: makeMove_50Move_white** ()
- **State of the system:** piece=QUEEN, white turn, from (7,1) to (1,0)
- **Expected output:** draw
- **Implemented:*implemented*

- **TC16: makeMove_selfCheck_white** ()
- **State of the system:** piece=QUEEN, white turn, from (7,1) to (1,0)
- **Expected output:** self-check
- **Implemented:*implemented*

- **TC17: makeMove_pawnPromoption_white** ()
- **State of the system:** piece=Pawn, white turn, from (7,0) to (0,0)
- **Expected output:** check
- **Implemented:*implemented*

- **TC18: makeMove_pawnPromoption_black** ()
- **State of the system:** piece=Pawn, white turn, from (0,1) to (7,1)
- **Expected output:** check
- **Implemented:*implemented*

- **TC19: switchTurn_white** ()
- **State of the system:** white_turn
- **Expected output:** black_turn
- **Implemented:*implemented*

- **TC20: switchTurn_black** ()
- **State of the system:** black_turn
- **Expected output:** white_turn
- **Implemented:*implemented*

- **TC21: isInCheck_check** ()
- **State of the system:** white_turn, piece (0,0), king (7,7)
- **Expected output:** true
- **Implemented:*implemented*

- **TC22: isInCheck_check_firstIsNull** ()
- **State of the system:** white_turn, piece (0,1), king (7,7)
- **Expected output:** true
- **Implemented:*implemented*

- **TC23: isInCheck_check_false** ()
- **State of the system:** black_turn, piece (7,7), king (0,0)
- **Expected output:** false
- **Implemented:*implemented*

- **TC24: isCheckmate_true** ()
- **State of the system:** white_turn, king at(0,0), always check
- **Expected output:** true
- **Implemented:*implemented*

- **TC25: isCheckmate_false** ()
- **State of the system:** black_turn, king at(7,7), first time isincheck return true then return false
- **Expected output:** false
- **Implemented:*implemented*

- **TC26: isStalemate_allCheck_true** ()
- **State of the system:** black_turn, king at(7,7), all place are check
- **Expected output:** True
- **Implemented:*implemented*

- **TC27: isStalemate_noMove_true** ()
- **State of the system:** black_turn, king at(7,7), no place can move
- **Expected output:** True
- **Implemented:*implemented*

- **TC28: isStalemate_false** ()
- **State of the system:** black_turn, king at(7,7), can move and not check
- **Expected output:** False
- **Implemented:*implemented*

- **TC29: resign** ()
- **State of the system:**
- **Expected output:** moveresult=RESIGN
- **Implemented:*implemented*

- **TC30: pawnPromption_Queen** ()
- **State of the system:** color=white, chose Queen
- **Expected output:** create Queen
- **Implemented:*implemented*

- **TC31: pawnPromption_Bishop** ()
- **State of the system:** color=black, choose Bishop
- **Expected output:** create Bishop
- **Implemented:*implemented*

- **TC32: pawnPromption_Knight** ()
- **State of the system:** color=black, choose Knight
- **Expected output:** create Knight
- **Implemented:*implemented*

- **TC33: pawnPromption_Rook** ()
- **State of the system:** color=white, choose Rook
- **Expected output:** create Rook
- **Implemented:*implemented*

- **TC34: getStatus_whiteTurn** ()
- **State of the system:** status=whiteTurn
- **Expected output:** whiteTurn
- **Implemented:*implemented*

- **TC35: getStatus_blackTurn** ()
- **State of the system:** status=blackTurn
- **Expected output:** blackTurn
- **Implemented:*implemented*

- **TC36: getStatus_whiteWin** ()
- **State of the system:** status=whiteWin
- **Expected output:** whiteWin
- **Implemented:*implemented*

- **TC37: getStatus_blackWin** ()
- **State of the system:** status=blackWin
- **Expected output:** blackWin
- **Implemented:*implemented*

- **TC38: getStatus_white_check** ()
- **State of the system:** status=white_check
- **Expected output:** white_check
- **Implemented:*implemented*
-
- **TC39: getStatus_black_check** ()
- **State of the system:** status=black_check
- **Expected output:** black_check
- **Implemented:*implemented*

- **TC40: getStatus_draw** ()
- **State of the system:** status=draw
- **Expected output:** draw
- **Implemented:*implemented*

- **TC41: getStatus_resigned** ()
- **State of the system:** status=resigned
- **Expected output:** resigned
- **Implemented:*implemented*

- **TC42: getMoveHistory_empty** ()
- **State of the system:** history is empty
- **Expected output:** empty list
- **Implemented:*implemented*

- **TC43: getMoveHistory_nonEmpty** ()
- **State of the system:** history=(move((0,0),(7,7)))
- **Expected output:** (move((0,0),(7,7)))
- **Implemented:*implemented*

- **TC44: isCheckmate_notCheck** ()
- **State of the system:** color=black
- **Expected output:** return not check
- **Implemented:*implemented*

- **TC45: isCheckmate_true_white** ()
- **State of the system:** color=white
- **Expected output:** return true
- **Implemented:*implemented*

- **TC46: isCheckmate_outbound** ()
- **State of the system:** color=white
- **Expected output:** return true
- **Implemented:*implemented*

- **TC47: isCheckmate_targetOutBound** ()
- **State of the system:** color=white, piece can move is outofbound
- **Expected output:** return true
- **Implemented:*implemented*

- **TC48: isStalemate_false_check** ()
- **State of the system:** color=black, isInCheck
- **Expected output:** return false
- **Implemented:*implemented*

- **TC49: getLastMove** ()
- **State of the system:** lastmove=Move((0,0),(7,7))
- **Expected output:** return lastmove
- **Implemented:*implemented*

- **TC50: timeOut_white** ()
- **State of the system:**
- **Expected output:** status=black_win
- **Implemented:*implemented*

- **TC51: timeOut_black** ()
- **State of the system:**
- **Expected output:** status=white_win
- **Implemented:*implemented*

- **TC52: createNotation_castle** ()
- **State of the system:**king from (7,4) to (7,6)
- **Expected output:** return O-O
- **Implemented:*implemented*

- **TC53: createNotation_QueenCastle** ()
- **State of the system:**queen from (7,4) to (7,2)
- **Expected output:** return O-O-O
- **Implemented:*implemented*

- **TC54: createNotation_normalMove** ()
- **State of the system:**pawn from (6,4) to (4,4)
- **Expected output:** return pawn from (6,4) -> (4,4)
- **Implemented:*implemented*

- **TC55: createNotation_captureByEnPassant** ()
- **State of the system:**pawn from (3,4) to (2,5)
- **Expected output:** return pawn from (3,4) -> (2,5),captures PAWN en passant
- **Implemented:*implemented*

- **TC56: createNotation_promotion** ()
- **State of the system:**promotion type is Bishop, from (6,4) to (7,4)
- **Expected output:** return pawn from (6,4) -> (7,4),promotes to Bishop
- **Implemented:*implemented*

- **TC57: isCastleMove_false** ()
- **State of the system:**king from (7,4) to (7,5)
- **Expected output:** false
- **Implemented:*implemented*

- **TC58: isCastleMove_differentRow_false** ()
- **State of the system:**king from (7,4) to (6,4)
- **Expected output:** false
- **Implemented:*implemented*

- **TC59: isCastleMove_blocked_false** ()
- **State of the system:**king from (7,4) rook at (7,7), blocked by rook at (7,5)
- **Expected output:** false
- **Implemented:*implemented*

- **TC60: isCastleMove_move3Col_false** ()
- **State of the system:**king from (7,4) to (7,7)
- **Expected output:** false
- **Implemented:*implemented*

- **TC61: isCastleMove_true** ()
- **State of the system:**king from (7,4), rook at (7,7)
- **Expected output:** true
- **Implemented:*implemented*

- **TC62: performCastle** ()
- **State of the system:**king from (7,4), rook at (7,7)
- **Expected output:** sets both piece to moved
- **Implemented:*implemented*

- **TC63: isEnPassantMove_false** ()
- **State of the system:**pawn from (3,4) to (2,4)
- **Expected output:** false
- **Implemented:*implemented*

- **TC64: isEnPassantMove_cross2Col_false** ()
- **State of the system:**pawn from (3,4) to (2,6)
- **Expected output:** false
- **Implemented:*implemented*

- **TC65: isEnPassantMove_previousMove1Row_false** ()
- **State of the system:**previous pawn from (2,5) to (3,5)
- **Expected output:** false
- **Implemented:*implemented*

- **TC66: isEnPassantMove_true** ()
- **State of the system:**previous pawn from (1,5) to (3,5), current pawn at (3,4) move to (2,5)
- **Expected output:** true
- **Implemented:*implemented*

- **TC67: makeMove_enPassant** ()
- **State of the system:**move from (0,0) to (7,7), enPassant
- **Expected output:** valid move
- **Implemented:*implemented*

- **TC68: makeMove_castleMove** ()
- **State of the system:**move from (7,4) to (7,6), castle
- **Expected output:** valid move
- **Implemented:*implemented*

- **TC69: makeMove_validMove_blackTurn** ()
- **State of the system:** piece=Rook, valid move, halfMoveClock=0, from (7,0) to (0,7)
- **Expected output:** valid, halfMoveClock=1, last move equal this move, add move to movehistory and positionhistory
- **Implemented:*implemented*

- **TC70: isInCheck_check_whiteInCheck** ()
- **State of the system:** white_turn, piece (0,0), king (7,7)
- **Expected output:** true
- **Implemented:*implemented*

- **TC71: isEnPassantMove_movingPieceNull** ()
- **State of the system:** piece is null
- **Expected output:** false
- **Implemented:*implemented*

- **TC72: isEnPassantMove_lastMoveNotPawn** ()
- **State of the system:** last move is not pawn
- **Expected output:** false
- **Implemented:*implemented*

- **TC73: isEnPassantMove_sameColor** ()
- **State of the system:** last move has same color as current move
- **Expected output:** false
- **Implemented:*implemented*

- **TC74: isEnPassantMove_block** ()
- **State of the system:** the cell move to is blocked
- **Expected output:** false
- **Implemented:*implemented*

- **TC75: isEnPassantMove_differentRow** ()
- **State of the system:** the cell last move to is wrong row
- **Expected output:** false
- **Implemented:*implemented*

- **TC76: isEnPassantMove_black** ()
- **State of the system:** the piece is black
- **Expected output:** true
- **Implemented:*implemented*

- **TC77: isEnPassantMove_wrongCol** ()
- **State of the system:** the cell last move to is wrong col
- **Expected output:** false
- **Implemented:*implemented*

- **TC78: isEnPassantMove_wrongDirection** ()
- **State of the system:** the move goes to wrong direction, from (2,4) to (3,5) for white pawn
- **Expected output:** false
- **Implemented:*implemented*

- **TC79: isCastleMove_QueenSide** ()
- **State of the system:** move to queen side
- **Expected output:** true
- **Implemented:*implemented*

- **TC80: isCastleMove_kingMoved** ()
- **State of the system:** king hasMoved is true
- **Expected output:** false
- **Implemented:*implemented*

- **TC81: isCastleMove_notRook** ()
- **State of the system:** king hasMoved is true
- **Expected output:** false
- **Implemented:*implemented*

- **TC82: isCastleMove_rookMoved** ()
- **State of the system:** rook hasMoved is true
- **Expected output:** false
- **Implemented:*implemented*

- **TC83: isCastleMove_differentColorKingRook** ()
- **State of the system:** rook is black and king is white
- **Expected output:** false
- **Implemented:*implemented*