package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepository implements MealRepository {
    private static final Sort SORT_DATE = Sort.by(Sort.Direction.DESC, "dateTime");


    @Autowired
    private CrudMealRepository crudRepository;

    @Autowired
    private CrudUserRepository userRepository;

    @Override
    public Meal save(Meal meal, int userId) {
        if (!meal.isNew() && !get(meal.getId(), userId).getUser().getId().equals(userId)) {
            return null;
        }
        meal.setUser(userRepository.getOne(userId));
        return crudRepository.save(meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudRepository.delete(id, userId) != 0;
    }

    @Override
    @Transactional
    public Meal get(int id, int userId) {
        Meal meal = crudRepository.getOne(id);
        if (meal != null && meal.getUser().getId().equals(userId)) {
            return meal;
        }
        return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.getAllByUserId(userId, SORT_DATE);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return crudRepository.getBetweenDate(startDateTime, endDateTime, userId);
    }
}
