package com.iyzico.challenge.service.controller;

import com.iyzico.challenge.controller.SeatController;
import com.iyzico.challenge.entity.Seat;
import com.iyzico.challenge.response.FlightSeatResponse;
import com.iyzico.challenge.service.SeatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class SeatControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SeatService seatService;

    @InjectMocks
    private SeatController seatController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(seatController).build();
    }

    @Test
    void addSeat_ShouldReturnOk_WhenSeatsAreAdded() throws Exception {
        Long flightId = 1L;
        List<String> seatNumbers = Arrays.asList("1A", "1B");

        when(seatService.addSeat(seatNumbers, flightId)).thenReturn(seatNumbers);

        mockMvc.perform(post("/seats/" + flightId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[\"1A\", \"1B\"]"))
                .andExpect(status().isOk())
                .andExpect(content().string("[\"1A\",\"1B\"]"));

        verify(seatService, times(1)).addSeat(seatNumbers, flightId);
    }

    @Test
    void getAllSeats_ShouldReturnListOfSeats() throws Exception {
        FlightSeatResponse seatResponse = new FlightSeatResponse();
        seatResponse.setSeatNumber("1A");
        seatResponse.setFlightName("Flight123");

        List<FlightSeatResponse> seatResponses = Collections.singletonList(seatResponse);

        when(seatService.getAllSeats()).thenReturn(seatResponses);

        mockMvc.perform(get("/seats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].seatNumber").value("1A"))
                .andExpect(jsonPath("$[0].flightName").value("Flight123"));

        verify(seatService, times(1)).getAllSeats();
    }

    @Test
    void getSeatById_ShouldReturnSeat_WhenSeatExists() throws Exception {
        Seat seat = new Seat();
        seat.setId(1L);
        seat.setSeatNumber("1A");

        when(seatService.getSeatById(1L)).thenReturn(seat);

        mockMvc.perform(get("/seats/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.seatNumber").value("1A"));

        verify(seatService, times(1)).getSeatById(1L);
    }

    @Test
    void updateSeat_ShouldReturnUpdatedSeat_WhenSeatUpdated() throws Exception {
        Seat seat = new Seat();
        seat.setId(1L);
        seat.setSeatNumber("1A");

        when(seatService.updateSeat(any(Seat.class))).thenReturn(seat);

        mockMvc.perform(put("/seats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"seatNumber\": \"1A\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.seatNumber").value("1A"));

        verify(seatService, times(1)).updateSeat(any(Seat.class));
    }
}
