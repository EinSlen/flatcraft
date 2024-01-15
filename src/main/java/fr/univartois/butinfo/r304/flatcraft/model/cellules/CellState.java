package fr.univartois.butinfo.r304.flatcraft.model.cellules;

import fr.univartois.butinfo.r304.flatcraft.model.movables.IMovable;

public interface CellState {
    boolean interactWithPlayer(IMovable player, Cell cell);

    boolean accepts(IMovable movable);
}
