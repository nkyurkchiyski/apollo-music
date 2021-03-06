package com.apollo.music;

import com.apollo.music.agent.AgentManager;
import com.apollo.music.agent.commons.AgentConstants;
import com.apollo.music.agent.impl.CuratorAgent;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * The entry point of the Spring Boot application.
 * <p>
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 */
@SpringBootApplication
@EnableAutoConfiguration
@Theme(value = "apollomusic", variant = Lumo.DARK)
@PWA(name = "Apollo Music", shortName = "Apollo Music", offlineResources = {"images/logo.png"})
@NpmPackage(value = "line-awesome", version = "1.3.0")
public class Application extends SpringBootServletInitializer implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        AgentManager.initGateway();
        AgentManager.createNewAgent(AgentConstants.CURATOR_AGENT_NAME, CuratorAgent.class);
    }

}
