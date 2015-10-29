package wargame;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MinMax {
	int nodesnumber1=0;
	int nodesnumber2=0;
	public Point calculate(String curr_team,String evil_team,Main grid,int depth,Point point){
		if(curr_team.equals("blue"))
			nodesnumber1++;
		else if(curr_team.equals("green"))
			nodesnumber2++;
//		String curr_team;
	    int max_depth = 4;
	    String	max_team = "blue";
	    String min_team="green";
	    	Main board = new Main(grid);
	    	Point result=new Point(point);
	    	double retval;
	    	if(depth == 0){
//	    	        nodesnumber = 1;
	    	       
	    	        if (curr_team.equals(max_team)){
	    	        	   double max=Integer.MIN_VALUE;
	    	        		List<Point> copy=new ArrayList<Point>(board.openplace);
//	    	        		List<Integer> ha=new LinkedList<Integer>();
	    	        		for(Point open:copy){
	    	        			
	    	        			double value=calculate(evil_team, curr_team, board, depth+1, open).value;
	    	        			if(value>max){
	    	        				result=new Point(open);
	    	        				max=value;
	    	        			}
//	    	        			int tempint=calculate(curr_team, evil_team, board, depth+1, open).value;
////	    	        			ha.add(tempint);
//	    	        			max=Math.max(tempint,max);
	    	        			
	    	        		}
//	    	        		System.out.println("first recursion\t"+max);
//	    	        		System.out.print(ha);
	    	        		result.value=max;
	    	        		return result;
	    	        }
	    	        else{    	        	
	    	        	double min=Integer.MAX_VALUE;
	    	        	List<Point> copy=new ArrayList<Point>(board.openplace);
	    	        	for(Point open:copy){
	    	        			double value=calculate(evil_team, curr_team, board, depth+1, open).value;
	    	        			if(value<min){
	    	        				result=new Point(open);
	    	        				min=value;
	    	        			}
//	    	        			min = Math.min(calculate(curr_team, evil_team, board, depth+1, open).value,min);
	    	        	}
	    	        		result.value=min;
	    	        		return result;
	    	        }
	    	}
	    	  	int x = point.x;
	    	    int y = point.y;
	    	    board.moveto(x, y, curr_team);
	    	    
	    	    	Point [] neighbours=new Point[4];
	    	    neighbours[0]=board.get(x-1,y);
	    	    neighbours[1]=board.get(x+1,y);
	    	    neighbours[2]=board.get(x,y+1);
	    	    neighbours[3]=board.get(x,y-1);
	    	  
	    	    for(Point temp:neighbours){
	    	        if(temp==null)
	    	            continue;
	    	        else if(temp.occupied&&temp.player.equals(curr_team)){
//	    	            # check the neighbors for ones on the enemy team
	    	            for(Point evil:neighbours){
	    	            	 	if(evil==null)
	    		    	            continue;
	    	                if(evil.occupied&&evil.player.equals(evil_team)){
	    	                		
	    	                	
	    	                    if(curr_team.equals(max_team)){
//	    	                    		
	    	                    		board.currentscore.put(max_team,board.currentscore.get(max_team)+evil.value);
	    	                    		board.currentscore.put(min_team,board.currentscore.get(min_team)-evil.value);
	    	                    }
	    	                    else{
//	    	                    	System.out.println("evil"+evil.value);
	    	                    	board.currentscore.put(max_team,board.currentscore.get(max_team)-evil.value);
    	                    		board.currentscore.put(min_team,board.currentscore.get(min_team)+evil.value);
	    	                    }
//	    	                    System.out.println(evil.player+"\tcpnta"+curr_team);
	    	                 evil.player = curr_team;
	    	                 board.grid[evil.x][evil.y].player=curr_team;
	    	                }
	    	            }
	    	            break;
	    	    }
	    	    }   
	    	    
	    	    
	    	    if(depth >= max_depth||board.openplace.size() == 0){
	    	        retval=board.currentscore.get(max_team)-board.currentscore.get(min_team);
//	    	        System.out.println("max_team"+board.currentscore.get(max_team));
//	    	        System.out.println("min_team"+board.currentscore.get(min_team));
	    	        result.value=retval;
//	    	        System.out.println("returnla");
	    	        return result;
	    	    }
	    	    else{
	    	        if(!curr_team.equals(max_team)){
	    	        		double min=Integer.MAX_VALUE;
	    	        		List<Point> copy=new ArrayList<Point>(board.openplace);
	    	        		for(Point open:copy){
//		    	            min=Math.min(calculate(evil_team, curr_team, board, depth+1, open).value,min);
	    	        		double value=calculate(evil_team, curr_team, board, depth+1, open).value;
    	        			if(value<min){
    	        				result=new Point(open);
    	        				min=value;
    	        			}
	    	        		}
	    	        		result.value=min;
	    	            return result;
	    	        }
	    	        else{
	    	        	double max=Integer.MIN_VALUE;
	    	        	List<Point> copy=new ArrayList<Point>(board.openplace);
	    	        	for(Point open:copy){
//	    	            max = Math.max(calculate(evil_team, curr_team, board, depth+1, open).value,max);
	    	        		double value=calculate(evil_team, curr_team, board, depth+1, open).value;
    	        			if(value>max){
    	        				result=new Point(open);
    	        				max=value;
    	        			}
	    	        }
	    	        		result.value=max;
	    	            return result;
	    	    }
	}
	}
	
//	public int getnodesnumber(){
//		return nodesnumber;
//	}
}
