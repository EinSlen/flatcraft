package fr.univartois.butinfo.r304.flatcraft.model.mobs.strategy.lineaire;
import fr.univartois.butinfo.r304.flatcraft.model.IMovable;
import fr.univartois.butinfo.r304.flatcraft.model.mobs.strategy.IDeplacement;

public class DeplacementLineare implements IDeplacement {

    @Override
    public void move(IMovable movable, long delta) {
        movable.setHorizontalSpeed(100);
    }
}
