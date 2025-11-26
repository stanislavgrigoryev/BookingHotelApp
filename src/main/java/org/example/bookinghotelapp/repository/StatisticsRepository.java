package org.example.bookinghotelapp.repository;

import org.example.bookinghotelapp.entity.Statistics;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatisticsRepository extends MongoRepository<Statistics, String> {
}
