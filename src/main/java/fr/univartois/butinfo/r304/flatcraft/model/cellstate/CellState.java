package fr.univartois.butinfo.r304.flatcraft.model.cellstate;

import fr.univartois.butinfo.r304.flatcraft.model.FlatcraftGame;
import fr.univartois.butinfo.r304.flatcraft.model.cellules.Cell;
import fr.univartois.butinfo.r304.flatcraft.model.cellules.MyCell;
import fr.univartois.butinfo.r304.flatcraft.model.movables.IMovable;

/**
 * Interface représentant les différents états d'une cellule.
 */
public interface CellState {
    /**
     * Méthode appelée lorsqu'un joueur interagit avec une cellule.
     *
     * @param game L'instance du jeu Flatcraft
     * @param cell La cellule avec laquelle le joueur interagit
     */
    void interact(FlatcraftGame game, Cell cell);

    boolean dig(IMovable player, MyCell myCell);


    boolean move(IMovable movable, MyCell myCell);
}
