package fr.univartois.butinfo.r304.flatcraft.model.resources;

public class State3 implements IState{

    private final int hardness = 3;
    private final Resource resource;

    public State3(Resource resource) {
        this.resource = resource;
    }

    @Override
    public void diminue() {
        resource.changeState(new State2(resource));
    }

    @Override
    public int getHardness() {
        return this.hardness;
    }
}
