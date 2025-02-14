package kr.geun.springbootreactdelopy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.function.RequestPredicate;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;
import java.util.List;

import static org.springframework.web.servlet.function.RequestPredicates.path;
import static org.springframework.web.servlet.function.RequestPredicates.pathExtension;
import static org.springframework.web.servlet.function.RouterFunctions.route;

/**
 * WebMvcConfig
 *
 * @author akageun
 * @since 2025-02-14
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.html");
    }

    @Bean
    public RouterFunction<ServerResponse> spaRouter() {
        ClassPathResource index = new ClassPathResource("static/index.html");
        List<String> extensions = List.of("js", "css", "ico", "png", "jpg", "gif");
        RequestPredicate spaPredicate = path("/api/**")
            .or(path("/error"))
            .or(pathExtension(extensions::contains))
            .negate();
        return route()
            .resource(spaPredicate, index)
            .build();
    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry
//            .addResourceHandler("/**")
//            .addResourceLocations("classpath:/static/**")
//            .resourceChain(true)
//            .addResolver(new PathResourceResolver() {
//                @Override
//                protected Resource getResource(String resourcePath, Resource location) throws IOException {
//                    Resource requestedResource = location.createRelative(resourcePath);
//
//                    if (requestedResource.exists() && requestedResource.isReadable()) {
//                        return requestedResource;
//                    }
//
//                    return new ClassPathResource("/static/index.html");
//                }
//            })
//        ;
//    }
}
