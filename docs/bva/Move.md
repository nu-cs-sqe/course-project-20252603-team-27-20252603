# BVA: `domain.Move`

Design reference: `Move` stores `Location from`, `Location to`, `Piece movedPiece`, optional `Piece capturedPiece`, optional `PieceType promotionType`, plus castling/en passant flags and notation.

### Method under test: `Move(Location from, Location to, Piece movedPiece, Piece capturedPiece, PieceType promotionType)`

- **TC1: ctor_acceptsRequiredFields** ( :white_check_mark: )
  - **State of the system:** `from` and `to` are valid `Location` objects, `movedPiece` is a non-null `Piece`, `capturedPiece` is null, `promotionType` is null
  - **Expected output:** `Move` is created; `getFrom()`/`getTo()`/`getMovedPiece()` return the given values; `getCapturedPiece()` and `getPromotionType()` are null; `isCastle()` and `isEnPassant()` are false; `getNotation()` is empty

- **TC2: ctor_rejectsNullFrom** ( :white_check_mark: )
  - **State of the system:** `from` is null, all other required args are valid
  - **Expected output:** throws `IllegalArgumentException` with message mentioning `from`

- **TC3: ctor_rejectsNullTo** ( :white_check_mark: )
  - **State of the system:** `to` is null, all other required args are valid
  - **Expected output:** throws `IllegalArgumentException` with message mentioning `to`

- **TC4: ctor_rejectsNullMovedPiece** ( :white_check_mark: )
  - **State of the system:** `movedPiece` is null, `from` and `to` are valid, `capturedPiece` and `promotionType` are null
  - **Expected output:** throws `IllegalArgumentException` with message mentioning `movedPiece`

- **TC5: ctor_storesCapturedPiece** ( :white_check_mark: )
  - **State of the system:** `capturedPiece` is a non-null `Piece`, all required args valid, `promotionType` is null
  - **Expected output:** `Move` is created; `getCapturedPiece()` returns the given piece; `getPromotionType()` is null

- **TC6: ctor_storesPromotionType** ( :white_check_mark: )
  - **State of the system:** `promotionType` is a non-null `PieceType` (e.g. `QUEEN`), all required args valid, `capturedPiece` is null
  - **Expected output:** `Move` is created; `getPromotionType()` returns the given type; `getCapturedPiece()` is null

### Method under test: `Move(..., boolean isCastle, boolean isEnPassant, String notation)` (extended constructor)

- **TC7: extendedCtor_storesCastleFlag** ( :white_check_mark: )
  - **State of the system:** extended constructor called with `isCastle` true, `isEnPassant` false, empty notation; required args valid
  - **Expected output:** `Move` is created; `isCastle()` is true; `isEnPassant()` is false
