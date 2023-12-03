import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("♣️ Welcome to The Code Research Lab Casino ♣️");
        Scanner input = new Scanner(System.in);
        String answer;

        do {
            Game game = new Game();
            game.play(input);

            System.out.println("----------------------------------------------");
            System.out.println("Do you want to play again? (y or n)");
            answer = input.nextLine().trim().toLowerCase();

        } while ("y".equals(answer));

        System.out.println("Remember, 99% of gamblers quit before winning big.");
        System.out.println("Exiting...");
        input.close();
    }
}
