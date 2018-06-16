package ro.academyplus.swingy.model.characters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ro.academyplus.swingy.model.enums.CharacterClass;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Setter
public abstract class Character {

    @NotNull
    protected String name;

    @NotNull
    protected CharacterClass characterClass;
}
