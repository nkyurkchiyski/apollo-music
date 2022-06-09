package com.apollo.music.agent.behaviour;

import jade.core.behaviours.OneShotBehaviour;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KillAgentBehaviour extends OneShotBehaviour {
    private static final Logger LOGGER = LoggerFactory.getLogger(KillAgentBehaviour.class);

    private final String agentName;

    public KillAgentBehaviour(final String agentName) {
        this.agentName = agentName;
    }

    @Override
    public void action() {
        try {
            final AgentController agentController = myAgent.getContainerController().getAgent(agentName);
            agentController.kill();
        } catch (final ControllerException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
