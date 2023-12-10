package fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.strategy.aleatoire;

import fr.univartois.butinfo.r304.flatcraft.model.movables.IMovable;
import fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.strategy.IDeplacement;
import fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.strategy.lineaire.DeplacementLineare;

import java.util.Random;

import static java.lang.Math.*;

public class DeplacementAleatoire implements IDeplacement {
    private static DeplacementAleatoire instance;

    private long time;
    private DeplacementAleatoire(){}
    Random random = new Random();

    public static DeplacementAleatoire getInstance(){
        if(instance==null) instance = new DeplacementAleatoire();
        return instance;
    }
    @Override
    public void move(IMovable movable, long delta) {
        time +=delta;
        if(time/3000==1){
            switch (random.nextInt(4)) {
                case 0:
                    movable.setHorizontalSpeed(-50);
                    time=0;
                    break;
                case 1:
                    movable.setHorizontalSpeed(50);
                    time=0;
                    break;
                case 2:
                    movable.setHorizontalSpeed(-150);
                    time=0;
                    break;
                case 3:
                    movable.setHorizontalSpeed(150);
                    time=0;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: ");
            }
        }
    }
}
