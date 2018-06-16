package ro.academyplus.swingy.model.artifacts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Setter
public class Weapon {
    @NotNull
    private String name;
    @Min(10)
    private int attack;
}
