package ro.academyplus.swingy.controller;

import ro.academyplus.swingy.model.characters.Hero;
import ro.academyplus.swingy.model.characters.Villain;
import ro.academyplus.swingy.util.Factory;

import java.util.Random;

@SuppressWarnings("Duplicates")
public class GamePlayController {
    private Random random;
    private CharacterController characterController;

    private Hero selectedHero;
    private int x;      // hero x position
    private int y;      // hero y position
    private int prevX;  // hero prev x position
    private int prevY;  // hero prev y position
    private int dim;    // map dimension
    private Villain[][] villainsMap;

    public GamePlayController(CharacterController characterController) {
        this.characterController = characterController;
        this.random = new Random();
    }

    /**
     * Attempts to start the game
     * @return true on success; false if a hero has not been selected yet
     */
    public boolean startGame() {
        selectedHero = characterController.getSelectedHero();
        if (selectedHero == null) {
            return false;
        }

        initMap();

        return true;
    }

    private void initMap() {
        // create villain map
        dim = (selectedHero.getLevel() - 1) * 5 + 10 - (selectedHero.getLevel() % 2);
        villainsMap = new Villain[dim][dim];
        for (int i = 0; i < dim; i++) {
            // ^^ say, "dim" villains
            int px = random.nextInt(dim);
            int py = random.nextInt(dim);
            int pow = random.nextInt((int)(2.5 * selectedHero.getAttack()));
            villainsMap[px][py] = Factory.createVillain(pow);
        }

        // put hero in the middle of the map
        this.x = dim / 2;
        this.y = dim / 2;
        this.prevX = this.x;
        this.prevY = this.y;
    }

    /**
     * Moves North.
     * @return true on game win
     */
    public boolean moveNorth() {
        if (x == 0) {
            characterController.increaseXP(selectedHero.getLevel() * 100);
            return true;
        }

        prevX = x;
        prevY = y;

        x -= 1;
        return false;
    }

    /**
     * Moves South.
     * @return true on game win
     */
    public boolean moveSouth() {
        if (x == (dim - 1)) {
            characterController.increaseXP(selectedHero.getLevel() * 100);
            return true;
        }

        prevX = x;
        prevY = y;

        x += 1;
        return false;
    }

    /**
     * Moves West.
     * @return true on game win
     */
    public boolean moveWest() {
        if (y == 0) {
            characterController.increaseXP(selectedHero.getLevel() * 100);
            return true;
        }

        prevX = x;
        prevY = y;

        y -= 1;
        return false;
    }

    /**
     * Moves East.
     * @return true on game win
     */
    public boolean moveEast() {
        if (y == (dim - 1)) {
            characterController.increaseXP(selectedHero.getLevel() * 100);
            return true;
        }

        prevX = x;
        prevY = y;

        y += 1;
        return false;
    }

    /**
     * Checks if on the current position is a villain or not.
     * @return true|false
     */
    public boolean checkIfVillainHere() {
        return villainsMap[x][y] != null;
    }

    /**
     * Fight villain.
     * @return true if battle won; false if lost
     */
    public boolean fight() {
        if (!checkIfVillainHere()) {
            return true;
        }

        int power = villainsMap[x][y].getPower();
        int attack = characterController.getSelectedHero().getWeapon().getAttack();
        int defense = characterController.getSelectedHero().getArmor().getDefense();

        if (attack + defense > power) {
            // win
            characterController.increaseXP(5 * power);
            villainsMap[x][y] = null;
            return true;
        } else {
            // lose
            initMap();
            return false;
        }
    }

    /**
     * Run with a chance of 50%. If not, fight.
     * @return true if successfully run; false in fight was necessary
     */
    public boolean run() {
        if ((random.nextInt(10) % 2) == 0) {
            x = prevX;
            y = prevY;
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * @return a string-representation of the map
     */
    public String getMapStringRepresentation() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if ((i == x) && (j == y)) {
                    sb.append("h");
                }
                else if (villainsMap[i][j] == null) {
                    sb.append(".");
                }
                else {
                    sb.append("w");
                }
                if (j < (dim - 1)) {
                    sb.append(" ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
