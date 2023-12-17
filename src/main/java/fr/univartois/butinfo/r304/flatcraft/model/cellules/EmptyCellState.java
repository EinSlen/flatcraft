package fr.univartois.butinfo.r304.flatcraft.model.cellules;

import fr.univartois.butinfo.r304.flatcraft.model.movables.IMovable;

public class EmptyCellState implements CellState {

    @Override
    public boolean move(IMovable movable, MyCell cell) {
        int nouvelleLigne = cell.getRow() * cell.getSprite().getHeight();
        int nouvelleColonne = cell.getColumn() * cell.getSprite().getWidth();
        movable.setX(nouvelleColonne);
        movable.setY(nouvelleLigne);
        return true;
    }

    @Override
    public boolean dig(IMovable player, MyCell cell) {
        return false;
    }
}