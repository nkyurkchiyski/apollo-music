package com.apollo.music.jade.behaviour;

import com.apollo.music.jade.commons.AgentConstants;
import com.apollo.music.jade.entityexecutor.SongExecutor;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Set;

public class SearchSongsBehaviour extends CyclicBehaviour {
    private final SongExecutor songExecutor;

    public SearchSongsBehaviour(final SongExecutor songExecutor) {
        this.songExecutor = songExecutor;
    }

    @Override
    public void action() {
        final MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
        final ACLMessage msg = myAgent.receive(mt);

        if (msg != null) {
            final String msgContent = msg.getContent();
            final String[] requestedSongsOntoDesc = msgContent.split(AgentConstants.SONG_ONTO_DESC_SPLITTER);
            final Set<String> recommendations = songExecutor.findWithOntoDesc(requestedSongsOntoDesc);

            final ACLMessage reply = msg.createReply();
            reply.setPerformative(ACLMessage.PROPOSE);
            reply.setContent(String.join(AgentConstants.SONG_ONTO_DESC_SPLITTER, recommendations));
            myAgent.send(reply);
        }
    }
}
