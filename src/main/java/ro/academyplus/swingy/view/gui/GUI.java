package ro.academyplus.swingy.view.gui;

import ro.academyplus.swingy.controller.CharacterController;
import ro.academyplus.swingy.controller.GamePlayController;
import ro.academyplus.swingy.view.GameView;

public class GUI implements GameView {
    private CharacterController characterController;
    private GamePlayController gamePlayController;

    public GUI(CharacterController characterController, GamePlayController gamePlayController) {
        this.characterController = characterController;
        this.gamePlayController = gamePlayController;
    }

    @Override
    public void run() {
        SelectHero selectHero = new SelectHero(characterController, gamePlayController);
        selectHero.show();
    }
}
