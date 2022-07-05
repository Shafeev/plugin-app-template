package mcs.plugin.app.core;

import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

public interface Service {
    void doJob();

    static List<Service> getServices(ModuleLayer layer) {
        return ServiceLoader
                .load(layer, Service.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .collect(Collectors.toList());
    }
}
