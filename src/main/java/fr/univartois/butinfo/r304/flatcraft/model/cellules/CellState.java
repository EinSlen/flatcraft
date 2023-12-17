package fr.univartois.butinfo.r304.flatcraft.model.cellules;

import fr.univartois.butinfo.r304.flatcraft.model.movables.IMovable;

public interface CellState {
    boolean move(IMovable movable, MyCell cell);
    boolean dig(IMovable player, MyCell cell);
}