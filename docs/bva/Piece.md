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

## Valid move case 1: one space forward to empty position

PTC1: Pawn moves one space forward, hasMoved, to empty position ( :X: )
State of the system: from (2,0) to (3,0), hasMoved, isEmpty at (3,0)
Expected output: true

PTC2: Pawn moves one space forward, !hasMoved, to empty position ( :X: )
State of the system: from (1,0) to (2,0), !hasMoved, isEmpty at (2,0)
Expected output: true

## Valid move case 2: two spaces forward on first move, clear path, to empty position

PTC3: Pawn moves two spaces forward, !hasMoved, clear path, to empty position ( :X: )
State of the system: from (1,0) to (3,0), !hasMoved, isEmpty at (2,0), isEmpty at (3,0)
Expected output: true

## Valid move case 3: one space forward diagonal capture

PTC4: Pawn moves one space forward-right, to foe-occupied position, hasMoved ( :X: )
State of the system: from (2,1) to (3,2), hasMoved, !isEmpty at (3,2), foe at (3,2)
Expected output: true

PTC5: Pawn moves one space forward-left, to foe-occupied position, hasMoved ( :X: )
State of the system: from (2,1) to (3,0), hasMoved, !isEmpty at (3,0), foe at (3,0)
Expected output: true

PTC6: Pawn moves one space forward-right, to foe-occupied position, !hasMoved ( :X: )
State of the system: from (1,1) to (2,2), !hasMoved, !isEmpty at (2,2), foe at (2,2)
Expected output: true

PTC7: Pawn moves one space forward-left, to foe-occupied position, !hasMoved ( :X: )
State of the system: from (1,1) to (2,0), !hasMoved, !isEmpty at (2,0), foe at (2,0)
Expected output: true

## Invalid move case 1: one space forward to occupied position

PTC8: Pawn moves one space forward, hasMoved, to foe-occupied position (:X:)
State of the system: from (2,0) to (3,0), hasMoved, !isEmpty at (3,0), foe at (3,0)
Expected output: false

PTC9: Pawn moves one space forward, hasMoved, to friend-occupied position (:X:)
State of the system: from (2,0) to (3,0), hasMoved, !isEmpty at (3,0), friend at (3,0)
Expected output: false

PTC10: Pawn moves one space forward, !hasMoved, to foe-occupied position (:X:)
State of the system: from (1,0) to (2,0), !hasMoved, !isEmpty at (2,0), foe at (2,0)
Expected output: false

PTC11: Pawn moves one space forward, !hasMoved, to friend-occupied position (:X:)
State of the system: from (1,0) to (2,0), !hasMoved, !isEmpty at (2,0), friend at (2,0)
Expected output: false

## Invalid move case 2: two spaces forward on first move to occupied position

PTC12: Pawn moves two spaces forward, !hasMoved, to foe-occupied position (:X:)
State of the system: from (1,0) to (3,0), !hasMoved, isEmpty at (2,0), !isEmpty at (3,0), foe at (3,0)
Expected output: false

PTC13: Pawn moves two spaces forward, !hasMoved, to friend-occupied position (:X:)
State of the system: from (1,0) to (3,0), !hasMoved, isEmpty at (2,0), !isEmpty at (3,0), friend at (3,0)
Expected output: false

## Invalid move case 3: two spaces forward on first move, piece in path

PTC14: Pawn moves two spaces forward, !hasMoved, foe in path (:X:)
State of the system: from (1,0) to (3,0), !hasMoved, !isEmpty at (2,0), foe at (2,0), isEmpty at (3,0)
Expected output: false

PTC15: Pawn moves two spaces forward, !hasMoved, friend in path (:X:)
State of the system: from (1,0) to (3,0), !hasMoved, !isEmpty at (2,0), friend at (2,0), isEmpty at (3,0)
Expected output: false

## Invalid move case 4: two spaces forward after having moved

PTC16: Pawn moves two spaces forward, hasMoved, to empty position (:X:)
State of the system: from (2,0) to (4,0), hasMoved, isEmpty at (3,0), isEmpty at (4,0)
Expected output: false

PTC17: Pawn moves two spaces forward, hasMoved, to foe-occupied position (:X:)
State of the system: from (2,0) to (4,0), hasMoved, isEmpty at (3,0), !isEmpty at (4,0), foe at (4,0)
Expected output: false

PTC18: Pawn moves two spaces forward, hasMoved, to friend-occupied position (:X:)
State of the system: from (2,0) to (4,0), hasMoved, isEmpty at (3,0), !isEmpty at (4,0), friend at (4,0)
Expected output: false

## Invalid move case 5: one space forward diagonal to empty or friend-occupied position

PTC19: Pawn moves one space forward-right, hasMoved, to empty position (:X:)
State of the system: from (2,1) to (3,2), hasMoved, isEmpty at (3,2)
Expected output: false

PTC20: Pawn moves one space forward-left, hasMoved, to empty position (:X:)
State of the system: from (2,1) to (3,0), hasMoved, isEmpty at (3,0)
Expected output: false

PTC21: Pawn moves one space forward-right, !hasMoved, to empty position (:X:)
State of the system: from (1,1) to (2,2), !hasMoved, isEmpty at (2,2)
Expected output: false

PTC22: Pawn moves one space forward-left, !hasMoved, to empty position (:X:)
State of the system: from (1,1) to (2,0), !hasMoved, isEmpty at (2,0)
Expected output: false

PTC23: Pawn moves one space forward-right, hasMoved, to friend-occupied position (:X:)
State of the system: from (2,1) to (3,2), hasMoved, !isEmpty at (3,2), friend at (3,2)
Expected output: false

PTC24: Pawn moves one space forward-left, hasMoved, to friend-occupied position (:X:)
State of the system: from (2,1) to (3,0), hasMoved, !isEmpty at (3,0), friend at (3,0)
Expected output: false

PTC25: Pawn moves one space forward-right, !hasMoved, to friend-occupied position (:X:)
State of the system: from (1,1) to (2,2), !hasMoved, !isEmpty at (2,2), friend at (2,2)
Expected output: false

PTC26: Pawn moves one space forward-left, !hasMoved, to friend-occupied position (:X:)
State of the system: from (1,1) to (2,0), !hasMoved, !isEmpty at (2,0), friend at (2,0)
Expected output: false

## Invalid move case 6: two spaces forward diagonal

PTC27: Pawn moves two spaces forward-right, !hasMoved, to empty position (:X:)
State of the system: from (1,0) to (3,2), !hasMoved, isEmpty at (3,2)
Expected output: false

PTC28: Pawn moves two spaces forward-left, !hasMoved, to empty position (:X:)
State of the system: from (1,2) to (3,0), !hasMoved, isEmpty at (3,0)
Expected output: false

PTC29: Pawn moves two spaces forward-right, !hasMoved, to foe-occupied position (:X:)
State of the system: from (1,0) to (3,2), !hasMoved, !isEmpty at (3,2), foe at (3,2)
Expected output: false

PTC30: Pawn moves two spaces forward-left, !hasMoved, to foe-occupied position (:X:)
State of the system: from (1,2) to (3,0), !hasMoved, !isEmpty at (3,0), foe at (3,0)
Expected output: false

PTC31: Pawn moves two spaces forward-right, !hasMoved, to friend-occupied position (:X:)
State of the system: from (1,0) to (3,2), !hasMoved, !isEmpty at (3,2), friend at (3,2)
Expected output: false

PTC32: Pawn moves two spaces forward-left, !hasMoved, to friend-occupied position (:X:)
State of the system: from (1,2) to (3,0), !hasMoved, !isEmpty at (3,0), friend at (3,0)
Expected output: false

## Invalid move case 7: backward movement

PTC33: Pawn moves one space backward, hasMoved, to empty position (:X:)
State of the system: from (2,0) to (1,0), hasMoved, isEmpty at (1,0)
Expected output: false

PTC34: Pawn moves one space backward, hasMoved, to foe-occupied position (:X:)
State of the system: from (2,0) to (1,0), hasMoved, !isEmpty at (1,0), foe at (1,0)
Expected output: false

PTC35: Pawn moves one space backward, hasMoved, to friend-occupied position (:X:)
State of the system: from (2,0) to (1,0), hasMoved, !isEmpty at (1,0), friend at (1,0)
Expected output: false

PTC36: Pawn moves one space backward-right, hasMoved, to empty position (:X:)
State of the system: from (2,1) to (1,2), hasMoved, isEmpty at (1,2)
Expected output: false

PTC37: Pawn moves one space backward-right, hasMoved, to foe-occupied position (:X:)
State of the system: from (2,1) to (1,2), hasMoved, !isEmpty at (1,2), foe at (1,2)
Expected output: false

PTC38: Pawn moves one space backward-right, hasMoved, to friend-occupied position (:X:)
State of the system: from (2,1) to (1,2), hasMoved, !isEmpty at (1,2), friend at (1,2)
Expected output: false

PTC39: Pawn moves one space backward-left, hasMoved, to empty position (:X:)
State of the system: from (2,1) to (1,0), hasMoved, isEmpty at (1,0)
Expected output: false

PTC40: Pawn moves one space backward-left, hasMoved, to foe-occupied position (:X:)
State of the system: from (2,1) to (1,0), hasMoved, !isEmpty at (1,0), foe at (1,0)
Expected output: false

PTC41: Pawn moves one space backward-left, hasMoved, to friend-occupied position (:X:)
State of the system: from (2,1) to (1,0), hasMoved, !isEmpty at (1,0), friend at (1,0)
Expected output: false

PTC42: Pawn moves one space backward, !hasMoved, to empty position (:X:)
State of the system: from (1,0) to (0,0), !hasMoved, isEmpty at (0,0)
Expected output: false

PTC43: Pawn moves one space backward, !hasMoved, to foe-occupied position (:X:)
State of the system: from (1,0) to (0,0), !hasMoved, !isEmpty at (0,0), foe at (0,0)
Expected output: false

PTC44: Pawn moves one space backward, !hasMoved, to friend-occupied position (:X:)
State of the system: from (1,0) to (0,0), !hasMoved, !isEmpty at (0,0), friend at (0,0)
Expected output: false

PTC45: Pawn moves one space backward-right, !hasMoved, to empty position (:X:)
State of the system: from (1,1) to (0,2), !hasMoved, isEmpty at (0,2)
Expected output: false

PTC46: Pawn moves one space backward-right, !hasMoved, to foe-occupied position (:X:)
State of the system: from (1,1) to (0,2), !hasMoved, !isEmpty at (0,2), foe at (0,2)
Expected output: false

PTC47: Pawn moves one space backward-right, !hasMoved, to friend-occupied position (:X:)
State of the system: from (1,1) to (0,2), !hasMoved, !isEmpty at (0,2), friend at (0,2)
Expected output: false

PTC48: Pawn moves one space backward-left, !hasMoved, to empty position (:X:)
State of the system: from (1,1) to (0,0), !hasMoved, isEmpty at (0,0)
Expected output: false

PTC49: Pawn moves one space backward-left, !hasMoved, to foe-occupied position (:X:)
State of the system: from (1,1) to (0,0), !hasMoved, !isEmpty at (0,0), foe at (0,0)
Expected output: false

PTC50: Pawn moves one space backward-left, !hasMoved, to friend-occupied position (:X:)
State of the system: from (1,1) to (0,0), !hasMoved, !isEmpty at (0,0), friend at (0,0)
Expected output: false


## Invalid move case 8: sideways movement

PTC51: Pawn moves one space left, hasMoved, to empty position (:X:)
State of the system: from (2,1) to (2,0), hasMoved, isEmpty at (2,0)
Expected output: false

PTC52: Pawn moves one space left, hasMoved, to foe-occupied position (:X:)
State of the system: from (2,1) to (2,0), hasMoved, !isEmpty at (2,0), foe at (2,0)
Expected output: false

PTC53: Pawn moves one space left, hasMoved, to friend-occupied position (:X:)
State of the system: from (2,1) to (2,0), hasMoved, !isEmpty at (2,0), friend at (2,0)
Expected output: false

PTC54: Pawn moves one space right, hasMoved, to empty position (:X:)
State of the system: from (2,0) to (2,1), hasMoved, isEmpty at (2,1)
Expected output: false

PTC55: Pawn moves one space right, hasMoved, to foe-occupied position (:X:)
State of the system: from (2,0) to (2,1), hasMoved, !isEmpty at (2,1), foe at (2,1)
Expected output: false

