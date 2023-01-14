package mcs.plugin.app.core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
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
        JFrame mainFrame = new JFrame("Plugin test");
        final JFrame frame = mainFrame;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 120);
        frame.getContentPane().setLayout(new FlowLayout());

        Path pluginsDir = Paths.get("plugins");
        System.out.println(pluginsDir.toAbsolutePath().toString());

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

        synchronized (services) {
            for (Service service : services) {
                final JButton button = new JButton("run plugin job");
                button.addActionListener((ActionEvent e) -> {
                    service.doJob();
                });
                frame.getContentPane().add(button);
            }

            JLabel label = new JLabel("Java version : " + System.getProperty("java.version"));
            label.setSize(200, 24);
            frame.getContentPane().add(label);
            frame.setVisible(true);
        }
    }

}
