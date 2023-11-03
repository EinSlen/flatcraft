package fr.univartois.butinfo.r304.flatcraft.model;

import java.util.Random;

public class TreeMap {
    private int hauteurTronc;
    private int nombreBlocsFeuillage;

    public TreeMap(int hauteurLimite) {
        Random random = new Random();
        this.hauteurTronc = random.nextInt(hauteurLimite) + 1; // Hauteur aléatoire entre 1 et la hauteur limite
        this.nombreBlocsFeuillage = this.hauteurTronc / 2; // Nombre de blocs de feuillage est la moitié de la hauteur du tronc
    }

    public int getHauteurTronc() {
        return hauteurTronc;
    }

    public int getNombreBlocsFeuillage() {
        return nombreBlocsFeuillage;
    }
}
