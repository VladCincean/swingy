package ro.academyplus.swingy.model.characters;

import ro.academyplus.swingy.model.artifacts.Armor;
import ro.academyplus.swingy.model.artifacts.Weapon;
import ro.academyplus.swingy.model.enums.CharacterClass;

public class Knight extends Hero {
    public Knight(String name, CharacterClass characterClass, int level, int experience, Weapon weapon, Armor armor) {
        super(name, characterClass, level, experience, weapon, armor);
    }
}
