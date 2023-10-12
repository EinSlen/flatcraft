package fr.univartois.butinfo.r304.flatcraft.model;

import fr.univartois.butinfo.r304.flatcraft.model.map.SimpleGameMap;
import fr.univartois.butinfo.r304.flatcraft.view.ISpriteStore;

public class MapGenerator {

    private int width;
    private int height;
    private CellFactory cellFactory;
    private ISpriteStore spriteStore;

    private MapGenerator SimpleGameMap;

    /**
     * Set le sprite dans cell Factory
     * @param spriteStore
     */
    public void setSpriteStore(ISpriteStore spriteStore) {
        this.spriteStore = spriteStore;
        //Mettre le sprite pour cell factory
        cellFactory.setSpriteStore(spriteStore);
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
        SimpleGameMap map = new SimpleGameMap(height, width, height/2);
        for(int i = 0; i<height;i++) {
            for(int j = 0; j<width; j++) {
                if(i>map.getSoilHeight()) map.setAt(i,j,cell.createSubSoil());
                if(i==map.getSoilHeight()) map.setAt(i,j,cell.createSoilSurface());
                if(i<map.getSoilHeight()) map.setAt(i,j,cell.createSky() );
            }
        }
        return map;
    }
}

