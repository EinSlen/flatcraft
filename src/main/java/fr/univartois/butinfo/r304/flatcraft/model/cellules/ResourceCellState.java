package fr.univartois.butinfo.r304.flatcraft.model.cellules;

import fr.univartois.butinfo.r304.flatcraft.model.movables.IMovable;
import fr.univartois.butinfo.r304.flatcraft.model.movables.Player;
import fr.univartois.butinfo.r304.flatcraft.model.resources.Resource;

public class ResourceCellState implements CellState {
    @Override
    public void interactWithPlayer(IMovable movable, Cell cell) {
        Resource resource = cell.getResource();
        if (resource != null) {
            resource.dig();
            if (resource.getHardness() == 0) {
                ((Player) movable).ajouterInventaire(resource);
            }
        }
    }
}