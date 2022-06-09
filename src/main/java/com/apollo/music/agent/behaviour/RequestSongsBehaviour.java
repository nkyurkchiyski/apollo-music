package com.apollo.music.agent.behaviour;

import com.apollo.music.agent.commons.AgentConstants;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RequestSongsBehaviour extends CyclicBehaviour {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestSongsBehaviour.class);
    private ACLMessage replyToRequestMsg;

    private int currentStep;
    private MessageTemplate mt;


    @Override
    public void action() {
        try {
            switch (currentStep) {
                case 0:
                    receiveRequest();
                    break;
                case 1:
                    receiveSongs();
                    break;
            }
        } catch (final FIPAException | UnreadableException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void receiveRequest() throws FIPAException {
        final MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);

        final ACLMessage msg = myAgent.receive(mt);

        if (msg != null) {
            replyToRequestMsg = msg.createReply();
            replyToRequestMsg.setPerformative(ACLMessage.PROPOSE);
            currentStep++;
            requestSongs(msg.getContent());
        }
    }

    private void requestSongs(final String requestedSongsOntoDescriptors) throws FIPAException {
        final DFAgentDescription searchedDesc = new DFAgentDescription();
        final ServiceDescription sd = new ServiceDescription();

        final String serviceDescName = AgentConstants.CURATOR_AGENT_NAME;
        sd.setName(serviceDescName);
        sd.setType(serviceDescName);

        searchedDesc.addServices(sd);

        final DFAgentDescription[] descriptions = DFService.search(myAgent, searchedDesc);

        final List<AID> agents = Arrays.stream(descriptions)
                .map(DFAgentDescription::getName)
                .collect(Collectors.toList());

        final ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
        agents.forEach(cfp::addReceiver);

        cfp.setContent(requestedSongsOntoDescriptors);
        cfp.setConversationId(AgentConstants.SONG_REQ_CONVO_ID);
        cfp.setReplyWith(AgentConstants.CFP + System.currentTimeMillis());
        mt = MessageTemplate.and(MessageTemplate.MatchConversationId(AgentConstants.SONG_REQ_CONVO_ID),
                MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
        myAgent.send(cfp);
    }


    private void receiveSongs() throws UnreadableException {
        final ACLMessage msg = myAgent.receive(mt);
        if (msg != null) {
            if (msg.getPerformative() == ACLMessage.PROPOSE) {
                final String receivedSongs = msg.getContent();
                replyToRequestMsg.setContent(receivedSongs);
                myAgent.send(replyToRequestMsg);
                replyToRequestMsg = null;
                currentStep = 0;
            }
        }
    }
}
