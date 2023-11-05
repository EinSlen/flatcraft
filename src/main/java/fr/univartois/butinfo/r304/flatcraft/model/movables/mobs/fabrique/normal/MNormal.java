package fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.fabrique.normal;

import fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.fabrique.IMobMake;
import fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.fabrique.MobDim;

public class MNormal extends MobDim {
    @Override
    public IMobMake createMob() {
        return new MakeMobNormal();
    }
}
