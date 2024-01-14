package fr.univartois.butinfo.r304.flatcraft.model.cellules;

import fr.univartois.butinfo.r304.flatcraft.model.movables.IMovable;
import fr.univartois.butinfo.r304.flatcraft.model.resources.Resource;

public class EmptyCellState implements CellState {
    @Override
    public boolean interactWithPlayer(IMovable movable, Cell cell) {
        Resource resource = cell.getResource();
        if(resource == null) {
            int nouvelleLigne = cell.getRow() * cell.getSprite().getWidth();
            int nouvelleColonne = cell.getColumn() * cell.getSprite().getHeight();
            movable.setX(nouvelleColonne);
            movable.setY(nouvelleLigne);
            return true;
        }
        return false;
    }
}