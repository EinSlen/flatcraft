package fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.fabrique;
import fr.univartois.butinfo.r304.flatcraft.model.FlatcraftGame;
import fr.univartois.butinfo.r304.flatcraft.model.movables.IMovable;
import fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.strategy.IDeplacement;

public interface IMobMake {
    public IMovable render(FlatcraftGame game, IDeplacement deplacement);
}
