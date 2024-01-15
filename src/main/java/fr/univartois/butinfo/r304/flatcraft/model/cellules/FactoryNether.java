package fr.univartois.butinfo.r304.flatcraft.model.cellules;
import fr.univartois.butinfo.r304.flatcraft.model.resources.Resource;
import fr.univartois.butinfo.r304.flatcraft.model.resources.ToolType;
import fr.univartois.butinfo.r304.flatcraft.view.ISpriteStore;
import fr.univartois.butinfo.r304.flatcraft.view.Sprite;
import fr.univartois.butinfo.r304.flatcraft.view.SpriteStore;
import java.util.Random;

public class FactoryNether implements CellFactory {

    SpriteStore spriteStore;

    private static FactoryNether instance;

    Random random = new Random();

    private FactoryNether(){}

    public static FactoryNether getInstance(){
        if (instance == null){
            instance = new FactoryNether();
        }
        return instance;
    }

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
            return createRessouresCell("lava",ToolType.MEDIUM_TOOL,1);
        return createRessouresCell("obsidian",ToolType.HARD_TOOL,2);
    }

    @Override
    public Cell createSubSoil() {
        return createRessouresCell("obsidian",ToolType.HARD_TOOL,2);
    }

    @Override
    public Cell createTrunk() {
        return createCell("lava");
    }

    @Override
    public Cell createLeaves() {
        return createRessouresCell("lava",ToolType.MEDIUM_TOOL,1);
    }

    @Override
    public Cell createJunglegrass() {
        return createRessouresCell("coal_block",ToolType.MEDIUM_TOOL,1);
    }

    @Override
    public Cell createStone() {
        return createRessouresCell("stone",ToolType.MEDIUM_TOOL,2);
    }

    public MyCell createCell(String name){
        Sprite sprite = spriteStore.getSprite(name);
        return new MyCell(sprite);
    }

    public Cell createRessouresCell(String name, ToolType tool, int hardness){
        Sprite sprite = spriteStore.getSprite(name);
        return new MyCell(new Resource(name, sprite, tool, hardness));
    }
}
