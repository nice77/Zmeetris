import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;
public class Game {
	public static void main(String[] args) {
		//-----init-----
		Random r = new Random();
		Scanner sc = new Scanner(System.in);
		final int WIDTH = 30, HEIGHT = 30;
		int[][] field = new int[HEIGHT][WIDTH];
		final int LEN = 4;
		int points = 0;
		int noSpawnPoint;	// В ней хранится значение, относительно которого нельзя спавнить хвост змеи
		int[][] snake = new int[LEN][2];	// Хранятся значения в формате [[y0, x0], [y1, x1], [y2, x2], [y3, x3]]
		int[] pointPlace = new int[2];	// Хранится точка с целью змейки
		String data;
		boolean toDraw = true;
		boolean skipper = false;
		boolean strLine = true;
		int[][] bufferField = new int[HEIGHT][WIDTH];
		int[] strLines = new int[0];

		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				field[i][j] = 0;
			}
			bufferField[i] = Arrays.copyOf(field[i], WIDTH);
		}

		snakeCreator(snake, pointPlace, r, HEIGHT, field);

		//-----game cycle-----
		while (true) {
			System.out.println("  mmmm  m    m mmmmmm mmmmmmmmmmmmm mmmmm  m   mm   mmm ");
			System.out.println(" '   '# ##  ## #      #        #    #   '# #  m'# m'   ");
			System.out.println("   mmm' # ## # #mmmmm #mmmmm   #    #mmm#' # m# # #     ");
			System.out.println("     '# # '' # #      #        #    #      # #  # #     ");
			System.out.println(" 'mmm#' #    # #mmmmm #mmmmm   #    #      ##   #  'mmm");
			System.out.println();
			//-----Drawing the gameboard-----
			for (int i = 0; i < HEIGHT; i++) {
				for (int j = 0; j < WIDTH; j++) {
					for (int k = 0; k < LEN; k++) {
						if (i == snake[k][0] && j == snake[k][1]) {
							System.out.print("\u001B[32m" + (k + 1) + " \u001B[0m");
							toDraw = false;
						}
					}
					if (toDraw) {
						if (pointPlace[0] == i && pointPlace[1] == j) {
							System.out.print("P ");
						}
						else {
							System.out.print((field[i][j] == 0) ? "~ " : "# ");
						}
					}
					toDraw = true;
				}
				System.out.println();
			}
			//-----Check if we ate the point or not-----
			for (int[] elem : snake) {
				if (elem[0] == pointPlace[0] && elem[1] == pointPlace[1]) {
					skipper = true;
				}
			}
			//-----Reading user input-----
			data = ((skipper) ? "n" : sc.nextLine());
			if ((data.equals("w") || data.equals("a") || data.equals("d") || data.equals("s")) && !skipper) {
				switch (data) {
					case ("w"):
						if (!(snake[0][0] > snake[1][0]) || (snake[0][0] == HEIGHT - 1)) {
							mover(snake);
							snake[0][0] = ((snake[0][0] - 1 < 0) ? HEIGHT - 1 : snake[0][0] - 1);
						}
						break;
					case ("s"):
						if (!(snake[0][0] < snake[1][0]) || snake[0][0] == 0) {
							mover(snake);
							snake[0][0] = (snake[0][0] + 1) % HEIGHT;
						}
						break;
					case ("d"):
						if (snake[0][1] > snake[1][1] || (snake[0][1] == snake[1][1]) || snake[0][1] == 0) {
							mover(snake);
							snake[0][1] = (snake[0][1] + 1) % WIDTH;
						}
						break;
					case ("a"):
						if (snake[0][1] < snake[1][1] || (snake[0][1] == snake[1][1]) || snake[0][1] == WIDTH - 1) {
							mover(snake);
							snake[0][1] = ((snake[0][1] - 1 < 0) ? WIDTH - 1 : snake[0][1] - 1);
						}
						break;
				}
			}
			//-----Placing all "dead" snakes on the "ground"-----
			else if (skipper) {
				while (true) {
					for (int i = 0; i < snake.length; i++) {
						snake[i][0] += 1;
						if (snake[i][0] == HEIGHT - 1 || (snake[i][0] + 1 < HEIGHT && field[snake[i][0] + 1][snake[i][1]] == 2)) {
							skipper = false;
						}
					}
					if (!skipper) {
						break;
					}
				}
				if (!skipper) {
					for (int j = 0; j < snake.length; j++) {
						field[snake[j][0]][snake[j][1]] = 2;
					}
					snakeCreator(snake, pointPlace, r, HEIGHT, field);
				}
			}

			//-----Checking if we have straight line dead snakes or not-----
			for (int i = 0; i < HEIGHT; i++) {
				for (int j = 0; j < WIDTH; j++) {
					if (field[i][j] != 2) {
						strLine = false;
						break;
					}
				}
				if (strLine) {
					addElem(strLines, i);
				}
				strLine = true;
			}
			int i = HEIGHT - 1;
			int k = HEIGHT - 1;
			boolean cont = false;
			while (i >= 0) {
				for (int j = 0; j < strLines.length; j++) {
					if (strLines[j] == i) {
						cont = true;
					}
				}
				if (!(cont)) {
					bufferField[k] = Arrays.copyOf(field[i], WIDTH);
					k--;
				}
				i--;
			}
			for (i = HEIGHT - 1; i >= 0; i--) {
				field[i] = Arrays.copyOf(bufferField[i], WIDTH);
				for (int j = 0; j < WIDTH; j++) {
					bufferField[i][j] = 0;
				}
			}
			strLines = resetter();
			strLine = true;
			System.out.println("\033[2J");
		}
	}

	public static void mover(int[][] snake) {
		for (int i = (snake.length - 1); i > 0; i--) {
			snake[i] = Arrays.copyOf(snake[i - 1], 2);
		}
	}

	public static void snakeCreator(int[][] snake, int[] pointPlace, Random r, int height, int[][] field) {
		pointPlace[0] = r.nextInt(30);
		pointPlace[1] =  r.nextInt(30);
		snake[0][0] = r.nextInt(30);
		snake[0][1] = r.nextInt(30);
		while (snake[0][0] == pointPlace[0] && snake[0][1] == pointPlace[1] && field[snake[0][0]][snake[0][1]] != 2) {
			snake[0][0] = r.nextInt(30);
			snake[0][1] = r.nextInt(30);
		}
		for (int i = 0; i < 4; i++) {
			snake[i][0] = (snake[0][0] + i) % height;
			snake[i][1] = snake[0][1];
		}
	}

	public static int[] addElem(int[] arr, int elem) {
		arr = Arrays.copyOf(arr, arr.length + 1);
		arr[arr.length - 1] = elem;
		return arr;
	}

	public static int[] resetter() {
		return new int[0];
	}
}


