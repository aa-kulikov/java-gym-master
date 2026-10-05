package ru.yandex.practicum.gym;

import java.util.Objects;

public class CounterOfTrainings implements Comparable<CounterOfTrainings> {

    private Coach coach;
    private int count;

    public CounterOfTrainings(Coach coach, int count) {
        this.coach = coach;
        this.count = count;
    }

    @Override
    public int compareTo(CounterOfTrainings o) {
        return count - o.count;
    }

    public Coach getCoach() {
        return coach;
    }

    public int getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CounterOfTrainings counterOfTrainings = (CounterOfTrainings) o;
        return Objects.equals(coach, counterOfTrainings.getCoach())
                && count == counterOfTrainings.getCount();
    }

    @Override
    public int hashCode() {
        return Objects.hash(coach, count);
    }
}