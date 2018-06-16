package ro.academyplus.swingy.view.gui;

import ro.academyplus.swingy.controller.CharacterController;
import ro.academyplus.swingy.controller.GamePlayController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class PlayMission {
    private JFrame frame;

    private CharacterController characterController;
    private GamePlayController gamePlayController;

    private JTextPane textPaneMap;
    private JButton buttonNorth;
    private JButton buttonSouth;
    private JButton buttonWest;
    private JButton buttonEast;
    private JButton buttonFight;
    private JButton buttonRun;
    private JPanel panelPlayMission;
    private JTextPane textPaneStats;

    private void updateState() {
        boolean isVillain = gamePlayController.checkIfVillainHere();

        buttonFight.setEnabled(isVillain);
        buttonRun.setEnabled(isVillain);
        buttonNorth.setEnabled(!isVillain);
        buttonSouth.setEnabled(!isVillain);
        buttonEast.setEnabled(!isVillain);
        buttonWest.setEnabled(!isVillain);

        textPaneMap.setText(gamePlayController.getMapStringRepresentation());
        textPaneStats.setText(characterController.getSelectedHero().getStats());

        if (isVillain) {
            JOptionPane.showMessageDialog(
                    null,
                    "A Villain is here. You have two options: FIGHT or RUN."
            );
        }
    }

    private void moveNorth() {
        if (gamePlayController.checkIfVillainHere()) {
            return;
        }

        if (gamePlayController.moveNorth()) {
            gamePlayController.startGame();
        }

        updateState();
    }

    private void moveSouth() {
        if (gamePlayController.checkIfVillainHere()) {
            return;
        }

        if (gamePlayController.moveSouth()) {
            gamePlayController.startGame();
        }

        updateState();
    }

    private void moveWest() {
        if (gamePlayController.checkIfVillainHere()) {
            return;
        }

        if (gamePlayController.moveWest()) {
            gamePlayController.startGame();
        }

        updateState();
    }

    private void moveEast() {
        if (gamePlayController.checkIfVillainHere()) {
            return;
        }

        if (gamePlayController.moveEast()) {
            gamePlayController.startGame();
        }

        updateState();
    }

    private void fight() {
        if (!gamePlayController.checkIfVillainHere()) {
            return;
        }

        if (gamePlayController.fight()) {
            JOptionPane.showMessageDialog(null, "You won the battle! Congratulations!");
        }
        else {
            JOptionPane.showMessageDialog(null, "Too bad! You lost!");
        }

        updateState();
    }

    private void run() {
        if (!gamePlayController.checkIfVillainHere()) {
            return;
        }

        if (gamePlayController.run()) {
            JOptionPane.showMessageDialog(null, "You run from the villain successfully");
        }
        else {
            JOptionPane.showMessageDialog(null, "Too bad, you have to fight with the villain");
            if (gamePlayController.fight()) {
                JOptionPane.showMessageDialog(null, "You won the battle! Congratulations!");
            }
            else {
                JOptionPane.showMessageDialog(null, "Too bad! You lost!");
            }
        }

        updateState();
    }

    public PlayMission(
            final CharacterController characterController,
            final GamePlayController gamePlayController
    ) {
        this.characterController = characterController;
        this.gamePlayController = gamePlayController;

        gamePlayController.startGame();
        updateState();

        buttonNorth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveNorth();
            }
        });
        buttonSouth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveSouth();
            }
        });
        buttonWest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveWest();
            }
        });
        buttonEast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveEast();
            }
        });
        buttonFight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fight();
            }
        });
        buttonRun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                run();
            }
        });

        KeyboardFocusManager manager =
                KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_TYPED) {
                    char key = java.lang.Character.toUpperCase(e.getKeyChar());
                    switch (key) {
                        case 'W':
                            moveNorth();
                            break;
                        case 'A':
                            moveWest();
                            break;
                        case 'S':
                            moveSouth();
                            break;
                        case 'D':
                            moveEast();
                            break;
                        case 'F':
                            fight();
                            break;
                        case 'R':
                            run();
                            break;
                    }
                }

                return false;
            }
        });

    }

    public void show() {
        frame = new JFrame("Play Mission");
        frame.setContentPane(panelPlayMission);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setState(JFrame.NORMAL);
        frame.pack();
        frame.setVisible(true);
    }
}
