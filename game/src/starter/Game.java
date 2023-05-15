package starter;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static logging.LoggerConfig.initBaseLogger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import configuration.Configuration;
import configuration.KeyboardConfig;
import controller.AbstractController;
import controller.SystemController;
import ecs.components.Component;
import ecs.components.InventoryComponent;
import ecs.components.MissingComponentException;
import ecs.components.PositionComponent;
import ecs.entities.Chest;
import ecs.entities.Entity;
import ecs.entities.Hero;
import ecs.entities.Trap;
import ecs.entities.Lever;
import ecs.items.*;
import ecs.entities.*;
import ecs.systems.*;
import graphic.Animation;
import graphic.DungeonCamera;
import graphic.Painter;
import graphic.hud.GameOverScreen;
import graphic.hud.PauseMenu;
import graphic.textures.TextureHandler;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import level.IOnLevelLoader;
import level.LevelAPI;
import level.elements.ILevel;
import level.elements.tile.Tile;
import level.generator.IGenerator;
import level.generator.postGeneration.WallGenerator;
import level.generator.randomwalk.RandomWalkGenerator;
import level.tools.LevelSize;
import tools.Constants;
import tools.Point;


/** The heart of the framework. From here all strings are pulled. */
public class Game extends ScreenAdapter implements IOnLevelLoader {

    private final LevelSize LEVELSIZE = LevelSize.SMALL;

    /**
     * The batch is necessary to draw ALL the stuff. Every object that uses draw need to know the
     * batch.
     */
    protected SpriteBatch batch;

    /** Contains all Controller of the Dungeon */
    protected List<AbstractController<?>> controller;

    public static DungeonCamera camera;
    /** Draws objects */
    protected Painter painter;

    protected LevelAPI levelAPI;
    /** Generates the level */
    protected IGenerator generator;

    private boolean doSetup = true;
    private static boolean paused = false;

    /** A handler for managing asset paths */
    private static TextureHandler handler;

    /** All entities that are currently active in the dungeon */
    private static final Set<Entity> entities = new HashSet<>();
    /** All entities to be removed from the dungeon in the next frame */
    private static final Set<Entity> entitiesToRemove = new HashSet<>();
    /** All entities to be added from the dungeon in the next frame */
    private static final Set<Entity> entitiesToAdd = new HashSet<>();

    /** List of all Systems in the ECS */
    public static SystemController systems;

    public static ILevel currentLevel;
    private static PauseMenu<Actor> pauseMenu;
    private static GameOverScreen<Actor> gameOverScreen;
    private static Entity hero;
    private Logger gameLogger;
    private int levelCount;
    private Trap trap;
    private Lever lever;

    private static Game game;

