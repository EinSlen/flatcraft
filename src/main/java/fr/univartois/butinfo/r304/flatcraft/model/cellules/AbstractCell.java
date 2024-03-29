/**
 * Ce logiciel est distribué à des fins éducatives.
 * Il est fourni "tel quel", sans garantie d’aucune sorte, explicite
 * ou implicite, notamment sans garantie de qualité marchande, d’adéquation
 * à un usage particulier et d’absence de contrefaçon.
 * En aucun cas, les auteurs ou titulaires du droit d’auteur ne seront
 * responsables de tout dommage, réclamation ou autre responsabilité, que ce
 * soit dans le cadre d’un contrat, d’un délit ou autre, en provenance de,
 * consécutif à ou en relation avec le logiciel ou son utilisation, ou avec
 * d’autres éléments du logiciel.
 * (c) 2023 Romain Wallon - Université d'Artois.
 * Tous droits réservés.
 */

package fr.univartois.butinfo.r304.flatcraft.model.cellules;

import fr.univartois.butinfo.r304.flatcraft.model.FlatcraftGame;
import fr.univartois.butinfo.r304.flatcraft.model.movables.IMovable;
import fr.univartois.butinfo.r304.flatcraft.model.resources.Resource;
import fr.univartois.butinfo.r304.flatcraft.view.Sprite;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * La classe {@link AbstractCell} fournit une implémentation de base pour l'interface
 * {@link Cell}.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public abstract class AbstractCell implements Cell {

    /**
     * La ligne où se trouve cette cellule dans la carte.
     */
    private int row;

    /**
     * La colonne où se trouve cette cellule dans la carte.
     */
    private int column;

    private CellState state;


    /**
     * La propriété contenant le sprite représentant le contenu de cette cellule sur la
     * carte.
     */
    private final ObjectProperty<Sprite> spriteProperty = new SimpleObjectProperty<>();

    /**
     * La propriété contenant la ressource présente sur cette cellule sur la carte.
     */
    private final ObjectProperty<Resource> resourceProperty = new SimpleObjectProperty<>();

    /**
     * Crée une nouvelle instance de AbstractCell.
     * La cellule créée est initialement vide.
     *
     * @param row La ligne où se trouve la cellule dans la carte.
     * @param column La colonne où se trouve la cellule dans la carte.
     */
    protected AbstractCell(int row, int column) {
        this.row = row;
        this.column = column;
        this.state = new ResourceCellState();
    }

    /**
     * Crée une nouvelle instance de AbstractCell.
     *
     * @param sprite La représentation du contenu initial de la cellule.
     */
    protected AbstractCell(Sprite sprite) {
        this.spriteProperty.set(sprite);
    }


    /**
     * Crée une nouvelle instance de AbstractCell.
     *
     * @param resource La ressource initialement présente sur la cellule.
     */
    protected AbstractCell(Resource resource) {
        this.resourceProperty.set(resource);
        this.spriteProperty.set(resource.getSprite());

    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.r304.flatcraft.model.cellules.Cell#getRow()
     */
    @Override
    public int getRow() {
        return row;
    }

    public void setState(CellState state) {
        this.state = state;
    }

    public CellState getState() {
        return state;
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.r304.flatcraft.model.cellules.Cell#getColumn()
     */
    @Override
    public int getColumn() {
        return column;
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.r304.flatcraft.model.cellules.Cell#getSprite()
     */
    @Override
    public Sprite getSprite() {
        return spriteProperty.get();
    }


    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.r304.flatcraft.model.cellules.Cell#getSpriteProperty()
     */
    @Override
    public ObjectProperty<Sprite> getSpriteProperty() {
        return spriteProperty;
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.r304.flatcraft.model.cellules.Cell#getResource()
     */
    @Override
    public Resource getResource() {
        return resourceProperty.get();
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.r304.flatcraft.model.cellules.Cell#getResourceProperty()
     */
    @Override
    public ObjectProperty<Resource> getResourceProperty() {
        return resourceProperty;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * fr.univartois.butinfo.r304.flatcraft.model.cellules.Cell#replaceBy(fr.univartois.butinfo.
     * r304.flatcraft.model.Cell)
     */
    @Override
    public void replaceBy(Cell cell) {
        spriteProperty.set(cell.getSprite());
        resourceProperty.set(cell.getResource());
    }

    @Override
    public boolean move(IMovable movable) {
        if(getResource() == null){
            int nouvelleLigne = getRow() * getSprite().getWidth();
            int nouvelleColonne = getColumn() * getSprite().getHeight();
            movable.setX(nouvelleColonne);
            movable.setY(nouvelleLigne);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean accepts(IMovable movable) {
        //return true;
        return state.accepts(movable);
    }
    @Override
    public boolean dig(IMovable player) {
        boolean result = state.interactWithPlayer(player, this);
        //state = new EmptyCellState();
        return result;
    }
}

