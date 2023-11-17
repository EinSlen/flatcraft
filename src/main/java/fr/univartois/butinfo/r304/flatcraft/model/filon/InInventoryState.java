package fr.univartois.butinfo.r304.flatcraft.model.filon;

import fr.univartois.butinfo.r304.flatcraft.model.resources.Resource;
import fr.univartois.butinfo.r304.flatcraft.view.SpriteStore;

public class InInventoryState implements ResourceState {
    @Override
    public void handleOnMap(Resource resource) {
        resource.changeState(new OnMapState());
    }

    @Override
    public void handleInInventory(Resource resource) {
        // mettre à jour le sprite, etc.
        resource.setSprite(new SpriteStore().getSprite(resource.getName()+"_lump"));
        resource.changeState(new InInventoryState());
    }
}