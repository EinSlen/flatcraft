package fr.univartois.butinfo.r304.flatcraft.model.cellules;
import fr.univartois.butinfo.r304.flatcraft.model.resources.Resource;
import fr.univartois.butinfo.r304.flatcraft.model.resources.ToolType;
import fr.univartois.butinfo.r304.flatcraft.view.ISpriteStore;
import fr.univartois.butinfo.r304.flatcraft.view.Sprite;
import fr.univartois.butinfo.r304.flatcraft.view.SpriteStore;
import java.util.Random;

public class FactoryNether implements CellFactory {

    SpriteStore spriteStore;
    Random random = new Random();
    @Override
    public void setSpriteStore(ISpriteStore spriteStore) {
        this.spriteStore = (SpriteStore) spriteStore;
    }

    @Override
    public Cell createSky() {
        return createCell("coal_block");
    }

    @Override
    public Cell createSoilSurface() {
        if(random.nextInt(10)<2)
            return createRessouresCell("lava");
        return createRessouresCell("obsidian");
    }

    @Override
    public Cell createSubSoil() {
        return createRessouresCell("obsidian");
    }

    @Override
    public Cell createTrunk() {
        return createCell("lava");
    }

    @Override
    public Cell createLeaves() {
        return createRessouresCell("lava");
    }

    @Override
    public Cell createJunglegrass() {
        return createRessouresCell("coal_block");
    }

    public MyCell createCell(String name){
        Sprite sprite = spriteStore.getSprite(name);
        return new MyCell(sprite);
    }

    public Cell createRessouresCell(String name){
        Sprite sprite = spriteStore.getSprite(name);
        return new MyCell(new Resource(name, sprite, ToolType.NO_TOOL, 1));
    }
}
