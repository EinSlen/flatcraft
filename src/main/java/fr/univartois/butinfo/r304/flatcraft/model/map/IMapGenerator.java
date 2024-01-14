package fr.univartois.butinfo.r304.flatcraft.model.map;

import fr.univartois.butinfo.r304.flatcraft.model.cellules.CellFactory;

public interface IMapGenerator {


    void generateMap(int height, int width, CellFactory cell);

    SimpleGameMap getMap();

}
