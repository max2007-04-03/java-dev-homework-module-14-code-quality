package ua.goit.goodjob;

import java.util.Scanner;
import java.util.logging.Logger;
import java.util.Random;

/**
 * Клас реалізує логіку гри "Хрестики-Нулики".
 * Рефакторинг виконано для усунення зауважень Sonar щодо чистого коду.
 */
public class App {
    private static final Logger logger = Logger.getLogger(App.class.getName());
    private static final char[] box = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static final Scanner scan = new Scanner(System.in);
    private static final Random random = new Random();

    public static void main(String[] args) {
        logger.info("Enter box number to select. Enjoy!\n");
        resetBoard();
        runGameLoop();
    }

    /**
     * Основний цикл гри. Розбиття "Brain Method" на логічні частини.
     */
    private static void runGameLoop() {
        int winner = 0;
        while (winner == 0) {
            displayBoard();
            processPlayerMove();
            winner = checkWinner('X');

            if (winner == 0 && !isBoardFull()) {
                processComputerMove();
                winner = checkWinner('O');
            } else if (winner == 0) {
                winner = 3; // Нічия
            }
        }
        displayBoard();
        showEndGameMessage(winner);
    }

    private static void processPlayerMove() {
        while (true) {
            if (scan.hasNextByte()) {
                byte input = scan.nextByte();
                if (input > 0 && input < 10 && box[input - 1] != 'X' && box[input - 1] != 'O') {
                    box[input - 1] = 'X';
                    break;
                }
            } else {
                scan.next(); // Пропуск некоректного вводу
            }
            logger.warning("Invalid input or already in use. Enter again.");
        }
    }

    private static void processComputerMove() {
        while (true) {
            int rand = random.nextInt(9);
            if (box[rand] != 'X' && box[rand] != 'O') {
                box[rand] = 'O';
                break;
            }
        }
    }

    private static int checkWinner(char player) {
        if (isWinningCombination(player)) {
            return (player == 'X') ? 1 : 2;
        }
        return 0;
    }

    private static boolean isWinningCombination(char p) {
        return (box[0] == p && box[1] == p && box[2] == p) ||
                (box[3] == p && box[4] == p && box[5] == p) ||
                (box[6] == p && box[7] == p && box[8] == p) ||
                (box[0] == p && box[3] == p && box[6] == p) ||
                (box[1] == p && box[4] == p && box[7] == p) ||
                (box[2] == p && box[5] == p && box[8] == p) ||
                (box[0] == p && box[4] == p && box[8] == p) ||
                (box[2] == p && box[4] == p && box[6] == p);
    }

    private static boolean isBoardFull() {
        for (char c : box) {
            if (c != 'X' && c != 'O') return false;
        }
        return true;
    }

    private static void displayBoard() {
        String layout = String.format("%n%n %c | %c | %c %n-----------%n %c | %c | %c %n-----------%n %c | %c | %c %n",
                box[0], box[1], box[2], box[3], box[4], box[5], box[6], box[7], box[8]);
        logger.info(layout);
    }

    private static void resetBoard() {
        for (int i = 0; i < 9; i++) {
            box[i] = ' ';
        }
    }

    private static void showEndGameMessage(int winner) {
        String msg = switch (winner) {
            case 1 -> "You won the game!";
            case 2 -> "You lost the game!";
            default -> "It's a draw!";
        };
        logger.info(msg);
        logger.info("Created by Shreyas Saha. Thanks for playing!");
    }
}