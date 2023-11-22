package fr.univartois.butinfo.r304.flatcraft.model.resources;

public class State2 implements IState{
    private final int hardness = 2;
    private final Resource resource;

    public State2(Resource resource) {
        this.resource = resource;
    }

    @Override
    public void diminue() {
        resource.changeState(new State1(resource));
    }
    @Override
    public int getHardness() {
        return hardness;
    }
}
