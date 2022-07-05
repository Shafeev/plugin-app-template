package mcs.plugin.app.core;

public class BasicService implements Service {
    @Override
    public void doJob() {
        System.out.println("Basic service");
    }
}
