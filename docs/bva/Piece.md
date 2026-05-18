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


### Method under test: `canMove(board: Board, from: Location, to: Location)`

### PieceType under test: Pawn

- **PTC1: Move two spaces forward on first move to unoccupied position** ( :x: )
  - **State of the system**: from (1,0) to (3,0), !hasMoved, isEmpty at (3,0)
  - **Expected output**: true

- **PTC2: Move three spaces forward on first move to unoccupied position** ( :x: )
  - **State of the system**: from (1,0) to (4,0), !hasMoved, isEmpty at (4,0)
  - **Expected output**: false

- **PTC3: Move two spaces forward on first move to foe-occupied position** ( :x: )
  - **State of the system**: from (1,0) to (3,0), !hasMoved, !isEmpty at (3,0), (3,0) contains foe
  - **Expected output**: false

- **PTC4: Move two spaces forward on first move to friend-occupied position** ( :x: )
  - **State of the system**: from (1,0) to (3,0), !hasMoved, !isEmpty at (3,0), (3,0) contains friend
  - **Expected output**: false

- **PTC5: Move one space forward on non-first move to unoccupied position** ( :x: )
  - **State of the system**: from (2,0) to (3,0), hasMoved, isEmpty at (3,0)
  - **Expected output**: true

- **PTC6: Move one space forward on non-first move to foe-occupied position** ( :x: )
  - **State of the system**: from (2,0) to (3,0), hasMoved, !isEmpty at (3,0), (3,0) contains foe
  - **Expected output**: false

- **PTC7: Move one space forward on non-first move to friend-occupied position** ( :x: )
  - **State of the system**: from (2,0) to (3,0), hasMoved, !isEmpty at (3,0), (3,0) contains friend
  - **Expected output**: false

- **PTC8: Move two spaces forward on non-first move to empty position** ( :x: )
  - **State of the system**: from (2,0) to (4,0), hasMoved, isEmpty at (4,0)
  - **Expected output**: false

- **PTC9: Move one space forward diagonally right to empty position** ( :x: )
  - **State of the system**: from (1,0) to (2,1), !hasMoved, isEmpty at (2,1)
  - **Expected output**: false

- **PTC10: Move one space forward diagonally left to empty position** ( :x: )
  - **State of the system**: from (1,1) to (2,0), !hasMoved, isEmpty at (2,0)
  - **Expected output**: false

- **PTC11: Move one space forward diagonally right to friend-occupied position** ( :x: )
  - **State of the system**: from (1,0) to (2,1), !hasMoved, !isEmpty at (2,1), (2,1) contains friend
  - **Expected output**: false

- **PTC12: Move one space forward diagonally left to friend-occupied position** ( :x: )
  - **State of the system**: from (1,1) to (2,0), !hasMoved, !isEmpty at (2,0), (2,0) contains friend
  - **Expected output**: false

- **PTC13: Move one space forward diagonally right to foe-occupied position** ( :x: )
  - **State of the system**: from (1,0) to (2,1), !hasMoved, !isEmpty at (2,1), (2,1) contains foe
  - **Expected output**: true

- **PTC14: Move one space forward diagonally left to foe-occupied position** ( :x: )
  - **State of the system**: from (1,1) to (2,0), !hasMoved, !isEmpty at (2,0), (2,0) contains foe
  - **Expected output**: true

- **PTC15: Move two spaces forward diagonally right to empty position** ( :x: )
  - **State of the system**: from (1,0) to (3,2), !hasMoved, isEmpty at (3,2)
  - **Expected output**: false

- **PTC16: Move two spaces forward diagonally left to empty position** ( :x: )
  - **State of the system**: from (2,1) to (3,0), !hasMoved, isEmpty at (3,0)
  - **Expected output**: false

- **PTC17: Move one space backward (any direction)** ( :x: )
  - **State of the system**: from (3,1) to (2,0), isEmpty at (2,0)
  - **Expected output**: false

- **PTC18: Move one space forward out-of-bounds** ( :x: )
  - **State of the system**: from (7,0) to (8,0)
  - **Expected output**: false

- **PTC19: Move one space forward left out-of-bounds** ( :x: )
  - **State of the system**: from (7,0) to (8,-1)
  - **Expected output**: false

- **PTC20: Move one space forward right out-of-bounds** ( :x: )
  - **State of the system**: from (7,7) to (8,8)
  - **Expected output**: false

