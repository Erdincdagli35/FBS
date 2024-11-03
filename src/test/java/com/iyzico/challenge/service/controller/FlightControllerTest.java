package com.iyzico.challenge.service.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.iyzico.challenge.controller.FlightController;
import com.iyzico.challenge.entity.Flight;
import com.iyzico.challenge.repository.FlightRepository;
import com.iyzico.challenge.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

public class FlightControllerTest {

    @InjectMocks
    private FlightController flightController;

    @Mock
    private FlightService flightService;

    @Mock
    private FlightRepository flightRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(flightController).build();
    }

    @Test
    public void testAddFlight() throws Exception {
        Flight flight = new Flight();
        flight.setFlightNumber("AB123");
        // Set other flight properties as needed

        when(flightService.addFlight(any(Flight.class))).thenReturn(1L);

        mockMvc.perform(post("/flights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"flightNumber\": \"AB123\"}"))  // Adjust based on your Flight class structure
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        verify(flightService, times(1)).addFlight(any(Flight.class));
    }

    @Test
    public void testGetAllFlights() throws Exception {
        Flight flight1 = new Flight();
        flight1.setFlightNumber("AB123");
        Flight flight2 = new Flight();
        flight2.setFlightNumber("CD456");
        List<Flight> flights = Arrays.asList(flight1, flight2);

        when(flightService.getAllFlights()).thenReturn(flights);

        mockMvc.perform(get("/flights"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].flightNumber").value("AB123"))
                .andExpect(jsonPath("$[1].flightNumber").value("CD456"));

        verify(flightService, times(1)).getAllFlights();
    }

    @Test
    public void testDeleteFlight_Success() throws Exception {
        Long flightId = 1L;
        when(flightRepository.existsById(flightId)).thenReturn(true);
        when(flightService.deleteFlight(flightId)).thenReturn(flightId);

        mockMvc.perform(delete("/flights/{flightId}", flightId))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        verify(flightService, times(1)).deleteFlight(flightId);
    }

    @Test
    public void testDeleteFlight_FlightNotFound() throws Exception {
        Long flightId = 1L;
        when(flightRepository.existsById(flightId)).thenReturn(false);

        mockMvc.perform(delete("/flights/{flightId}", flightId))
                .andExpect(status().isBadRequest());

        verify(flightService, never()).deleteFlight(anyLong());
    }

    @Test
    public void testUpdateFlight() throws Exception {
        Flight flight = new Flight();
        flight.setId(1L);
        flight.setFlightNumber("AB123");
        // Set other properties as needed

        when(flightService.updateFlight(any(Flight.class))).thenReturn(flight);

        mockMvc.perform(put("/flights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"flightNumber\": \"AB123\"}"))  // Adjust based on your Flight class structure
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flightNumber").value("AB123"));

        verify(flightService, times(1)).updateFlight(any(Flight.class));
    }
}