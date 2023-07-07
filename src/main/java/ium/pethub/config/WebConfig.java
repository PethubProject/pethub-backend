package ium.pethub.config;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import ium.pethub.util.FilePathResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import ium.pethub.util.interceptor.AuthInterceptor;
import ium.pethub.util.interceptor.ValidInterceptor;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final FilePathResolver filePathResolver;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ValidInterceptor validInterceptor() {
        return new ValidInterceptor();}
    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedOriginPatterns("*")
                .allowedMethods("OPTIONS","GET","POST","PUT","DELETE")
                .allowCredentials(true)
                .maxAge(3000);
    }

    public void addInterceptors(InterceptorRegistry registry) {
        // List<String> excludes = Arrays.asList("/favicon.ico");
        registry.addInterceptor(validInterceptor());
        registry.addInterceptor(authInterceptor());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String imageBasePath = filePathResolver.resolveImageBasePath().toString();
        File file = new File(imageBasePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        registry.addResourceHandler("/upload_img/**")
                .addResourceLocations("file:" + imageBasePath + "/");
    }
}
