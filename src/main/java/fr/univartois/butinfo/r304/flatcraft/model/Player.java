package fr.univartois.butinfo.r304.flatcraft.model;

import fr.univartois.butinfo.r304.flatcraft.model.movables.AbstractMovable;
import fr.univartois.butinfo.r304.flatcraft.model.resources.Resource;
import fr.univartois.butinfo.r304.flatcraft.view.Sprite;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class Player extends AbstractMovable {

    /**
     * Attribut représentant les points de vie du joueur
     */
    private IntegerProperty pv;

    /**
     * Attribut représentant l'expéience du joueur
     */
    private IntegerProperty xp;

    /**
     * Attribut représentant l'inventaire du joueur
     */
    private ObservableMap<Resource, Integer> inventaire;

    /**
     * Crée une nouvelle instance de AbstractMovable.
     *
     * @param game      Le jeu dans lequel l'objet évolue.
     * @param xPosition La position en x initiale de l'objet.
     * @param yPosition La position en y initiale de l'objet.
     * @param sprite    L'instance de {@link Sprite} représentant l'objet.
     */
    public Player(FlatcraftGame game, double xPosition, double yPosition, Sprite sprite, IntegerProperty pv, IntegerProperty xp, ObservableMap<Resource, Integer> inventaire) {
        super(game, xPosition, yPosition, sprite);
        pv = new SimpleIntegerProperty(3);
        xp = new SimpleIntegerProperty(1);
        this.inventaire = FXCollections.observableHashMap();
    }

    /**
     * Getter de l'attribut pv
     *
     * @return int
     */
    public int getPv() {
        return pv.get();
    }

    /**+-
     * @return IntegerProperty
     */
    public IntegerProperty pvProperty() {
        return pv;
    }

    public int getXp() {
        return xp.get();
    }

    public IntegerProperty xpProperty() {
        return xp;
    }

    public ObservableMap<Resource, Integer> getInventaire() {
        return inventaire;
    }

    public void setPv(int pv) {
        this.pv.set(pv);
    }

    public void setXp(int xp) {
        this.xp.set(xp);
    }

    public void ajouterInventaire(Resource r){
        Integer v = this.inventaire.get(r);
        this.inventaire.replace(r, v+1);
    }

    public void supprimerInventaire(Resource r){
        Integer v = this.inventaire.get(r);
        this.inventaire.replace(r, v-1);
    }

}
