package fr.univartois.butinfo.r304.flatcraft.model.compositeArbreTerri;

import java.util.ArrayList;
import java.util.List;

public class FactoryComposite implements IComponent {
    private List<IComponent> objets = new ArrayList<>();

    //Au cas où jveux mettre des arbres en plus dans la map
    public void ajouter(IComponent objet) {
        objets.add(objet);
    }

    @Override
    public void ajouterAleatoires() {
        for (IComponent objet : objets) {
            objet.ajouterAleatoires();
        }
    }
}
