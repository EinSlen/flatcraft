package fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.strategy.lineaire;
import fr.univartois.butinfo.r304.flatcraft.model.movables.IMovable;
import fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.strategy.IDeplacement;
import fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.strategy.intelligent.DeplacementIntelligent;

public class DeplacementLineare implements IDeplacement {
    private static DeplacementLineare instance;
    private DeplacementLineare(){};

    public static DeplacementLineare getInstance(){
        if(instance==null) instance = new DeplacementLineare();
        return instance;
    }
    @Override
    public void move(IMovable movable, long delta) {
        movable.setHorizontalSpeed(100);
    }
}
