package ro.academyplus.swingy.view.gui;

import ro.academyplus.swingy.controller.CharacterController;
import ro.academyplus.swingy.controller.GamePlayController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class NewHero {
    private JFrame frame;

    private CharacterController characterController;
    private GamePlayController gamePlayController;
    private Runnable onCreateCallback;

    private JComboBox<String> comboBoxClass;
    private JTextField textFieldName;
    private JButton buttonCreate;
    private JPanel panelNewHero;

    private void checkNameInput() {
        String txt = textFieldName.getText();

        if ((txt == null) || (txt.trim().equals(""))) {
            buttonCreate.setEnabled(false);
        }
        else {
            buttonCreate.setEnabled(true);
        }
    }

    public NewHero(
            final CharacterController characterController,
            final GamePlayController gamePlayController,
            final Runnable onCreateCallback
    ) {
        this.characterController = characterController;
        this.gamePlayController = gamePlayController;
        this.onCreateCallback = onCreateCallback;

        checkNameInput();

        textFieldName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyTyped(e);
                checkNameInput();
            }
        });
        buttonCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = textFieldName.getText();
                String cc = (String)comboBoxClass.getSelectedItem();
                characterController.createHero(name, cc);
                onCreateCallback.run();
                frame.dispose();
            }
        });
    }

    public void show() {
        frame = new JFrame("New Hero");
        frame.setContentPane(panelNewHero);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
