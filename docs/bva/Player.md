## Class under test: Player

## Fields
- `String name` — the player's display name
- `PieceColor color` — either `WHITE` or `BLACK`

---

### Method under test: `getColor()`

- **TC1: playerColorIsWhite()** ( :white_check_mark: )
    - **State of the system**: A `Player` has been constructed with `PieceColor.WHITE`
    - **Expected output**: `getColor()` returns `PieceColor.WHITE`

- **TC2: playerColorIsBlack()** ( :white_check_mark: )
    - **State of the system**: A `Player` has been constructed with `PieceColor.BLACK`
    - **Expected output**: `getColor()` returns `PieceColor.BLACK`

---

### Constructor under test

- **TC3: playerColorIsNull()** ( :X: )
    - **State of the system**: A `Player` has been constructed with `Null`
    - **Expected output**: `IllegalArgumentException` is thrown
  
- **TC4: playerGivenNoName()** ( :X: )
    - **State of the system**: A `Player` has been constructed with name `""`
    - **Expected output**: `IllegalArgumentException` is thrown

- **TC5: playerGivenNullName()** ( :X: )
    - **State of the system**: A `Player` has been constructed with `null` as the name
    - **Expected output**: `IllegalArgumentException` is thrown
---

### Method under test: `setColor(PieceColor color)`

- **TC6: setPlayerColorToWhite_previouslyBlack()** ( :X: )
    - **State of the system**: A `Player` exists with Black prior color; `setColor(PieceColor.WHITE)` is called
    - **Expected output**: `getColor()` returns `PieceColor.WHITE`

- **TC7: setPlayerColorToWhite_previouslyWhite()** ( :X: )
    - **State of the system**: A `Player` exists with White prior color; `setColor(PieceColor.WHITE)` is called
    - **Expected output**: `getColor()` returns `PieceColor.WHITE`

- **TC8: setPlayerColorToBlack_previouslyWhite()** ( :X: )
    - **State of the system**: A `Player` exists with White prior color; `setColor(PieceColor.BLACK)` is called
    - **Expected output**: `getColor()` returns `PieceColor.BLACK`

- **TC9: setPlayerColorToNull_previouslyBlack()** ( :X: )
    - **State of the system**: A Black `Player` exists; `setColor(null)` is called
    - **Expected output**: `IllegalArgumentException` is thrown

- **TC10: setPlayerColorToNull_previouslyWhite()** ( :X: )
    - **State of the system**: A White `Player` exists; `setColor(null)` is called
    - **Expected output**: `IllegalArgumentException` is thrown
---

### Method under test: `getName()`

- **TC11: playerGivenValidName()** ( :X: )
    - **State of the system**: A `Player` has been constructed with name `"Alice"`
    - **Expected output**: `getName()` returns `"Alice"`
