package com.example.skilllinkbackend.features.booking.validations.edition;

import com.example.skilllinkbackend.config.exceptions.ReservationTimeConflict;
import com.example.skilllinkbackend.features.booking.dto.BookingUpdateDTO;
import com.example.skilllinkbackend.features.booking.model.Booking;
import com.example.skilllinkbackend.features.booking.model.ReservationStatus;
import com.example.skilllinkbackend.features.booking.repository.IBookingRepository;
import com.example.skilllinkbackend.features.booking.validations.dto.TimeValidationResult;
import com.example.skilllinkbackend.features.bookingitem.dto.BookingItemUpdateDTO;
import com.example.skilllinkbackend.shared.util.BookingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EditTimeIntervalValidation implements BookingEditionValidation {

    private final IBookingRepository bookingRepository;

    @Override
    public void validate(BookingUpdateDTO bookingUpdateDTO) {
        List<BookingItemUpdateDTO> bookingItemsNew = bookingUpdateDTO.bookingItems();
        List<Long> accommodationIdsNew = bookingItemsNew.stream().map(BookingItemUpdateDTO::accommodationId).toList();
        OffsetDateTime checkInNew = bookingUpdateDTO.checkIn();
        OffsetDateTime checkOutNew = bookingUpdateDTO.checkOut();

        List<Booking> bookingsDb = bookingRepository.findAllByStatus(ReservationStatus.RESERVED);
        TimeValidationResult timeValidationResult = BookingUtil.validateTimeInterval(
                accommodationIdsNew,
                checkInNew,
                checkOutNew,
                bookingsDb.stream().filter(b -> b.getId() != bookingUpdateDTO.id()).toList()
        );

        if (!timeValidationResult.isValid()) {
            throw new ReservationTimeConflict("Existen alojamientos que ya se encuentran reservados en ese rango de fechas",
                    timeValidationResult);
        }
    }
}
