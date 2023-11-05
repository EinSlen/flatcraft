package fr.univartois.butinfo.r304.flatcraft.model.map;

import fr.univartois.butinfo.r304.flatcraft.model.cellules.CellFactory;

public interface IMapGenerator {


    public void generateMap(int height, int width, CellFactory cell);

    public SimpleGameMap getMap();

}
