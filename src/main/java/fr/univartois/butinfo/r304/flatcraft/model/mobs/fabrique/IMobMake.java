package fr.univartois.butinfo.r304.flatcraft.model.mobs.fabrique;

import fr.univartois.butinfo.r304.flatcraft.model.FlatcraftGame;
import fr.univartois.butinfo.r304.flatcraft.model.IMovable;
import fr.univartois.butinfo.r304.flatcraft.model.mobs.strategy.IDeplacement;

public interface IMobMake {
    public IMovable makeMobForDimension(FlatcraftGame game, IDeplacement deplacement);
}
