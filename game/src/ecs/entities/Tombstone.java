package ecs.entities;

public class Tombstone extends Entity {

    private Ghost ghost;
    private String texture;
    private int dmgAmount;
    private int healAmount;
    private float radius;

    public Tombstone(Ghost ghost) {
        this.ghost = ghost;

    }

    public void interact() {
        // select if the hero is going to be punished or rewarded at random

    }

    private void reward() {
        // permanently add 5 points to the hero's health
    }

    private void punish() {
        // add 3 damage to the hero's health
    }

}
