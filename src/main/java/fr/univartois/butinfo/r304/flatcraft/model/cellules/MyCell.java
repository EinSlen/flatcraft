package fr.univartois.butinfo.r304.flatcraft.model.cellules;

import fr.univartois.butinfo.r304.flatcraft.model.movables.IMovable;
import fr.univartois.butinfo.r304.flatcraft.model.movables.Player;
import fr.univartois.butinfo.r304.flatcraft.model.resources.Resource;
import fr.univartois.butinfo.r304.flatcraft.view.Sprite;

public class MyCell extends AbstractCell {
    public MyCell(int row, int column) {
        super(row, column);
    }

    protected MyCell(Sprite sprite) {
        super(sprite);
    }
    protected MyCell(Resource resource) {
        super(resource);
    }

    @Override
    public boolean move(IMovable movable) {
        if(super.getResource() == null){
            int nouvelleLigne = getRow() * getSprite().getWidth();
            int nouvelleColonne = getColumn() * getSprite().getHeight();
            movable.setX(nouvelleColonne);
            movable.setY(nouvelleLigne);
            return true;
        } else {
            return false;
        }
    }


    @Override
    public boolean dig(IMovable player) {
        Resource resource = super.getResource();
        if(resource != null){
            resource.dig();
            if(resource.getHardness()==0){
                ((Player) player).ajouterInventaire(resource);
                return true;
            }
        }
        return false;
    }
}
