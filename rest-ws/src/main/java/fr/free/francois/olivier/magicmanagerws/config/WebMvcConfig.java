package fr.free.francois.olivier.magicmanagerws.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages={"fr.free.francois.olivier.magicmanagerws"})
public class WebMvcConfig  extends WebMvcConfigurerAdapter{

}
