package fr.univartois.butinfo.r304.flatcraft.model.filon;

import fr.univartois.butinfo.r304.flatcraft.model.resources.Resource;
import fr.univartois.butinfo.r304.flatcraft.view.SpriteStore;

public class OnMapState implements ResourceState {
    public void handleOnMap(Resource resource) {
        //mettre à jour le sprite, etc.

        resource.setSprite(SpriteStore.getInstance().getSprite(resource.getName()));
        resource.changeState(new OnMapState());
    }

    public void handleInInventory(Resource resource) {
        resource.changeState(new InInventoryState());
    }
}