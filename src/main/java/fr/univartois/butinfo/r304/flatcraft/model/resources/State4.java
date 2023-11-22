package fr.univartois.butinfo.r304.flatcraft.model.resources;

public class State4 implements IState{

    private final int hardness = 4;
    private final Resource resource;

    public State4(Resource resource) {
        this.resource = resource;
    }

    @Override
    public void diminue() {
        resource.changeState(new State3(resource));
    }

    @Override
    public int getHardness() {
        return this.hardness;
    }
}
