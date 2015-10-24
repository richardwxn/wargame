package wargame;

import java.io.IOException;

public class PlayGame {
	public static void main(String[] args) throws IOException{
		
		Main board=new Main();
		MinMax minmax=new MinMax();
		AlphaBetaPruning abpruning=new AlphaBetaPruning();
		String curr_team;
		long time1=0;
		long time2=0;
//		String evil_team="green";
		
		int [] moves=new int[]{0,0};
		while(board.openplace.size()!=0){
//			 System.out.println(board.openplace.size());
			  curr_team=board.player;
//			  System.out.println(board.player);
		       Point point=new Point(1,curr_team);
//		       Main backup=new Main(board);
			if(curr_team.equals("blue")){
//				Point result=abpruning.calculate("blue", "green", board, 0, point,Integer.MIN_VALUE,Integer.MAX_VALUE);
				long startTime = System.currentTimeMillis();
				Point result=minmax.calculate("blue", "green", board, 0, point);
		    		long endTime=System.currentTimeMillis();
		    		
		    		time1=(moves[0]*time1+(endTime-startTime))/(moves[0]+1);
		    		moves[0]++;
//		    		nodesnumber[0]+=minmax.getnodesnumber();
//		    		System.out.println("first"+result);
		    		if(board.battle(result.x,result.y,curr_team)){
		    		}
		        else{
		        		board.drop(result.x,result.y,curr_team);
		        }
		    		board.attrition();
		        board.alternateplayer();
//		        System.out.println("hehe"+board.currentscore.get("blue"));
//				System.out.println(board.currentscore.get("green"));
		        
		}
		    else{
		    		long startTime = System.currentTimeMillis();
//		     	Point result=abpruning.calculate("green", "blue", board, 0, point,Integer.MIN_VALUE,Integer.MAX_VALUE);
		    		Point result=minmax.calculate("green", "blue", board, 0, point);
		    		
		    		long endTime=System.currentTimeMillis();
		    		
		    		time2=(moves[1]*time1+(endTime-startTime))/(moves[1]+1);
		    		moves[1]++;
//		    		nodesnumber[1]+=minmax.getnodesnumber();
//		    		System.out.println("Second"+result);
//		        move = calculate_abprune('green', 'blue', board)
		    		if(board.battle(result.x,result.y,curr_team)){
		    		}
		    		else{
	        		board.drop(result.x,result.y,curr_team);
		    		}
		    		board.attrition();
		        board.alternateplayer();
//		        System.out.println("hehe"+board.currentscore.get("blue"));
//				System.out.println(board.currentscore.get("green"));
			}
			
		}
		System.out.println("hehe"+board.currentscore.get("blue"));
		System.out.println(board.currentscore.get("green"));
		System.out.println(minmax.nodesnumber1);
		System.out.println(minmax.nodesnumber2);
		System.out.println(minmax.nodesnumber1/moves[0]);
		System.out.println(minmax.nodesnumber2/moves[1]);
		System.out.println(time1);
		System.out.println(time2);
		board.printboard();
	}
}
