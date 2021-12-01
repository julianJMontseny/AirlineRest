package org.iesfm.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.util.List;

public interface FlightAPI {

    List<Flight> list(String origin);

    Flight getFlight(String flightNumber);

    void createFlight(@RequestBody Flight flight);

    void updateFlight(String flightNumber,Flight flight);

    void deleteFlight(String flightNumber);

}
