package com.mitrais;

import com.mitrais.config.FileDataSource;
import com.mitrais.model.TransactionHistory;
import com.mitrais.repository.*;
import com.mitrais.view.FileLoader;
import com.mitrais.view.View;
import com.mitrais.viewhandler.FrontController;
import com.mitrais.viewhandler.FrontControllerImpl;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        View fileLoader = new FileLoader();
        fileLoader.display();

        FrontController frontController = new FrontControllerImpl();
        frontController.goToView("WELCOME");
    }
}