package mcs.plugin.app.core;

import java.lang.module.Configuration;
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Path pluginsDir = Paths.get("plugins");

        // Будем искать плагины в папке plugins
        ModuleFinder pluginsFinder = ModuleFinder.of(pluginsDir);

        // Пусть ModuleFinder найдёт все модули в папке plugins и вернёт нам список их имён
        List<String> plugins = pluginsFinder
                .findAll()
                .stream()
                .map(ModuleReference::descriptor)
                .map(ModuleDescriptor::name)
                .collect(Collectors.toList());

        // Создадим конфигурацию, которая выполнит резолюцию указанных модулей (проверит корректность графа зависимостей)
        Configuration pluginsConfiguration = ModuleLayer
                .boot()
                .configuration()
                .resolve(pluginsFinder, ModuleFinder.of(), plugins);

        // Создадим слой модулей для плагинов
        ModuleLayer layer = ModuleLayer
                .boot()
                .defineModulesWithOneLoader(pluginsConfiguration, ClassLoader.getSystemClassLoader());

        // Найдём все реализации сервиса Service в слое плагинов и в слое Boot
        List<Service> services = Service.getServices(layer);
        for (Service service : services) {
            service.doJob();
        }
    }

}
