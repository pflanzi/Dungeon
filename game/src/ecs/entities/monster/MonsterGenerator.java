package ecs.entities.monster;

import java.util.Random;
import java.util.logging.Logger;

public class MonsterGenerator {

    private static MonsterGenerator INSTANCE = new MonsterGenerator();
    private final Logger monsterGeneratorLogger = Logger.getLogger(this.getClass().getName());
    private Random random = new Random();

    private MonsterGenerator() {}

    public static MonsterGenerator getInstance() {
        if (INSTANCE == null) INSTANCE = new MonsterGenerator();

        return INSTANCE;
    }

    public static Monster generateRandomMonster() {
        return null;
    }
}
