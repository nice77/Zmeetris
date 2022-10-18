import java.util.Arrays;
import java.util.Scanner;

public class Snake {
	int[][] snake = new int[4][2];
	char cmd = 'n';
	boolean isAlive = true;

	public Snake(int j, int i) {
		for (int k = 0; k < 4; k++) {
			this.snake[k][0] = i + k;
			this.snake[k][1] = j;
		}
	}

	public void move(Scanner sc, int w, int h) {
		reader(sc);
		switch (this.cmd) {
			case ('w'):
				if (this.snake[0][0] - 1 >= 0 && this.snake[0][0] - 1 != this.snake[1][0]) {
					snakeBodyMover();
					this.snake[0][0] -= 1;
				}
				break;
				// <Добавить проверку на неверный ход>
			case ('a'):
				if (this.snake[0][1] - 1 >= 0 && this.snake[0][1] - 1 != this.snake[1][1]) {
					snakeBodyMover();
					this.snake[0][1] -= 1;
				}
				break;
			case ('s'):
				if (this.snake[0][0] + 1 < h && this.snake[0][0] + 1 != this.snake[1][0]) {
					snakeBodyMover();
					this.snake[0][0] += 1;
				}
				break;
			case ('d'):
				if (this.snake[0][1] + 1 < w && this.snake[0][1] + 1 != this.snake[1][1]) {
					snakeBodyMover();
					this.snake[0][1] += 1;
				}
				break;
		}
	}

	private void reader(Scanner sc) {
		try {
			if (System.in.available() != 0) {
				do {
					this.cmd = sc.nextLine().charAt(0);
				}
				while (!("wasdn".contains(this.cmd + "")));
			}
		}
		catch (Exception e) {
			System.out.println();
		}
	}

	private void snakeBodyMover() {
		for (int i = snake.length - 1; i > 0; i--) {
			this.snake[i] = Arrays.copyOf(this.snake[i - 1], 2);
		}
	}

	public void ate(Apple apple) {
		for (int i = 0; i < 4; i++) {
			if (apple.i == this.snake[i][0] && apple.j == this.snake[i][1]) {
				this.isAlive = false;
			}
		}
	}

	public void fall(int h, Board board) {
		if (notOnGround(h, board)) {
			for (int i = 0; i < 4; i++) {
				snake[i][0] += 1;
			}
		}
	}

	public boolean notOnGround(int h, Board board) {
		for (int i = 0; i < 4; i++) {
			if (snake[i][0] == h - 1 || board.board[snake[i][0] + 1][snake[i][1]] == '#') {
				return false;
			}
		}
		return true;
	}

	public void boardTransform(Board board) {
		for (int i = 0; i < 4; i++) {
			board.board[this.snake[i][0]][this.snake[i][1]] = '#';
		}
	}
}
