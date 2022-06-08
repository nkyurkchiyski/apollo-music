package com.apollo.music.jade.agent;

import com.apollo.music.jade.OntologyConfigurator;
import com.apollo.music.jade.behaviour.SearchSongsBehaviour;
import com.apollo.music.jade.commons.AgentConstants;
import com.apollo.music.jade.entityexecutor.SongExecutor;
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

        final OntologyConfigurator ontologyConfigurator = new OntologyConfigurator();
        final SongExecutor songExecutor = new SongExecutor(ontologyConfigurator);
        addBehaviour(new SearchSongsBehaviour(songExecutor));
    }
}
