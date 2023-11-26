package fr.univartois.butinfo.r304.flatcraft.model.cellules;

import fr.univartois.butinfo.r304.flatcraft.model.movables.IMovable;
import fr.univartois.butinfo.r304.flatcraft.model.resources.Resource;
import fr.univartois.butinfo.r304.flatcraft.view.Sprite;

public class MyCell extends AbstractCell {
    private CellState state;

    public MyCell(int row, int column) {
        super(row, column);
        this.state = new EmptyCellState();
    }

    protected MyCell(Sprite sprite) {
        super(sprite);
        this.state = new EmptyCellState();
    }

    protected MyCell(Resource resource) {
        super(resource);
        this.state = new ResourceCellState();
    }

    public void setState(CellState state) {
        this.state = state;
    }

    @Override
    public boolean move(IMovable movable) {
        state.interactWithPlayer(movable, this);
        return true;
    }

    @Override
    public boolean dig(IMovable player) {
        state.interactWithPlayer(player, this);
        return true;
    }

}