PTC56: Pawn moves one space right, hasMoved, to friend-occupied position (:X:)
State of the system: from (2,0) to (2,1), hasMoved, !isEmpty at (2,1), friend at (2,1)
Expected output: false

## Invalid move case 9: out-of-bounds

PTC57: Pawn moves one space forward out-of-bounds, top boundary (:X:)
State of the system: from (7,0) to (8,0), hasMoved
Expected output: false

PTC58: Pawn moves one space forward-right out-of-bounds, top boundary (:X:)
State of the system: from (7,0) to (8,1), hasMoved
Expected output: false

PTC59: Pawn moves one space forward-left out-of-bounds, top boundary (:X:)
State of the system: from (7,1) to (8,0), hasMoved
Expected output: false

PTC60: Pawn moves one space forward-left out-of-bounds, left boundary (:X:)
State of the system: from (2,0) to (3,-1), hasMoved
Expected output: false

PTC61: Pawn moves one space forward-right out-of-bounds, right boundary (:X:)
State of the system: from (2,7) to (3,8), hasMoved
Expected output: false

## Invalid move case 10: movement to same current space (no movement)

PTC62: Pawn moves to its own current square (distance zero), has moved before (:X:)
State of the system: from (2,0) to (2,0), hasMoved
Expected output: false

PTC63: Pawn moves to its own current square (distance zero), hasn’t moved before (:X:)
State of the system: from (2,0) to (2,0), !hasMoved
Expected output: false


### PieceType under test: Rook

## Valid Moves case 1: one-space forward/backward/left/right to empty/foe position

RTC1: Rook moves forward one space to empty position (:X:)
State: from (0,0) to (1,0), isEmpty at (1,0)
Expectation: true

RTC2: Rook moves forward one space, to foe-occupied position (:X:)
State: from (0,0) to (1,0), !isEmpty at (1,0), foe at (1,0)
Expectation: true

RTC3: Rook moves backward one space to empty position (:X:)
State: from (1,0) to (0,0), isEmpty at (0,0)
Expectation: true

RTC4: Rook moves backward one space, to foe-occupied position (:X:)
State: from (1,0) to (0,0), !isEmpty at (0,0), foe at (0,0)
Expectation: true

RTC5: Rook moves right one space to empty position (:X:)
State: from (1,0) to (1,1), isEmpty at (1,1)
Expectation: true

RTC6: Rook moves right one space, to foe-occupied position (:X:)
State: from (1,0) to (1,1), !isEmpty at (1,1), foe at (1,1)
Expectation: true

RTC7: Rook moves left one space to empty position (:X:)
State: from (1,1) to (1,0), isEmpty at (1,0)
Expectation: true

RTC8: Rook moves left one space, to foe-occupied position (:X:)
State: from (1,1) to (1,0), !isEmpty at (1,0), foe at (1,0)
Expectation: true

## Valid moves case 2: max-spaces forward/backward/left/right to empty/foe position

RTC9: Rook moves forward maximum spaces, clear path, to empty position (:X:)
State: from (0,0) to (7,0), isEmpty at (7,0), isEmpty at (1,0) to (7,0)
Expectation: true

RTC10: Rook moves forward maximum spaces, clear path, to foe-occupied position (:X:)
State: from (0,0) to (7,0), isEmpty from (1,0) to (6,0), !isEmpty at (7,0), foe at (7,0)
Expectation: true

RTC11: Rook moves backward maximum spaces, clear path, to empty position (:X:)
State: from (7,0) to (0,0), isEmpty at (0,0), isEmpty at (1,0) to (7,0)
Expectation: true

RTC12: Rook moves backward maximum spaces, clear path, to foe-occupied position (:X:)
State: from (7,0) to (0,0), isEmpty from (1,0) to (6,0), !isEmpty at (0,0), foe at (0,0)
Expectation: true

RTC13: Rook moves right maximum spaces, clear path, to empty position (:X:)
State: from (7,0) to (7,7), isEmpty at (7,7), isEmpty at (7,1) to (7,7)
Expectation: true

RTC14: Rook moves right maximum spaces, clear path, to foe-occupied position (:X:)
State: from (7,0) to (7,7), isEmpty from (7,0) to (7,6), !isEmpty at (7,7), foe at (7,7)
Expectation: true

RTC15: Rook moves left maximum spaces, clear path, to empty position (:X:)
State: from (7,7) to (7,0), isEmpty at (7,0), isEmpty at (7,6) to (7,1)
Expectation: true

RTC16: Rook moves left maximum spaces, clear path, to foe-occupied position (:X:)
State: from (7,7) to (7,0), isEmpty from (7,6) to (7,1), !isEmpty at (7,0), foe at (7,0)
Expectation: true

## Invalid case 1: one-space movement to a friend-occupied position

RTC17: Rook moves forward one space, to friend-occupied position (:X:)
State: from (0,0) to (1,0), !isEmpty at (1,0), friend at (1,0)
Expectation: false

RTC18: Rook moves backward one space, to friend-occupied position (:X:)
State: from (1,0) to (0,0), !isEmpty at (0,0), friend at (0,0)
Expectation: false

RTC19: Rook moves left one space, to friend-occupied position (:X:)
State: from (1,1) to (1,0), !isEmpty at (1,0), friend at (1,0)
Expectation: false

RTC20: Rook moves right one space, to friend-occupied position (:X:)
State: from (0,0) to (0,1), !isEmpty at (0,1), friend at (0,1)
Expectation: false

## Invalid case 2: max-spaces movement, clear path, to a friend-occupied position

RTC21: Rook moves forward maximum spaces, clear path, to friend-occupied position (:X:)
State: from (0,0) to (7,0), isEmpty from (1,0) to (6,0), !isEmpty at (7,0), friend at (7,0)
Expectation: false

RTC22: Rook moves backward maximum spaces, clear path, to friend-occupied position (:X:)
State: from (7,0) to (0,0), isEmpty from (6,0) to (1,0), !isEmpty at (0,0), friend at (0,0)
Expectation: false

RTC23: Rook moves left maximum spaces, clear path, to friend-occupied position (:X:)
State: from (0,7) to (0,0), isEmpty from (0,6) to (0,1), !isEmpty at (0,0), friend at (0,0)
Expectation: false

RTC24: Rook moves right maximum spaces, clear path, to friend-occupied position (:X:)
State: from (0,0) to (0,7), isEmpty from (0,1) to (0,6), !isEmpty at (0,7), friend at (0,7)
Expectation: false

## Invalid case 3: max-spaces, friend-obstructed path, to empty/friend/foe-occupied position

RTC25: Rook moves forward maximum spaces, friend-obstructed path, to empty position (:X:)
State: from (0,0) to (7,0), isEmpty at (7,0), !isEmpty at (1,0), friend at (1,0)
Expectation: false

RTC26: Rook moves forward maximum spaces, friend-obstructed path, to friend-occupied position (:X:)
State: from (0,0) to (7,0), !isEmpty at (7,0), !isEmpty at (1,0), friend at (1,0), friend at (7,0)
Expectation: false

RTC27: Rook moves forward maximum spaces, friend-obstructed path, to foe-occupied position (:X:)
State: from (0,0) to (7,0), !isEmpty at (7,0), !isEmpty at (1,0), friend at (1,0), foe at (7,0)
Expectation: false

RTC28: Rook moves backward maximum spaces, friend-obstructed path, to empty position (:X:)
State: from (7,0) to (0,0), isEmpty at (0,0), !isEmpty at (6,0), friend at (6,0)
Expectation: false

RTC29: Rook moves backward maximum spaces, friend-obstructed path, to friend-occupied position (:X:)
State: from (7,0) to (0,0), !isEmpty at (0,0), !isEmpty at (6,0), friend at (6,0), friend at (0,0)
Expectation: false

RTC30: Rook moves backward maximum spaces, friend-obstructed path, to foe-occupied position (:X:)
State: from (7,0) to (0,0), !isEmpty at (0,0), !isEmpty at (6,0), friend at (6,0), foe at (0,0)
Expectation: false

RTC31: Rook moves left maximum spaces, friend-obstructed path, to empty position (:X:)
State: from (7,7) to (7,0), isEmpty at (7,0), !isEmpty at (7,6), friend at (7,6)
Expectation: false

RTC32: Rook moves left maximum spaces, friend-obstructed path, to friend-occupied position (:X:)
State: from (7,7) to (7,0), !isEmpty at (7,0), !isEmpty at (7,6), friend at (7,6), friend at (7,0)
Expectation: false

RTC33: Rook moves left maximum spaces, friend-obstructed path, to foe-occupied position (:X:)
State: from (7,7) to (7,0), !isEmpty at (7,0), !isEmpty at (7,6), friend at (7,6), foe at (7,0)
Expectation: false

RTC34: Rook moves right maximum spaces, friend-obstructed path, to empty position (:X:)
State: from (7,0) to (7,7), isEmpty at (7,7), !isEmpty at (7,1), friend at (7,1)
Expectation: false

RTC35: Rook moves right maximum spaces, friend-obstructed path, to friend-occupied position (:X:)
State: from (7,0) to (7,7), !isEmpty at (7,1), !isEmpty at (7,7), friend at (7,1), friend at (7,7)
Expectation: false

RTC36: Rook moves right maximum spaces, friend-obstructed path, to foe-occupied position (:X:)
State: from (7,0) to (7,7), !isEmpty at (7,1), !isEmpty at (7,7), friend at (7,1), foe at (7,7)
Expectation: false

## Invalid case 4: max-spaces, foe-obstructed path, to empty/friend/foe-occupied position

RTC37: Rook moves forward maximum spaces, foe-obstructed path, to empty position (:X:)
State: from (0,0) to (7,0), isEmpty at (7,0), !isEmpty at (1,0), foe at (1,0)
Expectation: false

RTC38: Rook moves forward maximum spaces, foe-obstructed path, to friend-occupied position (:X:)
State: from (0,0) to (7,0), !isEmpty at (7,0), !isEmpty at (1,0), foe at (1,0), friend at (7,0)
Expectation: false

RTC39: Rook moves forward maximum spaces, foe-obstructed path, to foe-occupied position (:X:)
State: from (0,0) to (7,0), !isEmpty at (7,0), !isEmpty at (1,0), foe at (1,0), foe at (7,0)
Expectation: false

RTC40: Rook moves backward maximum spaces, foe-obstructed path, to empty position (:X:)
State: from (7,0) to (0,0), isEmpty at (0,0), !isEmpty at (6,0), foe at (6,0)
Expectation: false

RTC41: Rook moves backward maximum spaces, foe-obstructed path, to friend-occupied position (:X:)
State: from (7,0) to (0,0), !isEmpty at (0,0), !isEmpty at (6,0), foe at (6,0), friend at (0,0)
Expectation: false

RTC42: Rook moves backward maximum spaces, foe-obstructed path, to foe-occupied position (:X:)
State: from (7,0) to (0,0), !isEmpty at (0,0), !isEmpty at (6,0), foe at (6,0), foe at (0,0)
Expectation: false

RTC43: Rook moves left maximum spaces, foe-obstructed path, to empty position (:X:)
State: from (7,7) to (7,0), isEmpty at (7,0), !isEmpty at (7,6), foe at (7,6)
Expectation: false

RTC44: Rook moves left maximum spaces, foe-obstructed path, to friend-occupied position (:X:)
State: from (7,7) to (7,0), !isEmpty at (7,0), !isEmpty at (7,6), foe at (7,6), friend at (7,0)
Expectation: false

RTC45: Rook moves left maximum spaces, foe-obstructed path, to foe-occupied position (:X:)
State: from (7,7) to (7,0), !isEmpty at (7,0), !isEmpty at (7,6), foe at (7,6), foe at (7,0)
Expectation: false

RTC46: Rook moves right maximum spaces, foe-obstructed path, to empty position (:X:)
State: from (7,0) to (7,7), isEmpty at (7,7), !isEmpty at (7,1), foe at (7,1)
Expectation: false

RTC47: Rook moves right maximum spaces, foe-obstructed path, to friend-occupied position (:X:)
State: from (7,0) to (7,7), !isEmpty at (7,1), !isEmpty at (7,7), foe at (7,1), friend at (7,7)
Expectation: false

RTC48: Rook moves right maximum spaces, foe-obstructed path, to foe-occupied position (:X:)
State: from (7,0) to (7,7), !isEmpty at (7,1), !isEmpty at (7,7), foe at (7,1), foe at (7,7)
Expectation: false

## Invalid case 5: improper shape, one-space, to empty/friend/foe-occupied position

