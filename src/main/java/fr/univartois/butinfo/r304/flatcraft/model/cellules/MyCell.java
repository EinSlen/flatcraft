package fr.univartois.butinfo.r304.flatcraft.model.cellules;

import fr.univartois.butinfo.r304.flatcraft.model.FlatcraftGame;
import fr.univartois.butinfo.r304.flatcraft.model.movables.IMovable;
import fr.univartois.butinfo.r304.flatcraft.model.resources.Inventoriable;
import fr.univartois.butinfo.r304.flatcraft.model.resources.Resource;
import fr.univartois.butinfo.r304.flatcraft.view.Sprite;

public class MyCell extends AbstractCell {
    private CellState state;
    private FlatcraftGame game = FlatcraftGame.getInstance();

    public MyCell(int row, int column) {
        super(row, column);
        this.state = new ResourceCellState();
    }

    protected MyCell(Sprite sprite) {
        super(sprite);
        this.state = new ResourceCellState();
    }

    protected MyCell(Resource resource) {
        super(resource);
        this.state = new EmptyCellState();
    }

    @Override
    public boolean setResource(Inventoriable resource) {
        return false;
    }

    @Override
    public void setState(CellState state) {
        this.state = state;
    }

    @Override
    public boolean accepts(IMovable movable) {
        return state.accepts(movable);
    }

    @Override
    public boolean move(IMovable movable) {
        return super.move(movable);
    }


    @Override
    public boolean dig(IMovable player) {
        state = new EmptyCellState();
        return super.dig(player);
    }

    @Override
    public void execute() {
            Resource resource = getResource();
            if (resource != null && resource.getName().equals("obsidian")) {
                game.changeDimensionV1();
            }
    }

}
