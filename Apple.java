import java.util.Random;

public class Apple {
	int i, j;
	public Apple(Random r, int w, int h, Snake snake){
		do {
			this.i = r.nextInt(h);
			this.j = r.nextInt(w);
		}
		while (snakeChecker(snake));
	}

	public boolean snakeChecker(Snake snake) {
		for (int i = 0; i < 4; i++) {
			if (snake.snake[0][0] == i && snake.snake[0][1] == j) {
				return true;
			}
		}
		return false;
	}

	/*public boolean boardChecker(Board board) {
		for (int i = 0; i < board.board.length; i++) {
			if (rowFree(board.board[i]) {
				return 
			}
		}
	}

	public boolean rowFree(char[] row) {
		for (int j = 0; j < row.length; j++) {
		}
	}*/
}
