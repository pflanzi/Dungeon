package ecs.items;

public enum ItemCategory {

    POTION,
    WEAPON,
    BAG,
    OTHER;

    public String getItemCategory() {
        return switch (this) {
            case POTION -> "Trank";
            case WEAPON -> "Waffe";
            case BAG -> "Tasche";
            case OTHER -> "Weitere";
        };
    }

}
