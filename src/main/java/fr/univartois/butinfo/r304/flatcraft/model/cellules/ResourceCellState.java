package fr.univartois.butinfo.r304.flatcraft.model.cellules;

import fr.univartois.butinfo.r304.flatcraft.model.movables.IMovable;
import fr.univartois.butinfo.r304.flatcraft.model.movables.Player;
import fr.univartois.butinfo.r304.flatcraft.model.resources.Resource;

public class ResourceCellState implements CellState {

    @Override
    public boolean move(IMovable movable, MyCell cell) {
        return false;
    }

    @Override
    public boolean dig(IMovable player, MyCell cell) {
        Resource resource = cell.getResource();
        if (resource != null) {
            resource.dig();
            if (resource.getHardness() == 0) {
                ((Player) player).ajouterInventaire(resource);
                return true;
            }
        }
        return false;
    }
}