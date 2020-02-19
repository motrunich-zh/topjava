package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;

@Service
public class MealService {

    private MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int userId) {
        Meal savedMeal = repository.save(meal, userId);
        checkNotFound(savedMeal != null, "id=" + meal.getId() + " for user with id=" + userId);
        return savedMeal;
    }

    public void delete(int id, int userId) {
        checkNotFound(repository.delete(id, userId), "id=" + id + " for user with id=" + userId);
    }

    public Meal get(int id, int userId) {
        Meal meal = repository.get(id, userId);
        checkNotFound(meal != null, "id=" + id + " for user with id=" + userId);
        return meal;
    }

    public List<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }



}