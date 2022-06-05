package com.apollo.music.jade.behaviour;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateAgentBehaviour<T extends Agent> extends OneShotBehaviour {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateAgentBehaviour.class);
    private final String agentName;
    private final Class<T> agentClass;

    public CreateAgentBehaviour(final String agentName, final Class<T> agentClass) {
        this.agentName = agentName;
        this.agentClass = agentClass;
    }

    @Override
    public void action() {
        try {
            final AgentController agentController = myAgent.getContainerController().createNewAgent(agentName, agentClass.getName(), new Object[0]);
            agentController.start();
        } catch (final StaleProxyException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
