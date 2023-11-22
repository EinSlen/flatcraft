package fr.univartois.butinfo.r304.flatcraft.model.resources;

public class State1 implements IState{
    private final int hardness = 1;
    private final Resource resource;

    public State1(Resource resource) {
        this.resource = resource;
    }

    @Override
    public void diminue() {
        resource.changeState(new State0(resource));
    }
    @Override
    public int getHardness() {
        return hardness;
    }
}