RTC49: Rook moves forward-right one space to empty position  (:X:)
State: from (3,4) to (4,3), isEmpty at (4,3)
Expectation: false

RTC50: Rook moves forward-left one space to empty position (:X:)
State: from (3,4) to (4,5), isEmpty at (4,5)
Expectation: false

RTC51: Rook moves backward-left one space to empty position (:X:)
State: from (3,4) to (2,5), isEmpty at (2,5)
Expectation: false

RTC52: Rook moves backward-right one space to empty position (:X:)
State: from (3,4) to (2,3), isEmpty at (2,3)
Expectation: false

RTC53: Rook moves forward-right one space to friend-occupied position (:X:)
State: from (3,4) to (4,3), !isEmpty at (4,3), friend at (4,3)
Expectation: false

RTC54: Rook moves forward-left one space to friend-occupied position (:X:)
State: from (3,4) to (4,5), !isEmpty at (4,5), friend at (4,5)
Expectation: false

RTC55: Rook moves backward-left one space to friend-occupied position (:X:)
State: from (3,4) to (2,5), !isEmpty at (2,5), friend at (2,5)
Expectation: false

RTC56: Rook moves backward-right one space to friend-occupied position (:X:)
State: from (3,4) to (2,3), !isEmpty at (2,3), friend at (2,3)
Expectation: false

RTC57: Rook moves forward-right one space to foe-occupied position (:X:)
State: from (3,4) to (4,3), !isEmpty at (4,3), foe at (4,3)
Expectation: false

RTC58: Rook moves forward-left one space to foe-occupied position (:X:)
State: from (3,4) to (4,5), !isEmpty at (4,5), foe at (4,5)
Expectation: false

RTC59: Rook moves backward-left one space to foe-occupied position (:X:)
State: from (3,4) to (2,5), !isEmpty at (2,5), foe at (2,5)
Expectation: false

RTC60: Rook moves backward-right one space to foe-occupied position (:X:)
State: from (3,4) to (2,3), !isEmpty at (2,3), foe at (2,3)
Expectation: false

## Invalid case 6: out-of-bounds movement

RTC61: Rook moves right out-of-bounds (:X:)
State: from (0,7) to (0,8)
Expectation: false

RTC62: Rook moves forward out-of-bounds (:X:)
State: from (7,0) to (8,0)
Expectation: false

RTC63: Rook backward right out-of-bounds (:X:)
State: from (0,0) to (-1,0)
Expectation: false

RTC64: Rook left forward out-of-bounds (:X:)
State: from (0,0) to (0,-1)
Expectation: false

## Invalid case 7: movement to current position (no movement)

RTC65: Rook moves to its own current square (distance zero)  (:X:)
State of the system: from (3,3) to (3,3)
Expected output: false


### PieceType under test: Knight

## Valid moves case 1: to empty positions

KTC1: Knight moves in valid forward-left L-shape to empty position (:X:)
State of the system: from (3,3) to (5,4), isEmpty at (5,4)
Expected output: true

KTC2: Knight moves in valid forward-right L-shape to empty position (:X:)
State of the system: from (3,3) to (5,2), isEmpty at (5,2)
Expected output: true

KTC3: Knight moves in valid right-forward L-shape to empty position (:X:)
State of the system: from (3,3) to (4,1), isEmpty at (4,1)
Expected output: true

KTC4: Knight moves in valid right-backward L-shape to empty position (:X:)
State of the system: from (3,3) to (2,1), isEmpty at (2,1)
Expected output: true

KTC5: Knight moves in valid backward-left L-shape to empty position (:X:)
State of the system: from (3,3) to (1,4), isEmpty at (1,4)
Expected output: true

KTC6: Knight moves in valid backward-right L-shape to empty position (:X:)
State of the system: from (3,3) to (1,2), isEmpty at (1,2)
Expected output: true

KTC7: Knight moves in valid left-forward L-shape to empty position (:X:)
State of the system: from (3,3) to (4,5), isEmpty at (4,5)
Expected output: true

KTC8: Knight moves in valid left-backward L-shape to empty position (:X:)
State of the system: from (3,3) to (2,5), isEmpty at (2,5)
Expected output: true

## Valid moves case 2: to foe-occupied positions

KTC9: Knight moves in valid forward-left L-shape to foe-occupied position (:X:)
State of the system: from (3,3) to (5,4), !isEmpty at (5,4), foe at (5,4)
Expected output: true

KTC10: Knight moves in valid forward-right L-shape to foe-occupied position (:X:)
State of the system: from (3,3) to (5,2), !isEmpty at (5,2), foe at (5,2)
Expected output: true

KTC11: Knight moves in valid right-forward L-shape to foe-occupied position (:X:)
State of the system: from (3,3) to (4,1), !isEmpty at (4,1), foe at (4,1)
Expected output: true

KTC12: Knight moves in valid right-backward L-shape to foe-occupied position (:X:)
State of the system: from (3,3) to (2,1), !isEmpty at (2,1), foe at (2,1)
Expected output: true

KTC13: Knight moves in valid backward-left L-shape to foe-occupied position (:X:)
State of the system: from (3,3) to (1,4), !isEmpty at (1,4), foe at (1,4)
Expected output: true

KTC14: Knight moves in valid backward-right L-shape to foe-occupied position (:X:)
State of the system: from (3,3) to (1,2), !isEmpty at (1,2), foe at (1,2)
Expected output: true

KTC15: Knight moves in valid left-forward L-shape to foe-occupied position (:X:)
State of the system: from (3,3) to (4,5), !isEmpty at (4,5), foe at (4,5)
Expected output: true

KTC16: Knight moves in valid left-backward L-shape to foe-occupied position (:X:)
State of the system: from (3,3) to (2,5), !isEmpty at (2,5), foe at (2,5)
Expected output: true

## Valid moves case 3: to empty positions, path friend-obstructed

KTC17: Knight moves in valid forward-left L-shape to empty position, friend-obstructed (:X:)
State of the system: from (3,3) to (5,4), isEmpty at (5,4), !isEmpty at (4,3), friend at (4,3)
Expected output: true

KTC18: Knight moves in valid forward-right L-shape to empty position, friend-obstructed (:X:)
State of the system: from (3,3) to (5,2), isEmpty at (5,2), !isEmpty at (4,3), friend at (4,3)
Expected output: true

KTC19: Knight moves in valid right-forward L-shape to empty position, friend-obstructed (:X:)
State of the system: from (3,3) to (4,1), isEmpty at (4,1), !isEmpty at (3,2), friend at (3,2)
Expected output: true

KTC20: Knight moves in valid right-backward L-shape to empty position, friend-obstructed (:X:)
State of the system: from (3,3) to (2,1), isEmpty at (2,1), !isEmpty at (3,2), friend at (3,2)
Expected output: true

KTC21: Knight moves in valid backward-left L-shape to empty position, friend-obstructed (:X:)
State of the system: from (3,3) to (1,4), isEmpty at (1,4), !isEmpty at (2,3), friend at (2,3)
Expected output: true

KTC22: Knight moves in valid backward-right L-shape to empty position, friend-obstructed (:X:)
State of the system: from (3,3) to (1,2), isEmpty at (1,2), !isEmpty at (2,3), friend at (2,3)
Expected output: true

KTC23: Knight moves in valid left-forward L-shape to empty position, friend-obstructed (:X:)
State of the system: from (3,3) to (4,5), isEmpty at (4,5), !isEmpty at (3,4), friend at (3,4)
Expected output: true

KTC24: Knight moves in valid left-backward L-shape to empty position, friend-obstructed (:X:)
State of the system: from (3,3) to (2,5), isEmpty at (2,5), !isEmpty at (3,4), friend at (3,4)
Expected output: true

## Valid moves case 4: to foe-occupied positions, path friend-obstructed

KTC25: Knight moves in valid forward-left L-shape to foe-occupied position, friend-obstructed (:X:)
State of the system: from (3,3) to (5,4), !isEmpty at (5,4), foe at (5,4), !isEmpty at (4,3), friend at (4,3)
Expected output: true

KTC26: Knight moves in valid forward-right L-shape to foe-occupied position, friend-obstructed (:X:)
State of the system: from (3,3) to (5,2), !isEmpty at (5,2), foe at (5,2), !isEmpty at (4,3), friend at (4,3)
Expected output: true

KTC27: Knight moves in valid right-forward L-shape to foe-occupied position, friend-obstructed (:X:)
State of the system: from (3,3) to (4,1), !isEmpty at (4,1), foe at (4,1), !isEmpty at (3,2), friend at (3,2)
Expected output: true

KTC28: Knight moves in valid right-backward L-shape to foe-occupied position, friend-obstructed (:X:)
State of the system: from (3,3) to (2,1), !isEmpty at (2,1), foe at (2,1), !isEmpty at (3,2), friend at (3,2)
Expected output: true

KTC29: Knight moves in valid backward-left L-shape to foe-occupied position, friend-obstructed (:X:)
State of the system: from (3,3) to (1,4), !isEmpty at (1,4), foe at (1,4), !isEmpty at (2,3), friend at (2,3)
Expected output: true

KTC30: Knight moves in valid backward-right L-shape to foe-occupied position, friend-obstructed (:X:)
State of the system: from (3,3) to (1,2), !isEmpty at (1,2), foe at (1,2), !isEmpty at (2,3), friend at (2,3)
Expected output: true

KTC31: Knight moves in valid left-forward L-shape to foe-occupied position, friend-obstructed (:X:)
State of the system: from (3,3) to (4,5), !isEmpty at (4,5), foe at (4,5), !isEmpty at (3,4), friend at (3,4)
Expected output: true

KTC32: Knight moves in valid left-backward L-shape to foe-occupied position, friend-obstructed (:X:)
State of the system: from (3,3) to (2,5), !isEmpty at (2,5), foe at (2,5), !isEmpty at (3,4), friend at (3,4)
Expected output: true

## Invalid moves case 1: valid move to friend-occupied position

KTC33: Knight moves in valid forward-left L-shape to friend-occupied position (:X:)
State of the system: from (3,3) to (5,4), !isEmpty at (5,4), friend at (5,4)
Expected output: false

KTC34: Knight moves in valid forward-right L-shape to friend-occupied position (:X:)
State of the system: from (3,3) to (5,2), !isEmpty at (5,2), friend at (5,2)
Expected output: false

KTC35: Knight moves in valid right-forward L-shape to friend-occupied position (:X:)
State of the system: from (3,3) to (4,1), !isEmpty at (4,1), friend at (4,1)
Expected output: false

KTC36: Knight moves in valid right-backward L-shape to friend-occupied position (:X:)
State of the system: from (3,3) to (2,1), !isEmpty at (2,1), friend at (2,1)
Expected output: false

KTC37: Knight moves in valid backward-left L-shape to friend-occupied position (:X:)
State of the system: from (3,3) to (1,4), !isEmpty at (1,4), friend at (1,4)
Expected output: false

KTC38: Knight moves in valid backward-right L-shape to friend-occupied position (:X:)
State of the system: from (3,3) to (1,2), !isEmpty at (1,2), friend at (1,2)
Expected output: false

KTC39: Knight moves in valid left-forward L-shape to friend-occupied position (:X:)
State of the system: from (3,3) to (4,5), !isEmpty at (4,5), friend at (4,5)
Expected output: false

KTC40: Knight moves in valid left-backward L-shape to friend-occupied position (:X:)
State of the system: from (3,3) to (2,5), !isEmpty at (2,5), friend at (2,5)
Expected output: false

## Invalid move case 2: too-short L-shape, (one-space diagonal)

KTC41: Knight moves one space diagonally forward-right, to empty position (:X:)
State of the system: from (3,3) to (4,4), isEmpty at (4,4)
Expected output: false

KTC42: Knight moves one space diagonally forward-right, to friend-occupied position (:X:)
State of the system: from (3,3) to (4,4), !isEmpty at (4,4), friend at (4,4)
Expected output: false

KTC43: Knight moves one space diagonally forward-right, to foe-occupied position (:X:)
State of the system: from (3,3) to (4,4), !isEmpty at (4,4), foe at (4,4)
Expected output: false

KTC44: Knight moves one space diagonally forward-left, to empty position (:X:)
State of the system: from (3,3) to (4,2), isEmpty at (4,2)
Expected output: false

KTC45: Knight moves one space diagonally forward-left, to friend-occupied position (:X:)
State of the system: from (3,3) to (4,2), !isEmpty at (4,2), friend at (4,2)
Expected output: false

