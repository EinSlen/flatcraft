package fr.univartois.butinfo.r304.flatcraft.composite;

import fr.univartois.butinfo.r304.flatcraft.model.Cell;
import fr.univartois.butinfo.r304.flatcraft.model.CellFactory;
import fr.univartois.butinfo.r304.flatcraft.model.FlatcraftGame;
import fr.univartois.butinfo.r304.flatcraft.view.Sprite;
import fr.univartois.butinfo.r304.flatcraft.view.SpriteStore;
import fr.univartois.butinfo.r304.flatcraft.composite.ComponentArbre;

import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class ArbreFactory implements ComponentArbre {
    private FlatcraftGame game;
    private CellFactory cellFactory;
    private static Random random = new Random();
    private int maxArbres;
    private int hauteurLimiteTronc;

    public ArbreFactory(FlatcraftGame game, CellFactory cellFactory, int maxArbres, int hauteurLimiteTronc) {
        this.game = game;
        this.cellFactory = cellFactory;
        this.maxArbres = maxArbres;
        this.hauteurLimiteTronc = hauteurLimiteTronc;
    }

    public void ajouterArbresAleatoires() {
        int arbresAjoutes = 0;

        while (arbresAjoutes < maxArbres) {
            int col = random.nextInt(game.getWidth() / new SpriteStore().getSpriteSize());
            int hauteurTronc = random.nextInt(hauteurLimiteTronc) + 1;

            if (peutPlacerArbre(col, hauteurTronc)) {
                genererArbre(col, hauteurTronc);
                arbresAjoutes++;
            }
        }
    }

    private boolean peutPlacerArbre(int col, int hauteurTronc) {
        // Vérifiez si l'emplacement est approprié pour un nouvel arbre
        // (vous pouvez personnaliser cela en fonction de vos besoins)
        return true;
    }

    private void genererArbre(int col, int hauteurTronc) {
        int hauteurCourante =  Math.max(game.getMap().getSoilHeight() - hauteurTronc, game.getMap().getSoilHeight() - 1);

        // Générer le tronc de l'arbre
        for (int i = 0; i < hauteurTronc; i++) {
            Cell tronc = cellFactory.createTrunk();
            game.getMap().setAt(hauteurCourante, col, tronc);
            hauteurCourante--;
        }

        // Génération des fueilles
        int feuillageRadius = 2;
        int troncRow = hauteurCourante;
        for (int row = hauteurCourante; row > hauteurCourante - hauteurTronc; row--) {
            for (int offset = -feuillageRadius; offset <= feuillageRadius; offset++) {
                if (Math.abs(offset) < feuillageRadius && row != troncRow) {
                    Cell feuillage = cellFactory.createLeaves();
                    game.getMap().setAt(row+1, col + offset, feuillage);
                }
            }
        }
    }
}
