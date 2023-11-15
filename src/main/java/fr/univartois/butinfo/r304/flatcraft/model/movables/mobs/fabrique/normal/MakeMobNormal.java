package fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.fabrique.normal;

import fr.univartois.butinfo.r304.flatcraft.model.FlatcraftGame;
import fr.univartois.butinfo.r304.flatcraft.model.movables.IMovable;
import fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.fabrique.IMobMake;
import fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.strategy.IDeplacement;
import fr.univartois.butinfo.r304.flatcraft.model.movables.mobs.strategy.Mob;
import fr.univartois.butinfo.r304.flatcraft.view.Sprite;
import fr.univartois.butinfo.r304.flatcraft.view.SpriteStore;

public class MakeMobNormal implements IMobMake {
    @Override
    public IMovable render(FlatcraftGame game, IDeplacement deplacement) {
        SpriteStore spriteStore = SpriteStore.getInstance();
        Sprite sprite= spriteStore.getSprite("nc_front");
        return new Mob(game,0,game.getMap().getSoilHeight()*spriteStore.getSpriteSize()-spriteStore.getSpriteSize(), sprite, deplacement);
    }
}
