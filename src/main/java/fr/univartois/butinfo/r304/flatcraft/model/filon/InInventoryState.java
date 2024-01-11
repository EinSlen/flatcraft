package fr.univartois.butinfo.r304.flatcraft.model.filon;

import fr.univartois.butinfo.r304.flatcraft.model.resources.Resource;
import fr.univartois.butinfo.r304.flatcraft.view.SpriteStore;

public class InInventoryState implements ResourceState {


    @Override
    public void handleOnMap(Resource resource) {
        resource.changeState(new OnMapState());
    }

    public void handleInInventory(Resource resource) {
        //mineral_MINERAIS => MINERAIS_lump
        resource.setName(resource.getName().split("_")[1].toLowerCase() +"_lump");
        resource.setSprite(SpriteStore.getInstance().getSprite(resource.getName()));
        resource.changeState(new OnMapState());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return true;
    }
}