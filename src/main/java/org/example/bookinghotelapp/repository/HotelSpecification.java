package org.example.bookinghotelapp.repository;

import org.example.bookinghotelapp.controller.request.HotelFilter;
import org.example.bookinghotelapp.entity.Hotel;
import org.springframework.data.jpa.domain.Specification;

public interface HotelSpecification {

    static Specification<Hotel> withFilter(HotelFilter filter) {
        return Specification.allOf(
                byHotelId(filter.getId()),
                byHotelName(filter.getName()),
                byAdvertisementTitle(filter.getAdvertisementTitle()),
                byCity(filter.getCity()),
                byHotelAddress(filter.getAddress()),
                byDistanceFromCityCenter(filter.getDistanceFromCityCenter()),
                byRating(filter.getRating()),
                byRatingCount(filter.getRatingCount())
        );
    }

    static Specification<Hotel> byHotelId(Long id) {
        return ((root, query, criteriaBuilder) -> id == null ? null : criteriaBuilder.equal(root.get(Hotel.Fields.id), id));
    }

    static Specification<Hotel> byHotelName(String name) {
        return ((root, query, criteriaBuilder) -> name == null ? null : criteriaBuilder.equal(root.get(Hotel.Fields.name), name));
    }

    static Specification<Hotel> byAdvertisementTitle(String advertisementTitle) {
        return ((root, query, criteriaBuilder) -> advertisementTitle == null ? null : criteriaBuilder.equal(root.get(Hotel.Fields.advertisementTitle), advertisementTitle));
    }

    static Specification<Hotel> byCity(String city) {
        return ((root, query, criteriaBuilder) -> city == null ? null : criteriaBuilder.equal(root.get(Hotel.Fields.city), city));
    }

    static Specification<Hotel> byHotelAddress(String address) {
        return ((root, query, criteriaBuilder) -> address == null ? null : criteriaBuilder.equal(root.get(Hotel.Fields.address), address));
    }

    static Specification<Hotel> byDistanceFromCityCenter(Double distanceFromCityCenter) {
        return ((root, query, criteriaBuilder) -> distanceFromCityCenter == null ? null : criteriaBuilder.equal(root.get(Hotel.Fields.distanceFromCityCenter), distanceFromCityCenter));
    }

    static Specification<Hotel> byRating(Double rating) {
        return ((root, query, criteriaBuilder) -> rating == null ? null : criteriaBuilder.equal(root.get(Hotel.Fields.rating), rating));
    }
    static Specification<Hotel> byRatingCount(Integer rating) {
        return ((root, query, criteriaBuilder) -> rating == null ? null : criteriaBuilder.equal(root.get(Hotel.Fields.ratingCount), rating));
    }
}
