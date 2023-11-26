/**
 * Ce logiciel est distribué à des fins éducatives.
 *
 * Il est fourni "tel quel", sans garantie d’aucune sorte, explicite
 * ou implicite, notamment sans garantie de qualité marchande, d’adéquation
 * à un usage particulier et d’absence de contrefaçon.
 * En aucun cas, les auteurs ou titulaires du droit d’auteur ne seront
 * responsables de tout dommage, réclamation ou autre responsabilité, que ce
 * soit dans le cadre d’un contrat, d’un délit ou autre, en provenance de,
 * consécutif à ou en relation avec le logiciel ou son utilisation, ou avec
 * d’autres éléments du logiciel.
 *
 * (c) 2023 Romain Wallon - Université d'Artois.
 * Tous droits réservés.
 */

package fr.univartois.butinfo.r304.flatcraft.model;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import fr.univartois.butinfo.r304.flatcraft.model.arbreterri.ArbreFactory;
import fr.univartois.butinfo.r304.flatcraft.model.arbreterri.FactoryComposite;
import fr.univartois.butinfo.r304.flatcraft.model.cellules.Cell;
import fr.univartois.butinfo.r304.flatcraft.model.cellules.CellFactory;
import fr.univartois.butinfo.r304.flatcraft.model.arbreterri.TerrilFactory;
import fr.univartois.butinfo.r304.flatcraft.model.map.IMapGenerator;
import fr.univartois.butinfo.r304.flatcraft.model.movables.IMovable;
import fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.fabrique.MobDim;
import fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.fabrique.normal.MNormal;
import fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.fabrique.otherdim.MOtherDim;
import fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.strategy.aleatoire.DeplacementAleatoire;
import fr.univartois.butinfo.r304.flatcraft.model.movables.Player;
import fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.strategy.intelligent.DeplacementIntelligent;
import fr.univartois.butinfo.r304.flatcraft.model.resources.Resource;
import fr.univartois.butinfo.r304.flatcraft.view.ISpriteStore;
import fr.univartois.butinfo.r304.flatcraft.view.Sprite;
import fr.univartois.butinfo.r304.flatcraft.view.SpriteStore;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import java.io.*;


