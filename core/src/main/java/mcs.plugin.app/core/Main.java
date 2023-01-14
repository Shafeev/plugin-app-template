package mcs.plugin.app.core;

import javafx.application.Application;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.lang.module.Configuration;
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        HBox root = FXMLLoader.load(getClass().getResource("/plugins.fxml"));

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

        TextField textField = new TextField("");
        root.getChildren().add(textField);
        for (Service service : services) {
            Button button = new Button(service.getClass().getSimpleName());
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                service.doJob();
                textField.setText(service.getClass().getSimpleName());
            });
            root.getChildren().add(button);
        }



        primaryStage.setTitle("Plugin");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
