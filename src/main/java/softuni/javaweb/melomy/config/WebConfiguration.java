package softuni.javaweb.melomy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import softuni.javaweb.melomy.web.interceptors.LoggerInterceptor;
import softuni.javaweb.melomy.web.interceptors.RequestCounterInterceptor;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final RequestCounterInterceptor requestCounterInterceptor;
    private final LoggerInterceptor loggerInterceptor;

    public WebConfiguration(RequestCounterInterceptor requestCounterInterceptor, LoggerInterceptor loggerInterceptor) {
        this.requestCounterInterceptor = requestCounterInterceptor;
        this.loggerInterceptor = loggerInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestCounterInterceptor);
        registry.addInterceptor(loggerInterceptor);
    }
}
