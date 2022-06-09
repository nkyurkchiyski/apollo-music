package com.apollo.music.agent.impl;

import com.apollo.music.agent.behaviour.SearchSongsBehaviour;
import com.apollo.music.agent.commons.AgentConstants;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CuratorAgent extends Agent {
    private static final Logger LOGGER = LoggerFactory.getLogger(CuratorAgent.class);

    @Override
    protected void setup() {
        final DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());

        final ServiceDescription sd = new ServiceDescription();

        final String serviceDescName = AgentConstants.CURATOR_AGENT_NAME;
        sd.setName(serviceDescName);
        sd.setType(serviceDescName);

        dfd.addServices(sd);

        try {
            DFService.register(this, dfd);
        } catch (final FIPAException e) {
            LOGGER.error(e.getMessage());
        }

        addBehaviour(new SearchSongsBehaviour());
    }
}
