package fr.univartois.butinfo.r304.flatcraft.model.compositeArbreTerri;

import fr.univartois.butinfo.r304.flatcraft.model.Cell;
import fr.univartois.butinfo.r304.flatcraft.model.CellFactory;
import fr.univartois.butinfo.r304.flatcraft.model.FlatcraftGame;
import fr.univartois.butinfo.r304.flatcraft.view.SpriteStore;

import java.util.Random;

public class TerrilFactory implements IComponent {
    private FlatcraftGame game;
    private CellFactory cellFactory;
    private static Random random = new Random();
    private int maxTerrilSize;

    public TerrilFactory(FlatcraftGame game, CellFactory cellFactory, int maxTerrilSize) {
        this.game = game;
        this.cellFactory = cellFactory;
        this.maxTerrilSize = maxTerrilSize;
    }

    public void ajouterAleatoires() {
        int terrilSize = random.nextInt(maxTerrilSize) + 1;
        int terrilCol = random.nextInt(game.getWidth() / new SpriteStore().getSpriteSize());
        int hauteurCourante = game.getMap().getSoilHeight() - terrilSize;

        for (int i = 0; i < terrilSize; i++) {
            int nbBlocs = 2 * i + 1;
            int startCol = terrilCol - i;

            for (int j = 0; j < nbBlocs; j++) {
                Cell blocSousSol = cellFactory.createSubSoil();
                game.getMap().setAt(hauteurCourante, startCol, blocSousSol);
                startCol++;
            }
            hauteurCourante++;
        }
    }
}