- **PTC21: Move two spaces forward on first move with piece blocking intermediate square** ( :x: )
  - **State of the system**: from (1,0) to (3,0), !hasMoved, !isEmpty at (2,0)
  - **Expected output**: false

### PieceType under test: Rook

- **RTC1: Rook moves forward one space to empty position** ( :x: )
    - **State of the system**: from (0,0) to (1,0), isEmpty at (1,0)
    - **Expected output**: true

- **RTC2: Rook moves forward one space to friend-occupied position** ( :x: )
    - **State of the system**: from (0,0) to (1,0), !isEmpty at (1,0), friend at (1,0)
    - **Expected output**: false

- **RTC3: Rook moves forward one space to foe-occupied position** ( :x: )
    - **State of the system**: from (0,0) to (1,0), !isEmpty at (1,0), foe at (1,0)
    - **Expected output**: true

- **RTC4: Rook moves forward maximum spaces, clear path, to empty position** ( :x: )
    - **State of the system**: from (0,0) to (7,0), isEmpty from (1,0) to (7,0)
    - **Expected output**: true

- **RTC5: Rook moves forward maximum spaces, clear path, to friend-occupied position** ( :x: )
    - **State of the system**: from (0,0) to (7,0), isEmpty from (1,0) to (6,0), !isEmpty at (7,0), friend at (7,0)
    - **Expected output**: false

- **RTC6: Rook moves forward maximum spaces, clear path, to foe-occupied position** ( :x: )
    - **State of the system**: from (0,0) to (7,0), isEmpty from (1,0) to (6,0), !isEmpty at (7,0), foe at (7,0)
    - **Expected output**: true

- **RTC7: Rook moves forward maximum spaces, obstructed path, to empty position** ( :x: )
    - **State of the system**: from (0,0) to (7,0), isEmpty at (7,0), !isEmpty at (1,0), friend at (1,0)
    - **Expected output**: false

- **RTC8: Rook moves right out-of-bounds** ( :x: )
    - **State of the system**: from (0,7) to (0,8)
    - **Expected output**: false

- **RTC9: Rook moves forward out-of-bounds** ( :x: )
    - **State of the system**: from (7,0) to (8,0)
    - **Expected output**: false

### PieceType under test: Knight

- **KTC1: Knight moves in valid L-shape to empty position** ( :x: )
  - **State of the system**: from (3,3) to (5,4), isEmpty at (5,4)
  - **Expected output**: true

- **KTC2: Knight moves in valid L-shape to friend-occupied position** ( :x: )
  - **State of the system**: from (3,3) to (5,4), !isEmpty at (5,4), friend at (5,4)
  - **Expected output**: false

- **KTC3: Knight moves in valid L-shape to foe-occupied position** ( :x: )
  - **State of the system**: from (3,3) to (5,4), !isEmpty at (5,4), foe at (5,4)
  - **Expected output**: true

- **KTC4: Knight moves in valid L-shape over intervening pieces** ( :x: )
  - **State of the system**: from (3,3) to (5,4), !isEmpty at (4,3), friend at (4,3)
  - **Expected output**: true

- **KTC5: Knight moves in invalid geometry (non-L-shape)** ( :x: )
  - **State of the system**: from (3,3) to (5,5), isEmpty at (5,5)
  - **Expected output**: false

- **KTC6: Knight moves in valid L-shape out-of-bounds** ( :x: )
  - **State of the system**: from (0,0) to (-1,2)
  - **Expected output**: false

### PieceType under test: Bishop

- **BTC1: Move diagonally one space to empty position** ( :x: )
    - **State of the system**: from (0,0) to (1,1), isEmpty at (1,1)
    - **Expected output**: true

- **BTC2: Move diagonally one space to friend-occupied position** ( :x: )
    - **State of the system**: from (0,0) to (1,1), !isEmpty at (1,1), friend at (1,1)
    - **Expected output**: false

- **BTC3: Move diagonally one space to foe-occupied position** ( :x: )
    - **State of the system**: from (0,0) to (1,1), !isEmpty at (1,1), foe at (1,1)
    - **Expected output**: true

- **BTC4: Move diagonally max spaces, clear path, to empty position** ( :x: )
    - **State of the system**: from (0,0) to (7,7), isEmpty from (1,1) to (7,7)
    - **Expected output**: true