    public static void main(String[] args) {
        // start the game
        try {
            Configuration.loadAndGetConfiguration("dungeon_config.json", KeyboardConfig.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        game = new Game();
        DesktopLauncher.run(game);
    }

    /**
     * Main game loop. Redraws the dungeon and calls the own implementation (beginFrame, endFrame
     * and onLevelLoad).
     *
     * @param delta Time since last loop.
     */
    @Override
    public void render(float delta) {
        if (doSetup) setup();
        batch.setProjectionMatrix(camera.combined);
        frame();
        clearScreen();
        levelAPI.update();
        controller.forEach(AbstractController::update);
        camera.update();
    }

    /** Called once at the beginning of the game. */
    protected void setup() {
        levelCount = 0;
        doSetup = false;
        /*
         * THIS EXCEPTION HANDLING IS A TEMPORARY WORKAROUND !
         *
         * <p>The TextureHandler can throw an exception when it is first created. This exception
         * (IOEception) must be handled somewhere. Normally we want to pass exceptions to the method
         * caller. This approach is (atm) not possible in the libgdx render method because Java does
         * not allow extending method signatures derived from a class. We should try to make clean
         * code out of this workaround later.
         *
         * <p>Please see also discussions at:<br>
         * - https://github.com/Programmiermethoden/Dungeon/pull/560<br>
         * - https://github.com/Programmiermethoden/Dungeon/issues/587<br>
         */
        try {
            handler = TextureHandler.getInstance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        controller = new ArrayList<>();
        setupCameras();
        painter = new Painter(batch, camera);
        generator = new RandomWalkGenerator();
        levelAPI = new LevelAPI(batch, painter, generator, this);
        initBaseLogger();
        gameLogger = Logger.getLogger(this.getClass().getName());
        systems = new SystemController();
        controller.add(systems);
        pauseMenu = new PauseMenu<>();
        controller.add(pauseMenu);
        gameOverScreen = new GameOverScreen<>();
        controller.add(gameOverScreen);
        hero = new Hero();
        levelAPI = new LevelAPI(batch, painter, new WallGenerator(new RandomWalkGenerator()), this);
        levelAPI.loadLevel(LEVELSIZE);
        createSystems();
        //monster = new Monster();

    }

    public void reset() {
        hideGameOverScreen();

        getGame().setup();
    }

    public static Game getGame() {
        return game;
    }

    /** Called at the beginning of each frame. Before the controllers call <code>update</code>. */
    protected void frame() {
        setCameraFocus();
        manageEntitiesSets();
        getHero().ifPresent(this::loadNextLevelIfEntityIsOnEndTile);
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) togglePause();
    }

    @Override
    public void onLevelLoad() {
        levelCount++;
        currentLevel = levelAPI.getCurrentLevel();
        entities.clear();

        spawnMonster();

        getHero().ifPresent(this::placeOnLevelStart);
        spawnTraps();

        /**Quickfix for chests to spawn a chest to demonstrate items and inventory mechanics*/
        Chest newChest = Chest.createNewChest();
        Optional<Component> ic = newChest.getComponent(InventoryComponent.class);
        ((InventoryComponent) ic.get()).getItems().forEach(inventoryComponent -> inventoryComponent.setOnCollect((WorldItemEntity, whoCollides) -> {
            hero.getComponent(InventoryComponent.class)
                .ifPresent(ice->{
                    ((InventoryComponent) ice).addItem(inventoryComponent);
                });

        }));
        entities.add(newChest);

        hero.getComponent(InventoryComponent.class)
            .ifPresent(icb->{
                ((InventoryComponent) icb).addItem(new ItemData(
                    ItemType.Basic,
                    ItemCategory.BAG,
                    new Animation(Collections.singleton("items/other/bag_small.png"), 1),
                    new Animation(Collections.singleton("items/other/bag_small.png"), 1),
                    "kleine Tasche",
                    "Eine kleine Tasche, in der bis zu 5 Gegenstände einer Kategorie aufbewahrt werden können",
                    5
                ));
            });
    }

    public int calculateMonstersToSpawn(int level) {
        return (int) ((Math.random()*level)+1);
    }

    private void spawnMonster(){
        int monsters=calculateMonstersToSpawn(levelCount);
        for (int i = 0;i<monsters;i++){
            switch ((i*2)%3){
                case 0:
                    addEntity(new Ogre(levelCount+1));
                    break;
                case 1:
                    addEntity(new Demon(levelCount+1));
                    break;
                case 2:
                    addEntity(new Necromancer(levelCount+1));
                    break;
            }
        }
    }

    /**Spawns traps based on the levelCount, if a trap is deactivatable it will spawn a lever and connect it to the trap*/
    public void spawnTraps() {
        for(int i = 0; i < levelCount*2; i++){
            if(Math.random()<0.3){
                trap = new Trap(levelCount);
                entities.add(trap);
                if(trap.isDeactivatable()){
                    lever = new Lever(trap);
                    entities.add(lever);
                }
            }
        }
    }

    private void manageEntitiesSets() {
        entities.removeAll(entitiesToRemove);
        entities.addAll(entitiesToAdd);
        for (Entity entity : entitiesToRemove) {
            gameLogger.info("Entity '" + entity.getClass().getSimpleName() + "' was deleted.");
        }
        for (Entity entity : entitiesToAdd) {
            gameLogger.info("Entity '" + entity.getClass().getSimpleName() + "' was added.");
        }
        entitiesToRemove.clear();
        entitiesToAdd.clear();
    }

    private void setCameraFocus() {
        if (getHero().isPresent()) {
            PositionComponent pc =
                    (PositionComponent)
                            getHero()
                                    .get()
                                    .getComponent(PositionComponent.class)
                                    .orElseThrow(
                                            () ->
                                                    new MissingComponentException(
                                                            "PositionComponent"));
            camera.setFocusPoint(pc.getPosition());

        } else camera.setFocusPoint(new Point(0, 0));
    }

    private void loadNextLevelIfEntityIsOnEndTile(Entity hero) {
        if (isOnEndTile(hero)) levelAPI.loadLevel(LEVELSIZE);
    }

    private boolean isOnEndTile(Entity entity) {
        PositionComponent pc =
                (PositionComponent)
                        entity.getComponent(PositionComponent.class)
                                .orElseThrow(
                                        () -> new MissingComponentException("PositionComponent"));
        Tile currentTile = currentLevel.getTileAt(pc.getPosition().toCoordinate());
        return currentTile.equals(currentLevel.getEndTile());
    }

    private void placeOnLevelStart(Entity hero) {
        levelCount++;
        entities.add(hero);
        PositionComponent pc =
                (PositionComponent)
                        hero.getComponent(PositionComponent.class)
                                .orElseThrow(
                                        () -> new MissingComponentException("PositionComponent"));
        pc.setPosition(currentLevel.getStartTile().getCoordinate().toPoint());
    }

    public static TextureHandler getHandler() {
        return handler;
    }

    /** Toggle between pause and run */
    public static void togglePause() {
        paused = !paused;
        if (systems != null) {
            systems.forEach(ECS_System::toggleRun);
        }
        if (pauseMenu != null) {
            if (paused) pauseMenu.showMenu();
            else pauseMenu.hideMenu();
        }
    }

    public static void showGameOverScreen() {
        gameOverScreen.showMenu();
    }

    public static void hideGameOverScreen() { gameOverScreen.hideMenu();}

    /**
     * Given entity will be added to the game in the next frame
     *
     * @param entity will be added to the game next frame
     */
    public static void addEntity(Entity entity) {
        entitiesToAdd.add(entity);
    }

    /**
     * Given entity will be removed from the game in the next frame
     *
     * @param entity will be removed from the game next frame
     */
    public static void removeEntity(Entity entity) {
        entitiesToRemove.add(entity);
    }

    /**
     * @return Set with all entities currently in game
     */
    public static Set<Entity> getEntities() {
        return entities;
    }

    /**
     * @return Set with all entities that will be added to the game next frame
     */
    public static Set<Entity> getEntitiesToAdd() {
        return entitiesToAdd;
    }

    /**
     * @return Set with all entities that will be removed from the game next frame
     */
    public static Set<Entity> getEntitiesToRemove() {
        return entitiesToRemove;
    }

    /**
     * @return the player character, can be null if not initialized
     */
    public static Optional<Entity> getHero() {
        return Optional.ofNullable(hero);
    }

    /**
     * set the reference of the playable character careful: old hero will not be removed from the
     * game
     *
     * @param hero new reference of hero
     */
    public static void setHero(Entity hero) {
        Game.hero = hero;
    }

    public void setSpriteBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    private void setupCameras() {
        camera = new DungeonCamera(null, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.zoom = Constants.DEFAULT_ZOOM_FACTOR;

        // See also:
        // https://stackoverflow.com/questions/52011592/libgdx-set-ortho-camera
    }

    private void createSystems() {
        new VelocitySystem();
        new DrawSystem(painter);
        new PlayerSystem();
        new AISystem();
        new CollisionSystem();
        new HealthSystem();
        new XPSystem();
        new SkillSystem();
        new ProjectileSystem();
    }
}
