package com.koalatechie.app;

import io.quarkus.mongodb.panache.PanacheMongoEntity;

public class Artista extends PanacheMongoEntity {
	public String nombre;
	public String representante;
	public String email;
	public String direccion;
	public int codigo_postal;
	public String telefono;
	public static class Rider {
		public String backline;
		public String microfonos;
		public String otros;
	}
	public Rider rider;
	public static Artista findByNombre(String nombre){
        return find("nombre", nombre).firstResult();
    }

}