KTC46: Knight moves one space diagonally forward-left, to foe-occupied position (:X:)
State of the system: from (3,3) to (4,2), !isEmpty at (4,2), foe at (4,2)
Expected output: false

KTC47: Knight moves one space diagonally backward-right, to empty position (:X:)
State of the system: from (3,3) to (2,4), isEmpty at (2,4)
Expected output: false

KTC48: Knight moves one space diagonally backward-right, to friend-occupied position (:X:)
State of the system: from (3,3) to (2,4), !isEmpty at (2,4), friend at (2,4)
Expected output: false

KTC49: Knight moves one space diagonally backward-right, to foe-occupied position (:X:)
State of the system: from (3,3) to (2,4), !isEmpty at (2,4), foe at (2,4)
Expected output: false

KTC50: Knight moves one space diagonally backward-left, to empty position (:X:)
State of the system: from (3,3) to (2,2), isEmpty at (2,2)
Expected output: false

KTC51: Knight moves one space diagonally backward-left, to friend-occupied position (:X:)
State of the system: from (3,3) to (2,2), !isEmpty at (2,2), friend at (2,2)
Expected output: false

KTC52: Knight moves one space diagonally backward-left, to foe-occupied position (:X:)
State of the system: from (3,3) to (2,2), !isEmpty at (2,2), foe at (2,2)
Expected output: false

## Invalid move case 3: too long L-shape (two-space diagonal)

KTC53: Knight moves two spaces diagonally forward-right, to empty position (:X:)
State of the system: from (3,3) to (5,5), isEmpty at (5,5)
Expected output: false

KTC54: Knight moves two spaces diagonally forward-right, to friend-occupied position (:X:)
State of the system: from (3,3) to (5,5), !isEmpty at (5,5), friend at (5,5)
Expected output: false

KTC55: Knight moves two spaces diagonally forward-right, to foe-occupied position (:X:)
State of the system: from (3,3) to (5,5), !isEmpty at (5,5), foe at (5,5)
Expected output: false

KTC56: Knight moves two spaces diagonally forward-left, to empty position (:X:)
State of the system: from (3,3) to (5,1), isEmpty at (5,1)
Expected output: false

KTC57: Knight moves two spaces diagonally forward-left, to friend-occupied position (:X:)
State of the system: from (3,3) to (5,1), !isEmpty at (5,1), friend at (5,1)
Expected output: false

KTC58: Knight moves two spaces diagonally forward-left, to foe-occupied position (:X:)
State of the system: from (3,3) to (5,1), !isEmpty at (5,1), foe at (5,1)
Expected output: false

KTC59: Knight moves two spaces diagonally backward-right, to empty position (:X:)
State of the system: from (3,3) to (1,5), isEmpty at (1,5)
Expected output: false

KTC60: Knight moves two spaces diagonally backward-right, to friend-occupied position (:X:)
State of the system: from (3,3) to (1,5), !isEmpty at (1,5), friend at (1,5)
Expected output: false

KTC61: Knight moves two spaces diagonally backward-right, to foe-occupied position (:X:)
State of the system: from (3,3) to (1,5), !isEmpty at (1,5), foe at (1,5)
Expected output: false

KTC62: Knight moves two spaces diagonally backward-left, to empty position (:X:)
State of the system: from (3,3) to (1,1), isEmpty at (1,1)
Expected output: false

KTC63: Knight moves two spaces diagonally backward-left, to friend-occupied position (:X:)
State of the system: from (3,3) to (1,1), !isEmpty at (1,1), friend at (1,1)
Expected output: false

KTC64: Knight moves two spaces diagonally backward-left, to foe-occupied position (:X:)
State of the system: from (3,3) to (1,1), !isEmpty at (1,1), foe at (1,1)
Expected output: false

## Invalid move case 4: straight line, one-space, to friend/foe/empty

KTC65: Knight moves one space forward, to empty position (:X:)
State of the system: from (3,3) to (4,3), isEmpty at (4,3)
Expected output: false

KTC66: Knight moves one space forward, to friend-occupied position (:X:)
State of the system: from (3,3) to (4,3), !isEmpty at (4,3), friend at (4,3)
Expected output: false

KTC67: Knight moves one space forward, to foe-occupied position (:X:)
State of the system: from (3,3) to (4,3), !isEmpty at (4,3), foe at (4,3)
Expected output: false

KTC68: Knight moves one space backward, to empty position (:X:)
State of the system: from (3,3) to (2,3), isEmpty at (2,3)
Expected output: false

KTC69: Knight moves one space backward, to friend-occupied position (:X:)
State of the system: from (3,3) to (2,3), !isEmpty at (2,3), friend at (2,3)
Expected output: false

KTC70: Knight moves one space backward, to foe-occupied position (:X:)
State of the system: from (3,3) to (2,3), !isEmpty at (2,3), foe at (2,3)
Expected output: false

KTC71: Knight moves one space left, to empty position (:X:)
State of the system: from (3,3) to (3,2), isEmpty at (3,2)
Expected output: false

KTC72: Knight moves one space left, to friend-occupied position (:X:)
State of the system: from (3,3) to (3,2), !isEmpty at (3,2), friend at (3,2)
Expected output: false

KTC73: Knight moves one space left, to foe-occupied position (:X:)
State of the system: from (3,3) to (3,2), !isEmpty at (3,2), foe at (3,2)
Expected output: false

KTC74: Knight moves one space right, to empty position (:X:)
State of the system: from (3,3) to (3,4), isEmpty at (3,4)
Expected output: false

KTC75: Knight moves one space right, to friend-occupied position (:X:)
State of the system: from (3,3) to (3,4), !isEmpty at (3,4), friend at (3,4)
Expected output: false

KTC76: Knight moves one space right, to foe-occupied position (:X:)
State of the system: from (3,3) to (3,4), !isEmpty at (3,4), foe at (3,4)
Expected output: false

## Invalid move case 5: out-of-bounds

KTC77: Knight moves out-of-bounds, top boundary, forward-left L-shape (:X:)
State of the system: from (6,3) to (8,2)
Expected output: false

KTC78: Knight moves out-of-bounds, top boundary, forward-right L-shape (:X:)
State of the system: from (6,3) to (8,4)
Expected output: false

KTC79: Knight moves out-of-bounds, top boundary, left-forward L-shape  (:X:)
State of the system: from (7,3) to (8,1)
Expected output: false

KTC80: Knight moves out-of-bounds, top boundary, right-forward L-shape (:X:) 
State of the system: from (7,3) to (8,5)
Expected output: false

KTC81: Knight moves out-of-bounds, bottom boundary, backward-left L-shape (:X:)
State of the system: from (1,3) to (-1,2)
Expected output: false

KTC82: Knight moves out-of-bounds, bottom boundary, backward-right L-shape (:X:)
State of the system: from (1,3) to (-1,4)
Expected output: false

KTC83: Knight moves out-of-bounds, bottom boundary, left-backward L-shape (:X:)
State of the system: from (0,3) to (-1,1)
Expected output: false

KTC84: Knight moves out-of-bounds, bottom boundary, right-backward L-shape (:X:) 
State of the system: from (0,3) to (-1,5)
Expected output: false

KTC85: Knight moves out-of-bounds, left boundary, forward-left L-shape (:X:)
Correction: Changed destination column from 0 to -1 to step off the left edge.
State of the system: from (3,1) to (5,-1)
Expected output: false

KTC86: Knight moves out-of-bounds, left boundary, left-forward L-shape (:X:)
Correction: Changed destination column from 0 to -1 to step off the left edge.
State of the system: from (3,1) to (4,-1)
Expected output: false

KTC87: Knight moves out-of-bounds, left boundary, left-backward L-shape (:X:)
Correction: Changed destination column from 0 to -1 to step off the left edge.
State of the system: from (3,1) to (2,-1)
Expected output: false

KTC88: Knight moves out-of-bounds, left boundary, backward-left L-shape (:X:)
Correction: Changed destination column from 0 to -1 to step off the left edge.
State of the system: from (3,1) to (1,-1)
Expected output: false

KTC89: Knight moves out-of-bounds, right boundary, forward-right L-shape (:X:)
State of the system: from (3,6) to (5,8)
Expected output: false

KTC90: Knight moves out-of-bounds, right boundary, right-forward L-shape (:X:)
Correction: Changed starting column to 6 and destination column from 7 to 8 to step off the right edge.
State of the system: from (3,6) to (4,8)
Expected output: false

KTC91: Knight moves out-of-bounds, right boundary, right-backward L-shape (:X:)
Correction: Changed starting column to 6 and destination column from 7 to 8 to step off the right edge.
State of the system: from (3,6) to (2,8)
Expected output: false

KTC92: Knight moves out-of-bounds, right boundary, backward-right L-shape (:X:)
State of the system: from (3,6) to (1,8)
Expected output: false

## Invalid move case 6: movement to current position (no movement)

KTC93: Knight moves to its own current square (distance zero) (:X:)
State of the system: from (3,3) to (3,3)
Expected output: false

## Valid moves case 5: path foe-obstructed (Jumping Enemy Mechanic)

KTC94: Knight moves in valid forward-left L-shape to empty position, foe-obstructed
State of the system: from (3,3) to (5,4), isEmpty at (5,4), !isEmpty at (4,3), foe at (4,3)
Expected output: true

KTC95: Knight moves in valid forward-left L-shape to foe-occupied position, foe-obstructed
State of the system: from (3,3) to (5,4), !isEmpty at (5,4), foe at (5,4), !isEmpty at (4,3), foe at (4,3)
Expected output: true

### PieceType under test: Bishop

## Valid move case 1: one space to empty positions

BTC1: Move diagonally forward-right one space to empty position (:X:)
State: from (1,1) to (2,0), isEmpty at (2,0)
Expectation: true

BTC2: Move diagonally forward-left one space to empty position (:X:)
State: from (1,1) to (2,2), isEmpty at (2,2)
Expectation: true

BTC3: Move diagonally backward-left one space to empty position (:X:)
State: from (1,1) to (0,2), isEmpty at (0,2)
Expectation: true

BTC4: Move diagonally backward-right one space to empty position (:X:)
State: from (1,1) to (0,0), isEmpty at (0,0)
Expectation: true

## Valid move case 2: one space to foe-occupied position

BTC5: Move diagonally forward-right one space to foe-occupied position  (:X:)
State: from (1,1) to (2,0), !isEmpty at (2,0), foe at (2,0)
Expectation: true

BTC6: Move diagonally forward-left one space to foe-occupied position (:X:)
State: from (1,1) to (2,2), !isEmpty at (2,2), foe at (2,2)
Expectation: true

BTC7: Move diagonally backward-left one space to foe-occupied position (:X:)
State: from (1,1) to (0,2), !isEmpty at (0,2), foe at (0,2)
Expectation: true

BTC8: Move diagonally backward-right one space to foe-occupied position (:X:)
State: from (1,1) to (0,0), !isEmpty at (0,0), foe at (0,0)
Expectation: true

## Valid move case 3: max spaces, clear path, to empty position

BTC9: Move diagonally forward-right max spaces to empty position, clear path  (:X:)
State: from (0,0) to (7,7), isEmpty from (1,1) to (7,7)
Expectation: true

BTC10: Move diagonally forward-left max spaces to empty position, clear path (:X:)
State: from (0,7) to (7,0), isEmpty from (1,6) to (7,0)
Expectation: true

BTC11: Move diagonally backward-right max spaces to empty position, clear path (:X:)
State: from (7,7) to (0,0), isEmpty from (6,6) to (0,0)
Expectation: true

BTC12: Move diagonally backward-left max spaces to empty position, clear path (:X:)
State: from (7,0) to (0,7), isEmpty from (6,1) to (0,7)
Expectation: true

## Valid move case 4: max spaces, clear path, to foe-occupied position

BTC13: Move diagonally forward-right max spaces to foe-occupied position, clear path (:X:)
State: from (0,0) to (7,7), isEmpty from (1,1) to (6,6), !isEmpty at (7,7), foe at (7,7)
Expectation: true

BTC14: Move diagonally forward-left max spaces to foe-occupied position, clear path (:X:)
State: from (0,7) to (7,0), isEmpty from (1,6) to (6,1), !isEmpty at (7,0), foe at (7,0)
Expectation: true

BTC15: Move diagonally backward-right max spaces to foe-occupied position, clear path (:X:)
State: from (7,7) to (0,0), isEmpty from (6,6) to (1,1), !isEmpty at (0,0), foe at (0,0)
Expectation: true

