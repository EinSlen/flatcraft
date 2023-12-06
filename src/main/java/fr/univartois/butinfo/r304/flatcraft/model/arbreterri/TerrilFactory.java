package fr.univartois.butinfo.r304.flatcraft.model.arbreterri;

import fr.univartois.butinfo.r304.flatcraft.model.GameMap;
import fr.univartois.butinfo.r304.flatcraft.model.cellules.Cell;
import fr.univartois.butinfo.r304.flatcraft.model.cellules.CellFactory;
import fr.univartois.butinfo.r304.flatcraft.model.FlatcraftGame;
import fr.univartois.butinfo.r304.flatcraft.view.SpriteStore;

import java.util.Random;

public class TerrilFactory implements IComponent {
    private final FlatcraftGame game;
    private final CellFactory cellFactory;
    private static final Random random = new Random();
    private final int maxTerrilSize;
    private final GameMap map;

    public TerrilFactory(FlatcraftGame game, CellFactory cellFactory, GameMap map, int maxTerrilSize) {
        this.game = game;
        this.cellFactory = cellFactory;
        this.maxTerrilSize = maxTerrilSize;
        this.map=map;
    }

    public void ajouterAleatoires() {
        int terrilSize = random.nextInt(maxTerrilSize) + 1;
        int terrilCol = random.nextInt(game.getWidth() / SpriteStore.getInstance().getSpriteSize());
        int hauteurCourante = map.getSoilHeight() - terrilSize;

        for (int i = 0; i < terrilSize; i++) {
            int nbBlocs = 2 * i + 1;
            int startCol = terrilCol - i;

            for (int j = 0; j < nbBlocs; j++) {
                // Vérifiez si les coordonnées sont dans les limites de la carte
                if (hauteurCourante >= 0 && hauteurCourante < game.getHeight() && startCol >= 0 && startCol < game.getWidth()) {
                    Cell blocSousSol = cellFactory.createSubSoil();
                    map.setAt(hauteurCourante, startCol, blocSousSol);
                }
                startCol++;
            }
            hauteurCourante++;
        }
    }

}