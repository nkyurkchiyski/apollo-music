package com.apollo.music.jade.entityexecutor;

import com.apollo.music.data.commons.GeneralUtils;
import com.apollo.music.data.entity.Genre;
import com.apollo.music.jade.OntologyConfigurator;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

public class GenreExecutor extends EntityExecutor<Genre> {
    public GenreExecutor(final OntologyConfigurator ontologyConfigurator) {
        super(ontologyConfigurator);
    }

    @Override
    public void insert(final Genre entity) {
        final String genreName = GeneralUtils.stripWhitespaces(entity.getName());
        final OWLClass genreClass = dataFactory.getOWLClass(IRI.create(ontologyIRIStr + "Genre"));
        final OWLClass newClass = dataFactory.getOWLClass(IRI.create(ontologyIRIStr + genreName));
        final OWLNamedIndividual individual = dataFactory.getOWLNamedIndividual(ontologyIRIStr + genreName);
        final OWLClassAssertionAxiom classAssertion = dataFactory.getOWLClassAssertionAxiom(newClass, individual);

        final OWLSubClassOfAxiom subClassOf = dataFactory.getOWLSubClassOfAxiom(newClass, genreClass);
        final AddAxiom subClassAxiom = new AddAxiom(musicOntology, subClassOf);
        final AddAxiom classAssertAxiom = new AddAxiom(musicOntology, classAssertion);

        applyAndSaveChanges(subClassAxiom, classAssertAxiom);
    }

    @Override
    public void delete(final Genre entity) {
        final String genreName = GeneralUtils.stripWhitespaces(entity.getName());
        removeInstances("Genre", genreName);
    }

}
