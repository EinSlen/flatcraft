package fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.fabrique.otherdim;
import fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.fabrique.IMobMake;
import fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.fabrique.MobDim;

public class MOtherDim extends MobDim {
    @Override
    public IMobMake createMob() {
        return new MakeMobOtherDim();
    }
}
