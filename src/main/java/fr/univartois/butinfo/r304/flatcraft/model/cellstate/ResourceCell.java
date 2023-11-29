package fr.univartois.butinfo.r304.flatcraft.model.cellstate;

import fr.univartois.butinfo.r304.flatcraft.model.FlatcraftGame;
import fr.univartois.butinfo.r304.flatcraft.model.cellules.Cell;
import fr.univartois.butinfo.r304.flatcraft.model.cellules.MyCell;
import fr.univartois.butinfo.r304.flatcraft.model.movables.IMovable;
import fr.univartois.butinfo.r304.flatcraft.model.movables.Player;
import fr.univartois.butinfo.r304.flatcraft.model.resources.Resource;

public class ResourceCell implements CellState {

    public void interact(FlatcraftGame game, MyCell cell) {
        // Le joueur ne bouge plus s'il est au-dessus de la cellule contenant une ressource
        // Le joueur peut creuser pour tenter de récupérer la ressource
        // Si la ressource est récupérée, la cellule devient vide
        if (cell.dig(game.getPlayer())) {
            cell.setState(new EmptyCell()); // La cellule devient vide
        }
    }

    public boolean move(IMovable movable, MyCell cell) {
        // Le joueur ne bouge pas s'il est au-dessus d'une cellule contenant une ressource
        return false;
    }


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

    @Override
    public void interact(FlatcraftGame game, Cell cell) {
        return;
    }
}
