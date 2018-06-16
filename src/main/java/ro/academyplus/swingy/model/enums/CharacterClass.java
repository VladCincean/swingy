package ro.academyplus.swingy.model.enums;

public enum CharacterClass {
    BLACK_MAGE,
    ELF,
    KNIGHT,
    ORC,
    VILLAIN;

    @Override
    public String toString() {
        String ss = null;
        switch (this) {
            case BLACK_MAGE:
                ss = "BlackMage";
                break;
            case ELF:
                ss = "Elf";
                break;
            case KNIGHT:
                ss = "Knight";
                break;
            case ORC:
                ss = "Orc";
                break;
            case VILLAIN:
                ss = "Villain";
                break;
        }

        return ss;
    }
}
