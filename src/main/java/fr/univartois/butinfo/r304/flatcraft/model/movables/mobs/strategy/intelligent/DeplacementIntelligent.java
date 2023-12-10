package fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.strategy.intelligent;

import fr.univartois.butinfo.r304.flatcraft.model.FlatcraftGame;
import fr.univartois.butinfo.r304.flatcraft.model.GameMap;
import fr.univartois.butinfo.r304.flatcraft.model.cellules.Cell;
import fr.univartois.butinfo.r304.flatcraft.model.cellules.CellFactory;
import fr.univartois.butinfo.r304.flatcraft.model.cellules.Factory;
import fr.univartois.butinfo.r304.flatcraft.model.map.WorldMapEngine;
import fr.univartois.butinfo.r304.flatcraft.model.movables.IMovable;
import fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.strategy.IDeplacement;
import fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.strategy.aleatoire.DeplacementAleatoire;
import fr.univartois.butinfo.r304.flatcraft.view.SpriteStore;

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
        reperePlayer(movable);
    }

    private void reperePlayer(IMovable movable){
        IMovable player = FlatcraftGame.getInstance().getPlayer();
        Cell cellPlayer = getCellOf(player);
        Cell cellMovable = getCellOf(movable);
        //System.out.println(cellPlayer.getColumn()-cellMovable.getColumn());
        if(cellPlayer.getColumn()-cellMovable.getColumn()==-10 && cellPlayer.getRow()==cellMovable.getRow()){
            movable.setHorizontalSpeed(-50);
        } else if (cellPlayer.getColumn()-cellMovable.getColumn()==10 && cellPlayer.getRow()==cellMovable.getRow()){
            movable.setHorizontalSpeed(50);
        }
    }

    private Cell getCellOf(IMovable movable) {
        // On commence par récupérer la position du centre de l'objet.
        int midX = movable.getX() + (movable.getWidth() / 2);
        int midY = movable.getY() + (movable.getHeight() / 2);

        // On traduit cette position en position dans la carte.
        int row = midY / SpriteStore.getInstance().getSpriteSize();
        int column = midX / SpriteStore.getInstance().getSpriteSize();

        // On récupère enfin la cellule à cette position dans la carte.
        return WorldMapEngine.getInstance().getTableauActuel().getAt(row, column);
    }
}
