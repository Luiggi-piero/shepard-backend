package com.example.skilllinkbackend.features.booking.repository;

import com.example.skilllinkbackend.features.booking.model.Booking;
import com.example.skilllinkbackend.features.booking.model.ReservationStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación con Criteria API
 * El nombre de la clase debe ser BookingRepositoryCustomImpl para que Spring la detecte automáticamente como
 * implementación de BookingRepositoryCustom.
 */

@Repository
public class BookingRepositoryCustomImpl implements BookingRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Booking> findAllBookingWithFilters(
            Pageable pageable,
            ReservationStatus status,
            Long guestId,
            Long receptionistId,
            OffsetDateTime checkIn,
            OffsetDateTime checkOut,
            String guestFirstName) {

        // Paso 1: Crea el builder y el query base
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Booking> query = cb.createQuery(Booking.class);
        Root<Booking> root = query.from(Booking.class);

        // Paso 2: Lista de condiciones dinámicas (predicados)
        List<Predicate> predicates = new ArrayList<>();

        // WHERE b.enabled = true
        predicates.add(cb.isTrue(root.get("enabled")));

        // AND (status IS NULL OR b.status = :status)
        if (status != null) {
            predicates.add(cb.equal(root.get("status"), status));
        }

        // AND (guestId IS NULL OR b.guest.userId = :guestId)
        if (guestId != null) {
            predicates.add(cb.equal(root.get("guest").get("userId"), guestId));
        }

        // AND (receptionistId IS NULL OR b.receptionist.id = :receptionistId)
        if (receptionistId != null) {
            predicates.add(cb.equal(root.get("receptionist").get("id"), receptionistId));
        }

        // AND (checkIn IS NULL OR b.checkIn >= :checkIn)
        if (checkIn != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("checkOut"), checkIn));
        }
        // AND (checkOut IS NULL OR b.checkOut >= :checkOut)
        if (checkOut != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("checkIn"), checkOut));
        }

        if (guestFirstName != null && !guestFirstName.trim().isEmpty()) {
            predicates.add(cb.like(
                    cb.lower(root.get("guest").get("firstName")),
                    "%" + guestFirstName.toLowerCase() + "%"
            ));
        }

        // Paso 3: Aplica condiciones al query
        query.where(cb.and(predicates.toArray(new Predicate[0])));

        // Paso 4: Aplica orden si lo necesitas (ej: por fecha)
        // query.orderBy(cb.asc(root.get("checkIn")));

        // Paso 4: Aplica ordenamiento dinámico desde Pageable
        List<Order> orders = new ArrayList<>();
        for (Sort.Order sortOrder : pageable.getSort()) { // obtiene los campos por el cual se ordenaran
            Path<Object> path = root.get(sortOrder.getProperty()); // accedo a la propiedad de la entidad Booking
            Order order = sortOrder.isAscending() ? cb.asc(path) : cb.desc(path); // creo el orden con la propiedad
            orders.add(order);
        }
        query.orderBy(orders);


        // Paso 5: Ejecuta la consulta con paginación
        TypedQuery<Booking> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<Booking> results = typedQuery.getResultList();

        // Paso 6: Conteo total para el Page
        // Crea una consulta que solo cuenta, para saber el total.
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class); // consulta de criterios que obtiene un numero
        Root<Booking> countRoot = countQuery.from(Booking.class); // busca registros de la entidad Booking
        countQuery.select(cb.count(countRoot)); // cuenta la cantidad de registros
//        List<Predicate> countPredicates = new ArrayList<>(predicates); // No se puede reutilizar la misma lista de predicados, cada criteria tiene su propio
        List<Predicate> countPredicates = new ArrayList<>(); // Nueva lista de predicados/condiciones
        countPredicates.add(cb.isTrue(countRoot.get("enabled")));
        if (status != null) {
            countPredicates.add(cb.equal(countRoot.get("status"), status));
        }
        if (guestId != null) {
            countPredicates.add(cb.equal(countRoot.get("guest").get("userId"), guestId));
        }
        if (receptionistId != null) {
            countPredicates.add(cb.equal(countRoot.get("receptionist").get("id"), receptionistId));
        }
        if (checkIn != null) {
            countPredicates.add(cb.greaterThanOrEqualTo(countRoot.get("checkOut"), checkIn));
        }
        if (checkOut != null) {
            countPredicates.add(cb.lessThanOrEqualTo(countRoot.get("checkIn"), checkOut));
        }
        if (guestFirstName != null && !guestFirstName.trim().isEmpty()) {
            countPredicates.add(cb.like(
                    cb.lower(countRoot.get("guest").get("firstName")),
                    "%" + guestFirstName.toLowerCase() + "%"
            ));
        }


        // Aplica los mismos filtros para contar cuántos resultados hay en total.
        countQuery.where(cb.and(countPredicates.toArray(new Predicate[0])));
        Long total = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(results, pageable, total);
    }
}
