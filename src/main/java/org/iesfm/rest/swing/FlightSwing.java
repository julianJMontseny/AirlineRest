package org.iesfm.rest.swing;

import org.iesfm.rest.Flight;
import org.iesfm.rest.client.ErrorHandler;
import org.iesfm.rest.client.FlightClient;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FlightSwing {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Aerolinea");
        JPanel panel = new JPanel();
        JTextField flightNumberField = new JTextField();
        JButton boton = new JButton("Pedir vuelo");


        //Esto es para asegurar la raíz y en los metodos de FlightClient solo poner de ruta "/flights".
        FlightClient flightAPI = new FlightClient(
                new RestTemplateBuilder().
                        rootUri("http://localhost:8080").
                        build());

        List<Flight> flights = flightAPI.list("Amsterdam");
        for(Flight flight : flights){
            panel.add(new JLabel(flight.toString()));
        }

//        Cogemos la respuesta HTTP al crear el vuelo.
        flightAPI.createFlight(
                new Flight(
                        "4C",
                        "Boston",
                        "Barcelona"));
////        Comprobamos dependiendo del tipo de respuesta generada mostraremos un mensaje al usuario(JOptionPane).
//        if(response.getStatusCode() == HttpStatus.CREATED){
//            JOptionPane.showMessageDialog(frame,"Se ha creado el vuelo");
//        } else if(response.getStatusCode() == HttpStatus.CONFLICT){
//            JOptionPane.showMessageDialog(frame,"Ya existía el vuelo");
//        }

        flightNumberField.setText("Introduce numero vuelo");
        panel.add(flightNumberField);
        panel.add(boton);
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Flight flight = flightAPI.getFlight((flightNumberField.getText()));
                JOptionPane.showMessageDialog(frame,flight.toString());
            }
        });

        try{
            Flight flight = flightAPI.getFlight("9A");
            panel.add(new JLabel(flight.toString()));

        }catch(HttpClientErrorException.NotFound e){
            JOptionPane.showMessageDialog(frame,"No se encuentra el vuelo");
        }
        frame.add(panel);
        frame.setVisible(true);
        frame.setBounds(0,0,600,600);
    }


}
