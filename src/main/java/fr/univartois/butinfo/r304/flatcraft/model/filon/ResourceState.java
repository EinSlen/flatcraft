package fr.univartois.butinfo.r304.flatcraft.model.filon;

import fr.univartois.butinfo.r304.flatcraft.model.resources.Resource;

public interface ResourceState {
    void handleOnMap(Resource resource);

    void handleInInventory(Resource resource);

}