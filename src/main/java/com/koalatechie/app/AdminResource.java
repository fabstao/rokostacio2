package com.koalatechie.app;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/admin")
public class AdminResource {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String index() {
        return "<h1>ADMIN Site</h1><h3>In construction...</h3>";
    }
}