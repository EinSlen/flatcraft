package fr.univartois.butinfo.r304.flatcraft.model.map;

import fr.univartois.butinfo.r304.flatcraft.model.cellules.CellFactory;
import fr.univartois.butinfo.r304.flatcraft.view.ISpriteStore;

import java.util.Random;

public class MapGenerator implements IMapGenerator {

    private int width;
    private int height;
    private CellFactory cellFactory;

    private static Random random = new Random();
    private ISpriteStore spriteStore;

    private SimpleGameMap map;

    /**
     * Set le sprite dans cell Factory
     * @param spriteStore
     */
    public void setSpriteStore(ISpriteStore spriteStore) {
        this.spriteStore = spriteStore;
        //Mettre le sprite pour cell factory
        this.cellFactory.setSpriteStore(spriteStore);
    }


    /**
     * Q2) Dans cette classe définissez une méthode retournant la carte générée.
     * Pour la génération, vous aurez besoin de la hauteur et de la largeur
     * (en nombre de cellules) de la carte à générer, ainsi que d'une
     * instance de CellFactory.
     *
     * @Param width, height, CellFactory
     * @Return la carte générée
     */
    public SimpleGameMap GenerateMap(int height, int width, CellFactory cell) {
        this.map = new SimpleGameMap(height, width, height/2);
        for(int i = 0; i<height;i++) {
            for(int j = 0; j<width; j++) {
                if(i>map.getSoilHeight()) map.setAt(i,j,cell.createSubSoil());
                if(i==map.getSoilHeight()) map.setAt(i,j,cell.createSoilSurface());
                if(i<map.getSoilHeight()) map.setAt(i,j,cell.createSky() );
            }
        }
        return map;
    }

    @Override
    public void generateMap(int height, int width, CellFactory cell) {
        if (map == null) {
            map = new SimpleGameMap(height, width, height / 2);
            int stoneLayer = height / 2;  // Hauteur à laquelle se trouve la couche de stone
            int finstonelayer = (height / 2) + 3;

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (i == map.getSoilHeight()) {
                        // Ajout de la couche de terre en dessous de la surface
                        map.setAt(i, j, cell.createSoilSurface());
                        if (random.nextInt(10) < 1) {
                            map.setAt(i - 1, j, cell.createJunglegrass());
                        }
                    } else if (i < map.getSoilHeight()) {
                        map.setAt(i, j, cell.createSky());
                    } else {
                        map.setAt(i, j, cell.createStone());
                    }
                    if (i > stoneLayer+1 && i < stoneLayer + 4) {
                        if (random.nextDouble() < 0.5) {
                            map.setAt(i, j, cell.createSubSoil());
                        } else {
                            map.setAt(i, j, cell.createStone());
                        }
                    }
                    if (i == map.getSoilHeight()+1) {
                        map.setAt(i, j, cell.createSubSoil());
                    }
                }
            }
        }
    }





    @Override
    public SimpleGameMap getMap() {
        return map;
    }


}

