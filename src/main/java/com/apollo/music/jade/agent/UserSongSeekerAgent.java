package com.apollo.music.jade.agent;

import com.apollo.music.jade.behaviour.SearchSongsBehaviour;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

public class UserSongSeekerAgent extends Agent {
    @Override
    protected void setup() {
        final DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());

        final String agentName = getAID().getLocalName();
        ServiceDescription sd = new ServiceDescription();
        sd.setName(agentName + "@songSeeker");
        sd.setType(agentName + "@songSeeker");

        dfd.addServices(sd);

        try {
            DFService.register(this, dfd);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        addBehaviour(new SearchSongsBehaviour());
    }

}
