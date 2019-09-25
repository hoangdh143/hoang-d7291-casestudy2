package com.mitrais.viewhandler;

import lombok.Data;

@Data
public class FrontControllerImpl implements FrontController {
    private Dispatcher dispatcher;

    public FrontControllerImpl() {
        setDispatcher(new DispatcherImpl());
    }

    @Override
    public void goToView(String viewName) {
        dispatcher.dispatch(viewName);
    }
}
