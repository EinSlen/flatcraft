package fr.univartois.butinfo.r304.flatcraft.model.cellules;

import fr.univartois.butinfo.r304.flatcraft.model.movables.IMovable;
import fr.univartois.butinfo.r304.flatcraft.model.resources.Resource;

public class ResourceCellState implements CellState {
    @Override
    public boolean interactWithPlayer(IMovable movable, Cell cell) {
        Resource resource = cell.getResource();
        if (resource != null) {
            resource.dig();
            if (resource.getHardness() == 0) {
                cell.setState(new EmptyCellState());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean accepts(IMovable movable) {
        return false;
    }
}