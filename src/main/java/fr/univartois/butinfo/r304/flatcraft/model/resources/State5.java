package fr.univartois.butinfo.r304.flatcraft.model.resources;

public class State5 implements IState {
    private final int hardness = 5;
    private final Resource resource;


    public State5(Resource resource) {
        this.resource = resource;
    }

    @Override
    public void diminue() {
        resource.changeState(new State4(resource));
    }

    @Override
    public int getHardness() {
        return this.hardness;
    }
}
