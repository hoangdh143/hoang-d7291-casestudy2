package com.mitrais;

import com.mitrais.view.FileLoader;
import com.mitrais.view.View;
import com.mitrais.viewhandler.FrontController;
import com.mitrais.viewhandler.FrontControllerImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */

@SpringBootApplication
public class App
{
    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
        View fileLoader = new FileLoader();
        fileLoader.display();

        FrontController frontController = new FrontControllerImpl();
        frontController.goToView("WELCOME");
    }
}