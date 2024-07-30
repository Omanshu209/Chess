import java.util.Scanner;

class chess
{
	public char[][] board = {
		{'r', 'k', 'b', 'q', 'k', 'b', 'k', 'r'}, 
		{'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'}, 
		{'.', '.', '.', '.', '.', '.', '.', '.'}, 
		{'.', '.', '.', '.', '.', '.', '.', '.'}, 
		{'.', '.', '.', '.', '.', '.', '.', '.'}, 
		{'.', '.', '.', '.', '.', '.', '.', '.'}, 
		{'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'}, 
		{'R', 'K', 'B', 'Q', 'K', 'B', 'K', 'R'}
	};
	
	private boolean white = true;
	
	public void printBoard()
	{
		for(int i = 0 ; i < 8 ; i++)
		{
			for(int j = 0 ; j < 8 ; j++)
				System.out.print(this.board[i][j] + " ");
			System.out.println();
		}
	}
	
	private static int[] convertPos(String pos)
	{
		int[] newPos = {
			7 - ((int)pos.charAt(1) - 49), 
			(int)(pos.charAt(0) - 97)
		};
		
		return newPos;
	}
	
	public boolean hasPiece(String pos)
	{
		int[] newPos = this.convertPos(pos);
		return this.board[newPos[0]][newPos[1]] != '.';
	}
	
	public void move(String pos1, String pos2)
	{
		if(pos1.equals(pos2))
			return;
		
		int[] initPos = this.convertPos(pos1);
		int[] newPos = this.convertPos(pos2);
		
		if(white && !Character.isLowerCase(this.board[newPos[0]][newPos[1]]))
			return;
		else if((!white) && Character.isLowerCase(this.board[newPos[0]][newPos[1]]))
			return;
		
		char piece = Character.toLowerCase(this.board[initPos[0]][initPos[1]]);
		boolean isValid = false;
		
		switch(piece)
		{
			case 'p':
				isValid = this.isValidPawnMove(pos1, pos2);
				break;
			
			case 'r':
				isValid = this.isValidRookMove(pos1, pos2);
				break;
		}
		
		if(isValid)
		{
			this.board[newPos[0]][newPos[1]] = this.board[initPos[0]][initPos[1]];
			this.board[initPos[0]][initPos[1]] = '.';
			white = !white;
		}
	}
	
	private boolean isValidPawnMove(String pos1, String pos2)
	{
		int[] initPos = this.convertPos(pos1);
		int[] newPos = this.convertPos(pos2);
		
		if(Math.abs(initPos[1] - newPos[1]) > 1)
			return false;
		
		else if(white && this.board[initPos[0]][initPos[1]] != 'P')
			return false;
		
		else if(Math.abs(initPos[1] - newPos[1]) == 0 && this.board[newPos[0]][newPos[1]] != '.')
			return false;
		
		else if(!white && this.board[initPos[0]][initPos[1]] != 'p')
			return false;
		
		else if(Math.abs(initPos[1] - newPos[1]) == 1 && this.board[newPos[0]][newPos[1]] == '.')
			return false;
		
		else if(this.white && (newPos[0] - initPos[0]) == -1)
			return true;
		
		else if(!this.white && (newPos[0] - initPos[0]) == 1)
			return true;
		
		return false;
	}
	
	private boolean isValidRookMove(String pos1, String pos2)
	{
		int[] initPos = this.convertPos(pos1);
		int[] newPos = this.convertPos(pos2);
		
		if((initPos[0] - newPos[0]) != 0 && (initPos[1] - newPos[1]) != 0)
			return false;
		
		int direction = 0;
		if((initPos[1] - newPos[1]) != 0)
			direction = 1;
		
		int start = Math.min(initPos[direction], newPos[direction]) + 1, 
		end = Math.max(initPos[direction], newPos[direction]) - 1;
		
		for(int i = start ; i <= end ; i++)
		{
			if(direction == 0)
			{
				if(this.board[i][initPos[1]] != '.')
					return false;
			}
			
			else
			{
				if(this.board[initPos[0]][i] != '.')
					return false;
			}
		}
		
		return true;
	}
}

public class Main
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		chess game = new chess();
		
		while(true)
		{
			game.printBoard();
			System.out.print("INITIAL POS : ");
			String initPos = sc.next();
			System.out.print("FINAL POS : ");
			String newPos = sc.next();
			game.move(initPos, newPos);
		}
	}
}
