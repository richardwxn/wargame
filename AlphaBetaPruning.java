package wargame;

import java.util.ArrayList;
import java.util.List;

public class AlphaBetaPruning {
	int nodesnumber;
	public Point calculate(String curr_team,String evil_team,Main grid,int depth,Point point,int alpha,int beta){
		nodesnumber++;
//		String curr_team;
	    int max_depth = 5;
	    String	max_team = "blue";
	    String min_team="green";
	    	Main board = new Main(grid);
	    	Point result=new Point(point);
	    	int retval;
	    	if(depth == 0){
	    	        nodesnumber = 1;
	    	       
	    	        if (curr_team.equals(max_team)){
	    	        	   int max=Integer.MIN_VALUE;
	    	        		List<Point> copy=new ArrayList<Point>(board.openplace);
//	    	        		List<Integer> ha=new LinkedList<Integer>();
	    	        		for(Point open:copy){
	    	        			
	    	        			int value=calculate(evil_team, curr_team, board, depth+1, open,alpha,beta).value;
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
	    	        	int min=Integer.MAX_VALUE;
	    	        	List<Point> copy=new ArrayList<Point>(board.openplace);
	    	        	for(Point open:copy){
	    	        			int value=calculate(evil_team, curr_team, board, depth+1, open,alpha,beta).value;
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
//	    	    # Mark the location as captured
//	    	    # loc refers to the square in board, but we want to equivalent square in grid to alter
//	    	    Point temp = board.get(x,y);
	    	    board.moveto(x, y, curr_team);
//	    	    # we treat each square as a Para-Drop, then we check to see if it has any neighbors that belong
//	    	    # to the current team...it is more advantageous to Death Blitz whenever possible
	    	  
	    	    	Point [] neighbours=new Point[4];
	    	    neighbours[0]=board.get(x-1,y);
	    	    neighbours[1]=board.get(x+1,y);
	    	    neighbours[2]=board.get(x,y+1);
	    	    neighbours[3]=board.get(x,y-1);
	    	  
//	    	    neighbours = [grid.square_at((x+1, y)), grid.square_at((x-1,y)), grid.square_at((x,y+1)), grid.square_at((x,y-1))]
	    	    for(Point temp:neighbours){
//	    	        # check for wall
	    	        if(temp==null)
	    	            continue;
//	    	        # check if we have a neighbor on our side (we only need one neighbor)
	    	        else if(temp.occupied&&temp.player.equals(curr_team)){
//	    	            # check the neighbors for ones on the enemy team
	    	            for(Point evil:neighbours){
	    	            	 	if(evil==null)
	    		    	            continue;
	    	                if(evil.occupied&&evil.player.equals(evil_team)){
//	    	                	if(evil.value!=1)
//	    	                		System.out.println("evil"+evil.value);
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
	    	        result.value=retval;
	    	        return result;
	    	    }
	    	    else{
	    	        if(!curr_team.equals(max_team)){
//	    	        		int max=Integer.MIN_VALUE;
	    	        		for(Point open:board.openplace){
	    	        			int value=calculate(evil_team, curr_team, board, depth+1, open,alpha,beta).value;
//	    	        			beta=Math.min(value, beta);
	    	        			if(value<beta){
	    	        				result=new Point(open);
	    	        				beta=value;
	    	        			}
		    	            if(alpha>=beta){
//		    	            	 System.out.print("haha");
		    	            	 break;
		    	            }
	    	        			
	    	        		}
	    	        		
//	    	            retval[1] = loc
	    	        		result.value=beta;
	    	            return result;
	    	        }
	    	        else{
//	    	        	int min=Integer.MAX_VALUE;
	    	        	for(Point open:grid.openplace){
//	    	        		System.out.println("alpha"+alpha);
	    	        		int value=calculate(evil_team, curr_team, board, depth+1, open,alpha,beta).value;
//	    	        		alpha=Math.max(value, alpha);
	    	        		if(value>alpha){
    	        				result=new Point(open);
    	        				alpha=value;
    	        			}
		    	        if(alpha>=beta)
	    	        		break;
	    	        	}
	    	        	
	    	        	result.value=alpha;
	    	            return result;
	    	        }
	    	    }
		
	}
}