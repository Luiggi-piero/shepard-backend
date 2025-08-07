package com.example.skilllinkbackend.features.booking.service;

import com.example.skilllinkbackend.config.exceptions.NotFoundException;
import com.example.skilllinkbackend.features.accommodation.model.Accommodation;
import com.example.skilllinkbackend.features.accommodation.repository.IAccommodationRepository;
import com.example.skilllinkbackend.features.booking.dto.BookingRegisterDTO;
import com.example.skilllinkbackend.features.booking.dto.BookingResponseDTO;
import com.example.skilllinkbackend.features.booking.dto.BookingUpdateDTO;
import com.example.skilllinkbackend.features.booking.model.Booking;
import com.example.skilllinkbackend.features.booking.model.ReservationStatus;
import com.example.skilllinkbackend.features.booking.repository.IBookingRepository;
import com.example.skilllinkbackend.features.booking.validations.creation.BookingCreationValidation;
import com.example.skilllinkbackend.features.booking.validations.edition.BookingEditionValidation;
import com.example.skilllinkbackend.features.bookingitem.dto.BookingItemRegisterDTO;
import com.example.skilllinkbackend.features.bookingitem.dto.BookingItemUpdateDTO;
import com.example.skilllinkbackend.features.bookingitem.model.BookingItem;
import com.example.skilllinkbackend.features.receptionist.model.Receptionist;
import com.example.skilllinkbackend.features.receptionist.repository.IReceptionistRepository;
import com.example.skilllinkbackend.features.usuario.model.User;
import com.example.skilllinkbackend.features.usuario.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService implements IBookingService {

    private final IBookingRepository bookingRepository;
    private final IUserRepository userRepository;
    private final IReceptionistRepository receptionistRepository;
    private final IAccommodationRepository accommodationRepository;
    private final List<BookingCreationValidation> creationValidations;
    private final List<BookingEditionValidation> editionValidations;

    @Override
    public BookingResponseDTO createBooking(BookingRegisterDTO bookingRegisterDTO) {
        creationValidations.forEach(v -> v.validate(bookingRegisterDTO));

        User guest = userRepository.findByUserId(bookingRegisterDTO.guestId()).get();
        Receptionist receptionist = receptionistRepository.findById(bookingRegisterDTO.receptionistId()).get();

        Booking booking = new Booking();
        booking.setGuest(guest);
        booking.setReceptionist(receptionist);
        booking.setCheckIn(bookingRegisterDTO.checkIn());
        booking.setCheckOut(bookingRegisterDTO.checkOut());
        booking.setStatus(ReservationStatus.RESERVED); // Estado por defecto

        List<BookingItem> items = new ArrayList<>();
        for (BookingItemRegisterDTO itemDTO : bookingRegisterDTO.bookingItems()) {
            Accommodation accommodation = accommodationRepository.findById(itemDTO.accommodationId())
                    .orElseThrow(() -> new NotFoundException("El alojamiento con id " + itemDTO.accommodationId() + " no fue encontrado"));

            BookingItem bookingItem = new BookingItem();
            bookingItem.setBooking(booking);
            bookingItem.setAccommodation(accommodation);
            bookingItem.setPrice(itemDTO.price());
            items.add(bookingItem);
        }

        booking.setBookingItems(items);
        Booking bookingDb = bookingRepository.save(booking);
        return new BookingResponseDTO(bookingDb);
    }

    @Override
    public void deleteBooking(Long id) {
        Booking booking = bookingRepository.findByIdAndEnabledTrue(id)
                .orElseThrow(() -> new NotFoundException("Reserva no encontrada"));
        booking.deactive();
    }

    @Override
    public Page<BookingResponseDTO> findAllBooking(
            Pageable pageable,
            ReservationStatus status,
            Long guestId,
            Long receptionistId,
            OffsetDateTime checkIn,
            OffsetDateTime checkOut,
            String guestFirstName
    ) {
        /*log.info("Valor de checkIn {}", checkIn);
        OffsetDateTime checkInDefault = OffsetDateTime.parse("2000-07-28T15:00:00Z");
        OffsetDateTime checkInSearch = checkIn != null ? checkIn.withOffsetSameInstant(ZoneOffset.UTC) : checkInDefault;
        log.info("checkInSearch: {}", checkInSearch);
        log.info("Tipo de dato de checkInSearch: {}", checkInSearch.getClass().getName());*/


        return bookingRepository.findAllBookingWithFilters(
                pageable,
                status,
                guestId,
                receptionistId,
                checkIn,
                checkOut,
                guestFirstName
        ).map(BookingResponseDTO::new);
    }

    @Override
    public BookingResponseDTO findById(Long id) {
        Booking booking = bookingRepository.findByIdAndEnabledTrue(id)
                .orElseThrow(() -> new NotFoundException("Reserva no encontrada"));
        return new BookingResponseDTO(booking);
    }

    @Override
    public BookingResponseDTO updateBooking(Long id, BookingUpdateDTO bookingUpdateDTO) {
        editionValidations.forEach(v -> v.validate(bookingUpdateDTO));

        Booking bookingDB = bookingRepository.findByIdAndEnabledTrue(bookingUpdateDTO.id()).get();

        User guest = userRepository.findByUserId(bookingUpdateDTO.guestId()).get();
        Receptionist receptionist = receptionistRepository.findById(bookingUpdateDTO.receptionistId()).get();

        // Borrar todos los items de la reserva
        bookingDB.getBookingItems().clear();
        /*List<Long> bookingItemIds = bookingDB.getBookingItems().stream().map(BookingItem::getId).toList();
        bookingItemRepository.deleteAllByIdIn(bookingItemIds);*/

        // Crear una nueva lista de items de la reserva
        List<BookingItem> items = new ArrayList<>();

        for (BookingItemUpdateDTO itemDTO : bookingUpdateDTO.bookingItems()) {
            Accommodation accommodation = accommodationRepository.findById(itemDTO.accommodationId())
                    .orElseThrow(() -> new NotFoundException("El alojamiento con id " + itemDTO.accommodationId() + " no fue encontrado"));

            BookingItem bookingItem = new BookingItem();
            bookingItem.setBooking(bookingDB);
            bookingItem.setAccommodation(accommodation);
            bookingItem.setPrice(itemDTO.price());
            items.add(bookingItem);
        }

        bookingDB.update(
                id,
                guest,
                receptionist,
                bookingUpdateDTO.checkIn(),
                bookingUpdateDTO.checkOut(),
                bookingUpdateDTO.status()
        );
        bookingDB.getBookingItems().addAll(items);
        Booking updatedBooking = bookingRepository.findByIdAndEnabledTrue(id).get();
        return new BookingResponseDTO(updatedBooking);
    }
}
