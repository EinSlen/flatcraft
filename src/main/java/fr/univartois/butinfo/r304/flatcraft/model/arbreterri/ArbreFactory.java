package fr.univartois.butinfo.r304.flatcraft.model.arbreterri;

import fr.univartois.butinfo.r304.flatcraft.model.FlatcraftGame;
import fr.univartois.butinfo.r304.flatcraft.model.cellules.Cell;
import fr.univartois.butinfo.r304.flatcraft.model.cellules.CellFactory;
import fr.univartois.butinfo.r304.flatcraft.view.SpriteStore;

import java.util.Random;

public class ArbreFactory implements IComponent {
    private final FlatcraftGame game;
    private final CellFactory cellFactory;
    private static final Random random = new Random();
    private final int maxArbres;
    private final int hauteurLimiteTronc;

    public ArbreFactory(FlatcraftGame game, CellFactory cellFactory, int maxArbres, int hauteurLimiteTronc) {
        this.game = game;
        this.cellFactory = cellFactory;
        this.maxArbres = maxArbres;
        this.hauteurLimiteTronc = hauteurLimiteTronc;
    }

    public void ajouterAleatoires() {
        int arbresAjoutes = 0;

        while (arbresAjoutes < maxArbres) {
            int col = random.nextInt(game.getWidth() / new SpriteStore().getSpriteSize());
            int hauteurTronc = random.nextInt(hauteurLimiteTronc) + 1;

            if (!peutPlacerArbre(col, hauteurTronc)) {
                continue;
            }
            genererArbre(col, hauteurTronc);
            arbresAjoutes++;
        }
    }

    private boolean peutPlacerArbre(int col, int hauteurTronc) {
        // Vérifiez si l'emplacement est approprié pour un nouvel arbre
        // (personnaliser cela en fonction des besoins mais je pense si 0 > col-2 || map.width < col-2 RETURN FALSE à voir)
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
