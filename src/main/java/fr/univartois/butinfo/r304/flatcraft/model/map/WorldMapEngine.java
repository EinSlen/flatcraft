package fr.univartois.butinfo.r304.flatcraft.model.map;

import fr.univartois.butinfo.r304.flatcraft.model.FlatcraftGame;
import fr.univartois.butinfo.r304.flatcraft.model.GameMap;
import fr.univartois.butinfo.r304.flatcraft.model.arbreterri.ArbreFactory;
import fr.univartois.butinfo.r304.flatcraft.model.arbreterri.FactoryComposite;
import fr.univartois.butinfo.r304.flatcraft.model.arbreterri.TerrilFactory;
import fr.univartois.butinfo.r304.flatcraft.model.cellules.*;
import fr.univartois.butinfo.r304.flatcraft.view.SpriteStore;

import java.util.*;

public class WorldMapEngine {
    private static WorldMapEngine instance;
    public enum MondeName {NORMAL,NETHER,END}
    private final Map<MondeName, GameMap> mapList = new HashMap<>();

    private final CellFactory[] cellFactory = new CellFactory[]{
            Factory.getInstance(),
            FactoryEnd.getInstance(),
            FactoryNether.getInstance()
    };
    private final FlatcraftGame flatcraftGame = FlatcraftGame.getInstance();

    private final SpriteStore spriteStore = SpriteStore.getInstance();

    private GameMap tableauActuel;

    private WorldMapEngine() {
        mapList.put(MondeName.NORMAL,genMap(cellFactory[0]));
        System.out.println("créer : monde normal");
        mapList.put(MondeName.END, genMap(cellFactory[1]));
        System.out.println("créer : monde end");
        mapList.put(MondeName.NETHER,genMap(cellFactory[2]));
        System.out.println("créer : mondenether");
    }

    public static WorldMapEngine getInstance() {
        if(instance==null) instance = new WorldMapEngine();
        return instance;
    }

    private GameMap genMap(CellFactory cellFactory){
        cellFactory.setSpriteStore(spriteStore);
        IMapGenerator map = new MapGenerator();
        map.generateMap(flatcraftGame.getHeight()/ spriteStore.getSpriteSize(),
                flatcraftGame.getWidth()/ spriteStore.getSpriteSize(),
                cellFactory);
        remplirMap(cellFactory, map.getMap());
        return map.getMap();
    }

    private void remplirMap(CellFactory cellFactory, GameMap map){
        ArbreFactory arbreFactory = new ArbreFactory(flatcraftGame, cellFactory, map,50, 5);
        TerrilFactory terrilFactory = new TerrilFactory(flatcraftGame, cellFactory,map, 5);
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
                System.out.println("Changement monde Normal");
                tableauActuel = mapList.get(monde);
                break;
            case END:
                System.out.println("Changement monde l'End");
                tableauActuel = mapList.get(monde);
                break;
            case NETHER:
                System.out.println("Changement monde Nether");
                tableauActuel = mapList.get(monde);
                break;
        }
        return tableauActuel;
    }

}
