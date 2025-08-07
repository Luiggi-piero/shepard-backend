package com.example.skilllinkbackend.features.booking.controller;

import com.example.skilllinkbackend.config.responses.DataResponse;
import com.example.skilllinkbackend.features.booking.dto.BookingRegisterDTO;
import com.example.skilllinkbackend.features.booking.dto.BookingResponseDTO;
import com.example.skilllinkbackend.features.booking.dto.BookingUpdateDTO;
import com.example.skilllinkbackend.features.booking.model.ReservationStatus;
import com.example.skilllinkbackend.features.booking.service.IBookingService;
import com.example.skilllinkbackend.shared.util.PaginationResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
@PreAuthorize("hasAnyRole('ADMIN', 'RECEPTION')")
@Tag(name = "Reservas", description = "Operaciones relacionadas con la gestión de reservas")
public class BookingController {

    private final IBookingService bookingService;

    @Operation(
            summary = "Crear una nueva reserva",
            description = "Solo accesible por usuarios con rol ADMIN y RECEPTION",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Reserva creada exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
            }
    )
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

    @Operation(
            summary = "Eliminar una reserva por ID",
            description = "Solo accesible por usuarios con rol ADMIN y RECEPTION",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Reserva eliminada exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Reserva no encontrada", content = @Content)
            }
    )
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Listar reservas con paginación",
            description = "Solo accesible por usuarios con rol ADMIN o RECEPTION",
            parameters = {
                    @Parameter(name = "page", description = "Número de página (0-indexado)", example = "0"),
                    @Parameter(name = "size", description = "Cantidad de elementos por página", example = "10"),
                    @Parameter(name = "sort", description = "Campo para ordenar (ej: createdAt,asc)", example = "createdAt,desc")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de reservas")
            }
    )
    @GetMapping
    public Map<String, Object> findAllBooking(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) ReservationStatus status,
            @RequestParam(required = false) Long guestId,
            @RequestParam(required = false) Long receptionistId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime checkIn,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime checkOut,
            @RequestParam(required = false) String guestFirstName
    ) {

        Page<BookingResponseDTO> bookingPage = bookingService.findAllBooking(
                pageable,
                status,
                guestId,
                receptionistId,
                checkIn,
                checkOut,
                guestFirstName);
        return PaginationResponseBuilder.build(bookingPage);
    }

    @Operation(
            summary = "Obtener una reserva por su ID",
            description = "Solo accesible por usuarios con rol ADMIN o RECEPTION",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reserva encontrada"),
                    @ApiResponse(responseCode = "404", description = "Reserva no encontrada", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public BookingResponseDTO findById(@PathVariable Long id) {
        return bookingService.findById(id);
    }

    @Operation(
            summary = "Actualizar una reserva existente",
            description = "Solo accesible por usuarios con rol ADMIN o RECEPTION",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reserva actualizada exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Reserva no encontrada", content = @Content)
            }
    )
    @PutMapping("/{id}")
    @Transactional
    public BookingResponseDTO updateBooking(
            @PathVariable Long id,
            @RequestBody @Valid BookingUpdateDTO bookingUpdateDTO
    ) {
        return bookingService.updateBooking(id, bookingUpdateDTO);
    }
}
