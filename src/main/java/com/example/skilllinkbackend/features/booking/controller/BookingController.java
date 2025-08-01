package com.example.skilllinkbackend.features.booking.controller;

import com.example.skilllinkbackend.config.responses.DataResponse;
import com.example.skilllinkbackend.features.booking.dto.BookingRegisterDTO;
import com.example.skilllinkbackend.features.booking.dto.BookingResponseDTO;
import com.example.skilllinkbackend.features.booking.service.IBookingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
@PreAuthorize("hasAnyRole('ADMIN', 'RECEPTION')")
public class BookingController {

    private final IBookingService bookingService;

    @PostMapping
    @Transactional
    public ResponseEntity<DataResponse<BookingResponseDTO>> createBooking(
            @RequestBody @Valid BookingRegisterDTO bookingRegisterDTO,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        BookingResponseDTO bookingResponseDTO = bookingService.createBooking(bookingRegisterDTO);
        URI url = uriComponentsBuilder.path("/api/v1/bookings/{id}").buildAndExpand(bookingResponseDTO.id()).toUri();
        DataResponse<BookingResponseDTO> response = new DataResponse<>(
                "Reserva creada con éxito",
                HttpStatus.CREATED.value(),
                bookingResponseDTO
        );
        return ResponseEntity.created(url).body(response);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}
