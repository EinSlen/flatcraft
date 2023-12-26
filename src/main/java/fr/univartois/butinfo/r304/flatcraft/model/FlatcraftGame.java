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
import fr.univartois.butinfo.r304.flatcraft.view.ISpriteStore;
import fr.univartois.butinfo.r304.flatcraft.view.Sprite;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


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

    public static FlatcraftGame getInstance(int width, int height, ISpriteStore spriteStore){
        if(instance==null) instance = new FlatcraftGame(width, height, spriteStore);
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

    public IMapGenerator getGenMap() {
        return genMap;
    }

    /**
     * Crée une nouvelle instance de FlatcraftGame.
     *
     * @param width La largeur de la carte du jeu (en pixels).
     * @param height La hauteur de la carte du jeu (en pixels).
     * @param spriteStore L'instance de {@link ISpriteStore} permettant de créer les
     *        {@link Sprite} du jeu.
     */
    private FlatcraftGame(int width, int height, ISpriteStore spriteStore) {
        this.width = width;
        this.height = height;
        this.spriteStore = spriteStore;
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
        this.worldMapEngine = WorldMapEngine.getInstance();
        // On crée la carte du jeu.
        controller.prepare(worldMapEngine.changeMap("normal",0));
        cellFactory = Factory.getInstance();
        map = worldMapEngine.getTableauActuel();
        IntegerProperty playerHealth = new SimpleIntegerProperty(3);
        IntegerProperty playerExperience = new SimpleIntegerProperty(1);
        ObservableMap<Resource, Integer> playerInventory = FXCollections.observableHashMap();
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

        RuleParser craftRuleParser = new RuleParser("craftrules.txt");
        RuleParser furnaceRuleParser = new RuleParser("furnacerules.txt");

        try {
            craftRuleParser.parse();
            furnaceRuleParser.parse();
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

        // On traduit cette position en position dans la carte.
        int row = midY / spriteStore.getSpriteSize();
        int column = midX / spriteStore.getSpriteSize();

        // On récupère enfin la cellule à cette position dans la carte.
        return map.getAt(row, column);
    }

    /**
     * Crée une nouvelle ressource à l'aide d'un ensemble de ressources, en suivant les
     * règles de la table de craft.
     *
     * @param inputResources Les ressources déposées sur la table de craft.
     *
     * @return La ressource produite.
     */
    public Resource craft(Resource[][] inputResources) {
        Resource product = null;

        RuleParser craftRuleParser = new RuleParser("craftrules.txt");
        try {
            craftRuleParser.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Rule rule : craftRuleParser.getRuleList()) {
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
     * Crée une nouvelle ressource à l'aide d'un combustible et d'une ressource, en suivant les
     * règles du fourneau.
     *
     * @param fuel Le matériau combustible utilisé dans le fourneau.
     * @param resource La ressource à transformer.
     *
     * @return La ressource produite.
     */
    public Resource cook(Resource fuel, Resource resource) {
        Resource cookedResource = null;
        RuleParser cookRuleParser = new RuleParser("furnacerules.txt");

        try {
            cookRuleParser.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(resource.getName());

        for (Rule rule : cookRuleParser.getRuleList()) {
            // Vérifiez si les ressources passées en paramètres satisfont à la règle
            if (rule.getRule().equals(resource.getName())) {
                // Utilisez le RuleParser pour obtenir les informations nécessaires
                cookedResource = createResourceFromRule(rule);
                return cookedResource;
            }
        }

        return cookedResource;
    }

    private Resource createResourceFromRule(Rule rule) {
        // Utilisez les informations du RuleParser pour créer la nouvelle ressource
        Sprite sprite = spriteStore.getSprite(rule.getProduct().split(" ")[0]); // Assurez-vous que spriteStore est correctement initialisé
        ToolType toolType = ToolType.NO_TOOL; // Remplacez par la logique appropriée
        int hardness = 0; // Remplacez par la logique appropriée

        return new Resource(rule.getProduct(), sprite, toolType, hardness);
    }

}
