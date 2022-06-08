package com.apollo.music.jade.entityexecutor;

import com.apollo.music.data.commons.GeneralUtils;
import com.apollo.music.data.entity.Song;
import com.apollo.music.jade.OntologyConfigurator;
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
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.reasoner.NodeSet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SongExecutor extends EntityExecutor<Song> {
    public SongExecutor(final OntologyConfigurator ontologyConfigurator) {
        super(ontologyConfigurator);
    }

    @Override
    public void insert(final Song entity) {
        final Map<String, String> ontoDescMap = createOntoDescMap(entity.getOntoDescriptor());

        final String albumName = GeneralUtils.stripWhitespaces(ontoDescMap.get("Album"));
        final String artistName = GeneralUtils.stripWhitespaces(ontoDescMap.get("Artist"));
        final String genreName = GeneralUtils.stripWhitespaces(ontoDescMap.get("Genre"));
        final String songIndividualName = getIndividualName(entity);

        final OWLClass songClass = dataFactory.getOWLClass(IRI.create(ontologyIRIStr + "Song"));

        final OWLNamedIndividual songIndividual = dataFactory.getOWLNamedIndividual(ontologyIRIStr + songIndividualName);
        final OWLClassAssertionAxiom classAssertion = dataFactory.getOWLClassAssertionAxiom(songClass, songIndividual);
        final AddAxiom classAssertAxiom = new AddAxiom(musicOntology, classAssertion);

        final AddAxiom performerAxiom = createObjPropertyAddAxiom(songIndividual, "hasPerformer", artistName);
        final AddAxiom albumAxiom = createObjPropertyAddAxiom(songIndividual, "hasAlbum", albumName + artistName);
        final AddAxiom genreAxiom = createObjPropertyAddAxiom(songIndividual, "hasGenre", genreName);

        final OWLDataProperty hasTitle = dataFactory.getOWLDataProperty(ontologyIRIStr + "hasTitle");
        final OWLDataPropertyAssertionAxiom titleAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(hasTitle, songIndividual, entity.getName());
        final AddAxiom titleAssertionAxiom = new AddAxiom(musicOntology, titleAssertion);

        final OWLDataProperty hasReleaseDate = dataFactory.getOWLDataProperty(ontologyIRIStr + "hasReleaseDate");
        final OWLDatatype datatype = dataFactory.getOWLDatatype(ontologyIRIStr + "xsd:dateTime");
        final OWLLiteral literal = dataFactory.getOWLLiteral(ViewConstants.DATE_FORMAT.format(entity.getReleasedOn()), datatype);
        final OWLDataPropertyAssertionAxiom releaseDateAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(hasReleaseDate, songIndividual, literal);
        final AddAxiom releaseDateAssertionAxiom = new AddAxiom(musicOntology, releaseDateAssertion);

        applyAndSaveChanges(classAssertAxiom, performerAxiom, albumAxiom, genreAxiom, titleAssertionAxiom, releaseDateAssertionAxiom);
    }


    @Override
    public void delete(final Song entity) {
        final String individualName = getIndividualName(entity);
        removeInstances("Song", individualName);
    }


    public Set<String> findWithOntoDesc(final String... ontoDescriptors) {
        final Set<String> genres = new HashSet<>();

        Arrays.stream(ontoDescriptors).forEach(ontoDesc -> {
            final Map<String, String> keyValues = createOntoDescMap(ontoDesc);
            genres.add(keyValues.get("Genre"));
        });

        final Set<String> foundOntoDescriptors = new HashSet<>();

        for (final String genre : genres) {
            final OWLNamedIndividual genreIndividual = dataFactory.getOWLNamedIndividual(ontologyIRIStr + genre);
            final OWLObjectProperty isGenreOf = dataFactory.getOWLObjectProperty(ontologyIRIStr + "isGenreOf");


            final Set<OWLNamedIndividual> songsFound = reasoner.objectPropertyValues(genreIndividual, isGenreOf).collect(Collectors.toSet());
            songsFound.forEach(song -> {
                final String songName = getDataPropertyValue(song, "hasTitle");
                final OWLNamedIndividual artist = getFirstObjPropertyValue(song, "hasPerformer");
                final String artistName = getDataPropertyValue(artist, "hasName");
                final OWLNamedIndividual album = getFirstObjPropertyValue(song, "hasAlbum");
                final String albumName = getDataPropertyValue(album, "hasFullTitle");
                final String ontoDesc = Song.createOntoDescriptor(songName, albumName, artistName, genre);
                foundOntoDescriptors.add(ontoDesc);
            });
        }

        Arrays.asList(ontoDescriptors).forEach(foundOntoDescriptors::remove);
        return foundOntoDescriptors;
    }

    private String getDataPropertyValue(final OWLNamedIndividual individual, final String dataProp) {
        final OWLDataProperty prop = dataFactory.getOWLDataProperty(ontologyIRIStr + dataProp);
        final Set<OWLLiteral> literals = reasoner.getDataPropertyValues(individual, prop);
        return literals.isEmpty() ? null : literals.iterator().next().getLiteral();
    }


    private OWLNamedIndividual getFirstObjPropertyValue(final OWLNamedIndividual individual, final String objProp) {
        final OWLObjectProperty prop = dataFactory.getOWLObjectProperty(ontologyIRIStr + objProp);
        final NodeSet<OWLNamedIndividual> individuals = reasoner.getObjectPropertyValues(individual, prop);
        return individuals.isEmpty() ? null : individuals.entities().iterator().next();
    }

    private Map<String, String> createOntoDescMap(final String ontoDescriptor) {
        final Map<String, String> map = new HashMap<>();
        final String[] tokens = ontoDescriptor.split(";");
        Arrays.stream(tokens).forEach(t -> {
            final String[] keyValue = t.split(":");
            map.put(keyValue[0], keyValue[1]);
        });
        return map;
    }

    private String getIndividualName(final Song entity) {
        final Map<String, String> ontoDescMap = createOntoDescMap(entity.getOntoDescriptor());

        final String albumName = GeneralUtils.stripWhitespaces(ontoDescMap.get("Album"));
        final String artistName = GeneralUtils.stripWhitespaces(ontoDescMap.get("Artist"));
        final String genreName = GeneralUtils.stripWhitespaces(ontoDescMap.get("Genre"));
        final String songName = GeneralUtils.stripWhitespaces(entity.getName());
        return GeneralUtils.stripWhitespaces(songName + albumName + artistName + genreName);
    }


}
