package com.example.skilllinkbackend.shared.util;

import com.example.skilllinkbackend.features.booking.model.Booking;
import com.example.skilllinkbackend.features.booking.validations.dto.TimeValidationResult;

import java.time.OffsetDateTime;
import java.util.List;

public class BookingUtil {

    /***
     * Recorre todos los nuevos ítems de reserva (bookingItemsNew).
     * Revisa si alguno de ellos entra en conflicto con las reservas que ya están en la base de datos (bookingsDb).
     * Si hay conflicto, guarda el accommodationId (ID del alojamiento) en una lista.
     * @param accommodationIdsNew lista de ids de los nuevos items que se pretende crear
     * @param checkInNew fecha de inicio de la reserva nueva
     * @param checkOutNew fecha de salida de la reserva nueva
     * @param bookingsDb lista de reservas de la bd
     * @return Devuelve un objeto TimeValidationResult
     */
    public static TimeValidationResult validateTimeInterval(
            List<Long> accommodationIdsNew,
            OffsetDateTime checkInNew,
            OffsetDateTime checkOutNew,
            List<Booking> bookingsDb
    ) {
        var conflictedAccommodationIds = accommodationIdsNew.stream()
                .filter(accommodationId -> hasConflict(accommodationId, checkInNew, checkOutNew, bookingsDb))
                .distinct()
                .toList();

        return new TimeValidationResult(conflictedAccommodationIds.isEmpty(), conflictedAccommodationIds);
    }

    /***
     * Verifica si un ítem nuevo de reserva entra en conflicto con algún ítem de las reservas existentes.
     * Recorre todas las reservas (bookingsDb).
     * Dentro de cada reserva, revisa sus ítems (booking.getBookingItems()).
     * Para cada ítem:
     * Compara si el ID del alojamiento es igual al del ítem nuevo (itemNew.accommodationId()).
     * Verifica si las fechas se solapan usando isOverlapping(...).
     * @param accommodationIdNew
     * @param checkInNew
     * @param checkOutNew
     * @param bookingsDb
     * @return  Si encuentra al menos un conflicto, retorna true.
     */
    private static boolean hasConflict(
            Long accommodationIdNew,
            OffsetDateTime checkInNew,
            OffsetDateTime checkOutNew,
            List<Booking> bookingsDb
    ) {
        return bookingsDb.stream()
                .flatMap(booking -> booking.getBookingItems().stream()
                        .filter(itemDb -> accommodationIdNew.equals(itemDb.getAccommodation().getId()))
                        .filter(itemDb -> isOverlapping(
                                checkInNew, checkOutNew,
                                booking.getCheckIn(), booking.getCheckOut()
                        ))
                )
                .findAny()
                .isPresent();
    }

    /***
     * Comprueba si dos intervalos de fechas se solapan.
     * @param checkInNew
     * @param checkOutNew
     * @param checkInDb
     * @param checkOutDb
     * @return Retorna true si hay cruce
     */
    private static boolean isOverlapping(
            OffsetDateTime checkInNew,
            OffsetDateTime checkOutNew,
            OffsetDateTime checkInDb,
            OffsetDateTime checkOutDb
    ) {
        return (!checkOutNew.isBefore(checkInDb) && !checkOutNew.isAfter(checkOutDb)) ||
                (!checkInNew.isBefore(checkInDb) && !checkInNew.isAfter(checkOutDb));
    }

    /*public static TimeValidationResult validateTimeInterval(
            List<BookingItemRegisterDTO> bookingItemsNew,
            OffsetDateTime checkInNew,
            OffsetDateTime checkOutNew,
            List<Booking> bookingsDb
    ) {

        List<Long> conflictedAccommodationIds = new ArrayList<>();

        for (BookingItemRegisterDTO itemNew : bookingItemsNew) {
            for (Booking bookingDb : bookingsDb) {
                OffsetDateTime checkInDb = bookingDb.getCheckIn();
                OffsetDateTime checkOutDb = bookingDb.getCheckOut();
                List<BookingItem> bookingItemsDb = bookingDb.getBookingItems();
                for (BookingItem bookingItemDb : bookingItemsDb) {
                    if (itemNew.accommodationId().equals(bookingItemDb.getAccommodation().getId())) {
                        boolean withinInterval =
                                (!checkInNew.isBefore(checkInDb) && !checkInNew.isAfter(checkOutDb)) ||
                                       (!checkOutNew.isBefore(checkInDb) && !checkOutNew.isAfter(checkOutDb));

                        if (withinInterval) {
                            conflictedAccommodationIds.add(itemNew.accommodationId());
                        }
                    }
                }
            }
        }

        boolean isValid = conflictedAccommodationIds.isEmpty();
        return new TimeValidationResult(isValid, conflictedAccommodationIds);
    }*/
}
