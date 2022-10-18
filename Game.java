import java.util.Scanner;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Game {
	public static void main(String[] args) {
		//initialize
		Scanner sc = new Scanner(System.in);
		Random r = new Random();
		final int WIDTH = Integer.parseInt(sc.nextLine());
		final int HEIGHT = Integer.parseInt(sc.nextLine());
		Snake snake = new Snake(r.nextInt(WIDTH), r.nextInt(HEIGHT - 4));
		Board board = new Board(WIDTH, HEIGHT);
		Apple apple = new Apple(r, WIDTH, HEIGHT, snake);
		boolean isRunning = true;

		//game cycle
		while (isRunning) {
			//draw
			board.draw(snake, apple);
			//step <- we're here!
			if (snake.isAlive) {
				snake.move(sc, WIDTH, HEIGHT);
				snake.ate(apple);
			}
			//check if we'he eaten the apple
			else {
				snake.fall(HEIGHT);
				if (!snake.notOnGround(HEIGHT)) {
					newInit()
				}
			}
			//checks
			//FPS
			try {
				TimeUnit.MILLISECONDS.sleep(500);
			}
			catch (Exception e) {
				System.out.println();
				break;
			}
		}
	}
}