BTC16: Move diagonally backward-right max spaces to foe-occupied position, clear path (:X:)
State: from (7,0) to (0,7), isEmpty from (6,1) to (1,6), !isEmpty at (0,7), foe at (0,7)
Expectation: true

## Invalid move case 1: one-space to friend-occupied position

BTC17: Move diagonally forward-right one space to friend-occupied position (:X:)
State: from (1,1) to (2,0), !isEmpty at (2,0), friend at (2,0)
Expectation: false

BTC18: Move diagonally forward-left one space to friend-occupied position (:X:)
State: from (1,1) to (2,2), !isEmpty at (2,2), friend at (2,2)
Expectation: false

BTC19: Move diagonally backward-left one space to friend-occupied position (:X:)
State: from (1,1) to (0,2), !isEmpty at (0,2), friend at (0,2)
Expectation: false

BTC20: Move diagonally backward-right one space to friend-occupied position (:X:)
State: from (1,1) to (0,0), !isEmpty at (0,0), friend at (0,0)
Expectation: false

## Invalid move case 2: max spaces, clear path, to friend-occupied position

BTC21: Move diagonally forward-right max spaces to friend-occupied position, clear path (:X:)
State: from (0,0) to (7,7), isEmpty from (1,1) to (6,6), !isEmpty at (7,7), friend at (7,7)
Expectation:false

BTC22: Move diagonally forward-left max spaces to friend-occupied position, clear path (:X:)
State: from (0,7) to (7,0), isEmpty from (1,6) to (6,1), !isEmpty at (7,0), friend at (7,0)
Expectation: false

BTC23: Move diagonally backward-right max spaces to friend-occupied position, clear path (:X:)
State: from (7,7) to (0,0), isEmpty from (6,6) to (1,1), !isEmpty at (0,0), friend at (0,0)
Expectation: false

BTC24: Move diagonally backward-right max spaces to friend-occupied position, clear path (:X:)
State: from (7,0) to (0,7), isEmpty from (6,1) to (1,6), !isEmpty at (0,7), friend at (0,7)
Expectation: false

## Invalid move case 3: max spaces, friend/foe-obstructed path, to empty position

BTC25: Move diagonally forward-right, max spaces, to empty position, unclear path (:X:)
State: from (0,0) to (7,7), !isEmpty at (1,1), friend at (1,1), isEmpty at (7,7)
Expectation: false

BTC26: Move diagonally forward-right, max spaces, to empty position, unclear path (:X:)
State: from (0,0) to (7,7), !isEmpty at (1,1), foe at (1,1), isEmpty at (7,7)
Expectation: false

BTC27: Move diagonally forward-left, max spaces, to empty position, unclear path (:X:)
State: from (0,7) to (7,0), !isEmpty at (1,6), friend at (1,6), isEmpty at (7,0)
Expectation: false

BTC28: Move diagonally forward-left, max spaces, to empty position, unclear path (:X:)
State: from (0,7) to (7,0), !isEmpty at (1,6), foe at (1,6), isEmpty at (7,0)
Expectation: false

BTC29: Move diagonally backward-right, max spaces, to empty position, unclear path (:X:)
State: from (7,0) to (0,7), !isEmpty at (6,1), friend at (6,1), isEmpty at (0,7)
Expectation: false

BTC30: Move diagonally backward-right, max spaces, to empty position, unclear path (:X:)
State: from (7,0) to (0,7), !isEmpty at (6,1), foe at (6,1), isEmpty at (0,7)
Expectation: false

BTC31: Move diagonally backward-left, max spaces, to empty position, unclear path (:X:)
State: from (7,7) to (0,0), !isEmpty at (6,6), friend at (6,6), isEmpty at (0,0)
Expectation: false

BTC32: Move diagonally backward-left, max spaces, to empty position, unclear path (:X:)
State: from (7,7) to (0,0), !isEmpty at (6,6), foe at (6,6), isEmpty at (0,0)
Expectation: false


## Invalid move case 4: max spaces, friend/foe-obstructed path, to friend-occupied position

BTC33: Move diagonally forward-right, max spaces, to friend-occupied position, unclear path (:X:)
State: from (0,0) to (7,7), !isEmpty at (1,1), friend at (1,1), !isEmpty at (7,7), friend at (7,7)
Expectation: false

BTC34: Move diagonally forward-right, max spaces, to friend-occupied position, unclear path (:X:)
State: from (0,0) to (7,7), !isEmpty at (1,1), foe at (1,1), !isEmpty at (7,7), friend at (7,7)
Expectation: false

BTC35: Move diagonally forward-left, max spaces, to friend-occupied position, unclear path (:X:)
State: from (0,7) to (7,0), !isEmpty at (1,6), friend at (1,6), !isEmpty at (7,0), friend at (7,0)
Expectation: false

BTC36: Move diagonally forward-left, max spaces, to friend-occupied position, unclear path (:X:)
State: from (0,7) to (7,0), !isEmpty at (1,6), foe at (1,6), !isEmpty at (7,0), friend at (7,0)
Expectation: false

BTC37: Move diagonally backward-right, max spaces, to friend-occupied position, unclear path (:X:)
State: from (7,0) to (0,7), !isEmpty at (6,1), friend at (6,1), !isEmpty at (0,7), friend at (0,7)
Expectation: false

BTC38: Move diagonally backward-right, max spaces, to friend-occupied position, unclear path (:X:)
State: from (7,0) to (0,7), !isEmpty at (6,1), foe at (6,1), !isEmpty at (0,7), friend at (0,7)
Expectation: false

BTC39: Move diagonally backward-left, max spaces, to friend-occupied position, unclear path (:X:)
State: from (7,7) to (0,0), !isEmpty at (6,6), friend at (6,6), !isEmpty at (0,0), friend at (0,0)
Expectation: false

BTC40: Move diagonally backward-left, max spaces, to friend-occupied position, unclear path (:X:)
State: from (7,7) to (0,0), !isEmpty at (6,6), foe at (6,6), !isEmpty at (0,0), friend at (0,0)
Expectation: false


## Invalid move case 5: max spaces, friend/foe-obstructed path, to foe-occupied position

BTC41: Move diagonally forward-right, max spaces, to foe-occupied position, unclear path (:X:)
State: from (0,0) to (7,7), !isEmpty at (1,1), friend at (1,1), !isEmpty at (7,7), foe at (7,7)
Expectation: false

BTC42: Move diagonally forward-right, max spaces, to foe-occupied position, unclear path (:X:)
State: from (0,0) to (7,7), !isEmpty at (1,1), foe at (1,1), !isEmpty at (7,7), foe at (7,7)
Expectation: false

BTC43: Move diagonally forward-left, max spaces, to foe-occupied position, unclear path (:X:)
State: from (0,7) to (7,0), !isEmpty at (1,6), friend at (1,6), !isEmpty at (7,0), foe at (7,0)
Expectation: false

BTC44: Move diagonally forward-left, max spaces, to foe-occupied position, unclear path (:X:)
State: from (0,7) to (7,0), !isEmpty at (1,6), foe at (1,6), !isEmpty at (7,0), foe at (7,0)
Expectation: false

BTC45: Move diagonally backward-right, max spaces, to foe-occupied position, unclear path (:X:)
State: from (7,0) to (0,7), !isEmpty at (6,1), friend at (6,1), !isEmpty at (0,7), foe at (0,7)
Expectation: false

BTC46: Move diagonally backward-right, max spaces, to foe-occupied position, unclear path (:X:)
State: from (7,0) to (0,7), !isEmpty at (6,1), foe at (6,1), !isEmpty at (0,7), foe at (0,7)
Expectation: false

BTC47: Move diagonally backward-left, max spaces, to foe-occupied position, unclear path (:X:)
State: from (7,7) to (0,0), !isEmpty at (6,6), friend at (6,6), !isEmpty at (0,0), foe at (0,0)
Expectation: false

BTC48: Move diagonally backward-left, max spaces, to foe-occupied position, unclear path (:X:)
State: from (7,7) to (0,0), !isEmpty at (6,6), foe at (6,6), !isEmpty at (0,0), foe at (0,0)
Expectation: false


## Invalid move case 6: improper shape, forward/backward/left/right, to friend/empty/foe position

BTC49: Move one space forward, to empty position (:X:)
State: from (0,0) to (1,0), isEmpty at (1,0)
Expectation: false

BTC50: Move one space forward, to friend-occupied position (:X:)
State: from (0,0) to (1,0), !isEmpty at (1,0), friend at (1,0)
Expectation: false

BTC51: Move one space forward, to foe-occupied position (:X:)
State: from (0,0) to (1,0), !isEmpty at (1,0), foe at (1,0)
Expectation: false

BTC52: Move one space backward, to empty position (:X:)
State: from (1,0) to (0,0), isEmpty at (0,0)
Expectation: false

BTC53: Move one space backward, to friend-occupied position (:X:)
State: from (1,0) to (0,0), !isEmpty at (0,0), friend at (0,0)
Expectation: false

BTC54: Move one space backward, to foe-occupied position (:X:)
State: from (1,0) to (0,0), !isEmpty at (0,0), foe at (0,0)
Expectation: false

BTC55: Move one space left, to empty position (:X:)
State: from (0,1) to (0,0), isEmpty at (0,0)
Expectation: false

BTC56: Move one space left, to friend-occupied position (:X:)
State: from (0,1) to (0,0), !isEmpty at (0,0), friend at (0,0)
Expectation: false

BTC57: Move one space left, to foe-occupied position (:X:)
State: from (0,1) to (0,0), !isEmpty at (0,0), foe at (0,0)
Expectation: false

BTC58: Move one space right, to empty position (:X:)
State: from (0,0) to (0,1), isEmpty at (0,1)
Expectation: false

BTC59: Move one space right, to friend-occupied position (:X:)
State: from (0,0) to (0,1), !isEmpty at (0,1), friend at (0,1)
Expectation: false

BTC60: Move one space right, to foe-occupied position (:X:)
State: from (0,0) to (0,1), !isEmpty at (0,1), foe at (0,1)
Expectation: false

## Invalid move case 7: out-of-bounds movement

BTC61: Move diagonally out-of-bounds top boundary (:X:)
State: from (7,6) to (8,7)
Expectation: false

BTC62: Move diagonally out-of-bounds bottom boundary (:X:)
State: from (0,1) to (-1,0)
Expectation: false

BTC63: Move diagonally out-of-bounds left boundary (:X:)
State: from (1,0) to (2,-1)
Expectation: false

BTC64: Move diagonally out-of-bounds right boundary (:X:)
State: from (1,7) to (2,8)
Expectation: false

## Invalid move case 8: movement to current position (no movement)

BTC65: Bishop moves to its own current square (distance zero) (:X:)
State of the system: from (3,3) to (3,3)
Expected output: false


### PieceType under test: King

## Valid move case 1: one-space forward/backward/left/right to empty/foe position

KiTC1: King moves one space forward, to empty position (:X:)
State of the system: from (3,3) to (4,3), isEmpty at (4,3)
Expected output: true

KiTC2: King moves one space forward, to foe-occupied position (:X:)
State of the system: from (3,3) to (4,3), !isEmpty at (4,3), foe at (4,3)
Expected output: true

KiTC3: King moves one space backward, to empty position (:X:)
State of the system: from (3,3) to (2,3), isEmpty at (2,3)
Expected output: true

KiTC4: King moves one space backward, to foe-occupied position (:X:)
State of the system: from (3,3) to (2,3), !isEmpty at (2,3), foe at (2,3)
Expected output: true

KiTC5: King moves one space left, to empty position (:X:)
State of the system: from (3,3) to (3,2), isEmpty at (3,2)
Expected output: true

KiTC6: King moves one space left, to foe-occupied position (:X:)
State of the system: from (3,3) to (3,2), !isEmpty at (3,2), foe at (3,2)
Expected output: true

KiTC7: King moves one space right, to empty position (:X:)
State of the system: from (3,3) to (3,4), isEmpty at (3,4)
Expected output: true

KiTC8: King moves one space right, to foe-occupied position (:X:)
State of the system: from (3,3) to (3,4), !isEmpty at (3,4), foe at (3,4)
Expected output: true

## Valid move case 2: one-space diagonal to empty/foe position

KiTC9: King moves one space forward-left, to empty position (:X:)
State of the system: from (3,3) to (4,2), isEmpty at (4,2)
Expected output: true

KiTC10: King moves one space forward-left, to foe-occupied position (:X:)
State of the system: from (3,3) to (4,2), !isEmpty at (4,2), foe at (4,2)
Expected output: true

