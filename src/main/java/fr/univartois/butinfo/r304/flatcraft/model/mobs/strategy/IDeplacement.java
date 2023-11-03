package fr.univartois.butinfo.r304.flatcraft.model.mobs.strategy;


import fr.univartois.butinfo.r304.flatcraft.model.IMovable;

public interface IDeplacement {
    public void move(IMovable movable, long delta);
}
