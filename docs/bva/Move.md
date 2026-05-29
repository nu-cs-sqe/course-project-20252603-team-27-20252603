# BVA: `domain.Move`

Design reference: `Move` stores `Location from`, `Location to`, `Piece movedPiece`, optional `Piece capturedPiece`, optional `PieceType promotionType`, plus castling/en passant flags and notation.

### Method under test: `Move(Location from, Location to, Piece movedPiece, Piece capturedPiece, PieceType promotionType)`

- **TC1: ctor_acceptsRequiredFields** ( :white_check_mark: )
  - **State of the system:** `from` and `to` are valid `Location` objects, `movedPiece` is a non-null `Piece`, `capturedPiece` is null, `promotionType` is null
  - **Expected output:** `Move` is created; `getFrom()`/`getTo()`/`getMovedPiece()` return the given values; `getCapturedPiece()` and `getPromotionType()` are null; `isCastle()` and `isEnPassant()` are false; `getNotation()` is empty
