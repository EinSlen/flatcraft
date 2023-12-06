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
                int col = random.nextInt(game.getWidth() / SpriteStore.getInstance().getSpriteSize());
                int hauteurTronc = random.nextInt(hauteurLimiteTronc) + 1;

                if (!peutPlacerArbre(col)) {
                    continue;
                }
                genererArbre(col, hauteurTronc);
                arbresAjoutes++;
            }
        }

        private boolean peutPlacerArbre(int col) {
            int minCol = 1;
            int maxCol = game.getMap().getWidth() - 2;

            int minRow = game.getMap().getSoilHeight() - 1;
            int maxRow = game.getHeight() - 1;

            return col >= minCol && col <= maxCol && minRow >= 0 && maxRow < game.getHeight();
        }


        private void genererArbre(int col, int hauteurTronc) {
            int hauteurCourante =  Math.max(game.getMap().getSoilHeight() - hauteurTronc, game.getMap().getSoilHeight() - 1);
            // Générer le tronc de l'arbre
            for (int i = 0; i < hauteurTronc; i++) {
                try {
                    Cell currentCell = game.getMap().getAt(hauteurCourante, col);
                    if(currentCell != null && currentCell.getResource() != null)
                        while (("dirt".equals(currentCell.getResource().getName()))) {
                            hauteurCourante--;
                        }
                } catch(Exception e) {
                    e.printStackTrace();
                }
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
