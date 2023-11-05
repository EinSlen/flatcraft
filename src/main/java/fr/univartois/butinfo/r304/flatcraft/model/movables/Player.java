package fr.univartois.butinfo.r304.flatcraft.model.movables;

import fr.univartois.butinfo.r304.flatcraft.model.FlatcraftGame;
import fr.univartois.butinfo.r304.flatcraft.model.movables.AbstractMovable;
import fr.univartois.butinfo.r304.flatcraft.model.resources.Resource;
import fr.univartois.butinfo.r304.flatcraft.view.Sprite;
import javafx.beans.property.IntegerProperty;
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
        this.pv = pv;
        this.xp = xp;
        this.inventaire = inventaire;
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
        if(inventaire.containsKey(r)){
            inventaire.replace(r, inventaire.get(r)+1);
        } else
            inventaire.put(r, 1);
    }

    public void supprimerInventaire(Resource r){
        Integer v = this.inventaire.get(r);
        this.inventaire.replace(r, v-1);
    }

}