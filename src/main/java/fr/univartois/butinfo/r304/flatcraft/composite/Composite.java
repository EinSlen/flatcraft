package fr.univartois.butinfo.r304.flatcraft.composite;

import java.util.ArrayList;
import java.util.List;

public class Composite extends Component {
    private List<Component> composants = new ArrayList<>();

    public void ajouterComposant(Component composant) {
        composants.add(composant);
    }

    public void supprimerComposant(Component composant) {
        composants.remove(composant);
    }

    public void dessiner() {
        for (Component composant : composants) {
            composant.dessiner();
        }
    }
}