KiTC11: King moves one space forward-right, to empty position (:X:)
State of the system: from (3,3) to (4,4), isEmpty at (4,4)
Expected output: true

KiTC12: King moves one space forward-right, to foe-occupied position (:X:)
State of the system: from (3,3) to (4,4), !isEmpty at (4,4), foe at (4,4)
Expected output: true

KiTC13: King moves one space backward-left, to empty position (:X:)
State of the system: from (3,3) to (2,2), isEmpty at (2,2)
Expected output: true

KiTC14: King moves one space backward-left, to foe-occupied position (:X:)
State of the system: from (3,3) to (2,2), !isEmpty at (2,2), foe at (2,2)
Expected output: true

KiTC15: King moves one space backward-right, to empty position (:X:)
State of the system: from (3,3) to (2,4), isEmpty at (2,4)
Expected output: true

KiTC16: King moves one space backward-right, to foe-occupied position (:X:)
State of the system: from (3,3) to (2,4), !isEmpty at (2,4), foe at (2,4)
Expected output: true

## Valid move case 3: castling

KiTC17: King castles kingside, clear path, neither piece has moved (:X:)
State of the system: from (0,4) to (0,6), isEmpty at (0,5), isEmpty at (0,6), king has not moved, rook at (0,7) has not moved
Expected output: true

KiTC18: King castles queenside, clear path, neither piece has moved (:X:)
State of the system: from (0,4) to (0,2), isEmpty at (0,3), isEmpty at (0,2), isEmpty at (0,1), king has not moved, rook at (0,0) has not moved
Expected output: true

## Invalid move case 1: one-space forward/backward/left/right to friend-occupied position

KiTC19: King moves one space forward, to friend-occupied position (:X:)
State of the system: from (3,3) to (4,3), !isEmpty at (4,3), friend at (4,3)
Expected output: false

KiTC20: King moves one space backward, to friend-occupied position (:X:)
State of the system: from (3,3) to (2,3), !isEmpty at (2,3), friend at (2,3)
Expected output: false

KiTC21: King moves one space left, to friend-occupied position (:X:)
State of the system: from (3,3) to (3,2), !isEmpty at (3,2), friend at (3,2)
Expected output: false

KiTC22: King moves one space right, to friend-occupied position (:X:)
State of the system: from (3,3) to (3,4), !isEmpty at (3,4), friend at (3,4)
Expected output: false

## Invalid move case 2: one-space diagonal to friend-occupied position

KiTC23: King moves one space forward-left, to friend-occupied position (:X:)
State of the system: from (3,3) to (4,2), !isEmpty at (4,2), friend at (4,2)
Expected output: false

KiTC24: King moves one space forward-right, to friend-occupied position (:X:)
State of the system: from (3,3) to (4,4), !isEmpty at (4,4), friend at (4,4)
Expected output: false

KiTC25: King moves one space backward-left, to friend-occupied position (:X:)
State of the system: from (3,3) to (2,2), !isEmpty at (2,2), friend at (2,2)
Expected output: false

KiTC26: King moves one space backward-right, to friend-occupied position (:X:)
State of the system: from (3,3) to (2,4), !isEmpty at (2,4), friend at (2,4)
Expected output: false

## Invalid move case 3: two-space forward/backward/left/right to empty/foe position

KiTC27: King moves two spaces forward, to empty position (:X:)
State of the system: from (3,3) to (5,3), isEmpty at (5,3)
Expected output: false

KiTC28: King moves two spaces forward, to foe-occupied position (:X:)
State of the system: from (3,3) to (5,3), !isEmpty at (5,3), foe at (5,3)
Expected output: false

KiTC29: King moves two spaces backward, to empty position (:X:)
State of the system: from (3,3) to (1,3), isEmpty at (1,3)
Expected output: false

KiTC30: King moves two spaces backward, to foe-occupied position (:X:)
State of the system: from (3,3) to (1,3), !isEmpty at (1,3), foe at (1,3)
Expected output: false

KiTC31: King moves two spaces left, to empty position (:X:)
State of the system: from (3,3) to (3,1), isEmpty at (3,1)
Expected output: false

KiTC32: King moves two spaces left, to foe-occupied position (:X:)
State of the system: from (3,3) to (3,1), !isEmpty at (3,1), foe at (3,1)
Expected output: false

KiTC33: King moves two spaces right, to empty position (:X:)
State of the system: from (3,3) to (3,5), isEmpty at (3,5)
Expected output: false

KiTC34: King moves two spaces right, to foe-occupied position (:X:)
State of the system: from (3,3) to (3,5), !isEmpty at (3,5), foe at (3,5)
Expected output: false

## Invalid move case 4: two-space diagonal to empty/foe position

KiTC35: King moves two spaces forward-left, to empty position (:X:)
State of the system: from (3,3) to (5,1), isEmpty at (5,1)
Expected output: false

KiTC36: King moves two spaces forward-left, to foe-occupied position (:X:)
State of the system: from (3,3) to (5,1), !isEmpty at (5,1), foe at (5,1)
Expected output: false

KiTC37: King moves two spaces forward-right, to empty position (:X:)
State of the system: from (3,3) to (5,5), isEmpty at (5,5)
Expected output: false

KiTC38: King moves two spaces forward-right, to foe-occupied position (:X:)
State of the system: from (3,3) to (5,5), !isEmpty at (5,5), foe at (5,5)
Expected output: false

KiTC39: King moves two spaces backward-left, to empty position (:X:)
State of the system: from (3,3) to (1,1), isEmpty at (1,1)
Expected output: false

KiTC40: King moves two spaces backward-left, to foe-occupied position (:X:)
State of the system: from (3,3) to (1,1), !isEmpty at (1,1), foe at (1,1)
Expected output: false

KiTC41: King moves two spaces backward-right, to empty position (:X:)
State of the system: from (3,3) to (1,5), isEmpty at (1,5)
Expected output: false

KiTC42: King moves two spaces backward-right, to foe-occupied position (:X:)
State of the system: from (3,3) to (1,5), !isEmpty at (1,5), foe at (1,5)
Expected output: false

## Invalid move case 5: out-of-bounds

KiTC43: King moves out-of-bounds, top boundary, forward (:X:)
State of the system: from (7,3) to (8,3)
Expected output: false

KiTC44: King moves out-of-bounds, top boundary, forward-left (:X:)
State of the system: from (7,3) to (8,2)
Expected output: false

KiTC45: King moves out-of-bounds, top boundary, forward-right (:X:)
State of the system: from (7,3) to (8,4)
Expected output: false

KiTC46: King moves out-of-bounds, bottom boundary, backward (:X:)
State of the system: from (0,3) to (-1,3)
Expected output: false

KiTC47: King moves out-of-bounds, bottom boundary, backward-left (:X:)
State of the system: from (0,3) to (-1,2)
Expected output: false

KiTC48: King moves out-of-bounds, bottom boundary, backward-right (:X:)
State of the system: from (0,3) to (-1,4)
Expected output: false

KiTC49: King moves out-of-bounds, left boundary, left (:X:)
State of the system: from (3,0) to (3,-1)
Expected output: false

KiTC50: King moves out-of-bounds, left boundary, forward-left (:X:)
State of the system: from (3,0) to (4,-1)
Expected output: false

KiTC51: King moves out-of-bounds, left boundary, backward-left (:X:)
State of the system: from (3,0) to (2,-1)
Expected output: false

KiTC52: King moves out-of-bounds, right boundary, right (:X:)
State of the system: from (3,7) to (3,8)
Expected output: false

KiTC53: King moves out-of-bounds, right boundary, forward-right (:X:)
State of the system: from (3,7) to (4,8)
Expected output: false

KiTC54: King moves out-of-bounds, right boundary, backward-right (:X:)
State of the system: from (3,7) to (2,8)
Expected output: false

## Invalid move case 6: no movement

KiTC55: King moves to its own current square (distance zero) (:X:)
State of the system: from (3,3) to (3,3)
Expected output: false

## Invalid move case 7: castling attempts 

KiC56: King attempts castling, but intermediate path is obstructed by another piece (:X:)
State of the system: from (0,4) to (0,6), piece at (0,5) != null, rook at (0,7)
Expected output: false

KiTC57: King attempts castling, but target rook belongs to the enemy team (:X:)
State of the system: from (0,4) to (0,6), piece at (0,7) != null, pieceColor at (0,7) == BLACK
Expected output: false

KiTC58: King attempts castling, but target rook has already moved previously in the match
State of the system: from (0,4) to (0,6), piece at (0,7) != null, rook.hasMoved() == true
Expected output: false


### PieceType under test: Queen

## Valid move case 1: one-space in all 8 directions, to empty position

QTC1: Queen moves one space forward, to empty position (:X:)
State of the system: from (3,3) to (4,3), isEmpty at (4,3)
Expected output: true

QTC2: Queen moves one space backward, to empty position (:X:)
State of the system: from (3,3) to (2,3), isEmpty at (2,3)
Expected output: true

QTC3: Queen moves one space left, to empty position (:X:)
State of the system: from (3,3) to (3,2), isEmpty at (3,2)
Expected output: true

QTC4: Queen moves one space right, to empty position (:X:)
State of the system: from (3,3) to (3,4), isEmpty at (3,4)
Expected output: true

QTC5: Queen moves one space forward-left, to empty position (:X:)
State of the system: from (3,3) to (4,2), isEmpty at (4,2)
Expected output: true

QTC6: Queen moves one space forward-right, to empty position (:X:)
State of the system: from (3,3) to (4,4), isEmpty at (4,4)
Expected output: true

QTC7: Queen moves one space backward-left, to empty position (:X:)
State of the system: from (3,3) to (2,2), isEmpty at (2,2)
Expected output: true

QTC8: Queen moves one space backward-right, to empty position (:X:)
State of the system: from (3,3) to (2,4), isEmpty at (2,4)
Expected output: true

## Valid move case 2: one-space in all 8 directions, to foe-occupied position

QTC9: Queen moves one space forward, to foe-occupied position (:X:)
State of the system: from (3,3) to (4,3), !isEmpty at (4,3), foe at (4,3)
Expected output: true

QTC10: Queen moves one space backward, to foe-occupied position (:X:)
State of the system: from (3,3) to (2,3), !isEmpty at (2,3), foe at (2,3)
Expected output: true

QTC11: Queen moves one space left, to foe-occupied position (:X:)
State of the system: from (3,3) to (3,2), !isEmpty at (3,2), foe at (3,2)
Expected output: true

QTC12: Queen moves one space right, to foe-occupied position (:X:)
State of the system: from (3,3) to (3,4), !isEmpty at (3,4), foe at (3,4)
Expected output: true

QTC13: Queen moves one space forward-left, to foe-occupied position (:X:)
State of the system: from (3,3) to (4,2), !isEmpty at (4,2), foe at (4,2)
Expected output: true

QTC14: Queen moves one space forward-right, to foe-occupied position (:X:)
State of the system: from (3,3) to (4,4), !isEmpty at (4,4), foe at (4,4)
Expected output: true

QTC15: Queen moves one space backward-left, to foe-occupied position (:X:)
State of the system: from (3,3) to (2,2), !isEmpty at (2,2), foe at (2,2)
Expected output: true

QTC16: Queen moves one space backward-right, to foe-occupied position (:X:)
State of the system: from (3,3) to (2,4), !isEmpty at (2,4), foe at (2,4)
Expected output: true

## Valid move case 3: max-space in all 8 directions, clear path, to empty position

QTC17: Queen moves max spaces forward, clear path, to empty position (:X:)
State of the system: from (0,3) to (7,3), isEmpty from (1,3) to (7,3)
Expected output: true

QTC18: Queen moves max spaces backward, clear path, to empty position (:X:)
State of the system: from (7,3) to (0,3), isEmpty from (6,3) to (0,3)
Expected output: true

QTC19: Queen moves max spaces left, clear path, to empty position (:X:)
State of the system: from (3,7) to (3,0), isEmpty from (3,6) to (3,0)
Expected output: true

QTC20: Queen moves max spaces right, clear path, to empty position (:X:)
State of the system: from (3,0) to (3,7), isEmpty from (3,1) to (3,7)
Expected output: true

QTC21: Queen moves max spaces forward-left, clear path, to empty position (:X:)
State of the system: from (0,7) to (7,0), isEmpty from (1,6) to (7,0)
Expected output: true

QTC22: Queen moves max spaces forward-right, clear path, to empty position (:X:)
State of the system: from (0,0) to (7,7), isEmpty from (1,1) to (7,7)
Expected output: true

