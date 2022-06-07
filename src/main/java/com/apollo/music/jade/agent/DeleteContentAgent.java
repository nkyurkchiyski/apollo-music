package com.apollo.music.jade.agent;

import com.apollo.music.jade.OntologyConfigurator;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class DeleteContentAgent extends Agent {

    private final OntologyConfigurator ontologyConfigurator;

    public DeleteContentAgent() {
        ontologyConfigurator = new OntologyConfigurator();
    }

    @Override
    protected void setup() {
        addBehaviour(new DeleteBehaviour(ontologyConfigurator));
    }

    private static class DeleteBehaviour extends OneShotBehaviour {
        private final OntologyConfigurator ontologyConfigurator;

        public DeleteBehaviour(final OntologyConfigurator ontologyConfigurator) {
            this.ontologyConfigurator = ontologyConfigurator;
        }

        @Override
        public void action() {
            //TODO
//            ontologyManager.delete();
        }
    }


}
