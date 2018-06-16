package ro.academyplus.swingy.model.characters;

import lombok.Getter;
import lombok.Setter;
import ro.academyplus.swingy.model.enums.CharacterClass;

import javax.validation.constraints.Min;

@Getter
@Setter
public class Villain extends Character {
    @Min(1)
    private int power;

    public Villain(String name, CharacterClass characterClass, int power) {
        super(name, characterClass);
        this.power = power;
    }
}