QTC23: Queen moves max spaces backward-left, clear path, to empty position (:X:)
State of the system: from (7,7) to (0,0), isEmpty from (6,6) to (0,0)
Expected output: true

QTC24: Queen moves max spaces backward-right, clear path, to empty position (:X:)
State of the system: from (7,0) to (0,7), isEmpty from (6,1) to (0,7)
Expected output: true

## Valid move case 4: max-space in all 8 directions, clear path, to foe-occupied position

QTC25: Queen moves max spaces forward, clear path, to foe-occupied position (:X:)
State of the system: from (0,3) to (7,3), isEmpty from (1,3) to (6,3), !isEmpty at (7,3), foe at (7,3)
Expected output: true

QTC26: Queen moves max spaces backward, clear path, to foe-occupied position (:X:)
State of the system: from (7,3) to (0,3), isEmpty from (6,3) to (1,3), !isEmpty at (0,3), foe at (0,3)
Expected output: true

QTC27: Queen moves max spaces left, clear path, to foe-occupied position (:X:)
State of the system: from (3,7) to (3,0), isEmpty from (3,6) to (3,1), !isEmpty at (3,0), foe at (3,0)
Expected output: true

QTC28: Queen moves max spaces right, clear path, to foe-occupied position (:X:)
State of the system: from (3,0) to (3,7), isEmpty from (3,1) to (3,6), !isEmpty at (3,7), foe at (3,7)
Expected output: true

QTC29: Queen moves max spaces forward-left, clear path, to foe-occupied position (:X:)
State of the system: from (0,7) to (7,0), isEmpty from (1,6) to (6,1), !isEmpty at (7,0), foe at (7,0)
Expected output: true

QTC30: Queen moves max spaces forward-right, clear path, to foe-occupied position (:X:)
State of the system: from (0,0) to (7,7), isEmpty from (1,1) to (6,6), !isEmpty at (7,7), foe at (7,7)
Expected output: true

QTC31: Queen moves max spaces backward-left, clear path, to foe-occupied position (:X:)
State of the system: from (7,7) to (0,0), isEmpty from (6,6) to (1,1), !isEmpty at (0,0), foe at (0,0)
Expected output: true

QTC32: Queen moves max spaces backward-right, clear path, to foe-occupied position (:X:)
State of the system: from (7,0) to (0,7), isEmpty from (6,1) to (1,6), !isEmpty at (0,7), foe at (0,7)
Expected output: true

## Invalid move case 1: one-space in all 8 directions, to friend-occupied position

QTC33: Queen moves one space forward, to friend-occupied position (:X:)
State of the system: from (3,3) to (4,3), !isEmpty at (4,3), friend at (4,3)
Expected output: false

QTC34: Queen moves one space backward, to friend-occupied position (:X:)
State of the system: from (3,3) to (2,3), !isEmpty at (2,3), friend at (2,3)
Expected output: false

QTC35: Queen moves one space left, to friend-occupied position (:X:)
State of the system: from (3,3) to (3,2), !isEmpty at (3,2), friend at (3,2)
Expected output: false

QTC36: Queen moves one space right, to friend-occupied position (:X:)
State of the system: from (3,3) to (3,4), !isEmpty at (3,4), friend at (3,4)
Expected output: false

QTC37: Queen moves one space forward-left, to friend-occupied position (:X:)
State of the system: from (3,3) to (4,2), !isEmpty at (4,2), friend at (4,2)
Expected output: false

QTC38: Queen moves one space forward-right, to friend-occupied position (:X:)
State of the system: from (3,3) to (4,4), !isEmpty at (4,4), friend at (4,4)
Expected output: false

QTC39: Queen moves one space backward-left, to friend-occupied position (:X:)
State of the system: from (3,3) to (2,2), !isEmpty at (2,2), friend at (2,2)
Expected output: false

QTC40: Queen moves one space backward-right, to friend-occupied position (:X:)
State of the system: from (3,3) to (2,4), !isEmpty at (2,4), friend at (2,4)
Expected output: false

## Invalid move case 2: max-space in all 8 directions, clear path, to friend-occupied position

QTC41: Queen moves max spaces forward, clear path, to friend-occupied position (:X:)
State of the system: from (0,3) to (7,3), isEmpty from (1,3) to (6,3), !isEmpty at (7,3), friend at (7,3)
Expected output: false

QTC42: Queen moves max spaces backward, clear path, to friend-occupied position (:X:)
State of the system: from (7,3) to (0,3), isEmpty from (6,3) to (1,3), !isEmpty at (0,3), friend at (0,3)
Expected output: false

QTC43: Queen moves max spaces left, clear path, to friend-occupied position (:X:)
State of the system: from (3,7) to (3,0), isEmpty from (3,6) to (3,1), !isEmpty at (3,0), friend at (3,0)
Expected output: false

QTC44: Queen moves max spaces right, clear path, to friend-occupied position (:X:)
State of the system: from (3,0) to (3,7), isEmpty from (3,1) to (3,6), !isEmpty at (3,7), friend at (3,7)
Expected output: false

QTC45: Queen moves max spaces forward-left, clear path, to friend-occupied position (:X:)
State of the system: from (0,7) to (7,0), isEmpty from (1,6) to (6,1), !isEmpty at (7,0), friend at (7,0)
Expected output: false

QTC46: Queen moves max spaces forward-right, clear path, to friend-occupied position (:X:)
State of the system: from (0,0) to (7,7), isEmpty from (1,1) to (6,6), !isEmpty at (7,7), friend at (7,7)
Expected output: false

QTC47: Queen moves max spaces backward-left, clear path, to friend-occupied position (:X:)
State of the system: from (7,7) to (0,0), isEmpty from (6,6) to (1,1), !isEmpty at (0,0), friend at (0,0)
Expected output: false

QTC48: Queen moves max spaces backward-right, clear path, to friend-occupied position (:X:)
State of the system: from (7,0) to (0,7), isEmpty from (6,1) to (1,6), !isEmpty at (0,7), friend at (0,7)
Expected output: false

## Invalid move case 3: max-space, friend-obstructed path, to empty position

QTC49: Queen moves max spaces forward, friend-obstructed path, to empty position (:X:)
State of the system: from (0,3) to (7,3), !isEmpty at (1,3), friend at (1,3), isEmpty at (7,3)
Expected output: false

QTC50: Queen moves max spaces backward, friend-obstructed path, to empty position (:X:)
State of the system: from (7,3) to (0,3), !isEmpty at (6,3), friend at (6,3), isEmpty at (0,3)
Expected output: false

QTC51: Queen moves max spaces left, friend-obstructed path, to empty position (:X:)
State of the system: from (3,7) to (3,0), !isEmpty at (3,6), friend at (3,6), isEmpty at (3,0)
Expected output: false

QTC52: Queen moves max spaces right, friend-obstructed path, to empty position (:X:)
State of the system: from (3,0) to (3,7), !isEmpty at (3,1), friend at (3,1), isEmpty at (3,7)
Expected output: false

QTC53: Queen moves max spaces forward-left, friend-obstructed path, to empty position (:X:)
State of the system: from (0,7) to (7,0), !isEmpty at (1,6), friend at (1,6), isEmpty at (7,0)
Expected output: false

QTC54: Queen moves max spaces forward-right, friend-obstructed path, to empty position (:X:)
State of the system: from (0,0) to (7,7), !isEmpty at (1,1), friend at (1,1), isEmpty at (7,7)
Expected output: false

QTC55: Queen moves max spaces backward-left, friend-obstructed path, to empty position (:X:)
State of the system: from (7,7) to (0,0), !isEmpty at (6,6), friend at (6,6), isEmpty at (0,0)
Expected output: false

QTC56: Queen moves max spaces backward-right, friend-obstructed path, to empty position (:X:)
State of the system: from (7,0) to (0,7), !isEmpty at (6,1), friend at (6,1), isEmpty at (0,7)
Expected output: false

## Invalid move case 4: max-space, foe-obstructed path, to empty position

QTC57: Queen moves max spaces forward, foe-obstructed path, to empty position (:X:)
State of the system: from (0,3) to (7,3), !isEmpty at (1,3), foe at (1,3), isEmpty at (7,3)
Expected output: false

QTC58: Queen moves max spaces backward, foe-obstructed path, to empty position (:X:)
State of the system: from (7,3) to (0,3), !isEmpty at (6,3), foe at (6,3), isEmpty at (0,3)
Expected output: false

QTC59: Queen moves max spaces left, foe-obstructed path, to empty position (:X:)
State of the system: from (3,7) to (3,0), !isEmpty at (3,6), foe at (3,6), isEmpty at (3,0)
Expected output: false

QTC60: Queen moves max spaces right, foe-obstructed path, to empty position (:X:)
State of the system: from (3,0) to (3,7), !isEmpty at (3,1), foe at (3,1), isEmpty at (3,7)
Expected output: false

QTC61: Queen moves max spaces forward-left, foe-obstructed path, to empty position (:X:)
State of the system: from (0,7) to (7,0), !isEmpty at (1,6), foe at (1,6), isEmpty at (7,0)
Expected output: false

QTC62: Queen moves max spaces forward-right, foe-obstructed path, to empty position (:X:)
State of the system: from (0,0) to (7,7), !isEmpty at (1,1), foe at (1,1), isEmpty at (7,7)
Expected output: false

QTC63: Queen moves max spaces backward-left, foe-obstructed path, to empty position (:X:)
State of the system: from (7,7) to (0,0), !isEmpty at (6,6), foe at (6,6), isEmpty at (0,0)
Expected output: false

QTC64: Queen moves max spaces backward-right, foe-obstructed path, to empty position (:X:)
State of the system: from (7,0) to (0,7), !isEmpty at (6,1), foe at (6,1), isEmpty at (0,7)
Expected output: false

## Invalid move case 5: max-space, friend-obstructed path, to foe-occupied position

QTC65: Queen moves max spaces forward, friend-obstructed path, to foe-occupied position (:X:)
State of the system: from (0,3) to (7,3), !isEmpty at (1,3), friend at (1,3), !isEmpty at (7,3), foe at (7,3)
Expected output: false

QTC66: Queen moves max spaces backward, friend-obstructed path, to foe-occupied position (:X:)
State of the system: from (7,3) to (0,3), !isEmpty at (6,3), friend at (6,3), !isEmpty at (0,3), foe at (0,3)
Expected output: false

QTC67: Queen moves max spaces left, friend-obstructed path, to foe-occupied position (:X:)
State of the system: from (3,7) to (3,0), !isEmpty at (3,6), friend at (3,6), !isEmpty at (3,0), foe at (3,0)
Expected output: false

QTC68: Queen moves max spaces right, friend-obstructed path, to foe-occupied position (:X:)
State of the system: from (3,0) to (3,7), !isEmpty at (3,1), friend at (3,1), !isEmpty at (3,7), foe at (3,7)
Expected output: false

QTC69: Queen moves max spaces forward-left, friend-obstructed path, to foe-occupied position (:X:)
State of the system: from (0,7) to (7,0), !isEmpty at (1,6), friend at (1,6), !isEmpty at (7,0), foe at (7,0)
Expected output: false

QTC70: Queen moves max spaces forward-right, friend-obstructed path, to foe-occupied position (:X:)
State of the system: from (0,0) to (7,7), !isEmpty at (1,1), friend at (1,1), !isEmpty at (7,7), foe at (7,7)
Expected output: false

QTC71: Queen moves max spaces backward-left, friend-obstructed path, to foe-occupied position (:X:)
State of the system: from (7,7) to (0,0), !isEmpty at (6,6), friend at (6,6), !isEmpty at (0,0), foe at (0,0)
Expected output: false

QTC72: Queen moves max spaces backward-right, friend-obstructed path, to foe-occupied position (:X:)
State of the system: from (7,0) to (0,7), !isEmpty at (6,1), friend at (6,1), !isEmpty at (0,7), foe at (0,7)
Expected output: false

## Invalid move case 6: max-space, foe-obstructed path, to foe-occupied position

QTC73: Queen moves max spaces forward, foe-obstructed path, to foe-occupied position (:X:)
State of the system: from (0,3) to (7,3), !isEmpty at (1,3), foe at (1,3), !isEmpty at (7,3), foe at (7,3)
Expected output: false

QTC74: Queen moves max spaces backward, foe-obstructed path, to foe-occupied position (:X:)
State of the system: from (7,3) to (0,3), !isEmpty at (6,3), foe at (6,3), !isEmpty at (0,3), foe at (0,3)
Expected output: false

