package fr.univartois.butinfo.r304.flatcraft.model.cellstate;

import fr.univartois.butinfo.r304.flatcraft.model.FlatcraftGame;
import fr.univartois.butinfo.r304.flatcraft.model.cellules.Cell;
import fr.univartois.butinfo.r304.flatcraft.model.cellules.MyCell;
import fr.univartois.butinfo.r304.flatcraft.model.movables.IMovable;
import fr.univartois.butinfo.r304.flatcraft.model.movables.Player;

public class EmptyCell implements CellState {

    public void interact(FlatcraftGame game, MyCell cell) {
        // Rien ne se passe lorsque le joueur creuse sur une cellule vide
        // Le joueur descend d'une case s'il est au-dessus de la cellule
        game.moveDown();
    }

    public boolean move(IMovable movable, MyCell cell) {
        int nouvelleLigne = cell.getRow() * cell.getSprite().getWidth();
        int nouvelleColonne = cell.getColumn() * cell.getSprite().getHeight();
        movable.setX(nouvelleColonne);
        movable.setY(nouvelleLigne);
        return true;
    }

    public boolean dig(IMovable player, MyCell cell) {
        // Rien ne se passe lorsque le joueur essaie de creuser une cellule vide
        return false;
    }

    @Override
    public void interact(FlatcraftGame game, Cell cell) {

    }
}
