This folder should include the system design. It can be in the form of design diagrams or a textual description of what classes the system shall have and their relationships.

## Class: `Board`

### Purpose

`Board` stores the 8×8 chess board and the current placement of all pieces.

### Fields

- `Piece[][] pieces`

### Methods

- `initBoard(): void`
- `clearBoard(): void`
- `getPiece(location: Location): Piece`
- `getSnapshot(): Piece[][]`
- `movePiece(from: Location, to: Location): void`
- `isInsideBoard(location: Location): boolean`
- `isEmpty(location: Location): boolean`
- `findKing(color: Color): Location`
- `toPositionString(): String`

### Responsibilities

- Represent the board as an 8×8 grid.
- Store which piece is on each square.
- Initialize all 32 pieces in standard starting positions.
- Return the piece at a given location.
- Return a deep-copy snapshot of the board for safe external inspection.
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
