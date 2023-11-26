package fr.univartois.butinfo.r304.flatcraft.model.cellules;

import fr.univartois.butinfo.r304.flatcraft.model.movables.IMovable;

public class EmptyCellState implements CellState {
    @Override
    public void interactWithPlayer(IMovable movable, Cell cell) {
        if(cell.getResource() == null) {
            int nouvelleLigne = cell.getRow() * cell.getSprite().getWidth();
            int nouvelleColonne = cell.getColumn() * cell.getSprite().getHeight();
            movable.setX(nouvelleColonne);
            movable.setY(nouvelleLigne);
        }
    }
}