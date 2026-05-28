# Overall Rule
This folder should contain the BVA analysis. There should be one .md for each class, and there should be BVA analysis for each public method.

# What to Include in each BVA Analysis File (like `MyVector.md`)

You are encouraged to document your intermediate analysis results for Steps 1-3.
However, you are only required to document Step 4.

- **TC1: startNewGame_prepareBoard** ()
- **State of the system:** player1=P1，player2=p2
- **Expected output:** a board with two player, initiate all data
- **Implemented:*implemented* 

- **TC2: startNewGame_sameInput** ()
- **State of the system:** player1=p1，player2=p1
- **Expected output:** throw Error shows need two different player
- **Implemented:*implemented* 

- **TC3: makeMove_validMove** ()
- **State of the system:** piece=Rook, valid move
- **Expected output:** valid
- **Implemented:*implemented* 
- 
- **TC4: makeMove_InCheck_white** ()
- **State of the system:** piece=Rook, from (7,0) to (0,0) to check, white turn
- **Expected output:** CHECK
- **Implemented:*implemented* 
- 
- **TC5: makeMove_Checkmate_black** ()
- **State of the system:** piece=knight, from (0,0) to (7,0) to checkmate, black turn
- **Expected output:** CHECKMATE
- **Implemented:*implemented* 

- **TC6: makeMove_Stalemate_white** ()
- **State of the system:** piece=knight, from (0,0) to (7,7) to stalemate, white turn
- **Expected output:** Stalemate
- **Implemented:*implemented* 

- **TC7: makeMove_sameColorCapture_white** ()
- **State of the system:** piece=knight, white turn
- **Expected output:** INVALID_SAME_COLOR_CAPTURE
- **Implemented:*implemented* 

- **TC8: makeMove_emptySource_white** ()
- **State of the system:**  white turn
- **Expected output:** INVALID_EMPTY_SOURCE
- **Implemented:*implemented* 

- **TC8: makeMove_wrongTurn_white** ()
- **State of the system:**  piece=Bishop, from (0,0) to (1,7), white turn, black move
- **Expected output:** INVALID_EMPTY_SOURCE
- **Implemented:*implemented* 

