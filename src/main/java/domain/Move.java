package domain;

import java.util.Objects;

public class Move {
	Location from, to;
	public Move(Location from, Location to){
		this.from=from;
		this.to=to;
	}
	@Override
	public boolean equals(Object obj){
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Move queen = (Move) obj;
		return queen.from==this.from && queen.to==this.to;
	}
	public String getNotation(){
		return "String";
	}

	@Override
	public int hashCode() {
		return Objects.hash(from, to);
	}
}
