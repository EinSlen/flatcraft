package fr.univartois.butinfo.r304.flatcraft.model;

import fr.univartois.butinfo.r304.flatcraft.view.ISpriteStore;
import fr.univartois.butinfo.r304.flatcraft.view.Sprite;
import javafx.scene.image.Image;

public class Factory implements CellFactory{
    @Override
    public void setSpriteStore(ISpriteStore spriteStore) {

    }

    @Override
    public Cell createSky() {
        Image image = new Image("./../view/images/air.png");
        Sprite sprite = new Sprite(image);
        MyCell myCell = new MyCell(sprite);
        return myCell;
    }

    @Override
    public Cell createSoilSurface() {
        Image image = new Image("./../view/images/default_grass.png");
        Sprite sprite = new Sprite(image);
        MyCell myCell = new MyCell(sprite);
        return myCell;
    }

    @Override
    public Cell createSubSoil() {
        Image image = new Image("./../view/images/default_stone.png");
        Sprite sprite = new Sprite(image);
        MyCell myCell = new MyCell(sprite);
        return myCell;
    }

    @Override
    public Cell createTrunk() {
        Image image = new Image("./../view/images/default_chest_front.png");
        Sprite sprite = new Sprite(image);
        MyCell myCell = new MyCell(sprite);
        return myCell;
    }

    @Override
    public Cell createLeaves() {
        Image image = new Image("./../view/images/default_acacia_leaves.png");
        Sprite sprite = new Sprite(image);
        MyCell myCell = new MyCell(sprite);
        return myCell;
    }
}
