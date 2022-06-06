package com.apollo.music.jade;

import com.apollo.music.jade.commons.AgentConstants;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class OntologyManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(OntologyManager.class);
    private final OWLOntologyManager ontoManager;
    private OWLOntology musicOntology;
    private final OWLDataFactory dataFactory;

    private final String ontologyIRIStr;

    public OntologyManager() {
        ontoManager = OWLManager.createOWLOntologyManager();
        dataFactory = ontoManager.getOWLDataFactory();

        loadOntologyFile();

        ontologyIRIStr = musicOntology.getOntologyID().getOntologyIRI().toString() + "#";
    }

    private void loadOntologyFile() {
        try {
            final URL url = OntologyManager.class.getResource(AgentConstants.MUSIC_OWL_LOCATION);
            if (url != null) {
                final File ontoFile = new File(url.toURI());
                musicOntology = ontoManager.loadOntologyFromOntologyDocument(ontoFile);
            }
        } catch (final OWLOntologyCreationException | URISyntaxException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
