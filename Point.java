package wargame;

public class Point {
	int x;
	int y;
	double value;
	String player;
	public boolean occupied=false;
	public Point(double value,String player){
		this.value=value;
		this.player=player;	
		
	}
	public Point(int x,int y,double value,String player){
		this.x=x;
		this.y=y;
		this.value=value;
		this.player=player;	
		
	}
	
	public Point(Point copy){
		this.x=copy.x;
		this.y=copy.y;
		this.value=copy.value;
		this.player=copy.player;	
		this.occupied=copy.occupied;
		
	}
//	public Point[] getneighbour(){
//		
//		
//		
//		
//	}
	
	
	@Override
	public boolean equals(Object B){
		if(B==this)
			return true;
		if(!(B instanceof Point))
			return false;
		Point compare=(Point) B;
		if(compare.x==this.x&&compare.y==this.y)
			return true;
		return false;
		
	}
	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + ", value=" + value + ", player="
				+ player + "]";
	}
		
	}
