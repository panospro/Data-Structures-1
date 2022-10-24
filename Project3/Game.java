public class Game {
	static int round;
	static int gameIsOver=0; 
	

	public Game() {
		round = 0;
	}
	
	public Game(int r) {
		round = r;
	}
	
	public static int getRound() {
		return round;
	}
	
	//All we messed up from the sample solution is changing the two Player class players to 1 MinMaxPlayer and 1 Player
	//Also in the for that it had to print player data for each round we simply changed it and wrote the same data to print
	//just twice so it can get the values of both players who are from different class
	public static void main(String args[]) {

		int N = 15;
		int n = 100;
		int numsup = 4;
		//Game game = new Game(1);
		String winnerName = null;
		Board board = new Board(N, numsup, (N * N * 3 + 1) / 2 ); //, (N*N +1)/4
		board.createBoard();
		Player[] players = new Player[1];
		MinMaxPlayer[] minMaxPlayers=new MinMaxPlayer[1];
		minMaxPlayers[0] = new MinMaxPlayer(0, "Theseus", board, 0, 0, 0);
		players[0] = new Player(1, "Minotaur", board, 0, N/2, N/2);
		int[] currentPosition = new int[players.length+minMaxPlayers.length];
		int newPosition = 0;
		//int moves=0;
		currentPosition[0] = 0;
		currentPosition[1] = N * N / 2;
		String[][]x = board.getStringRepresentation(currentPosition[0], currentPosition[1]);
		for(int i = 0; i< 2*N + 1;i++) {
			for(int j=0; j< N;j++) {
				if(j == N - 1) {
					System.out.println(x[i][j]);
				}else {
					System.out.print(x[i][j]);
				}
			}
		}
		System.out.println();
		System.out.println("*********** The game begins **********");
		System.out.println();
		Game.round = 0;
		boolean minFlag = false;
		boolean thFlag = false;
		
		for (;;) {
			Game.round++;
			System.out.println("********************************** Round " + Game.round + " **********************************");
		
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!! Player: " + minMaxPlayers[0].getName() + " !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			int dice = 0;
			
			
			newPosition=minMaxPlayers[0].getNextMove(currentPosition[0],currentPosition[1], players[0])[0];

			for(int i=0;i<board.getSupplies().length;i++) {
				if(newPosition==board.getSupplies()[i].getSupplyTileId()) {
					board.getSupplies()[i].setSupplyTileId(-1);
					board.getSupplies()[i].setX(-1);
					board.getSupplies()[i].setY(-1);
					
					minMaxPlayers[0].setScore(minMaxPlayers[0].getScore()+1);
				}	
			}
			if (minMaxPlayers[0].getScore() == numsup) {
				winnerName=minMaxPlayers[0].getName() ;
				thFlag= true;
				break;
			} 
			
			
			if(newPosition == currentPosition[1]) {
				winnerName=players[0].getName() ;
				minFlag= true;
				break;
			}
				
			
			//minMaxPlayers[0].statistics();
			System.out.println("Player: " + minMaxPlayers[0].getName() + " Current Position:" + currentPosition[0] + "   New Position:" + newPosition	+ " Player Score:" + minMaxPlayers[0].getScore());
				
			
			currentPosition[0] = newPosition;
			minMaxPlayers[0].setX(newPosition/N);
			minMaxPlayers[0].setY(newPosition%N);	
			
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!! Player: " + players[0].getName() + " !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			dice = 0;
			do {
				dice = (int) (Math.random()*8);
			}while(dice % 2 != 1);
			newPosition = players[0].move(currentPosition[1], 1, dice)[0];
			 
			
			if(newPosition == currentPosition[0]) {
				winnerName=players[0].getName() ;
				minFlag= true;
				break;
			}
			
			System.out.println("Player: " + players[0].getName() + " Current Position: " + currentPosition[1] + "   New Position:" + newPosition);

			
			currentPosition[1] = newPosition;
			players[0].setX(newPosition/N);
			players[0].setY(newPosition%N);
			
			String[][]x2 = board.getStringRepresentation(currentPosition[0], currentPosition[1]);
			for(int ii = 0; ii< 2*N + 1;ii++) {
				for(int j=0; j< N;j++) {
					if(j == N - 1) {
						System.out.println(x2[ii][j]);
					}else {
						System.out.print(x2[ii][j]);
					}
				}
			}
	
			if (Game.round >= 2*n || minFlag || thFlag) {
				break;
			}
			
		}
		
		Game.gameIsOver=1;
	//	minMaxPlayers[0].statistics();
		System.out.println();
		System.out.println("*********** The game is over *********");
		System.out.println();
		System.out.println("Rounds played: "+Game.round);
		if(Game.round >= 2*n) {
			System.out.println("The game is a tie!!!");
		}else {
			System.out.println(winnerName +" won the game!!!");
		}
	}
}

