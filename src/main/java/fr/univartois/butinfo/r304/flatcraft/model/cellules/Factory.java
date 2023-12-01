package fr.univartois.butinfo.r304.flatcraft.model.cellules;
import fr.univartois.butinfo.r304.flatcraft.model.resources.Resource;
import fr.univartois.butinfo.r304.flatcraft.model.resources.ToolType;
import fr.univartois.butinfo.r304.flatcraft.view.ISpriteStore;
import fr.univartois.butinfo.r304.flatcraft.view.Sprite;
import fr.univartois.butinfo.r304.flatcraft.view.SpriteStore;
import java.util.Random;

public class Factory implements CellFactory {

    SpriteStore spriteStore;

    private static Factory instance;
    Random random = new Random();

    private Factory(){}

    public static Factory getInstance(){
        if (instance == null){
            instance = new Factory();
        }
        return instance;
    }
    @Override
    public void setSpriteStore(ISpriteStore spriteStore) {
        this.spriteStore = (SpriteStore) spriteStore;
    }

    @Override
    public Cell createSky() {
        if(random.nextInt(10)<2)
            return createCell("cloud");
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
    public Cell createStone() {
        int randomValue = random.nextInt(100);
        int mineral = random.nextInt(100);

        if (randomValue < 80) {
            return createRessouresCell("stone");
        } else {
            if (mineral < 5) {
                return createRessouresCell("mineral_coal");
            } else if (mineral < 15) {
                return createRessouresCell("mineral_copper");
            } else if (mineral < 30) {
                return createRessouresCell("mineral_iron");
            } else if (mineral < 50) {
                return createRessouresCell("mineral_gold");
            } else if (mineral < 80) {
                return createRessouresCell("mineral_diamond");
            }
        }
        return createRessouresCell("stone");
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
