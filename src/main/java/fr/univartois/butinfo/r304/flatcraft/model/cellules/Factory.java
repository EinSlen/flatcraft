package fr.univartois.butinfo.r304.flatcraft.model.cellules;
import fr.univartois.butinfo.r304.flatcraft.model.resources.Resource;
import fr.univartois.butinfo.r304.flatcraft.model.resources.ToolType;
import fr.univartois.butinfo.r304.flatcraft.view.ISpriteStore;
import fr.univartois.butinfo.r304.flatcraft.view.Sprite;
import fr.univartois.butinfo.r304.flatcraft.view.SpriteStore;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

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
            return createRessouresCell("ice","water",1);
        return createRessouresCell("grass",1);
    }

    public Cell createJunglegrass() {
        return createRessouresCell("ice","junglegrass",1);
    }

    @Override
    public Cell createStone() {
        int randomValue = random.nextInt(100);
        int mineral = random.nextInt(100);

        if (randomValue < 80) {
            return createRessouresCell("stone",2);
        } else {
            if (mineral < 5) {
                return createRessouresCell("stone","mineral_coal",2);
            } else if (mineral < 15) {
                return createRessouresCell("stone","mineral_copper",2);
            } else if (mineral < 30) {
                return createRessouresCell("stone","mineral_iron",3);
            } else if (mineral < 50) {
                return createRessouresCell("stone","mineral_gold",4);
            } else if (mineral < 80) {
                return createRessouresCell("stone","mineral_diamond",5);
            }
        }
        return createRessouresCell("stone","stone",2);
    }

    @Override
    public Cell createSubSoil() {
        return createRessouresCell("dirt",2);
    }

    @Override
    public Cell createTrunk() {
        return createRessouresCell("tree", 2);
    }

    @Override
    public Cell createLeaves() {
        return createRessouresCell("ice","acacia_leaves",1);
    }



    public MyCell createCell(String name){
        Sprite sprite = spriteStore.getSprite(name);
        return new MyCell(sprite);
    }

    public Cell createRessouresCell(String name , int hardness){
        Sprite sprite = spriteStore.getSprite(name);
        return new MyCell(new Resource(name, sprite, ToolType.NO_TOOL, hardness));
    }

    private Cell createRessouresCell(String background, String name, int hardness){
        Sprite sprite1 = spriteStore.getSprite(background);
        Sprite sprite2 = spriteStore.getSprite(name);
        Image image = makeImageWithBackground(sprite1,sprite2);
        Sprite sprite = new Sprite(image);
        return new MyCell(new Resource(name, sprite, ToolType.NO_TOOL, hardness));
    }



    public Image makeImageWithBackground(Sprite sprite1, Sprite sprite2){
        Canvas canvas = new Canvas(sprite1.getWidth(),sprite2.getHeight());
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.drawImage(sprite1.getImage(),0,0);
        graphicsContext.drawImage(sprite2.getImage(),0,0);
        SnapshotParameters snapshotParameters = new SnapshotParameters();
        WritableImage image = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        return canvas.snapshot(snapshotParameters, image);
    }
}