/**
 * La classe {@link FlatcraftGame} permet de gérer une partie du jeu Flatcraft.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public final class FlatcraftGame {
    private static FlatcraftGame instance;

    public static FlatcraftGame getInstance(int width, int height, ISpriteStore spriteStore, CellFactory factory){
        if(instance==null) instance = new FlatcraftGame(width, height, spriteStore, factory);
        return instance;
    }

    /**
     * La largeur de la carte du jeu affichée (en pixels).
     */
    private final int width;

    /**
     * La hauteur de la carte du jeu affichée (en pixels).
     */
    private final int height;

    /**
     * Le contrôleur de l'application.
     */
    private IFlatcraftController controller;

    /**
     * L'instance e {@link ISpriteStore} utilisée pour créer les sprites du jeu.
     */
    private final ISpriteStore spriteStore;

    /**
     * L'instance de {@link CellFactory} utilisée pour créer les cellules du jeu.
     */
    private final CellFactory cellFactory;

    /**
     * La carte du jeu, sur laquelle le joueur évolue.
     */
    private GameMap map;

    /**
     * Le temps écoulé depuis le début de la partie.
     */
    private final IntegerProperty time = new SimpleIntegerProperty(12);

    /**
     * Le niveau actuel de la partie.
     */
    private final IntegerProperty level = new SimpleIntegerProperty(1);

    /**
     * La représentation du joueur.
     */
    private Player player;

    /**
     * La liste des objets mobiles du jeu.
     */
    private final List<IMovable> movableObjects = new CopyOnWriteArrayList<>();

    /**
     * L'animation simulant le temps qui passe dans le jeu.
     */
    private final FlatcraftAnimation animation = new FlatcraftAnimation(this, movableObjects);

    private IMapGenerator genMap;


    /**
     * Crée une nouvelle instance de FlatcraftGame.
     *
     * @param width La largeur de la carte du jeu (en pixels).
     * @param height La hauteur de la carte du jeu (en pixels).
     * @param spriteStore L'instance de {@link ISpriteStore} permettant de créer les
     *        {@link Sprite} du jeu.
     * @param factory La fabrique permettant de créer les cellules du jeux.
     */
    private FlatcraftGame(int width, int height, ISpriteStore spriteStore, CellFactory factory) {
        this.width = width;
        this.height = height;
        this.spriteStore = spriteStore;
        this.cellFactory = factory;
        this.cellFactory.setSpriteStore(spriteStore);
    }

    /**
     * Donne la largeur de la carte du jeu affichée (en pixels).
     *
     * @return La largeur de la carte du jeu affichée (en pixels).
     */
    public int getWidth() {
        return width;
    }

    /**
     * Donne la hauteur de la carte du jeu affichée (en pixels).
     *
     * @return La hauteur de la carte du jeu affichée (en pixels).
     */
    public int getHeight() {
        return height;
    }

    /**
     * Associe à cette partie le contrôleur gérant l'affichage du jeu.
     *
     * @param controller Le contrôleur gérant l'affichage.
     */
    public void setController(IFlatcraftController controller) {
        this.controller = controller;
    }

    /**
     * Prépare la partie de Flatcraft avant qu'elle ne démarre.
     */
    public void prepare() {
        // On crée la carte du jeu.
        map = createMap();
        controller.prepare(map);
        IntegerProperty playerHealth = new SimpleIntegerProperty(3);
        IntegerProperty playerExperience = new SimpleIntegerProperty(1);
        ObservableMap<Resource, Integer> playerInventory = FXCollections.observableHashMap();
        SpriteStore spriteStore1 = SpriteStore.getInstance();
        Sprite sprite = spriteStore1.getSprite("player");
        this.player = new Player(this, 0, map.getSoilHeight()*spriteStore.getSpriteSize()-spriteStore.getSpriteSize(), sprite, playerHealth, playerExperience, playerInventory);;
        ArbreFactory arbreFactory = new ArbreFactory(this, cellFactory, 5, 5);
        TerrilFactory terrilFactory = new TerrilFactory(this, cellFactory, 5);
        FactoryComposite factory = new FactoryComposite();
        factory.ajouter(arbreFactory);
        factory.ajouter(terrilFactory);
        factory.ajouterAleatoires();
        movableObjects.add(player);

        // Créer 1 mob pour la dimension normal, la gestion des dimensions n'est pas encore faite
        MobDim mobDim = new MNormal();
        IMovable mob = mobDim.render(this, DeplacementAleatoire.getInstance());
        movableObjects.add(mob);
        controller.addMovable(mob);

        controller.addMovable(player);
        controller.bindTime(time);
        controller.bindLevel(level);
        controller.bindHealth(playerHealth);
        controller.bindXP(playerExperience);


        animation.start();
    }


    public void setGenMap(IMapGenerator genMap) {
        this.genMap = genMap;
    }

    public GameMap getMap() {
        return map;
    }

    /**
     * Crée la carte du jeu.
     *
     * @return La carte du jeu créée.
     */
    private GameMap createMap() {
        /**
         int spriteSize = spriteStore.getSpriteSize();

        int mapWidthInPixels = width / spriteSize;
        int mapHeightInPixels = height / spriteSize;

        MapGenerator map = new MapGenerator();

        return map.GenerateMap(mapHeightInPixels, mapWidthInPixels, cellFactory);
         */
        IMapGenerator map = this.genMap;
        map.generateMap(height/ spriteStore.getSpriteSize(),width/ spriteStore.getSpriteSize(),cellFactory);
        return map.getMap();

    }

    /**
     * Indique à cette partie de Flatcraft qu'une nouvelle heure s'est écoulée
     * (dans le jeu).
     */
    void oneHour() {
        time.set((time.get() + 1) % 24);
    }

    /**
     * Fait se déplacer le joueur vers le haut.
     */
    public void moveUp() {
        Cell cell = getCellOf(player);
        Cell cellToDig = map.getAt(cell.getRow()-1, cell.getColumn());
        if(cellToDig.getResource() == null) {
            player.setVerticalSpeed(-50);
            move(player);
        }
    }

    /**
     * Fait se déplacer le joueur vers le bas.
     */
    public void moveDown() {
        Cell cell = getCellOf(player);
        Cell cellToDig = map.getAt(cell.getRow()+1, cell.getColumn());
        if(cellToDig.getResource() == null) {
            player.setVerticalSpeed(50);
            move(player);
        }

    }

    /**
     * Fait se déplacer le joueur vers la gauche.
     */
    public void moveLeft() {
        Cell cell = getCellOf(player);
        Cell cellToDig = map.getAt(cell.getRow(), cell.getColumn()-1);
        if(cellToDig.getResource() == null) {
            player.setHorizontalSpeed(-50);
            move(player);
        }
    }

    /**
     * Fait se déplacer le joueur vers la droite.
     */
    public void moveRight() {
        Cell cell = getCellOf(player);
        Cell cellToDig = map.getAt(cell.getRow(), cell.getColumn()+1);
        System.out.println(cellToDig.getResource());
        if(cellToDig.getResource() == null) {
            player.setHorizontalSpeed(50);
            move(player);
        }
    }

    /**
     * Déplace un objet mobile en tenant compte de la gravité.
     *
     * @param movable L'objet à déplacer.
     */
    private void move(IMovable movable) {
        Cell currentCell = getCellOf(movable);
        for (int row = currentCell.getRow() + 1; row < map.getHeight(); row++) {
            Cell below = map.getAt(row, currentCell.getColumn());
            if (!below.move(movable)) {
                break;
            }
        }
    }

    /**
     * Interrompt le déplacement du joueur.
     */
    public void stopMoving() {
        player.setHorizontalSpeed(0);
        player.setVerticalSpeed(0);
    }

    /**
     * Fait sauter le joueur.
     */
    public void jump() throws InterruptedException {
        moveUp();
        Thread.sleep(100);
        move(player);
        moveDown();
        move(player);
    }



    /**
     * Fait creuser le joueur vers le haut.
     */
    public void digUp() {
        Cell cell = getCellOf(player);
        Cell cellToDig = map.getAt(cell.getRow()-1, cell.getColumn());
        System.out.println(cellToDig.getSprite().getImage().getUrl());
        System.out.println(cellToDig.getResource());
        if (cellToDig != null){
            dig(cellToDig);
            move(player);
        }
    }

    /**
     * Fait creuser le joueur vers le bas.
     */
    public void digDown() {
        Cell cell = getCellOf(player);
        Cell cellToDig = map.getAt(cell.getRow()+1, cell.getColumn());
        System.out.println(cellToDig.getSprite().getImage().getUrl());
        System.out.println(cellToDig.getResource());
        if (cellToDig != null){
            dig(cellToDig);
            move(player);
        }
    }

    /**
     * Fait creuser le joueur vers la gauche.
     */
    public void digLeft() {
        Cell cell = getCellOf(player);
        Cell cellToDig = map.getAt(cell.getRow(), cell.getColumn()-1);
        if (cellToDig != null){
            dig(cellToDig);
            move(player);
        }
    }

    /**
     * Fait creuser le joueur vers la droite.
     */
    public void digRight() {
        Cell cell = getCellOf(player);
        Cell cellToDig = map.getAt(cell.getRow(), cell.getColumn()+1);
        if (cellToDig != null){
            dig(cellToDig);
            move(player);
        }
    }

    /**
     * Creuse la cellule donnée pour en extraire une ressource.
     *
     * @param toDig La cellule sur laquelle creuser.
     */
    private void dig(Cell toDig) {
        if(toDig.dig(player)){
            toDig.replaceBy(cellFactory.createSky());
        }
    }

    /**
     * Récupére la cellule correspondant à la position d'un objet mobile.
     * Il s'agit de la cellule sur laquelle l'objet en question occupe le plus de place.
     *
     * @param movable L'objet mobile dont la cellule doit être récupérée.
     *
     * @return La cellule occupée par l'objet mobile.
     */
    private Cell getCellOf(IMovable movable) {
        // On commence par récupérer la position du centre de l'objet.
        int midX = movable.getX() + (movable.getWidth() / 2);
        int midY = movable.getY() + (movable.getHeight() / 2);

        // On traduit cette position en position dans la carte.
        int row = midY / spriteStore.getSpriteSize();
        int column = midX / spriteStore.getSpriteSize();

        // On récupère enfin la cellule à cette position dans la carte.
        return map.getAt(row, column);
    }


}
