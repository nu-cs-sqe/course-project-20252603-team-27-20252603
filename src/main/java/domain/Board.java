package domain;

public class Board {
	public void initBoard() {
	}
	public Piece getPiece(Location location) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	public void movePiece(Location from, Location to){}
	public Location findKing(Color color){
		throw new UnsupportedOperationException("Not implemented yet");
	}
	public String toPositionString(){
		return "position";
	}
}
