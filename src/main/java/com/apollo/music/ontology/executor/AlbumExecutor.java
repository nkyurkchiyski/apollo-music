package com.apollo.music.ontology.executor;

import com.apollo.music.data.commons.GeneralUtils;
import com.apollo.music.data.entity.Album;
import com.apollo.music.ontology.OntologyConfigurator;
import com.apollo.music.views.commons.ViewConstants;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntologyChange;

import java.util.ArrayList;
import java.util.Collection;

public class AlbumExecutor extends EntityExecutor<Album> {
    public AlbumExecutor(final OntologyConfigurator ontologyConfigurator) {
        super(ontologyConfigurator);
    }

    @Override
    public void insert(final Album entity) {
        final String albumName = GeneralUtils.stripWhitespaces(entity.getName() + entity.getArtist().getName());
        final String artistName = GeneralUtils.stripWhitespaces(entity.getArtist().getName());

        final OWLClass albumClass = dataFactory.getOWLClass(IRI.create(ontologyIRIStr + "Album"));

        final Collection<OWLOntologyChange> changes = new ArrayList<>();

        final OWLNamedIndividual albumIndividual = dataFactory.getOWLNamedIndividual(ontologyIRIStr + albumName);
        final OWLClassAssertionAxiom classAssertion = dataFactory.getOWLClassAssertionAxiom(albumClass, albumIndividual);
        changes.add(new AddAxiom(musicOntology, classAssertion));

        changes.add(createObjPropertyAddAxiom(albumIndividual, "isAlbumMadeBy", artistName));

        if (entity.getReleasedOn() != null) {
            final OWLDataProperty hasReleaseDate = dataFactory.getOWLDataProperty(ontologyIRIStr + "hasPublishDate");
            final OWLDatatype datatype = dataFactory.getOWLDatatype(ontologyIRIStr + "xsd:dateTime");
            final OWLLiteral literal = dataFactory.getOWLLiteral(ViewConstants.DATE_FORMAT.format(entity.getReleasedOn()), datatype);
            final OWLDataPropertyAssertionAxiom releaseDateAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(hasReleaseDate, albumIndividual, literal);
            changes.add(new AddAxiom(musicOntology, releaseDateAssertion));
        }
        final OWLDataProperty hasTitle = dataFactory.getOWLDataProperty(ontologyIRIStr + "hasFullTitle");
        final OWLDataPropertyAssertionAxiom titleAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(hasTitle, albumIndividual, entity.getName());
        changes.add(new AddAxiom(musicOntology, titleAssertion));

        applyAndSaveChanges(changes);

    }

    @Override
    public void delete(final Album entity) {
        final String albumName = GeneralUtils.stripWhitespaces(entity.getName() + entity.getArtist().getName());
        removeInstances("Album", albumName);
    }
}
