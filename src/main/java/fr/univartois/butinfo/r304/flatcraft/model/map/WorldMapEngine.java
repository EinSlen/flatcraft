package fr.univartois.butinfo.r304.flatcraft.model.map;

import fr.univartois.butinfo.r304.flatcraft.model.FlatcraftGame;
import fr.univartois.butinfo.r304.flatcraft.model.GameMap;
import fr.univartois.butinfo.r304.flatcraft.model.arbreterri.ArbreFactory;
import fr.univartois.butinfo.r304.flatcraft.model.arbreterri.FactoryComposite;
import fr.univartois.butinfo.r304.flatcraft.model.arbreterri.TerrilFactory;
import fr.univartois.butinfo.r304.flatcraft.model.cellules.*;
import fr.univartois.butinfo.r304.flatcraft.view.SpriteStore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WorldMapEngine {
    private static WorldMapEngine instance;
    private final List<GameMap> normal = new ArrayList<>();
    private final List<GameMap> end = new ArrayList<>();
    private final List<GameMap> nether = new ArrayList<>();
    private final CellFactory[] cellFactory = new CellFactory[]{
            Factory.getInstance(),
            FactoryEnd.getInstance(),
            FactoryNether.getInstance()
    };
    private final FlatcraftGame flatcraftGame = FlatcraftGame.getInstance();

    private final SpriteStore spriteStore = SpriteStore.getInstance();

    private GameMap tableauActuel;

    private WorldMapEngine() {
        normal.add(genMap(cellFactory[0]));
        end.add(genMap(cellFactory[1]));
        nether.add(genMap(cellFactory[2]));
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

    public void remplirMap(CellFactory cellFactory, GameMap map){
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

    //Permet de changer de tableau et de créer un tableau au besoin
    public GameMap changeMap(String monde, int localisation){
        switch (monde){
            case "normal":
                if(normal.size()<localisation)
                    normal.add(genMap(cellFactory[0]));
                System.out.println("Changement monde Normal");
                tableauActuel = normal.get(localisation);
                break;
            case "end":
                if(end.size()<localisation)
                    normal.add(genMap(cellFactory[1]));
                System.out.println("Changement monde l'End");
                tableauActuel = end.get(localisation);
                break;
            case "nether":
                if(nether.size()<localisation)
                    normal.add(genMap(cellFactory[2]));
                System.out.println("Changement monde Nether");
                tableauActuel = nether.get(localisation);
                break;
        }
        return tableauActuel;
    }

}