QTC75: Queen moves max spaces left, foe-obstructed path, to foe-occupied position (:X:)
State of the system: from (3,7) to (3,0), !isEmpty at (3,6), foe at (3,6), !isEmpty at (3,0), foe at (3,0)
Expected output: false

QTC76: Queen moves max spaces right, foe-obstructed path, to foe-occupied position (:X:)
State of the system: from (3,0) to (3,7), !isEmpty at (3,1), foe at (3,1), !isEmpty at (3,7), foe at (3,7)
Expected output: false

QTC77: Queen moves max spaces forward-left, foe-obstructed path, to foe-occupied position (:X:)
State of the system: from (0,7) to (7,0), !isEmpty at (1,6), foe at (1,6), !isEmpty at (7,0), foe at (7,0)
Expected output: false

QTC78: Queen moves max spaces forward-right, foe-obstructed path, to foe-occupied position (:X:)
State of the system: from (0,0) to (7,7), !isEmpty at (1,1), foe at (1,1), !isEmpty at (7,7), foe at (7,7)
Expected output: false

QTC79: Queen moves max spaces backward-left, foe-obstructed path, to foe-occupied position (:X:)
State of the system: from (7,7) to (0,0), !isEmpty at (6,6), foe at (6,6), !isEmpty at (0,0), foe at (0,0)
Expected output: false

QTC80: Queen moves max spaces backward-right, foe-obstructed path, to foe-occupied position (:X:)
State of the system: from (7,0) to (0,7), !isEmpty at (6,1), foe at (6,1), !isEmpty at (0,7), foe at (0,7)
Expected output: false

## Invalid move case 7: max-space, friend-obstructed path, to friend-occupied position

QTC81: Queen moves max spaces forward, friend-obstructed path, to friend-occupied position (:X:)
State of the system: from (0,3) to (7,3), !isEmpty at (1,3), friend at (1,3), !isEmpty at (7,3), friend at (7,3)
Expected output: false

QTC82: Queen moves max spaces backward, friend-obstructed path, to friend-occupied position (:X:)
State of the system: from (7,3) to (0,3), !isEmpty at (6,3), friend at (6,3), !isEmpty at (0,3), friend at (0,3)
Expected output: false

QTC83: Queen moves max spaces left, friend-obstructed path, to friend-occupied position (:X:)
State of the system: from (3,7) to (3,0), !isEmpty at (3,6), friend at (3,6), !isEmpty at (3,0), friend at (3,0)
Expected output: false

QTC84: Queen moves max spaces right, friend-obstructed path, to friend-occupied position (:X:)
State of the system: from (3,0) to (3,7), !isEmpty at (3,1), friend at (3,1), !isEmpty at (3,7), friend at (3,7)
Expected output: false

QTC85: Queen moves max spaces forward-left, friend-obstructed path, to friend-occupied position (:X:)
State of the system: from (0,7) to (7,0), !isEmpty at (1,6), friend at (1,6), !isEmpty at (7,0), friend at (7,0)
Expected output: false

QTC86: Queen moves max spaces forward-right, friend-obstructed path, to friend-occupied position (:X:)
State of the system: from (0,0) to (7,7), !isEmpty at (1,1), friend at (1,1), !isEmpty at (7,7), friend at (7,7)
Expected output: false

QTC87: Queen moves max spaces backward-left, friend-obstructed path, to friend-occupied position (:X:)
State of the system: from (7,7) to (0,0), !isEmpty at (6,6), friend at (6,6), !isEmpty at (0,0), friend at (0,0)
Expected output: false

QTC88: Queen moves max spaces backward-right, friend-obstructed path, to friend-occupied position (:X:)
State of the system: from (7,0) to (0,7), !isEmpty at (6,1), friend at (6,1), !isEmpty at (0,7), friend at (0,7)
Expected output: false

## Invalid move case 8: max-space, foe-obstructed path, to friend-occupied position

QTC89: Queen moves max spaces forward, foe-obstructed path, to friend-occupied position (:X:)
State of the system: from (0,3) to (7,3), !isEmpty at (1,3), foe at (1,3), !isEmpty at (7,3), friend at (7,3)
Expected output: false

QTC90: Queen moves max spaces backward, foe-obstructed path, to friend-occupied position (:X:)
State of the system: from (7,3) to (0,3), !isEmpty at (6,3), foe at (6,3), !isEmpty at (0,3), friend at (0,3)
Expected output: false

QTC91: Queen moves max spaces left, foe-obstructed path, to friend-occupied position (:X:)
State of the system: from (3,7) to (3,0), !isEmpty at (3,6), foe at (3,6), !isEmpty at (3,0), friend at (3,0)
Expected output: false

QTC92: Queen moves max spaces right, foe-obstructed path, to friend-occupied position (:X:)
State of the system: from (3,0) to (3,7), !isEmpty at (3,1), foe at (3,1), !isEmpty at (3,7), friend at (3,7)
Expected output: false

QTC93: Queen moves max spaces forward-left, foe-obstructed path, to friend-occupied position (:X:)
State of the system: from (0,7) to (7,0), !isEmpty at (1,6), foe at (1,6), !isEmpty at (7,0), friend at (7,0)
Expected output: false

QTC94: Queen moves max spaces forward-right, foe-obstructed path, to friend-occupied position (:X:)
State of the system: from (0,0) to (7,7), !isEmpty at (1,1), foe at (1,1), !isEmpty at (7,7), friend at (7,7)
Expected output: false

QTC95: Queen moves max spaces backward-left, foe-obstructed path, to friend-occupied position (:X:)
State of the system: from (7,7) to (0,0), !isEmpty at (6,6), foe at (6,6), !isEmpty at (0,0), friend at (0,0)
Expected output: false

QTC96: Queen moves max spaces backward-right, foe-obstructed path, to friend-occupied position (:X:)
State of the system: from (7,0) to (0,7), !isEmpty at (6,1), foe at (6,1), !isEmpty at (0,7), friend at (0,7)
Expected output: false

## Invalid move case 9: knight L-shape, to empty position

QTC97: Queen moves in forward-left L-shape, to empty position (:X:)
State of the system: from (3,3) to (5,2), isEmpty at (5,2)
Expected output: false

QTC98: Queen moves in forward-right L-shape, to empty position (:X:)
State of the system: from (3,3) to (5,4), isEmpty at (5,4)
Expected output: false

QTC99: Queen moves in right-forward L-shape, to empty position (:X:)
State of the system: from (3,3) to (4,5), isEmpty at (4,5)
Expected output: false

QTC100: Queen moves in right-backward L-shape, to empty position (:X:)
State of the system: from (3,3) to (2,5), isEmpty at (2,5)
Expected output: false

QTC101: Queen moves in backward-right L-shape, to empty position (:X:)
State of the system: from (3,3) to (1,4), isEmpty at (1,4)
Expected output: false

QTC102: Queen moves in backward-left L-shape, to empty position (:X:)
State of the system: from (3,3) to (1,2), isEmpty at (1,2)
Expected output: false

QTC103: Queen moves in left-backward L-shape, to empty position (:X:)
State of the system: from (3,3) to (2,1), isEmpty at (2,1)
Expected output: false

QTC104: Queen moves in left-forward L-shape, to empty position (:X:)
State of the system: from (3,3) to (4,1), isEmpty at (4,1)
Expected output: false

## Invalid move case 10: knight L-shape, to friend-occupied position

QTC105: Queen moves in forward-left L-shape, to friend-occupied position (:X:)
State of the system: from (3,3) to (5,2), !isEmpty at (5,2), friend at (5,2)
Expected output: false

QTC106: Queen moves in forward-right L-shape, to friend-occupied position (:X:)
State of the system: from (3,3) to (5,4), !isEmpty at (5,4), friend at (5,4)
Expected output: false

QTC107: Queen moves in right-forward L-shape, to friend-occupied position (:X:)
State of the system: from (3,3) to (4,5), !isEmpty at (4,5), friend at (4,5)
Expected output: false

QTC108: Queen moves in right-backward L-shape, to friend-occupied position (:X:)
State of the system: from (3,3) to (2,5), !isEmpty at (2,5), friend at (2,5)
Expected output: false

QTC109: Queen moves in backward-right L-shape, to friend-occupied position (:X:)
State of the system: from (3,3) to (1,4), !isEmpty at (1,4), friend at (1,4)
Expected output: false

QTC110: Queen moves in backward-left L-shape, to friend-occupied position (:X:)
State of the system: from (3,3) to (1,2), !isEmpty at (1,2), friend at (1,2)
Expected output: false

QTC111: Queen moves in left-backward L-shape, to friend-occupied position (:X:)
State of the system: from (3,3) to (2,1), !isEmpty at (2,1), friend at (2,1)
Expected output: false

QTC112: Queen moves in left-forward L-shape, to friend-occupied position (:X:)
State of the system: from (3,3) to (4,1), !isEmpty at (4,1), friend at (4,1)
Expected output: false

## Invalid move case 11: knight L-shape, to foe-occupied position

QTC113: Queen moves in forward-left L-shape, to foe-occupied position (:X:)
State of the system: from (3,3) to (5,2), !isEmpty at (5,2), foe at (5,2)
Expected output: false

QTC114: Queen moves in forward-right L-shape, to foe-occupied position (:X:)
State of the system: from (3,3) to (5,4), !isEmpty at (5,4), foe at (5,4)
Expected output: false

QTC115: Queen moves in right-forward L-shape, to foe-occupied position (:X:)
State of the system: from (3,3) to (4,5), !isEmpty at (4,5), foe at (4,5)
Expected output: false

QTC116: Queen moves in right-backward L-shape, to foe-occupied position (:X:)
State of the system: from (3,3) to (2,5), !isEmpty at (2,5), foe at (2,5)
Expected output: false

QTC117: Queen moves in backward-right L-shape, to foe-occupied position (:X:)
State of the system: from (3,3) to (1,4), !isEmpty at (1,4), foe at (1,4)
Expected output: false

QTC118: Queen moves in backward-left L-shape, to foe-occupied position (:X:)
State of the system: from (3,3) to (1,2), !isEmpty at (1,2), foe at (1,2)
Expected output: false

QTC119: Queen moves in left-backward L-shape, to foe-occupied position (:X:)
State of the system: from (3,3) to (2,1), !isEmpty at (2,1), foe at (2,1)
Expected output: false

QTC120: Queen moves in left-forward L-shape, to foe-occupied position (:X:)
State of the system: from (3,3) to (4,1), !isEmpty at (4,1), foe at (4,1)
Expected output: false

## Invalid move case 12: out-of-bounds

QTC121: Queen moves out-of-bounds, top boundary, forward (:X:)
State of the system: from (7,3) to (8,3)
Expected output: false

QTC122: Queen moves out-of-bounds, top boundary, forward-left (:X:)
State of the system: from (7,3) to (8,2)
Expected output: false

QTC123: Queen moves out-of-bounds, top boundary, forward-right (:X:)
State of the system: from (7,3) to (8,4)
Expected output: false

QTC124: Queen moves out-of-bounds, bottom boundary, backward (:X:)
State of the system: from (0,3) to (-1,3)
Expected output: false

QTC125: Queen moves out-of-bounds, bottom boundary, backward-left (:X:)
State of the system: from (0,3) to (-1,2)
Expected output: false

QTC126: Queen moves out-of-bounds, bottom boundary, backward-right (:X:)
State of the system: from (0,3) to (-1,4)
Expected output: false

QTC127: Queen moves out-of-bounds, left boundary, left (:X:)
State of the system: from (3,0) to (3,-1)
Expected output: false

QTC128: Queen moves out-of-bounds, left boundary, forward-left (:X:)
State of the system: from (3,0) to (4,-1)
Expected output: false

QTC129: Queen moves out-of-bounds, left boundary, backward-left (:X:)
State of the system: from (3,0) to (2,-1)
Expected output: false

QTC130: Queen moves out-of-bounds, right boundary, right (:X:)
State of the system: from (3,7) to (3,8)
Expected output: false

QTC131: Queen moves out-of-bounds, right boundary, forward-right (:X:)
State of the system: from (3,7) to (4,8)
Expected output: false

QTC132: Queen moves out-of-bounds, right boundary, backward-right (:X:)
State of the system: from (3,7) to (2,8)
Expected output: false

## Invalid move case 13: no movement

QTC133: Queen moves to its own current square (distance zero) (:X:)
State of the system: from (3,3) to (3,3)
Expected output: false
