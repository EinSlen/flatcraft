package fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.strategy.aleatoire;

import fr.univartois.butinfo.r304.flatcraft.model.movables.IMovable;
import fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.strategy.IDeplacement;
import fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.strategy.lineaire.DeplacementLineare;

public class DeplacementAleatoire implements IDeplacement {
    private static DeplacementAleatoire instance;
    private DeplacementAleatoire(){}

    public static DeplacementAleatoire getInstance(){
        if(instance==null) instance = new DeplacementAleatoire();
        return instance;
    }
    @Override
    public void move(IMovable movable, long delta) {
        if(delta%5000==0){
            int direction = (int)(Math.random() * 4);
            switch (direction) {
                case 0:
                    movable.setHorizontalSpeed(-50);
                    break;
                case 1:
                    movable.setHorizontalSpeed(50);
                    break;
                case 2:
                    movable.setHorizontalSpeed(-150);
                    break;
                case 3:
                    movable.setHorizontalSpeed(150);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + direction);
            }
        }
    }
}
