package ro.academyplus.swingy.util;

import ro.academyplus.swingy.model.artifacts.Armor;
import ro.academyplus.swingy.model.artifacts.Weapon;
import ro.academyplus.swingy.model.characters.*;
import ro.academyplus.swingy.model.enums.CharacterClass;

public class Factory {
    private static int villainId = 1;

    public static Villain createVillain(int power) {
        String name = "villain #" + villainId;
        villainId += 1;
        return new Villain(name, CharacterClass.VILLAIN, power);
    }

    public static Hero createNewHero(String name, String characterClass) {
        return createHero(name, characterClass, 1, 0, "short bow", "leather armor");
    }

    public static Hero createHero(String description) {
        String[] tokens = description.split(",");

        String name = tokens[0];
        String characterClass = tokens[1];
        int level = Integer.valueOf(tokens[2]);
        int experience = Integer.valueOf(tokens[3]);
        String weapon = tokens[4];
        String armor = tokens[5];

        return createHero(name, characterClass, level, experience, weapon, armor);
    }

    public static Hero createHero(String name, String characterClass, int level, int experience, String weapon, String armor) {
        CharacterClass cc = null;

        switch (characterClass) {
            case "BlackMage":
                cc = CharacterClass.BLACK_MAGE;
                break;
            case "Elf":
                cc = CharacterClass.ELF;
                break;
            case "Knight":
                cc = CharacterClass.KNIGHT;
                break;
            case "Orc":
                cc = CharacterClass.ORC;
                break;
            default:
                cc = CharacterClass.ELF;
                break;
        }

        Weapon w = createWeapon(weapon);
        Armor a = createArmor(armor);

        Hero hero = null;

        switch (cc) {
            case BLACK_MAGE:
                hero = new BlackMage(name, cc, level, experience, w, a);
                break;
            case ELF:
                hero = new Elf(name, cc, level, experience, w, a);
                break;
            case KNIGHT:
                hero = new Knight(name, cc, level, experience, w, a);
                break;
            case ORC:
                hero = new Orc(name, cc, level, experience, w, a);
                break;
            default:
                hero = new Elf(name, CharacterClass.ELF, level, experience, w, a);
                break;
        }

        return hero;
    }

    public static Weapon createWeapon(String weapon) {
        Weapon w = null;

        switch (weapon) {
            case "short sword":
                w = new Weapon("short sword", 40);
                break;
            case "long sword":
                w = new Weapon("long sword", 80);
                break;
            case "short bow":
                w = new Weapon("short bow", 25);
                break;
            case "long bow":
                w = new Weapon("long bow", 50);
                break;
            default:
                w = new Weapon("no weapon", 0);
                break;
        }

        return w;
    }

    public static Armor createArmor(String armor) {
        Armor a = null;

        switch (armor) {
            case "leather armor":
                a = new Armor("leather armor", 30);
                break;
            case "steel armor":
                a = new Armor("steel armor", 60);
                break;
            case "titanium armor":
                a = new Armor("titanium armor", 90);
                break;
            default:
                a = new Armor("no armor", 0);
                break;
        }

        return a;
    }
}
