package fr.free.francois.olivier.magicmanagerws.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = {"fr.free.francois.olivier.magicmanagerws.services"})
public class ContextConfig {

}
