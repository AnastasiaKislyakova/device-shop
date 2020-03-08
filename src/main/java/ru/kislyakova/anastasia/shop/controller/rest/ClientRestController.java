package ru.kislyakova.anastasia.shop.controller.rest;

import ru.kislyakova.anastasia.shop.controller.rest.handlers.ClientWebHandler;
import ru.kislyakova.anastasia.shop.model.Client;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("customer")
public class ClientRestController {
    private ClientWebHandler clientWebHandler;

    @Inject
    public ClientRestController(ClientWebHandler clientWebHandler) {
        this.clientWebHandler = clientWebHandler;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Client> getClients(@QueryParam("firstName") String firstName,
                                   @QueryParam("lastName") String lastName,
                                   @QueryParam("middleName") String patronymic,
                                   @QueryParam("birthdate") String birthDateStr,
                                   @QueryParam("birthdateFrom") String birthDateFromStr,
                                   @QueryParam("birthdateTo") String birthDateToStr,
                                   @QueryParam("orderBy") String sortBy,
                                   @DefaultValue("10") @QueryParam("pageItems") int count,
                                   @DefaultValue("1") @QueryParam("page") int pageNumber) {

        return clientWebHandler.getClients(firstName, lastName, patronymic, birthDateStr, birthDateFromStr,
                birthDateToStr, sortBy, count, pageNumber);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Client createClient(Client client) {
        return clientWebHandler.createClient(client);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Client getClient(@PathParam("id") long id) {
        return clientWebHandler.getClient(id);
    }
}
