package ro.academyplus.swingy.controller;

import ro.academyplus.swingy.model.characters.Hero;
import ro.academyplus.swingy.storage.HeroStorage;
import ro.academyplus.swingy.util.Factory;

import java.util.List;

public class CharacterController {
    private HeroStorage storage;

    private Hero selectedHero;

    public CharacterController(HeroStorage storage) {
        this.storage = storage;
    }

    public List<Hero> getHeroList() {
        return storage.getAll();
    }

    public boolean createHero(String name, String characterClass) {
        Hero hero = Factory.createNewHero(name, characterClass);
        return storage.add(hero);
    }

    public boolean selectHero(int position) {
        Hero hero = storage.getOne(position);
        if (hero == null) {
            return false;
        }

        selectedHero = hero;

        return true;
    }

    public Hero getSelectedHero() {
        return selectedHero;
    }

    public void increaseXP(int xp) {
        // update experience
        int newXP = selectedHero.getExperience() + xp;
        selectedHero.setExperience(newXP);

        // update level (if necessary)
        int currentLevel = selectedHero.getLevel();
        int nextLevel = currentLevel + 1;
        while (newXP >= (currentLevel * 1000 + (currentLevel - 1) * (currentLevel - 1) * 450)) {
            newXP -= currentLevel * 1000 + (currentLevel - 1) * (currentLevel - 1) * 450;
            selectedHero.setLevel(nextLevel);

            currentLevel = selectedHero.getLevel();
            nextLevel = currentLevel + 1;
        }

        selectedHero.setExperience(newXP);

        storage.add(selectedHero);
    }
}
