package ro.academyplus.swingy.view.gui;

import ro.academyplus.swingy.controller.CharacterController;
import ro.academyplus.swingy.controller.GamePlayController;
import ro.academyplus.swingy.model.characters.Hero;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SelectHero {
    private JFrame frame;

    private CharacterController characterController;
    private GamePlayController gamePlayController;

    private JList<String> listHeroes;
    private JTextArea textAreaHeroStats;
    private JButton buttonSelectHero;
    private JButton buttonNewHero;
    private JPanel panelSelectHero;

    private void updateHeroesList() {
        listHeroes.setModel(new DefaultListModel<String>());

        List<Hero> heroes = characterController.getHeroList();
        ((DefaultListModel<String>)(listHeroes.getModel())).clear();
        for (int i = 0; i < heroes.size(); i++) {
            String txt = heroes.get(i).getName() + ", " + heroes.get(i).getCharacterClass();
            ((DefaultListModel<String>)(listHeroes.getModel())).addElement(txt);
        }
    }

    public SelectHero(
            final CharacterController characterController,
            final GamePlayController gamePlayController
    ) {
        this.characterController = characterController;
        this.gamePlayController = gamePlayController;

        updateHeroesList();

        buttonSelectHero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = listHeroes.getSelectedIndex();
                characterController.selectHero(index);

                PlayMission playMission = new PlayMission(characterController, gamePlayController);
                playMission.show();
                frame.dispose();
            }
        });
        listHeroes.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int index = listHeroes.getSelectedIndex();
                List<Hero> heroes = characterController.getHeroList();

                if ((index >= 0) && (index < heroes.size())) {
                    textAreaHeroStats.setText(characterController.getHeroList().get(index).getStats());
                }
                else {
                    textAreaHeroStats.setText("No Hero selected.");
                }
            }
        });
        buttonNewHero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewHero newHero = new NewHero(
                        characterController,
                        gamePlayController,
                        new Runnable() {
                            @Override
                            public void run() {
                                updateHeroesList();
                            }
                        }
                );
                newHero.show();
            }
        });
    }

    public void show() {
        frame = new JFrame("Select Hero");
        frame.setContentPane(panelSelectHero);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
