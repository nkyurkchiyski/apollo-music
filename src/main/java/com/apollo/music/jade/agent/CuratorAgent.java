package com.apollo.music.jade.agent;

import com.apollo.music.jade.OntologyManager;
import com.apollo.music.jade.commons.AgentConstants;
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

        final String agentName = getAID().getLocalName();
        final ServiceDescription sd = new ServiceDescription();

        final String serviceDescName = String.format(AgentConstants.SONG_CURATOR_AGENT_NAME_FORMAT, agentName);
        sd.setName(serviceDescName);
        sd.setType(serviceDescName);

        dfd.addServices(sd);

        try {
            DFService.register(this, dfd);
        } catch (final FIPAException e) {
            LOGGER.error(e.getMessage());
        }
//        addBehaviour(new SearchSongsBehaviour());

        final OntologyManager ontologyManager = new OntologyManager();
    }
}
