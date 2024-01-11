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

import fr.univartois.butinfo.r304.flatcraft.model.cellules.*;
import fr.univartois.butinfo.r304.flatcraft.model.craft.RuleParser;
import fr.univartois.butinfo.r304.flatcraft.model.craft.rule.Rule;
import fr.univartois.butinfo.r304.flatcraft.model.map.IMapGenerator;
import fr.univartois.butinfo.r304.flatcraft.model.map.WorldMapEngine;
import fr.univartois.butinfo.r304.flatcraft.model.movables.IMovable;
import fr.univartois.butinfo.r304.flatcraft.model.movables.Player;
import fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.fabrique.MobDim;
import fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.fabrique.normal.MNormal;
import fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.strategy.aleatoire.DeplacementAleatoire;
import fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.strategy.intelligent.DeplacementIntelligent;
import fr.univartois.butinfo.r304.flatcraft.model.resources.Resource;
import fr.univartois.butinfo.r304.flatcraft.model.resources.ToolType;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import fr.univartois.butinfo.r304.flatcraft.model.resources.Inventoriable;
import fr.univartois.butinfo.r304.flatcraft.view.ISpriteStore;
import fr.univartois.butinfo.r304.flatcraft.view.Sprite;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javafx.collections.ObservableMap;

/**
 * La classe {@link FlatcraftGame} permet de gérer une partie du jeu Flatcraft.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public final class FlatcraftGame {
    private static FlatcraftGame instance;
    private WorldMapEngine worldMapEngine;

    public static FlatcraftGame getInstance(int width, int height, int mapRepeat, ISpriteStore spriteStore, CellFactory factory){
        if(instance==null) instance = new FlatcraftGame(width, height, mapRepeat, spriteStore, factory);
        return instance;
    }
    public static FlatcraftGame getInstance(){
        if(instance!=null) return instance;
        return null;
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
     * Le nombre de fois que la carte se "répète" horizontalement.
     */
    private final int mapRepeat;

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
    private CellFactory cellFactory;

    /**
     * La carte du jeu, sur laquelle le joueur évolue.
     */
    private GameMap map;

    /**
     * La position à gauche de la carte dans la fenêtre.
     * Celle-ci change lorsque l'utilisateur se déplace horizontalement.
     */
    private IntegerProperty leftAnchor = new SimpleIntegerProperty(0);

    /**
     * Le temps écoulé depuis le début de la partie.
     */
    private final IntegerProperty time = new SimpleIntegerProperty(12);

    /**
     * Le niveau actuel de la partie.
     */
    private final IntegerProperty level = new SimpleIntegerProperty(1);

    private RuleParser craftRuleParser;

    private RuleParser furnaceRuleParser;

    /**
     * La représentation du joueur.
     */
    private Player player;

    /**
     * La dernière direction suivie par le joueur.
     * Elle est stockée sous la forme d'un entier, afin d'indiquer s'il avance ou s'il
     * recule.
     */
    private int lastDirection = 1;

    /**
     * L'iterateur permettant de parcourir les ressources contenues dans l'inventaire du
     * joueur.
     */
    private Iterator<Inventoriable> inventoryIterator;

    /**
     * La liste des objets mobiles du jeu.
     */
    private final List<IMovable> movableObjects = new CopyOnWriteArrayList<>();

    /**
     * L'animation simulant le temps qui passe dans le jeu.
     */
    private final FlatcraftAnimation animation = new FlatcraftAnimation(this, movableObjects);

    private IMapGenerator genMap;

    public IMapGenerator getGenMap() {
        return genMap;
    }

    /**
     * Crée une nouvelle instance de FlatcraftGame.
     *
     * @param width La largeur de la carte du jeu (en pixels).
     * @param height La hauteur de la carte du jeu (en pixels).
     * @param mapRepeat Le nombre de fois que la carte se "répète" horizontalement.
     * @param spriteStore L'instance de {@link ISpriteStore} permettant de créer les
     *        {@link Sprite} du jeu.
     */
    public FlatcraftGame(int width, int height, int mapRepeat, ISpriteStore spriteStore,
            CellFactory factory) {
        this.width = width;
        this.height = height;
        this.mapRepeat = mapRepeat;
        this.spriteStore = spriteStore;
    }

    /**
     * Donne la largeur de la carte du jeu affichée (en pixels).
     *
     * @return La largeur de la carte du jeu affichée (en pixels).
     */
    public int getWidth() {
        return width * mapRepeat;
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
        this.worldMapEngine = WorldMapEngine.getInstance();
        // On crée la carte du jeu.
        controller.prepare(worldMapEngine.changeMap("normal",0));
        cellFactory = Factory.getInstance();
        map = worldMapEngine.getTableauActuel();
        IntegerProperty playerHealth = new SimpleIntegerProperty(3);
        IntegerProperty playerExperience = new SimpleIntegerProperty(1);
        ObservableMap<Inventoriable, Integer> playerInventory = FXCollections.observableHashMap();
        Sprite sprite = spriteStore.getSprite("player");
        this.player = new Player(this, 0, worldMapEngine.getTableauActuel().getSoilHeight()*spriteStore.getSpriteSize()-spriteStore.getSpriteSize(), sprite, playerHealth, playerExperience, playerInventory);
        movableObjects.add(player);
        // Créer 1 mob pour la dimension normal, la gestion des dimensions n'est pas encore faite
        MobDim mobDim = new MNormal();
        IMovable mob = mobDim.render(this, DeplacementIntelligent.getInstance());
        MobDim mobDim1 = new MNormal();
        IMovable mob1 = mobDim1.render(this, DeplacementAleatoire.getInstance());
        movableObjects.add(mob);
        movableObjects.add(mob1);
        controller.addMovable(mob);
        controller.addMovable(mob1);
        controller.addMovable(player);
        controller.bindTime(time);
        controller.bindLevel(level);
        controller.bindHealth(playerHealth);
        controller.bindXP(playerExperience);
        controller.bindInventory(playerInventory);
        animation.start();

        this.craftRuleParser = new RuleParser("craftrules.txt");
        this.furnaceRuleParser = new RuleParser("furnacerules.txt");

        try {
            this.craftRuleParser.parse();
            this.furnaceRuleParser.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void setGenMap(IMapGenerator genMap) {
        this.genMap = genMap;
    }


    public CellFactory getCellFactory() {
        return cellFactory;
    }


    //Bouchons pour tester le changement de monde
    int i =0;
    private void changeDimensionV1(){
        Cell currentCell = getCellOf(player);
        if(currentCell.getRow()==21 && currentCell.getColumn()==3 && i==0){
            i++;
            map = worldMapEngine.changeMap("end",0);
            cellFactory = FactoryEnd.getInstance();
            controller.prepare(map);
        }
        if(currentCell.getRow()==21 && currentCell.getColumn()==8 && i==1){
            i++;
            map = worldMapEngine.changeMap("nether",0);
            cellFactory = FactoryNether.getInstance();
            controller.prepare(map);
        }
        if(currentCell.getRow()==21 && currentCell.getColumn()==12 && i==2){
            i++;
            map = worldMapEngine.changeMap("normal",0);
            cellFactory = Factory.getInstance();
            controller.prepare(map);
        }
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
        player.setHorizontalSpeed(0);
        Cell cell = getCellOf(player);
        Cell cellToDig = map.getAt(cell.getRow(), cell.getColumn()-1);
        if(cellToDig.getResource() == null) {
            player.setHorizontalSpeed(-45);
            move(player);
        }
        changeDimensionV1();
        // TODO Implémentez cette méthode.
        lastDirection = -1;
    }

    /**
     * Fait se déplacer le joueur vers la droite.
     */
    public void moveRight() {
        player.setHorizontalSpeed(0);
        Cell cell = getCellOf(player);
        Cell cellToDig = map.getAt(cell.getRow(), cell.getColumn()+1);
        if(cellToDig.getResource() == null) {
            player.setHorizontalSpeed(45);
            move(player);
        }
        changeDimensionV1();
        // TODO Implémentez cette méthode.
        lastDirection = 1;
    }

    /**
     * Déplace un objet mobile en tenant compte de la gravité.
     *
     * @param movable L'objet à déplacer.
     */
    private void move(IMovable movable) {
        // On applique la gravité.
        Cell currentCell = getCellOf(movable);
        for (int row = currentCell.getRow() + 1; row < map.getHeight(); row++) {
            Cell below = map.getAt(row, currentCell.getColumn());
            if (!below.move(movable)) {
                break;
            }
        }

        // On positionne la carte pour afficher la section où se trouve le joueur.
        int middlePosition = player.getX() + player.getWidth() / 2;
        int mapSection = middlePosition / width;
        leftAnchor.set(-mapSection * width);
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
        player.setVerticalSpeed(0);
        move(player);
    }



    /**
     * Fait creuser le joueur vers le haut.
     */
    public void digUp() {
        Cell cell = getCellOf(player);
        Cell cellToDig = map.getAt(cell.getRow()-1, cell.getColumn());
        if (cellToDig.getResource() != null){
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
        if (cellToDig.getResource() != null){
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
        if (cellToDig.getResource() != null){
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
        if (cellToDig.getResource() != null){
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
            if(toDig.getResource()!=null)
                //player.ajouterInventaire(toDig.getResource());
                toDig.setState(new EmptyCellState());
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
        return getCellAt(midX, midY);
    }

    /**
     * Donne la cellule à la position donnée sur la carte.
     *
     * @param x La position en x de la cellule.
     * @param y La position en y de la cellule.
     *
     * @return La cellule à la position donnée.
     */
    public Cell getCellAt(int x, int y) {
        // On traduit cette position en position dans la carte.
        int row = y / spriteStore.getSpriteSize();
        int column = x / spriteStore.getSpriteSize();

        // On récupère enfin la cellule à cette position dans la carte.
        return map.getAt(row, column);
    }

    /**
     * Récupére la cellule correspondant à la prochaine position d'un objet mobile.
     * Il s'agit de la cellule voisine de celle sur laquelle l'objet en question occupe le
     * plus de place, en suivant la dernière direction suivie par joueur.
     *
     * @param movable L'objet mobile dont la prochaine cellule doit être récupérée.
     *
     * @return La prochaine cellule occupée par l'objet mobile.
     */
    private Optional<Cell> getNextCellOf(IMovable movable) {
        // On commence par récupérer la position du centre de l'objet.
        int midX = movable.getX() + (movable.getWidth() / 2);
        int midY = movable.getY() + (movable.getHeight() / 2);

        // On traduit cette position en position dans la carte.
        int row = midY / spriteStore.getSpriteSize();
        int column = midX / spriteStore.getSpriteSize() + lastDirection;

        // On récupère enfin la cellule à cette position dans la carte.
        if (column < map.getWidth()) {
            return Optional.of(map.getAt(row, column));
        }

        return Optional.empty();
    }

    /**
     * Crée une nouvelle ressource à l'aide d'un ensemble de ressources, en suivant les
     * règles de la table de craft.
     *
     * @param inputResources Les ressources déposées sur la table de craft.
     *
     * @return La ressource produite.
     */
    public Inventoriable craft(Inventoriable[][] inputResources) {
        Inventoriable product = null;

        for (Rule rule : this.craftRuleParser.getRuleList()) {
            String[] ruleElements = rule.getRule().split("_");
                boolean allMatch = true;
                for (int i = 0; i < inputResources.length; i++) {
                    for (int j = 0; j < inputResources[i].length; j++) {
                        String resourceName = (inputResources[i][j] != null) ? inputResources[i][j].getName().toLowerCase() : "empty";
                        if (!ruleElements[i * inputResources[i].length + j].equals(resourceName)) {
                            allMatch = false;
                            break;
                        }
                    }
                    if (!allMatch) {
                        break;
                    }
                }

                if (allMatch) {
                    String name = (rule.getProduct().split(" ").length > 1 ) ?
                        rule.getProduct().split(" ")[0].toLowerCase()
                    :
                        rule.getProduct().toLowerCase();
                    Sprite sprite = spriteStore.getSprite(name);
                    product = new Resource(name, sprite, ToolType.NO_TOOL, 0);
                    break;
                }
        }
        return product;
    }



    /**
     * Crée une nouvelle ressource à l'aide d'un combustible et d'une ressource, en
     * suivant les règles du fourneau.
     *
     * @param fuel Le matériau combustible utilisé dans le fourneau.
     * @param resource La ressource à transformer.
     *
     * @return La ressource produite.
     */
    public Inventoriable cook(Inventoriable fuel, Inventoriable resource) {
        for (Rule rule : this.furnaceRuleParser.getRuleList()) {
            if (rule.getRule().equals(resource.getName())) {
                Sprite sprite = spriteStore.getSprite(rule.getProduct().split(" ")[0]);
                return new Resource(rule.getProduct(), sprite, ToolType.NO_TOOL, 0);
            }
        }
        return null;
    }
    /**
     * Dépose sur la carte la ressource que le joueur a actuellement en main.
     */
    public void dropResource() {
        // On commence par rechercher la cellule voisine de celle du joueur, si elle
        // existe.
        Optional<Cell> next = getNextCellOf(player);
        if (next.isEmpty()) {
            return;
        }

        // Le dépôt ne peut fonctionner que si la cellule ne contient pas de ressource.
        Cell target = next.get();
        // TODO Récupérer la ressource que le joueur a actuellement en main.
        Inventoriable inHand = null;
        if (target.setResource(inHand)) {
            // TODO Retirer la ressource de l'inventaire du joueur.
            switchResource();
        }
    }

    /**
     * Modifie la ressource que l'utilisateur a actuellement en main.
     * C'est la prochaine ressource dans l'inventaire qui est choisie.
     */
    public void switchResource() {
        if ((inventoryIterator == null) || (!inventoryIterator.hasNext())) {
            // TODO Récupérer l'inventaire du joueur.
            ObservableMap<Inventoriable, Integer> inventory = null;
            inventoryIterator = inventory.keySet().iterator();
        }

        Inventoriable inHand = inventoryIterator.next();
        // TODO Remplacer l'objet dans la main du joueur par inHand.
    }

    /**
     * Exécute l'action associée à la ressource située sur la cellule voisine de celle du
     * joueur.
     */
    public void executeResource() {
        Optional<Cell> next = getNextCellOf(player);
        if (next.isPresent()) {
            Cell cell = next.get();
            cell.execute();
        }
    }

}
