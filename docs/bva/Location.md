# BVA Analysis for Location

## Method under test: Location(int row, int col)

- **TC1: LocationIntConstructorStoresMinMin** (  )
  - **State of the system:** row = 0, col = 0
  - **Expected output:** getRow() = 0, getCol() = 0
  - **Implemented:** yes

- **TC2: LocationIntConstructorStoresMaxMax** (  )
  - **State of the system:** row = 7, col = 7
  - **Expected output:** getRow() = 7, getCol() = 7
  - **Implemented:** yes

## Methods under test: getRow(), getCol()

- **Status:** Implemented and covered by TC1 and TC2

## Method under test: equals(other: Location)

- **TC3: EqualsSameCoordinatesReturnsTrue** (  )
  - **State of the system:** a = Location(3, 5), b = Location(3, 5)
  - **Expected output:** a.equals(b) = true
  - **Implemented:** yes

- **TC4: EqualsDifferentRowReturnsFalse** (  )
  - **State of the system:** a = Location(3, 5), b = Location(4, 5)
  - **Expected output:** a.equals(b) = false
  - **Implemented:** yes

- **TC5: EqualsDifferentColReturnsFalse** (  )
  - **State of the system:** a = Location(3, 5), b = Location(3, 4)
  - **Expected output:** a.equals(b) = false
  - **Implemented:** yes

- **TC6: EqualsNullReturnsFalse** (  )
  - **State of the system:** a = Location(3, 5), other = null
  - **Expected output:** a.equals(null) = false
  - **Implemented:** yes

- **TC7: EqualsDifferentTypeReturnsFalse** (  )
  - **State of the system:** a = Location(3, 5), other = "3,5"
  - **Expected output:** a.equals(other) = false
  - **Implemented:** yes

## Method under test: hashCode()

- **TC8: HashCodeEqualLocationsMatch** (  )
  - **State of the system:** a = Location(3, 5), b = Location(3, 5)
  - **Expected output:** a.hashCode() = b.hashCode()
  - **Implemented:** not yet
