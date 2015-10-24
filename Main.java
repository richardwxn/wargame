package wargame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
	public Point [][] grid;
//	0 is for one player, 1is for another
//	public int [] currentscore=new int[2];
	Map<String,Double> currentscore=new HashMap<String,Double>();
	List<Point> openplace=new ArrayList<Point>();
	FileReader fr;
	String filename="/Users/newuser/Downloads/game_boards/Sevastopol.txt";
	int row;
	int col;
	String player="blue";
	public Main() throws IOException{
		
		currentscore.put("blue",0.0);
		currentscore.put("green",0.0);
		List<String> lines=null;
		try{
		 lines= Files.readAllLines(Paths.get(filename), Charset.defaultCharset());
		}catch(FileNotFoundException s){
			s.printStackTrace();
		}
		row=lines.size();
		col=lines.get(0).split("\t").length;
		grid=new Point[row][col];
		
		
		try{
			File file=new File(filename);
			fr=new FileReader(file);
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		BufferedReader br=new BufferedReader(fr);
		String sb;
		int i=0;
		while((sb=br.readLine())!=null){	
			String [] temp=sb.split("\t");
			for(int j=0;j<temp.length;j++){
				grid[i][j]=new Point(i,j,Integer.parseInt(temp[j]),null);
				openplace.add(grid[i][j]);
			}				
			i++;
		}
		
		
	}
	
	public Main(Main copy){
		this.currentscore=new HashMap<String,Double>(copy.currentscore);
		this.openplace=new ArrayList<Point>(copy.openplace);
		this.row=copy.row;
		this.col=copy.col;
		this.grid=new Point[copy.grid.length][copy.grid[0].length];
		for(int i=0;i<copy.grid.length;i++){
			for(int j=0;j<copy.grid[0].length;j++){
//				if(copy.grid[i][j]==null)
//					System.out.println(i+"\t"+j);
				this.grid[i][j]=new Point(copy.grid[i][j]);	
		}
		}
		this.player=copy.player;
	}
	
	
	
	public void moveto(int x,int y,String player){
//		Point point=new Point(x,y,grid[x][y].value,grid[x][y].player);
		
		Point temppoint=this.get(x,y);
		openplace.remove(temppoint);
//		System.out.println(openplace.size());
//		get enemy's
		String enemy=temppoint.player;
		if(temppoint.occupied&&!temppoint.player.equals(player)){
			currentscore.put(player,currentscore.get(player)+temppoint.value);
			currentscore.put(enemy,currentscore.get(enemy)-temppoint.value);
		}
//		get empty place
		else if(!temppoint.occupied){
			currentscore.put(player,currentscore.get(player)+temppoint.value);
		}
		this.grid[x][y].player=player;
		this.grid[x][y].occupied=true;
		
	}
	
	public Point get(int x,int y){
		if(0<=x&&x<grid.length&&0<=y&&y<grid[0].length)
			return grid[x][y];		
		else
			return null;
	}
	
	
	public void drop(int x,int y,String player){
		if(!grid[x][y].occupied)
			this.moveto(x, y,player);
	}
	public void alternateplayer(){
		if(this.player.equals("blue"))
			this.player="green";
		else if(this.player.equals("green"))
			this.player="blue";
		
		
	}
	public boolean bltiz(int x,int y,String player){
		Point temp=this.get(x,y);
		if(temp.occupied)
			return false;
		Point [] neighbours=new Point[4];
		neighbours[0]=this.get(x+1, y);
		neighbours[1]=this.get(x-1, y);
		neighbours[2]=this.get(x, y+1);
		neighbours[3]=this.get(x, y-1);
		boolean canwork=false;
		for(int i=0;i<4;i++){
			if(neighbours[i]==null)
				continue;
			if(neighbours[i].occupied&&neighbours[i].player.equals(player)){
//			if(!neighbours[i].occupied){
				canwork=true;
				break;
			}
		}
		if(!canwork)
			return false;
		this.moveto(x, y, player);
		for(int i=0;i<4;i++){
			if(neighbours[i]==null)
				continue;
			if(neighbours[i].occupied&&!neighbours[i].player.equals(player)){
				this.moveto(neighbours[i].x, neighbours[i].y, player);
//				need to break or not?
//				break;
			}
		}
		return canwork;
	}
	
	public boolean battle(int x,int y,String player){
		Point temp=this.get(x,y);
		if(temp.occupied)
			return false;
		Map<String,Double> map=new HashMap<String,Double>();
		map.put("blue", 0.0);
		map.put("green", 0.0);
		int count1=0;
		int count2=0;
		for(int i=0;i<grid.length;i++){
			for(int j=0;j<grid[0].length;j++){
				if(grid[i][j].player!=null){
					if(grid[i][j].player.equals("blue")){
						count1++;
						map.put("blue", map.get("blue")+grid[i][j].value);
					}
					else {
						count2++;
						map.put("green", map.get("green")+grid[i][j].value);
					}
				}
			}
		}
//		get average
		if(count1!=0)
			map.put("blue", map.get("blue")/count1);
		else 
			map.put("blue",0.0);
		if(count2!=0)
			map.put("green", map.get("green")/count2);
		else
			map.put("green", 0.0);
	
		Point [] neighbours=new Point[4];
		neighbours[0]=this.get(x+1, y);
		neighbours[1]=this.get(x-1, y);
		neighbours[2]=this.get(x, y+1);
		neighbours[3]=this.get(x, y-1);
		boolean canwork=false;
		for(int i=0;i<4;i++){
			if(neighbours[i]==null)
				continue;
			if(neighbours[i].occupied&&neighbours[i].player.equals(player)){
				canwork=true;
				break;
			}
		}
		if(!canwork)
			return false;
		this.moveto(x, y, player);
		String enemy=player.equals("blue")?"green":"blue";
		for(int i=0;i<4;i++){
			if(neighbours[i]==null)
				continue;
			if(neighbours[i].occupied&&!neighbours[i].player.equals(player)){
//				Battle case
//				double bat1=(findpoints(neighbours[i].x,neighbours[i].y,enemy)+1)*map.get(enemy);
//				double bat2=(findpoints(neighbours[i].x,neighbours[i].y,player)+1)*map.get(player);
//              Duel
				double bat1=map.get(enemy);
				double bat2=map.get(player);
				
//              Attrition
				
				
//				which means we blitz
				if(bat1<bat2)
					this.moveto(neighbours[i].x, neighbours[i].y, player);
//				break;
			}
		}
		return canwork;
	}
	
	public void attrition(){
		for(int i=0;i<grid.length;i++){
			for(int j=0;j<grid[0].length;j++){
				grid[i][j].value=(grid[i][j].value*0.9);
		}
		
		}	
	}
	
	public int findpoints(int x,int y,String player){
		int result=0;
//		String player=this.get(x,y).player;
		Point [] neighbours=new Point[4];
		neighbours[0]=this.get(x+1, y);
		neighbours[1]=this.get(x-1, y);
		neighbours[2]=this.get(x, y+1);
		neighbours[3]=this.get(x, y-1);
		for(Point hehe:neighbours){
			if(hehe==null)
				continue;
			else if(hehe.occupied&&hehe.player.equals(player))
				result+=1;
		}
		return result;
	}
	
	public void printboard(){
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<grid.length;i++){
			for(int j=0;j<grid[0].length;j++){
				sb.append(grid[i][j].player);
				sb.append(grid[i][j].value);
			}
			sb.append("\n");
		}
		System.out.println(sb.toString());
	}
	
}
