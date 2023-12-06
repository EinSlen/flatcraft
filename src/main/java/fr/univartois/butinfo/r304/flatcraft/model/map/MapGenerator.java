package fr.univartois.butinfo.r304.flatcraft.model.map;

import fr.univartois.butinfo.r304.flatcraft.model.cellules.CellFactory;
import fr.univartois.butinfo.r304.flatcraft.view.ISpriteStore;

import java.util.Random;
import java.util.logging.Logger;

public class MapGenerator implements IMapGenerator {

    private CellFactory cellFactory;

    private static Random random = new Random();
    private ISpriteStore spriteStore;

    private SimpleGameMap map;
    private Logger logger;

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
    public SimpleGameMap generateSimpleMap(int height, int width, CellFactory cell) {
        this.map = SimpleGameMap.getInstance(height, width, height/2);
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
            map = SimpleGameMap.getInstance(height, width, height / 2);
            generateSurface(height, width, cell);
            generateSubSoil(height, width, cell);
        } else {
            logger.warning("La map est déjà générée ! ");
        }
    }

    private void generateSurface(int height, int width, CellFactory cell) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i == map.getSoilHeight()) {
                    map.setAt(i, j, cell.createSoilSurface());
                    addJunglegrass(i, j, cell);
                } else if (i < map.getSoilHeight()) {
                    map.setAt(i, j, cell.createSky());
                } else {
                    map.setAt(i, j, cell.createStone());
                }
            }
        }
    }

    private void addJunglegrass(int i, int j, CellFactory cell) {
        if (random.nextInt(10) < 1) {
            map.setAt(i - 1, j, cell.createJunglegrass());
        }
    }

    private void generateSubSoil(int height, int width, CellFactory cell) {
        int stoneLayer = height / 2;
        int finStoneLayer = stoneLayer + 3;

        for (int i = stoneLayer + 2; i < finStoneLayer; i++) {
            for (int j = 0; j < width; j++) {
                if (random.nextDouble() < 0.5) {
                    map.setAt(i, j, cell.createSubSoil());
                } else {
                    map.setAt(i, j, cell.createStone());
                }
            }
        }

        for (int j = 0; j < width; j++) {
            map.setAt(map.getSoilHeight() + 1, j, cell.createSubSoil());
        }
    }





    @Override
    public SimpleGameMap getMap() {
        return map;
    }


}

