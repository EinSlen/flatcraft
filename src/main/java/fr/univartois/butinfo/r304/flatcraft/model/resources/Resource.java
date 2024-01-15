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

package fr.univartois.butinfo.r304.flatcraft.model.resources;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import fr.univartois.butinfo.r304.flatcraft.model.filon.InInventoryState;
import fr.univartois.butinfo.r304.flatcraft.model.filon.OnMapState;
import fr.univartois.butinfo.r304.flatcraft.model.filon.ResourceState;
import fr.univartois.butinfo.r304.flatcraft.model.fuel.FuelStrategy;
import fr.univartois.butinfo.r304.flatcraft.view.Sprite;

/**
 * Une ressource est un élément de la carte avec lequel le joueur peut interagir.
 * Il peut soit l'extraire, soit la laisser sur place.
 *
 * @author Daniel Le Berre
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public final class Resource implements Inventoriable {

    /**
     * Le nom unique identifiant le type de cette ressource.
     */
    private String name;

    private static final List<String> COMBUSTIBLE_RESOURCES = Arrays.asList("wood", "coal", "tree", "stick");

    /**
     * Le sprite représentant cette ressource.
     */
    private Sprite sprite;

    private FuelStrategy fuelStrategy;

    /**
     * Le type d'outils nécessaire pour extraire cette ressource de la carte.
     */
    private final ToolType toolType;


    private ResourceState currentState;

    /**
     * La dureté de cette ressource.
     * Il s'agit du nombre de coups devant être appliqués avec un outil pour extraire
     * cette ressource depuis la map.
     */
    private int hardness;

    private IState state;

    /**
     * Crée une nouvelle instance de Resource.
     *
     * @param name Le nom unique identifiant le type de la ressource.
     * @param sprite Le sprite représentant la ressource.
     * @param toolType Le type d'outils nécessaire pour extraire la ressource de la carte.
     * @param hardness La dureté initiale de la ressource.
     *
     * @throws IllegalArgumentException Si la valeur de {@code hardness} est négative.
     */
    public Resource(String name, Sprite sprite, ToolType toolType, int hardness) {
        if (hardness < 0 || hardness > 5 ) {
            throw new IllegalArgumentException("Resource hardness should be non-negative or above 5!");
        }
        this.name = name;
        this.sprite = sprite;
        this.toolType = toolType;
        this.hardness = hardness;
        this.currentState = changeCurrentState();
        this.state = initState(hardness);
    }

    private IState initState(int hardness){
        return switch (hardness) {
            case 0 -> new State0(this);
            case 1 -> new State1(this);
            case 2 -> new State2(this);
            case 3 -> new State3(this);
            case 4 -> new State4(this);
            case 5 -> new State5(this);
            default -> null;
        };
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.r304.flatcraft.model.resources.Inventoriable#getName()
     */
    @Override
    public String getName() {
        return name;
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.r304.flatcraft.model.resources.Inventoriable#getSprite()
     */
    @Override
    public Sprite getSprite() {
        return sprite;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    /**
     * Donne le type d'outils nécessaire pour extraire cette ressource de la carte.
=======
    /*
     * (non-Javadoc)
>>>>>>> 5da6b6e8cbd7d3adc6c1d522907d64255d054cc3
     *
     * @see fr.univartois.butinfo.r304.flatcraft.model.resources.Inventoriable#getToolType()
     */
    @Override
    public ToolType getToolType() {
        return toolType;
    }

    /**
     * Donne la dureté de cet élément.
     * Il s'agit du nombre de coups devant être appliqués avec un outil pour extraire
     * cet élément depuis la carte.
     *
     * @return La dureté de cet élément.
     */
    public int getHardness() {
        return state.getHardness();
    }

    /**
     * Donne un coup sur cette ressource pour l'extraire de la carte.
     * Cela réduit sa dureté.
     *
     * @throws IllegalStateException Si la dureté de la ressource est déjà égale
     *         à {@code 0}.
     */
    public void dig() {
        if (hardness <= 0) {
            throw new IllegalStateException("Cannot dig resource with 0 hardness!");
        }
        state.diminue();
    }

    public IState getState() {
        return state;
    }

    public ResourceState getCurrentState() {
        return currentState;
    }

    public ResourceState changeCurrentState() {
        if (this.getName().contains("mineral")) {
            return new InInventoryState();
        }
        return new OnMapState();
    }

    public void changeState(IState state){
        this.state = state;
    }

    public void changeState(ResourceState newState) {
        this.currentState = newState;
    }

    /**
     * Donne la ressource obtenue lorsque cette ressource est extraite de la carte.
     * Par défaut, la ressource obtenue ne change pas.
     *
     * @return La ressource obtenue après son extraction.
     */
    public Resource digBlock() {
        return this;
    }

    public boolean isUsableAsFuel() {
        return COMBUSTIBLE_RESOURCES.contains(name);
    }

    public void handleOnMap() {
        currentState.handleOnMap(this);
    }

    public void handleInInventory() {
        currentState.handleInInventory(this);
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.r304.flatcraft.model.resources.Inventoriable#execute()
     */
    @Override
    public void execute() {
        // On ne fait rien ici.
        // Une ressource ne propose pas d'action par défaut.
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Resource resource) {
            return name.equals(resource.name);
        }
        return false;
    }

}
