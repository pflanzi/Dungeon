package ecs.items;

public enum ItemCategory {

    POTION,
    WEAPON,
    OTHER;

    public String getItemCategory() {
        return switch (this) {
            case POTION -> "Trank";
            case WEAPON -> "Waffe";
            case OTHER -> "Weitere";
        };
    }

}
