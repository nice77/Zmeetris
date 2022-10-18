import java.util.Arrays;

public class Board {
	char[][] board;
	int w, h, free = w * h;
	public Board(int w, int h) {
		this.board = new char[h][w];
		this.w = w;
		this.h = h;
		for (int i = 0; i < this.h; i++) {
			for (int j = 0; j < this.w; j++) {
				this.board[i][j] = '.';
			}
		}
	}

	public void draw(Snake snake, Apple apple) {
		System.out.print("\033[2J");
		for (int i = 0; i < this.h; i++) {
			for (int j = 0; j < this.w; j++) {
				if (snakeChecker(snake, i, j)) {
					System.out.print(((snake.isAlive) ? "* " : "# "));
				}
				else if (i == apple.i && j == apple.j && snake.isAlive) {
					System.out.print("P ");
				}
				else {
					System.out.print(this.board[i][j] + " ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	public boolean snakeChecker(Snake snake, int i, int j) {
		for (int k = 0; k < 4; k++) {
			if (snake.snake[k][0] == i && snake.snake[k][1] == j) {
				return true;
			}
		}
		return false;
	}
}
