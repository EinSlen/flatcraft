package fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.strategy;
import fr.univartois.butinfo.r304.flatcraft.model.FlatcraftGame;
import fr.univartois.butinfo.r304.flatcraft.model.movables.AbstractMovable;
import fr.univartois.butinfo.r304.flatcraft.view.Sprite;


public class Mob extends AbstractMovable {
    private final IDeplacement deplacement;

    /**
     * Crée une nouvelle instance de AbstractMovable.
     *
     * @param game      Le jeu dans lequel l'objet évolue.
     * @param xPosition La position en x initiale de l'objet.
     * @param yPosition La position en y initiale de l'objet.
     * @param sprite    L'instance de {@link Sprite} représentant l'objet.
     */
    public Mob(FlatcraftGame game, double xPosition, double yPosition, Sprite sprite, IDeplacement deplacement) {
        super(game, xPosition, yPosition, sprite);
        this.deplacement = deplacement;
    }

    @Override
    public boolean move(long delta) {
        deplacement.move(this, delta);
        return super.move(delta);
    }

}
