This folder should include the system design. It can be in the form of design diagrams or a textual description of what classes the system shall have and their relationships.

# Core Classes

## Class: `Game`

### Purpose

`Game` is the main controller of the chess game. It manages the board, players, turns, move validation, special moves, game status, draw conditions, and move history.

### Fields

- `Board board`
- `Player white`
- `Player black`
- `Player currentPlayer`
- `GameStatus status`
- `List<Move> moveHistory`
- `Move lastMove`
- `int halfMoveClock`
- `Map<String, Integer> positionHistory`

### Methods

- `startNewGame(player1: Player, player2: Player): void`
- `makeMove(from: Location, to: Location, promotionType: PieceType): MoveResult`
- `isValidMove(move: Move): boolean`
- `isInCheck(color: PieceColor): boolean`
- `isCheckmate(color: PieceColor): boolean`
- `isStalemate(color: PieceColor): boolean`
- `resign(player: Player): void`
- `switchTurn(): void`
- `getBoard(): Board`
- `getStatus(): GameStatus`
- `getMoveHistory(): List<Move>`

### Responsibilities

- Initialize a new game.
- Assign White and Black players.
- Ensure White moves first.
- Control turn alternation.
- Prevent players from moving opponent pieces.
- Validate normal moves.
- Validate castling.
- Validate en passant.
- Validate pawn promotion.
- Prevent a move that leaves the player's own king in check.
- Move pieces on the board.
- Detect check.
- Detect checkmate.
- Detect stalemate.
- Detect official threefold repetition.
- Detect the fifty-move rule.
- Maintain move history.
- Handle resignation.
- Update game status after every valid move.

### Covers User Stories

- US-02: initialize all pieces at game start.
- US-04 to US-08: validate standard piece movement.
- US-09: castling.
- US-10: en passant.
- US-11: pawn promotion.
- US-12 to US-15: check, checkmate, stalemate.
- US-16 to US-18: turn management.
- US-19: move history.
- US-20: official threefold repetition.
- US-21: fifty-move rule.
- US-22: resignation.

---

## Class: `Board`

### Purpose

`Board` stores the 8×8 chess board and the current placement of all pieces.

### Fields

- `Piece[][] pieces`

### Methods

- `initBoard(): void`
- `clearBoard(): void`
- `getPiece(location: Location): Piece`
- `setPiece(location: Location, piece: Piece): void`
- `movePiece(from: Location, to: Location): void`
- `isInsideBoard(location: Location): boolean`
- `isEmpty(location: Location): boolean`
- `findKing(color: PieceColor): Location`
- `toPositionString(): String`

### Responsibilities

- Represent the board as an 8×8 grid.
- Store which piece is on each square.
- Initialize all 32 pieces in standard starting positions.
- Return the piece at a given location.
- Move pieces between locations.
- Remove captured pieces by replacing them on the destination square.
- Find the location of a king.
- Generate a string representation of the piece placement for repetition detection.

### Covers User Stories

- US-01: 8×8 grid.
- US-02: standard starting position.
- US-05: detect own piece on destination.
- US-06: capture opponent piece.
- US-12 to US-15: support check/checkmate/stalemate detection.
- US-20: support position tracking.
- US-23: provide board state for display.

---

## Class: `Piece`

### Purpose

`Piece` represents a chess piece. To reduce the number of classes, this design does not create separate `Pawn`, `Rook`, `Knight`, `Bishop`, `Queen`, and `King` classes. Instead, each piece has a `PieceType`.

### Fields

- `PieceType type`
- `PieceColor color`
- `boolean hasMoved`

### Methods

- `canMove(board: Board, from: Location, to: Location): boolean`
- `getType(): PieceType`
- `getColor(): PieceColor`
- `hasMoved(): boolean`
- `setMoved(moved: boolean): void`

### Responsibilities

- Store the piece type.
- Store the piece color.
- Store whether the piece has moved.
- Check whether this piece type can move from one location to another according to standard movement rules.
- Support pawn two-square movement.
- Support pawn diagonal capture.
- Support castling by tracking whether the king or rook has moved.
- Support promotion by allowing a pawn to become another `PieceType`.

### Covers User Stories

- US-04: standard movement for all piece types.
- US-07: pawn two-square movement.
- US-08: pawn diagonal capture.
- US-09: castling preconditions involving unmoved king/rook.
- US-11: promotion.

---

## Class: `Move`

### Purpose

`Move` represents one attempted or completed move.

### Fields

- `Location from`
- `Location to`
- `Piece movedPiece`
- `Piece capturedPiece`
- `PieceType promotionType`
- `boolean isCastle`
- `boolean isEnPassant`
- `String notation`

