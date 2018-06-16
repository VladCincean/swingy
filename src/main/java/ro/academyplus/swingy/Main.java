package ro.academyplus.swingy;

import ro.academyplus.swingy.controller.CharacterController;
import ro.academyplus.swingy.controller.GamePlayController;
import ro.academyplus.swingy.storage.HeroStorage;
import ro.academyplus.swingy.view.GameView;
import ro.academyplus.swingy.view.console.Console;
import ro.academyplus.swingy.view.gui.GUI;

public class Main {
    private static void printUsage() {
        System.out.println("usage: java -jar target/swingy-1.0-SNAPSHOT.jar [console|gui]");
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            printUsage();
            return;
        }

        HeroStorage storage = new HeroStorage();
        CharacterController characterController = new CharacterController(storage);
        GamePlayController gamePlayController = new GamePlayController(characterController);

        switch (args[0].trim().toLowerCase()) {
            case "console":
                GameView cli = new Console(characterController, gamePlayController);
                cli.run();
                break;
            case "gui":
                GameView gui = new GUI(characterController, gamePlayController);
                gui.run();
                break;
            default:
                printUsage();
                break;
        }
    }
}
