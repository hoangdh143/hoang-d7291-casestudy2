package com.mitrais.view;

import com.mitrais.viewhandler.Dispatcher;

public interface View {
    void display();
    void setDispatcher(Dispatcher dispatcher);
    Dispatcher getDispatcher();
}
