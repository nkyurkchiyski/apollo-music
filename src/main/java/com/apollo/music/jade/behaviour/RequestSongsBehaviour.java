package com.apollo.music.jade.behaviour;

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

public class RequestSongsBehaviour extends SimpleBehaviour {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestSongsBehaviour.class);
    private final String[] songsOntoHash;
    private final String agentName;

    private final List<String> songs = new ArrayList<>();
    private int currentStep;
    private MessageTemplate mt;

    public RequestSongsBehaviour(final String agentName, final String... songsOntoHash) {
        this.songsOntoHash = songsOntoHash;
        this.agentName = agentName;
    }

    @Override
    public void action() {
        try {
            switch (currentStep) {
                case 0:
                    requestSong();
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

    private void requestSong() throws FIPAException {
        final DFAgentDescription searchedDesc = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setName(agentName + "@songSeeker");
        sd.setType(agentName + "@songSeeker");

        searchedDesc.addServices(sd);

        final DFAgentDescription[] descriptions = DFService.search(myAgent, searchedDesc);

        final List<AID> agents = Arrays.stream(descriptions)
                .map(DFAgentDescription::getName)
                .collect(Collectors.toList());

        final ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
        agents.forEach(cfp::addReceiver);

        cfp.setContent(String.join("#", songsOntoHash));
        cfp.setConversationId("Song Recommendation");
        cfp.setReplyWith("cfp" + System.currentTimeMillis());
        mt = MessageTemplate.and(MessageTemplate.MatchConversationId("Song Recommendation"),
                MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
        myAgent.send(cfp);
    }


    private void receiveSongs() throws UnreadableException {
        final ACLMessage msg = myAgent.receive(mt);
        if (msg != null) {
            if (msg.getPerformative() == ACLMessage.PROPOSE) {
                final String receivedSongs = msg.getContent();
                songs.addAll(Arrays.asList(receivedSongs.split("#")));
            }
            currentStep++;
        }
    }

    @Override
    public boolean done() {
        return songs.size() != 0 || currentStep > 1;
    }


    public List<String> getSongs() {
        return songs;
    }
}
