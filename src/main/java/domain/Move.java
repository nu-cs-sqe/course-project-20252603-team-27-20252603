package domain;

public class Move {
	Location from, to;
	public Move(Location from, Location to){
		this.from=from;
		this.to=to;
	}
	public boolean equal(Move move){
		return move.from==this.from && move.to==this.to;
	}
	public String getNotation(){
		return "String";
	}
}
