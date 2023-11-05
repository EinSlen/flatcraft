package fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.fabrique;
import fr.univartois.butinfo.r304.flatcraft.model.FlatcraftGame;
import fr.univartois.butinfo.r304.flatcraft.model.movables.IMovable;
import fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.strategy.IDeplacement;

public abstract class MobDim {
    public abstract IMobMake createMob();
    public IMovable render(FlatcraftGame game, IDeplacement deplacement){
        IMobMake mob = createMob();
        return mob.render(game, deplacement);
    }
}
