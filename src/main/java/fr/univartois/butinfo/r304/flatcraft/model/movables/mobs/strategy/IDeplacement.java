package fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.strategy;


import fr.univartois.butinfo.r304.flatcraft.model.movables.IMovable;

public interface IDeplacement {
    public void move(IMovable movable, long delta);
}
