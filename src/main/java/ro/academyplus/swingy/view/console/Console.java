package ro.academyplus.swingy.view.console;

import ro.academyplus.swingy.controller.CharacterController;
import ro.academyplus.swingy.controller.GamePlayController;
import ro.academyplus.swingy.model.characters.Hero;
import ro.academyplus.swingy.view.GameView;

import java.util.List;
import java.util.Scanner;

public class Console implements GameView {
    private CharacterController characterController;
    private GamePlayController gamePlayController;
    private Scanner cin;

    public Console(CharacterController characterController, GamePlayController gamePlayController) {
        this.characterController = characterController;
        this.gamePlayController = gamePlayController;
        this.cin = new Scanner(System.in);
    }

    @Deprecated
    private static void clear() {
//        System.out.print("\033[H\033[2J");
//        System.out.flush();

        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cmd.exe /c cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (final Exception e) {
            System.out.println("Something went wrong :(");
        }
    }

    @Override
    public void run() {
        if (!selectHeroMenu()) {
            System.out.println("Bye!");
            return;
        }
//        Console.clear();
        System.out.println("Hero selected successfully!");

        if (!gamePlayController.startGame()) {
            System.out.println("Error. Could not start game. :(");
            return;
        }

        while (playMission()) {
            // ... go on
        }
    }

    /**
     * @return true if success; false if program should stop
     */
    private boolean selectHeroMenu() {
        List<Hero> heroes = characterController.getHeroList();

        for (int i = 0; i < heroes.size(); i++) {
            System.out.println(String.format("%2d - %s, %s (level %d, XP %d)",
                    i,
                    heroes.get(i).getName(),
                    heroes.get(i).getCharacterClass(),
                    heroes.get(i).getLevel(),
                    heroes.get(i).getExperience()
            ));
        }
        System.out.println(" N - new hero");
        System.out.println(" X - exit");

        boolean selected = false;
        while (!selected) {
            System.out.print("Your option: ");
            String input = cin.nextLine().trim().toUpperCase();
            if (input.matches("\\d+")) {
                int n = Integer.parseInt(input);
                selected = characterController.selectHero(n);
            }
            else {
                switch (input) {
                    case "N":
                        return newHero();
                    case "X":
                        return false;
                    default:
                        selected = false;
                        break;
                }
            }

            if (!selected) {
                System.out.println("Wrong input! Try again!");
            }
        }

        return true;
    }

    /**
     * @return true if success; false if program should stop
     */
    private boolean newHero() {
        System.out.println("1 - BlackMage");
        System.out.println("2 - Elf");
        System.out.println("3 - Knight");
        System.out.println("4 - Orc");

        String cc = null;

        boolean created = false;
        while (!created) {
            System.out.print("Your option: ");
            String input = cin.nextLine().trim();
            while (!input.matches("\\d+")) {
                System.out.println("Wrong input! Try again!");
                System.out.print("Your option: ");
                input = cin.nextLine().trim();
            }

            int opt = Integer.valueOf(input);
            if ((opt < 1) || (opt > 4)) {
                System.out.println("Wrong option! Try again!");
                continue;
            }
            else {
                switch (opt) {
                    case 1:
                        cc = "BlackMage";
                        break;
                    case 2:
                        cc = "Elf";
                        break;
                    case 3:
                        cc = "Knight";
                        break;
                    case 4:
                        cc = "Orc";
                        break;
                    default:
                        continue;
                }
            }

            System.out.println("Give it a name!");
            while (!created) {
                System.out.print("Name: ");
                String name = cin.nextLine();
                if (name.length() > 0) {
                    created = characterController.createHero(name, cc);
                }
                if (!created) {
                    System.out.println("Wrong input! Try again!");
                }
            }
        }

        List<Hero> heroes = characterController.getHeroList();
        characterController.selectHero(heroes.size() - 1);

        return true;
    }

    /**
     * @return true if the mission should go on; false if the program should stop
     */
    private boolean playMission() {
        Hero hero = characterController.getSelectedHero();

        System.out.println(hero.getStats());
        System.out.println(gamePlayController.getMapStringRepresentation());

        if (gamePlayController.checkIfVillainHere()) {
            System.out.println("A Villain is here. You have two options: FIGHT or RUN.");
            System.out.println("F - fight");
            System.out.println("R - run");
            System.out.println("X - exit game");

            boolean selected = false;
            while (!selected) {
                System.out.print("Your option: ");
                String opt = cin.nextLine().trim().toUpperCase();
                switch (opt) {
                    case "X":
                        return false;
                    case "F":
                        if (gamePlayController.fight()) {
                            System.out.println("You won the battle! Congratulations!");
                        }
                        else {
                            System.out.println("Too bad! You lost!");
                        }
                        selected = true;
                        break;
                    case "R":
                        if (gamePlayController.run()) {
                            System.out.println("You run from the villain successfully");
                        }
                        else {
                            System.out.println("Too bad, you have to fight with the villain");
                            if (gamePlayController.fight()) {
                                System.out.println("You won the battle! Congratulations!");
                            }
                            else {
                                System.out.println("Too bad! You lost!");
                            }
                        }
                        selected = true;
                        break;
                    default:
                        System.out.println("Wrong option! Try again!");
                        break;
                }
            }
        }
        else {
            System.out.println("N - move North");
            System.out.println("S - move South");
            System.out.println("E - move East");
            System.out.println("W - move West");
            System.out.println("X - exit game");

            boolean selected = false;
            while (!selected) {
                System.out.println("Your option: ");
                String opt = cin.nextLine().trim().toUpperCase();
                switch (opt) {
                    case "X":
                        return false;
                    case "N":
                        if (gamePlayController.moveNorth()) {
                            gamePlayController.startGame();
                        }
                        selected = true;
                        break;
                    case "S":
                        if (gamePlayController.moveSouth()) {
                            gamePlayController.startGame();
                        }
                        selected = true;
                        break;
                    case "E":
                        if (gamePlayController.moveEast()) {
                            gamePlayController.startGame();
                        }
                        selected = true;
                        break;
                    case "W":
                        if (gamePlayController.moveWest()) {
                            gamePlayController.startGame();
                        }
                        selected = true;
                        break;
                    default:
                        System.out.println("Wrong option! Try again!");
                        break;
                }
            }
        }

        return true;
    }
}
