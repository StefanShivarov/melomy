package softuni.javaweb.melomy.service;

import softuni.javaweb.melomy.model.view.RequestCounterViewModel;

public interface RequestCounterService {

    void onRequest();

    RequestCounterViewModel getRequestCounterStats();
}
