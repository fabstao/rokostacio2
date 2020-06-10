package com.koalatechie.app;

import java.util.Collections;
import java.util.LinkedHashMap;
//import java.util.List;
import java.util.Set;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/artista")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ArtistaResource  {
    private Set<Artista> artistas = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));
    private Artista bartista;
    public ArtistaResource() {
        bartista = Artista.findByNombre("Clave de Acceso");
        artistas.add(bartista);
    }
    @GET
    public Set<Artista> list() {
        return artistas;
    }

    @POST
    public Set<Artista> add(Artista artista) {
        artistas.add(artista);
        artista.persist();
        artista.update();
        return artistas;
    }

    @DELETE
    public Set<Artista> delete(Artista artista) {
        artistas.removeIf(existingArtista -> existingArtista.nombre.contentEquals(artista.nombre));
        return artistas;
    }
}

