package com.koalatechie.app;

import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//import org.bson.Document;

@Path("/artista")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ArtistaResource  {
    private Set<Artista> artistas = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));
    private Artista bartista;
    private List<Artista> listarts;
    public ArtistaResource() {
    	long cuenta = Artista.count();
    	cuenta = cuenta > 5 ? 5  : cuenta;
    	//listarts = Artista.list(new Document("fecha","{ \"$lte\" : new Date() }"));
    	//listarts = Artista.list(new Document("nombre","Bronson"));
    	listarts = Artista.list("fecha <= ?1", new Date());
    	Iterator<Artista> iterator = listarts.iterator();
        while(iterator.hasNext()) {
           artistas.add(iterator.next());
        }
    }
    @GET
    public Set<Artista> list() {
        return artistas;
    }

    @POST
    public Set<Artista> add(Artista artista) {
    	bartista = Artista.findByNombre(artista.nombre);
        if(bartista == null) {
	    	artistas.add(artista);
	        artista.persist();
        }
        if(bartista != null) {
        	artista.id = bartista.id;
	        artista.update();
        }
        return artistas;
    }

    @DELETE
    public Set<Artista> delete(Artista artista) {
        artistas.removeIf(existingArtista -> existingArtista.nombre.contentEquals(artista.nombre));
        bartista = Artista.findByNombre(artista.nombre);
        if(bartista != null)
        	bartista.delete();
        return artistas;
    }
}