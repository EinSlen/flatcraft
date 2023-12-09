package fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.strategy.intelligent;

import fr.univartois.butinfo.r304.flatcraft.model.FlatcraftGame;
import fr.univartois.butinfo.r304.flatcraft.model.GameMap;
import fr.univartois.butinfo.r304.flatcraft.model.cellules.CellFactory;
import fr.univartois.butinfo.r304.flatcraft.model.cellules.Factory;
import fr.univartois.butinfo.r304.flatcraft.model.map.WorldMapEngine;
import fr.univartois.butinfo.r304.flatcraft.model.movables.IMovable;
import fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.strategy.IDeplacement;
import fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.strategy.aleatoire.DeplacementAleatoire;

public class DeplacementIntelligent implements IDeplacement {
    private static DeplacementIntelligent instance;
    private DeplacementIntelligent(){}

    public static DeplacementIntelligent getInstance(){
        if(instance==null) instance = new DeplacementIntelligent();
        return instance;
    }
    @Override
    public void move(IMovable movable, long delta) {
        int x = movable.getX();
        if(x<= 0) movable.setHorizontalSpeed(150);
        if(x> FlatcraftGame.getInstance().getWidth()-movable.getWidth()-1) movable.setHorizontalSpeed(-150);
    }
}
