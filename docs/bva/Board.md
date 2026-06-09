# BVA Analysis for Board Setup

## Method under test: Location(String algebraic)

- **TC1: LocationValidMinMinReturnsRow7Col0** (  )
    - **State of the system:** algebraic = "a1"
    - **Expected output:** row = 7, col = 0
    - **Implemented:** Yes

- **TC2: LocationValidMaxMaxReturnsRow0Col7** (  )
    - **State of the system:** algebraic = "h8"
    - **Expected output:** row = 0, col = 7
    - **Implemented:** yes

- **TC3: LocationEmptyStringThrowsIllegalArgumentException** (  )
    - **State of the system:** algebraic = ""
    - **Expected output:** IllegalArgumentException
    - **Implemented:** yes

- **TC4: LocationStringTooLongThrowsIllegalArgumentException** (  )
    - **State of the system:** algebraic = "a12"
    - **Expected output:** IllegalArgumentException
    - **Implemented:** yes

- **TC5: LocationFileJustBelowValidThrowsIllegalArgumentException** (  )
    - **State of the system:** algebraic = "`1" (ASCII before 'a')
    - **Expected output:** IllegalArgumentException
    - **Implemented:** yes

- **TC6: LocationFileJustAboveValidThrowsIllegalArgumentException** (  )
    - **State of the system:** algebraic = "i1"
    - **Expected output:** IllegalArgumentException
    - **Implemented:** yes

- **TC7: LocationRankJustBelowValidThrowsIllegalArgumentException** (  )
    - **State of the system:** algebraic = "a0"
    - **Expected output:** IllegalArgumentException
    - **Implemented:** yes

- **TC8: LocationRankJustAboveValidThrowsIllegalArgumentException** (  )
    - **State of the system:** algebraic = "a9"
    - **Expected output:** IllegalArgumentException
    - **Implemented:** yes

## Method under test: isInsideBoard(Location location)

- **TC9: IsInsideBoardBothIndicesMinValidReturnTrue** ( )
    - **State of the system:** location = Location(0, 0)
    - **Expected output:** true
    - **Implemented:** yes

- **TC10: IsInsideBoardBothIndicesMaxValidReturnTrue** (  )
    - **State of the system:** location = Location(7, 7)
    - **Expected output:** true
    - **Implemented:** yes

- **TC11: IsInsideBoardRowJustBelowValidReturnFalse** (  )
    - **State of the system:** location = Location(-1, 0)
    - **Expected output:** false
    - **Implemented:** yes

- **TC12: IsInsideBoardColJustBelowValidReturnFalse** (  )
    - **State of the system:** location = Location(0, -1)
    - **Expected output:** false
    - **Implemented:** yes

- **TC13: IsInsideBoardRowJustAboveValidReturnFalse** (  )
    - **State of the system:** location = Location(8, 7)
    - **Expected output:** false
    - **Implemented:** yes

- **TC14: IsInsideBoardColJustAboveValid_ReturnFalse** (  )
    - **State of the system:** location = Location(7, 8)
    - **Expected output:** false
    - **Implemented:** yes

## Method under test: initBoard()

- **TC15: InitBoardCheckWhitePawnReturnsWhitePawn** (  )
    - **State of the system:** board initialized, location = "a2"
    - **Expected output:** Piece is not null, color is WHITE, type is Pawn
    - **Implemented:** yes

- **TC16: InitBoardCheckBlackRookReturnsBlackRook** (  )
    - **State of the system:** board initialized, location = "a8"
    - **Expected output:** Piece is not null, color is BLACK, type is Rook
    - **Implemented:** yes

- **TC17: InitBoardCheckEmptySquareReturnsEmpty** (  )
    - **State of the system:** board initialized, location = "e4"
    - **Expected output:** isEmpty(location) = true
    - **Implemented:** yes

## Method under test: setPiece(Location location, Piece piece)

- **TC18: SetPieceReplacesExistingPieceAndReturnsPrevious** (  )
    - **State of the system:** board initialized, location = "a2" contains a Pawn
    - **Action:** call setPiece("a2", replacementPieceMock)
    - **Expected output:** returned Piece is the original Pawn; board.getPiece("a2") is replacementPieceMock
    - **Implemented:** yes

- **TC19: SetPieceOnEmptySquareReturnsNullAndPlacesPiece** (  )
    - **State of the system:** board cleared, location = "e4" empty
    - **Action:** call setPiece("e4", replacementPieceMock)
    - **Expected output:** returned value is null; board.getPiece("e4") is replacementPieceMock
    - **Implemented:** yes

- **TC20: SetPieceWithInvalidLocationThrowsException** (  )
    - **State of the system:** board initialized
    - **Action:** call setPiece(Location(-1,0), replacementPieceMock)
    - **Expected output:** ArrayIndexOutOfBoundsException or IllegalArgumentException
    - **Implemented:** yes