### Methods

- `getFrom(): Location`
- `getTo(): Location`
- `getMovedPiece(): Piece`
- `getCapturedPiece(): Piece`
- `getPromotionType(): PieceType`
- `getNotation(): String`

### Responsibilities

- Store the source location.
- Store the destination location.
- Store the moved piece.
- Store the captured piece, if any.
- Store promotion choice, if any.
- Store whether the move is castling.
- Store whether the move is en passant.
- Store a move notation string for history.

### Covers User Stories

- US-06: capture.
- US-09: castling.
- US-10: en passant.
- US-11: promotion.
- US-19: move history.
- US-21: fifty-move rule support.

---

## Class: `Location`

### Purpose

`Location` represents a square on the board using numeric row and column values.

### Fields

- `int row`
- `int col`

### Methods

- `getRow(): int`
- `getCol(): int`
- `equals(other: Location): boolean`

### Responsibilities

- Store row and column.
- Represent a board square.
- Allow comparing two locations.

### Covers User Stories

- US-03: each square is identifiable.
- US-24: move input can use numeric row/column coordinates.

---

## Class: `Player`

### Purpose

`Player` represents a player in the game.

### Fields

- `String name`
- `PieceColor color`

### Methods

- `getName(): String`
- `getColor(): PieceColor`
- `setColor(color: PieceColor): void`

### Responsibilities

- Store player name.
- Store whether the player controls White or Black.
- Help `Game` enforce whose turn it is.
- Help `Game` handle resignation.

### Covers User Stories

- US-16: White first.
- US-17: alternating turns.
- US-18: prevent moving opponent pieces.
- US-22: resignation.

---

## Class: `ChessUI`

### Purpose

`ChessUI` handles user input and output. It does not enforce chess rules.

### Fields

- `Game game`

### Methods

- `displayBoard(board: Board): void`
- `readMoveInput(): Move`
- `showError(result: MoveResult): void`
- `showMessage(message: String): void`
- `showGameOver(status: GameStatus): void`

### Responsibilities

- Display the current board state.
- Read move input from the player.
- Show invalid move messages.
- Show check, checkmate, stalemate, draw, or resignation messages.
- Show final game result.

### Covers User Stories

- US-23: view current board state.
- US-24: input moves.
- US-25: invalid move error message.
- US-26: game-over message.

---

# Enums

## Enum: `PieceColor`

### Values

- `WHITE`
- `BLACK`

### Purpose

Represents the color of a player or piece.

---

## Enum: `PieceType`

### Values

- `PAWN`
- `ROOK`
- `KNIGHT`
- `BISHOP`
- `QUEEN`
- `KING`
- `EMPTY`

### Purpose

Represents the type of a chess piece.

---

## Enum: `GameStatus`

### Values

- `WHITE_TURN`
- `BLACK_TURN`
- `WHITE_IN_CHECK`
- `BLACK_IN_CHECK`
- `WHITE_WIN`
- `BLACK_WIN`
- `STALEMATE`
- `DRAW`
- `RESIGNED`

### Purpose

Represents the current state of the game.

---

## Enum: `MoveResult`

### Values

- `VALID`
- `INVALID_EMPTY_SOURCE`
- `INVALID_WRONG_TURN`
- `INVALID_OUT_OF_BOUNDS`
- `INVALID_SAME_COLOR_CAPTURE`
- `INVALID_ILLEGAL_PIECE_MOVE`
- `INVALID_SELF_CHECK`
- `CHECK`
- `CHECKMATE`
- `STALEMATE`
- `DRAW`
- `RESIGN`

### Purpose

Represents the result of a move attempt.

---

# Official Threefold Repetition Design

## User Story

US-20: As a player, I want the game to detect official threefold repetition and offer a draw, so that this draw condition is handled.

## Rule Used

A position is considered the same only if:

- The same player has the move.
- Pieces of the same kind and color occupy the same squares.
- The possible moves of all pieces are the same.

In practice, this means the position key must include:

- Piece placement.
- Current player to move.
- Castling rights.
- En passant availability.

This is needed because two positions with the same piece placement may still be different if castling rights or en passant rights differ. :contentReference[oaicite:1]{index=1}

## Design Fields Used

In `Game`:

- `Map<String, Integer> positionHistory`
- `Move lastMove`
- `Player currentPlayer`

In `Board`:

- `Piece[][] pieces`
- `toPositionString(): String`

In `Piece`:

- `boolean hasMoved`

## Position Key Design

`Game` should create a position key after each valid move.

The position key should include:

```text
board.toPositionString()
currentPlayer.color
castlingRights
enPassantTarget
