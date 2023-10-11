package fr.univartois.butinfo.r304.flatcraft.model;

import fr.univartois.butinfo.r304.flatcraft.model.map.AbstractCell;
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
        if(getResource() == null){
            int nouvelleLigne = getRow() * getSprite().getHeight();
            int nouvelleColonne = getColumn() * getSprite().getWidth();
            movable.setX(nouvelleLigne);
            movable.setY(nouvelleColonne);
            return true;
        } else {
            return false;
        }
    }


    @Override
    public boolean dig(IMovable player) {
        if(getResource().getHardness()==0){
            ((Player) player).ajouterInventaire(getResource());
            return true;
        }
        return false;
    }
}
