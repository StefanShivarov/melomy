package softuni.javaweb.melomy.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import softuni.javaweb.melomy.model.view.RequestCounterViewModel;
import softuni.javaweb.melomy.service.RequestCounterService;

@Service
public class RequestCounterServiceImpl implements RequestCounterService {

    private Integer anonymousRequests = 0, authorizedRequests = 0;

    @Override
    public void onRequest() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if(authentication != null && (authentication.getPrincipal() instanceof UserDetails)){
            authorizedRequests++;
        }else{
            anonymousRequests++;
        }
    }

    @Override
    public RequestCounterViewModel getRequestCounterStats() {
        return new RequestCounterViewModel(authorizedRequests, anonymousRequests);
    }
}
