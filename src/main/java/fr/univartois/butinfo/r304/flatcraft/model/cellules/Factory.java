package fr.univartois.butinfo.r304.flatcraft.model.cellules;
import fr.univartois.butinfo.r304.flatcraft.model.resources.Resource;
import fr.univartois.butinfo.r304.flatcraft.model.resources.ToolType;
import fr.univartois.butinfo.r304.flatcraft.view.ISpriteStore;
import fr.univartois.butinfo.r304.flatcraft.view.Sprite;
import fr.univartois.butinfo.r304.flatcraft.view.SpriteStore;
import java.util.Random;

public class Factory implements CellFactory {

    SpriteStore spriteStore;
    Random random = new Random();
    @Override
    public void setSpriteStore(ISpriteStore spriteStore) {
        this.spriteStore = (SpriteStore) spriteStore;
    }

    @Override
    public Cell createSky() {
        if(random.nextInt(10)<2)
            return createRessouresCell("cloud");
        return createCell("ice");
    }

    @Override
    public Cell createSoilSurface() {
        if(random.nextInt(10)<2)
            return createRessouresCell("water");
        return createRessouresCell("grass");
    }

    public Cell createJunglegrass() {
        return createRessouresCell("junglegrass");
    }

    @Override
    public Cell createSubSoil() {
        return createRessouresCell("dirt");
    }

    @Override
    public Cell createTrunk() {
        return createCell("tree");
    }

    @Override
    public Cell createLeaves() {
        return createRessouresCell("acacia_leaves");
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
