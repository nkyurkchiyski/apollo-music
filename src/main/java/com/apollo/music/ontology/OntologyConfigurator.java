package com.apollo.music.ontology;

import com.apollo.music.agent.commons.AgentConstants;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class OntologyConfigurator {
    private static final Logger LOGGER = LoggerFactory.getLogger(OntologyConfigurator.class);
    private final OWLOntologyManager ontoManager;
    private OWLOntology musicOntology;
    private final OWLDataFactory dataFactory;

    private final String ontologyIRIStr;

    public OntologyConfigurator() {
        ontoManager = OWLManager.createOWLOntologyManager();
        dataFactory = ontoManager.getOWLDataFactory();

        loadOntologyFile();

        ontologyIRIStr = musicOntology.getOntologyID().getOntologyIRI().get() + "#";
    }

    private void loadOntologyFile() {
        try {
            final File ontoFile = new File(AgentConstants.MUSIC_OWL_LOCATION);
            musicOntology = ontoManager.loadOntologyFromOntologyDocument(ontoFile);

        } catch (final OWLOntologyCreationException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void insertGenre() {
        final OWLClass myCLass = dataFactory.getOWLClass(IRI.create(ontologyIRIStr + "Rap"));
        final OWLNamedIndividual individual = dataFactory.getOWLNamedIndividual(ontologyIRIStr + "Phonk");
        final OWLClassAssertionAxiom classAssertion = dataFactory.getOWLClassAssertionAxiom(myCLass, individual);

//        final OWLSubClassOfAxiom subClassOf = dataFactory.getOWLSubClassOfAxiom(myCLass, genreCLass);
//        final AddAxiom axiom = new AddAxiom(musicOntology, subClassOf);

        final AddAxiom axiom = new AddAxiom(musicOntology, classAssertion);

        ontoManager.applyChange(axiom);

        try {
            ontoManager.saveOntology(musicOntology);
        } catch (final OWLOntologyStorageException e) {
            e.printStackTrace();
        }

    }

    public OWLOntologyManager getOntoManager() {
        return ontoManager;
    }

    public OWLOntology getMusicOntology() {
        return musicOntology;
    }

    public OWLDataFactory getDataFactory() {
        return dataFactory;
    }

    public String getOntologyIRIStr() {
        return ontologyIRIStr;
    }


}
