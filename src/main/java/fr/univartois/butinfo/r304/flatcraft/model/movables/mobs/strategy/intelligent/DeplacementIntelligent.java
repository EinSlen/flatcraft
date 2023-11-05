package fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.strategy.intelligent;

import fr.univartois.butinfo.r304.flatcraft.model.movables.IMovable;
import fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.strategy.IDeplacement;

public class DeplacementIntelligent implements IDeplacement {
    @Override
    public void move(IMovable movable, long delta) {
        int x = movable.getX();
        if(x<= 0) movable.setHorizontalSpeed(150);
        if(x>= 1200) movable.setHorizontalSpeed(-150); // A revoir
    }
}
