package com.apollo.music.jade;

import com.apollo.music.jade.behaviour.CreateAgentBehaviour;
import com.apollo.music.jade.behaviour.KillAgentBehaviour;
import com.apollo.music.jade.behaviour.RequestSongsBehaviour;
import jade.core.Agent;
import jade.core.Profile;
import jade.util.leap.Properties;
import jade.wrapper.ControllerException;
import jade.wrapper.gateway.JadeGateway;

import java.util.List;
import java.util.function.Consumer;

public class AgentManager {
    public static void initGateway() {
        final Properties props = new Properties();
        props.setProperty(Profile.MAIN_HOST, "localhost");
        props.setProperty(Profile.MAIN_PORT, "1099");
        JadeGateway.init(null, props);
    }

    public static <T extends Agent> void createNewAgent(final String name, final Class<T> classType) {
        try {
            final CreateAgentBehaviour<T> createAgentBehaviour = new CreateAgentBehaviour<>(name, classType);
            JadeGateway.execute(createAgentBehaviour);
        } catch (final ControllerException | InterruptedException e) {
            e.printStackTrace();
            JadeGateway.shutdown();
        }
    }


    public static <T extends Agent> void createNewAgent(final String name, final Class<T> classType, final Object[] args) {
        try {
            final CreateAgentBehaviour<T> createAgentBehaviour = new CreateAgentBehaviour<>(name, classType, args);
            JadeGateway.execute(createAgentBehaviour);
        } catch (final ControllerException | InterruptedException e) {
            e.printStackTrace();
            JadeGateway.shutdown();
        }
    }


    public static void stopAgent(final String name) {
        try {
            final KillAgentBehaviour killAgentBehaviour = new KillAgentBehaviour(name);
            JadeGateway.execute(killAgentBehaviour);
        } catch (final ControllerException | InterruptedException e) {
            e.printStackTrace();
            JadeGateway.shutdown();
        }
    }


    public static void retrieveSongRecommendation(final String agentName,
                                                  final String songOntoHash,
                                                  final Consumer<List<String>> onSuccessConsumer) {
        try {
            final RequestSongsBehaviour requestSongsBehaviour = new RequestSongsBehaviour(agentName, songOntoHash);
            JadeGateway.execute(requestSongsBehaviour);
            onSuccessConsumer.accept(requestSongsBehaviour.getSongs());

        } catch (final ControllerException | InterruptedException e) {
            e.printStackTrace();
            JadeGateway.shutdown();
        }
    }

    public static void retrieveSongRecommendation(final String agentName,
                                                  final String[] songsOntoHash,
                                                  final Consumer<List<String>> onSuccessConsumer) {
        try {
            final RequestSongsBehaviour requestSongsBehaviour = new RequestSongsBehaviour(agentName, songsOntoHash);
            JadeGateway.execute(requestSongsBehaviour);
            onSuccessConsumer.accept(requestSongsBehaviour.getSongs());

        } catch (final ControllerException | InterruptedException e) {
            e.printStackTrace();
            JadeGateway.shutdown();
        }
    }

}
