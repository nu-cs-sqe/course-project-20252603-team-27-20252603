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
		Move other = (Move) obj;
		return Objects.equals(this.from, other.from)
				&& Objects.equals(this.to, other.to);
	}
	public String getNotation(){
		return "String";
	}
}
