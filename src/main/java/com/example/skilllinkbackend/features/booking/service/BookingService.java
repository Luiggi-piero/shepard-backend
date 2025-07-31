package com.example.skilllinkbackend.features.booking.service;

import com.example.skilllinkbackend.config.exceptions.NotFoundException;
import com.example.skilllinkbackend.features.accommodation.model.Accommodation;
import com.example.skilllinkbackend.features.accommodation.repository.IAccommodationRepository;
import com.example.skilllinkbackend.features.booking.dto.BookingRegisterDTO;
import com.example.skilllinkbackend.features.booking.dto.BookingResponseDTO;
import com.example.skilllinkbackend.features.booking.model.Booking;
import com.example.skilllinkbackend.features.booking.model.ReservationStatus;
import com.example.skilllinkbackend.features.booking.repository.IBookingRepository;
import com.example.skilllinkbackend.features.booking.validations.creation.BookingCreationValidation;
import com.example.skilllinkbackend.features.bookingitem.dto.BookingItemRegisterDTO;
import com.example.skilllinkbackend.features.bookingitem.model.BookingItem;
import com.example.skilllinkbackend.features.receptionist.model.Receptionist;
import com.example.skilllinkbackend.features.receptionist.repository.IReceptionistRepository;
import com.example.skilllinkbackend.features.usuario.model.User;
import com.example.skilllinkbackend.features.usuario.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService{

    private final IBookingRepository bookingRepository;
    private final IUserRepository userRepository;
    private final IReceptionistRepository receptionistRepository;
    private final IAccommodationRepository accommodationRepository;
    private final List<BookingCreationValidation> creationValidations;

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
        for (BookingItemRegisterDTO itemDTO: bookingRegisterDTO.bookingItems()){
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
}
