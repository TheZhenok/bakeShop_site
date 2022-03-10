package spring.Configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class MvcConfig implements WebMvcConfigurer {
    @Value("${upload.path.product}")
    private String productIconPath;

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/**");
        registry.addResourceHandler("/server_image/**")
                .addResourceLocations("file:///" + productIconPath + "/");
    }


}
