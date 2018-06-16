package ro.academyplus.swingy.model.characters;

import lombok.Getter;
import lombok.Setter;
import ro.academyplus.swingy.model.artifacts.Armor;
import ro.academyplus.swingy.model.artifacts.Weapon;
import ro.academyplus.swingy.model.enums.CharacterClass;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public abstract class Hero extends Character {

    @Min(1)
    int level;

    @Min(0)
    int experience;

    @NotNull
    private Weapon weapon;

    @NotNull
    private Armor armor;

    public Hero(
            String name,
            CharacterClass characterClass,
            int level,
            int experience,
            Weapon weapon,
            Armor armor
    ) {
        super(name, characterClass);
        this.level = level;
        this.experience = experience;
        this.weapon = weapon;
        this.armor = armor;
    }

    public int getAttack() {
        return weapon.getAttack();
    }

    public int getDefense() {
        return armor.getDefense();
    }

    public int getHP() {
        return (int)(0.4 * weapon.getAttack() + 0.6 * armor.getDefense());
    }

    @Override
    public String toString() {
        return name + "," +
                characterClass.toString() + "," +
                level + "," +
                experience + "," +
                weapon.getName() + "," +
                armor.getName();
    }

    public String getStats() {
        String stats = "";
        stats += String.format("Hero name: %s\n", this.getName());
        stats += String.format("Hero class: %s\n", this.getCharacterClass());
        stats += String.format("Level: %d\n", this.getLevel());
        stats += String.format("Experience: %d XP\n", this.getExperience());
        stats += String.format("Attack: %d\n", this.getAttack());
        stats += String.format("HP: %d\n", this.getHP());
        stats += String.format("Defense: %d\n", this.getDefense());
        stats += String.format("Weapon: %s\n", this.getWeapon().getName());
        stats += String.format("Armor: %s\n", this.getArmor().getName());
        return stats;
    }
}
