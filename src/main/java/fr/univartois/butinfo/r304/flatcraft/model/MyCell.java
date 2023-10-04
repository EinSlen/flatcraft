package fr.univartois.butinfo.r304.flatcraft.model;

import fr.univartois.butinfo.r304.flatcraft.model.map.AbstractCell;
import fr.univartois.butinfo.r304.flatcraft.model.resources.Resource;
import fr.univartois.butinfo.r304.flatcraft.view.Sprite;

public class MyCell extends AbstractCell {
    protected MyCell(int row, int column) {
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

        return false;
    }

    @Override
    public boolean dig(IMovable player) {

        return false;
    }
}
