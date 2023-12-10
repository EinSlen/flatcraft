package fr.univartois.butinfo.r304.flatcraft.model.cellules;

import fr.univartois.butinfo.r304.flatcraft.model.resources.Resource;
import fr.univartois.butinfo.r304.flatcraft.model.resources.ToolType;
import fr.univartois.butinfo.r304.flatcraft.view.ISpriteStore;
import fr.univartois.butinfo.r304.flatcraft.view.Sprite;
import fr.univartois.butinfo.r304.flatcraft.view.SpriteStore;

import java.util.Random;

public class FactoryEnd implements CellFactory {
    SpriteStore spriteStore;

    private static FactoryEnd instance;

    private FactoryEnd(){}

    public static FactoryEnd getInstance(){
        if (instance == null){
            instance = new FactoryEnd();
        }
        return instance;
    }
    Random random = new Random();
    @Override
    public void setSpriteStore(ISpriteStore spriteStore) {
        this.spriteStore = (SpriteStore) spriteStore;
    }

    @Override
    public Cell createSky() {
        return createCell("obsidian");
    }

    @Override
    public Cell createSoilSurface() {
        return createRessouresCell("sandstone",2);
    }

    @Override
    public Cell createSubSoil() {
        return createRessouresCell("sandstone",2);
    }

    @Override
    public Cell createTrunk() {
        return createCell("tree");
    }

    @Override
    public Cell createLeaves() {
        return createRessouresCell("obsidian_brick",1);
    }

    @Override
    public Cell createJunglegrass() {
        return createRessouresCell("sandstone",1);
    }

    @Override
    public Cell createStone() {
        return createRessouresCell("stone",2);
    }

    public MyCell createCell(String name){
        Sprite sprite = spriteStore.getSprite(name);
        return new MyCell(sprite);
    }

    public Cell createRessouresCell(String name, int hardness){
        Sprite sprite = spriteStore.getSprite(name);
        return new MyCell(new Resource(name, sprite, ToolType.NO_TOOL, hardness));
    }
}
