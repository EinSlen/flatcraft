package fr.univartois.butinfo.r304.flatcraft.model;

import fr.univartois.butinfo.r304.flatcraft.model.map.SimpleGameMap;

public interface IMapGenerator {


    public void generateMap(int height, int width, CellFactory cell);

    public SimpleGameMap getMap();

}
