package fr.univartois.butinfo.r304.flatcraft.model.mobs.fabrique.normal;

import fr.univartois.butinfo.r304.flatcraft.model.FlatcraftGame;
import fr.univartois.butinfo.r304.flatcraft.model.IMovable;
import fr.univartois.butinfo.r304.flatcraft.model.mobs.fabrique.IMobMake;
import fr.univartois.butinfo.r304.flatcraft.model.mobs.strategy.IDeplacement;
import fr.univartois.butinfo.r304.flatcraft.model.mobs.strategy.Mob;
import fr.univartois.butinfo.r304.flatcraft.view.Sprite;
import fr.univartois.butinfo.r304.flatcraft.view.SpriteStore;

public class MakeModNormal implements IMobMake {
    @Override
    public IMovable makeMobForDimension(FlatcraftGame game, IDeplacement deplacement) {
        SpriteStore spriteStore = new SpriteStore();
        Sprite sprite= spriteStore.getSprite("nc_front");
        return new Mob(game,0,0, sprite, deplacement);
    }
}
