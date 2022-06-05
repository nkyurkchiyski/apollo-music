package com.apollo.music.jade.behaviour;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class SearchSongsBehaviour extends CyclicBehaviour {
    @Override
    public void action() {
        final MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);

        final ACLMessage msg = myAgent.receive(mt);

        if (msg != null) {
            final String requestedSong = msg.getContent();

            final ACLMessage reply = msg.createReply();
            final String[] foundSongs = new String[]{"a8f3f80171ed5db4e661351a6665ffaf28855d17a756f0670ad41d7d99d0016b",
                    "8965c2d0e6b0ac3fec3d15a692ed88e320ddaf5f515a027e0dbde98f73785ec0"};

            reply.setPerformative(ACLMessage.PROPOSE);
            reply.setContent(String.join("#", foundSongs));
            myAgent.send(reply);
        }
    }
}
