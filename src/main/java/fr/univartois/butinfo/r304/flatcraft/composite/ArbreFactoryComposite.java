package fr.univartois.butinfo.r304.flatcraft.composite;

import java.util.ArrayList;
import java.util.List;

public class ArbreFactoryComposite implements ComponentArbre {
    private List<ComponentArbre> arbres = new ArrayList<>();

    public void ajouterArbre(ComponentArbre arbre) {
        arbres.add(arbre);
    }

    public void ajouterArbresAleatoires() {
        for (ComponentArbre arbre : arbres) {
            arbre.ajouterArbresAleatoires();
        }
    }
}