- **BTC5: Move diagonally max spaces, clear path, to friend-occupied position** ( :x: )
    - **State of the system**: from (0,0) to (7,7), isEmpty from (1,1) to (6,6), !isEmpty at (7,7), friend at (7,7)
    - **Expected output**: false

- **BTC6: Move diagonally max spaces, clear path, to foe-occupied position** ( :x: )
    - **State of the system**: from (0,0) to (7,7), isEmpty from (1,1) to (6,6), !isEmpty at (7,7), foe at (7,7)
    - **Expected output**: true

- **BTC7: Move diagonally max spaces, obstructed path, to empty position** ( :x: )
    - **State of the system**: from (0,0) to (7,7), !isEmpty at (1,1), isEmpty at (7,7)
    - **Expected output**: false

- **BTC8: Move diagonally out-of-bounds** ( :x: )
    - **State of the system**: from (7,7) to (8,8)
    - **Expected output**: false

### PieceType under test: King

- **KiTC1: King moves one space forward to empty position** ( :x: )
  - **State of the system**: from (3,3) to (4,3), isEmpty at (4,3)
  - **Expected output**: true

- **KiTC2: King moves one space forward to friend-occupied position** ( :x: )
  - **State of the system**: from (3,3) to (4,3), !isEmpty at (4,3), friend at (4,3)
  - **Expected output**: false

- **KiTC3: King moves one space forward to foe-occupied position** ( :x: )
  - **State of the system**: from (3,3) to (4,3), !isEmpty at (4,3), foe at (4,3)
  - **Expected output**: true

- **KiTC4: King moves one space diagonally to empty position** ( :x: )
  - **State of the system**: from (3,3) to (4,4), isEmpty at (4,4)
  - **Expected output**: true

- **KiTC5: King moves two spaces forward (invalid)** ( :x: )
  - **State of the system**: from (3,3) to (5,3), isEmpty at (5,3)
  - **Expected output**: false

- **KiTC6: King moves forward out-of-bounds** ( :x: )
  - **State of the system**: from (7,3) to (8,3)
  - **Expected output**: false

- **KiTC7: King castles kingside, neither king nor rook has moved** ( :x: )
  - **State of the system**: from (0,4) to (0,6), !hasMoved (king), !hasMoved (rook at (0,7)), isEmpty at (0,5) and (0,6)
  - **Expected output**: true

- **KiTC8: King castles kingside, king has moved** ( :x: )
  - **State of the system**: from (0,4) to (0,6), hasMoved (king), !hasMoved (rook at (0,7))
  - **Expected output**: false

- **KiTC9: King castles kingside, rook has moved** ( :x: )
  - **State of the system**: from (0,4) to (0,6), !hasMoved (king), hasMoved (rook at (0,7))
  - **Expected output**: false

### PieceType under test: Queen

- **QTC1: Queen moves horizontally to empty position, clear path** ( :x: )
  - **State of the system**: from (3,3) to (3,7), isEmpty from (3,4) to (3,7)
  - **Expected output**: true

- **QTC2: Queen moves diagonally to empty position, clear path** ( :x: )
  - **State of the system**: from (3,3) to (7,7), isEmpty from (4,4) to (7,7)
  - **Expected output**: true

- **QTC3: Queen moves horizontally, obstructed path** ( :x: )
  - **State of the system**: from (3,3) to (3,7), !isEmpty at (3,5), friend at (3,5)
  - **Expected output**: false

- **QTC4: Queen moves diagonally, obstructed path** ( :x: )
  - **State of the system**: from (3,3) to (7,7), !isEmpty at (5,5), friend at (5,5)
  - **Expected output**: false

- **QTC5: Queen moves to friend-occupied position** ( :x: )
  - **State of the system**: from (3,3) to (3,7), isEmpty from (3,4) to (3,6), !isEmpty at (3,7), friend at (3,7)
  - **Expected output**: false

- **QTC6: Queen moves to foe-occupied position** ( :x: )
  - **State of the system**: from (3,3) to (3,7), isEmpty from (3,4) to (3,6), !isEmpty at (3,7), foe at (3,7)
  - **Expected output**: true

- **QTC7: Queen moves in L-shape (invalid geometry)** ( :x: )
  - **State of the system**: from (3,3) to (5,4), isEmpty at (5,4)
  - **Expected output**: false

- **QTC8: Queen moves out-of-bounds** ( :x: )
  - **State of the system**: from (7,7) to (8,8)
  - **Expected output**: false

