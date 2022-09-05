package com.apollo.music.agent.behaviour;

import com.apollo.music.agent.commons.AgentConstants;
import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RequestSeekerBehaviour extends SimpleBehaviour {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestSeekerBehaviour.class);
    private final String[] songsOntoDesc;
    private final String agentName;

    private final List<String> songs = new ArrayList<>();
    private int currentStep;
    private MessageTemplate mt;

    public RequestSeekerBehaviour(final String agentName, final String... songsOntoDesc) {
        this.songsOntoDesc = songsOntoDesc;
        this.agentName = agentName;
    }

    @Override
    public void action() {
        try {
            switch (currentStep) {
                case 0:
                    requestSeeker();
                    currentStep++;
                    break;
                case 1:
                    receiveSongs();
                    break;
            }
        } catch (final FIPAException | UnreadableException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void requestSeeker() throws FIPAException {
        final DFAgentDescription searchedDesc = new DFAgentDescription();
        final ServiceDescription sd = new ServiceDescription();

        final String serviceDescName = String.format(AgentConstants.SONG_SEEKER_AGENT_NAME_FORMAT, agentName);
        sd.setName(serviceDescName);
        sd.setType(serviceDescName);

        searchedDesc.addServices(sd);

        final DFAgentDescription[] descriptions = DFService.search(myAgent, searchedDesc);

        final List<AID> agents = Arrays.stream(descriptions)
                .map(DFAgentDescription::getName)
                .collect(Collectors.toList());

        final ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
        agents.forEach(cfp::addReceiver);

        cfp.setContent(String.join(AgentConstants.SONG_ONTO_DESC_SPLITTER, songsOntoDesc));
        cfp.setConversationId(AgentConstants.SEEKER_REQ_CONVO_ID);
        cfp.setReplyWith(AgentConstants.CFP + System.currentTimeMillis());
        mt = MessageTemplate.and(MessageTemplate.MatchConversationId(AgentConstants.SEEKER_REQ_CONVO_ID),
                MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
        myAgent.send(cfp);
    }


    private void receiveSongs() throws UnreadableException {
        final ACLMessage msg = myAgent.receive(mt);
        if (msg != null) {
            if (msg.getPerformative() == ACLMessage.PROPOSE) {
                final String receivedSongs = msg.getContent();
                songs.addAll(Arrays.asList(
                        receivedSongs.split(
                                AgentConstants.SONG_ONTO_DESC_SPLITTER)));
            }
            currentStep++;
        }
    }

    public List<String> getSongs() {
        return songs;
    }

    @Override
    public boolean done() {
        return songs.size() != 0 || currentStep > 1;
    }

}
