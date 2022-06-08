package com.apollo.music.jade.entityexecutor;

import com.apollo.music.data.commons.GeneralUtils;
import com.apollo.music.data.entity.Artist;
import com.apollo.music.jade.OntologyConfigurator;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

public class ArtistExecutor extends EntityExecutor<Artist> {
    public ArtistExecutor(final OntologyConfigurator ontologyConfigurator) {
        super(ontologyConfigurator);
    }

    @Override
    public void insert(final Artist entity) {
        final String artistName = GeneralUtils.stripWhitespaces(entity.getName());
        final OWLClass artistClass = dataFactory.getOWLClass(IRI.create(ontologyIRIStr + "Performer"));

        final OWLNamedIndividual artistIndividual = dataFactory.getOWLNamedIndividual(ontologyIRIStr + artistName);
        final OWLClassAssertionAxiom classAssertion = dataFactory.getOWLClassAssertionAxiom(artistClass, artistIndividual);
        final AddAxiom classAssertAxiom = new AddAxiom(musicOntology, classAssertion);

        final OWLDataProperty hasName = dataFactory.getOWLDataProperty(ontologyIRIStr + "hasName");
        final OWLDataPropertyAssertionAxiom nameAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(hasName, artistIndividual, entity.getName());
        final AddAxiom nameAssertionAxiom = new AddAxiom(musicOntology, nameAssertion);

        applyAndSaveChanges(classAssertAxiom, nameAssertionAxiom);
    }

    @Override
    public void delete(final Artist entity) {
        final String artistName = GeneralUtils.stripWhitespaces(entity.getName());
        removeInstances("Performer", artistName);
    }
}
