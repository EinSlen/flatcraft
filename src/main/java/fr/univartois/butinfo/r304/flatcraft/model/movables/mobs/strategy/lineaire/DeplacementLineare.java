package fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.strategy.lineaire;
import fr.univartois.butinfo.r304.flatcraft.model.movables.IMovable;
import fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.strategy.IDeplacement;

public class DeplacementLineare implements IDeplacement {

    @Override
    public void move(IMovable movable, long delta) {
        movable.setHorizontalSpeed(100);
    }
}
