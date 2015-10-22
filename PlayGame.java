package wargame;

import java.io.IOException;

public class PlayGame {
	public static void main(String[] args) throws IOException{
		
		Main board=new Main();
		MinMax minmax=new MinMax();
		AlphaBetaPruning abpruning=new AlphaBetaPruning();
		String curr_team;
//		String evil_team="green";
		int depth;
		while(board.openplace.size()!=0){
			 System.out.println(board.openplace.size());
			  curr_team=board.player;
			  depth = 4;
//			  System.out.println(board.player);
		       Point point=new Point(1,curr_team);
		       Main backup=new Main(board);
			if(curr_team.equals("blue")){
//				Point result=abpruning.calculate("blue", "green", board, 0, point,Integer.MIN_VALUE,Integer.MAX_VALUE);
		    		Point result=minmax.calculate("blue", "green", board, 0, point);
		    		System.out.println("first"+result);
		    		if(board.battle(result.x,result.y,curr_team)){
		    			board.grid[result.x][result.y].player=curr_team;
		    			System.out.println("bltiz"+board.player);
		    		}
		        else{
		        		board.drop(result.x,result.y,curr_team);
		        }

		        board.alternateplayer();
		        System.out.println("hehe"+board.currentscore.get("blue"));
				System.out.println(board.currentscore.get("green"));
		        
		}
		    else{
//		     	Point result=abpruning.calculate("green", "blue", board, 0, point,Integer.MIN_VALUE,Integer.MAX_VALUE);
		    		Point result=minmax.calculate("green", "blue", board, 0, point);
		    		System.out.println("Second"+result);
//		        move = calculate_abprune('green', 'blue', board)
		    		if(board.battle(result.x,result.y,curr_team)){
		    			 System.out.println("bltiz"+board.player);
		    		}
		    		else{
	        		board.drop(result.x,result.y,curr_team);
		    		}
		        board.alternateplayer();
		        System.out.println("hehe"+board.currentscore.get("blue"));
				System.out.println(board.currentscore.get("green"));
			}
			
		}
		System.out.println("hehe"+board.currentscore.get("blue"));
		System.out.println(board.currentscore.get("green"));
		board.printboard();
	}
}
