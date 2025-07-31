package com.example.skilllinkbackend.features.booking.validations.creation;

import com.example.skilllinkbackend.config.exceptions.ReservationTimeConflict;
import com.example.skilllinkbackend.features.booking.dto.BookingRegisterDTO;
import com.example.skilllinkbackend.features.booking.model.Booking;
import com.example.skilllinkbackend.features.booking.model.ReservationStatus;
import com.example.skilllinkbackend.features.booking.repository.IBookingRepository;
import com.example.skilllinkbackend.features.booking.validations.dto.TimeValidationResult;
import com.example.skilllinkbackend.features.bookingitem.dto.BookingItemRegisterDTO;
import com.example.skilllinkbackend.shared.util.BookingUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class TimeIntervalValidation implements BookingCreationValidation {

    /***
     * @Slf4j es una anotación de Lombok que te genera automáticamente un
     * private static final Logger log = LoggerFactory.getLogger(...);
     */

    private final IBookingRepository bookingRepository;

    @Override
    public void validate(BookingRegisterDTO bookingRegisterDTO) {

        List<BookingItemRegisterDTO> bookingItemsNew = bookingRegisterDTO.bookingItems();
        List<Long> accommodationIdsNew = bookingItemsNew.stream().map(BookingItemRegisterDTO::accommodationId).toList();
        OffsetDateTime checkInNew = bookingRegisterDTO.checkIn();
        OffsetDateTime checkOutNew = bookingRegisterDTO.checkOut();

        /**
         * 1. Obtener todas las reservas existentes
         * 2. Cada reserva tiene una lista de booking items y su propio checkIn checkOut
         * 3. Cada booking item tiene un accommodationId
         * 4. Ver si algún accommodationId de bookingItemsNew es igual a accommodationId
         * 5. Si es igual, ver si checkInNew y checkOutNew están dentro de checkIn checkOut
         * 6. Si está dentro salta excepcion 400 BAD REQUEST
         */

        List<Booking> bookingsDb = bookingRepository.findAllByStatus(ReservationStatus.RESERVED);
        TimeValidationResult timeValidationResult = BookingUtil.validateTimeInterval(
                accommodationIdsNew,
                checkInNew,
                checkOutNew,
                bookingsDb
        );

        log.info("is valid {}", timeValidationResult.isValid());
        if (!timeValidationResult.isValid()) {
            throw new ReservationTimeConflict("Existen alojamientos que ya se encuentran reservados en ese rango de fechas",
                    timeValidationResult);
        }
    }
}
