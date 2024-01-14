package fr.univartois.butinfo.r304.flatcraft.model.resources;

public class State0 implements IState{
    private final Resource resource;

    public State0(Resource resource) {
        this.resource = resource;
    }

    @Override
    public void diminue() {
        //
    }
    @Override
    public int getHardness() {
        return 0;
    }
}
