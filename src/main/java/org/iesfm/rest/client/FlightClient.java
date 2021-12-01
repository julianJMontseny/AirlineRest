package org.iesfm.rest.client;

import org.iesfm.rest.Flight;
import org.iesfm.rest.FlightAPI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlightClient implements FlightAPI {

    //Este es el proceso SWING que va a arrancar el Cliente.

    //Dependencias:
    private RestTemplate restTemplate;

    public FlightClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Flight> list(String origin) {
        if (origin == null) {
            //el getForObject es igual al GET del controller. Pero de cara al cliente.
            Flight[] flights = restTemplate.getForObject(
                    //¿De donde cojo el get? De esta url.
                    "http://localhost:8080/flights",
                    //Convierte lo que devuelve en un array de vuelos.
                    Flight[].class);
            //Luego convertimos el array en una lista.
            return Arrays.asList(flights);
        } else {
            Map<String, Object> params = new HashMap<>();
            params.put("origin", origin);
            Flight[] flights = restTemplate.getForObject(
                    "/flights",
                    Flight[].class, params);

            return Arrays.asList(flights);
        }
    }

    @Override
    public Flight getFlight(String flightNumber) {
        Map<String, Object> params = new HashMap<>();
        params.put("flightNumber", flightNumber);
        return restTemplate.getForObject(
                "/flights/" + flightNumber,
                Flight.class,
                params);
    }

    @Override
    public void createFlight(Flight flight) {
        restTemplate.postForEntity("/flights", flight, Void.class);
    }

    @Override
    public void updateFlight(String flightNumber, Flight flight) {

    }

    @Override
    public void deleteFlight(String flightNumber) {

    }
}
