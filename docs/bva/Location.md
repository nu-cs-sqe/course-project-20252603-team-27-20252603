### Class under test: Location

## Test area 1: constructor / field storage — getRow()

LTC1: Location stores row correctly, minimum boundary row value ( :white_check_mark: )
State of the system: new Location(0, 4), call getRow()
Expected output: 0

LTC2: Location stores row correctly, maximum boundary row value ( :white_check_mark: )
State of the system: new Location(7, 4), call getRow()
Expected output: 7

## Test area 2: constructor / field storage — getCol()

LTC3: Location stores col correctly, minimum boundary col value ( :white_check_mark: )
State of the system: new Location(4, 0), call getCol()
Expected output: 0

LTC4: Location stores col correctly, maximum boundary col value ( :white_check_mark: )
State of the system: new Location(4, 7), call getCol()
Expected output: 7

## Test area 3: constructor / field storage — row and col stored independently

LTC5: Row and col are stored independently, both at minimum boundary ( :white_check_mark: )
State of the system: new Location(0, 0), call getRow() and getCol()
Expected output: getRow() == 0, getCol() == 0

LTC6: Row and col are stored independently, both at maximum boundary ( :white_check_mark: )
State of the system: new Location(7, 7), call getRow() and getCol()
Expected output: getRow() == 7, getCol() == 7

LTC7: Row and col are stored independently, row at minimum boundary, col at maximum boundary ( :white_check_mark: )
State of the system: new Location(0, 7), call getRow() and getCol()
Expected output: getRow() == 0, getCol() == 7

LTC8: Row and col are stored independently, row at maximum boundary, col at minimum boundary ( :white_check_mark: )
State of the system: new Location(7, 0), call getRow() and getCol()
Expected output: getRow() == 7, getCol() == 0

## Test area 4: equals() — same row, same col (true cases)

LTC9: Two locations with same minimum boundary row and col are equal ( :white_check_mark: )
State of the system: a = new Location(0, 0), b = new Location(0, 0), call a.equals(b)
Expected output: true

LTC10: Two locations with same maximum boundary row and col are equal ( :white_check_mark: )
State of the system: a = new Location(7, 7), b = new Location(7, 7), call a.equals(b)
Expected output: true

LTC11: Two locations with row at minimum boundary and col at maximum boundary are equal ( :white_check_mark: )
State of the system: a = new Location(0, 7), b = new Location(0, 7), call a.equals(b)
Expected output: true

LTC12: Two locations with row at maximum boundary and col at minimum boundary are equal ( :white_check_mark: )
State of the system: a = new Location(7, 0), b = new Location(7, 0), call a.equals(b)
Expected output: true

LTC13: Location equals itself (same reference), minimum boundary values ( :white_check_mark: )
State of the system: a = new Location(0, 0), call a.equals(a)
Expected output: true

LTC14: Location equals itself (same reference), maximum boundary values ( :white_check_mark: )
State of the system: a = new Location(7, 7), call a.equals(a)
Expected output: true

## Test area 5: equals() — different row, same col (false cases)

LTC15: Two locations differ only in row, a.row at minimum boundary, b.row at min + ε ( :white_check_mark: )
State of the system: a = new Location(0, 4), b = new Location(1, 4), call a.equals(b)
Expected output: false

LTC16: Two locations differ only in row, a.row at maximum boundary, b.row at max - ε ( :white_check_mark: )
State of the system: a = new Location(7, 4), b = new Location(6, 4), call a.equals(b)
Expected output: false

## Test area 6: equals() — same row, different col (false cases)

LTC17: Two locations differ only in col, a.col at minimum boundary, b.col at min + ε ( :white_check_mark: )
State of the system: a = new Location(4, 0), b = new Location(4, 1), call a.equals(b)
Expected output: false

LTC18: Two locations differ only in col, a.col at maximum boundary, b.col at max - ε ( :white_check_mark: )
State of the system: a = new Location(4, 7), b = new Location(4, 6), call a.equals(b)
Expected output: false

## Test area 7: equals() — different row and different col (false cases)

LTC19: Two locations differ in both row and col, a at minimum corner, b at maximum corner ( :white_check_mark: )
State of the system: a = new Location(0, 0), b = new Location(7, 7), call a.equals(b)
Expected output: false

LTC20: Two locations differ in both row and col, a at maximum corner, b at minimum corner ( :white_check_mark: )
State of the system: a = new Location(7, 7), b = new Location(0, 0), call a.equals(b)
Expected output: false

LTC21: Two locations differ in both row and col, a at min row/max col, b at max row/min col ( :white_check_mark: )
State of the system: a = new Location(0, 7), b = new Location(7, 0), call a.equals(b)
Expected output: false

LTC22: Two locations differ in both row and col, a at max row/min col, b at min row/max col ( :white_check_mark: )
State of the system: a = new Location(7, 0), b = new Location(0, 7), call a.equals(b)
Expected output: false

## Test area 8: equals() — comparison against null

LTC23: Location compared to null, minimum boundary values ( :white_check_mark: )
State of the system: a = new Location(0, 0), call a.equals(null)
Expected output: false

LTC24: Location compared to null, maximum boundary values ( :white_check_mark: )
State of the system: a = new Location(7, 7), call a.equals(null)
Expected output: false

## Test area 9: equals() — symmetry (a.equals(b) == b.equals(a))

LTC25: equals() is symmetric when locations are equal, both at minimum boundary ( :white_check_mark: )
State of the system: a = new Location(0, 0), b = new Location(0, 0), call b.equals(a)
Expected output: true

LTC26: equals() is symmetric when locations are equal, both at maximum boundary ( :white_check_mark: )
State of the system: a = new Location(7, 7), b = new Location(7, 7), call b.equals(a)
Expected output: true

LTC27: equals() is symmetric when locations differ in row only, rows at min and min + ε ( :white_check_mark: )
State of the system: a = new Location(0, 4), b = new Location(1, 4), call b.equals(a)
Expected output: false

LTC28: equals() is symmetric when locations differ in col only, cols at max and max - ε ( :white_check_mark: )
State of the system: a = new Location(4, 7), b = new Location(4, 6), call b.equals(a)
Expected output: false