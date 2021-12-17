package softuni.javaweb.melomy.web.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import softuni.javaweb.melomy.service.RequestCounterService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RequestCounterInterceptor implements HandlerInterceptor {

    private final RequestCounterService requestCounterService;

    public RequestCounterInterceptor(RequestCounterService requestCounterService) {
        this.requestCounterService = requestCounterService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        requestCounterService.onRequest();
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        requestCounterService.addRequestMethod(request.getMethod());
    }
}
