package fr.univartois.butinfo.r304.flatcraft.model.map;

import fr.univartois.butinfo.r304.flatcraft.model.FlatcraftGame;
import fr.univartois.butinfo.r304.flatcraft.model.GameMap;
import fr.univartois.butinfo.r304.flatcraft.model.arbreterri.ArbreFactory;
import fr.univartois.butinfo.r304.flatcraft.model.arbreterri.FactoryComposite;
import fr.univartois.butinfo.r304.flatcraft.model.arbreterri.TerrilFactory;
import fr.univartois.butinfo.r304.flatcraft.model.cellules.*;
import fr.univartois.butinfo.r304.flatcraft.view.SpriteStore;

import java.util.*;

import static java.lang.System.*;

public final class WorldMapEngine {
    private static WorldMapEngine instance;
    public enum MondeName {NORMAL,NETHER,END}
    private static final int ARBRES=500;
    private static final int TERRIL=5;
    private static final int HAUTEUR_TRONC =5;
    private final Map<MondeName, GameMap> mapList = new HashMap<>();

    private final FlatcraftGame flatcraftGame = FlatcraftGame.getInstance();

    private final SpriteStore spriteStore = SpriteStore.getInstance();

    private GameMap tableauActuel;

    private WorldMapEngine() {
        CellFactory[] cellFactory = new CellFactory[]{
                Factory.getInstance(),
                FactoryEnd.getInstance(),
                FactoryNether.getInstance()
        };
        mapList.put(MondeName.NORMAL,genMap(cellFactory[0]));
        out.println("créer : monde normal");
        mapList.put(MondeName.END, genMap(cellFactory[1]));
        out.println("créer : monde end");
        mapList.put(MondeName.NETHER,genMap(cellFactory[2]));
        out.println("créer : mondenether");
    }

    public static WorldMapEngine getInstance() {
        if(instance==null) instance = new WorldMapEngine();
        return instance;
    }

    private GameMap genMap(CellFactory cellFactory){
        cellFactory.setSpriteStore(spriteStore);
        IMapGenerator map = new MapGenerator();
        if (flatcraftGame != null) {
            map.generateMap(flatcraftGame.getHeight()/ spriteStore.getSpriteSize(),
                    flatcraftGame.getWidth()/ spriteStore.getSpriteSize(),
                    cellFactory);
        }
        remplirMap(cellFactory, map.getMap());
        return map.getMap();
    }

    private void remplirMap(CellFactory cellFactory, GameMap map){
        ArbreFactory arbreFactory = new ArbreFactory(flatcraftGame, cellFactory, map,ARBRES, HAUTEUR_TRONC);
        TerrilFactory terrilFactory = new TerrilFactory(flatcraftGame, cellFactory,map, TERRIL);
        FactoryComposite factory = new FactoryComposite();
        factory.ajouter(arbreFactory);
        factory.ajouter(terrilFactory);
        factory.ajouterAleatoires();
    }

    public GameMap getTableauActuel(){
        return tableauActuel;
    }

    //Permet de changer de monde
    public GameMap changeMap(MondeName monde){
        switch (monde){
            case NORMAL:
                out.println("Changement monde Normal");
                tableauActuel = mapList.get(monde);
                break;
            case END:
                out.println("Changement monde l'End");
                tableauActuel = mapList.get(monde);
                break;
            case NETHER:
                out.println("Changement monde Nether");
                tableauActuel = mapList.get(monde);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + monde);
        }
        return tableauActuel;
    }

}
