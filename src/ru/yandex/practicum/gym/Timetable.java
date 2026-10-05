package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {

        if (trainingSession == null) {
            return;
        }

        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();

        TreeMap<TimeOfDay, List<TrainingSession>> trainingsForDay = timetable.get(dayOfWeek);
        if (trainingsForDay == null) {
            trainingsForDay = new TreeMap<>();
            timetable.put(dayOfWeek, trainingsForDay);
        }

        List<TrainingSession> trainingSessions = trainingsForDay.get(timeOfDay);
        if (trainingSessions == null) {
            trainingSessions = new ArrayList<>();
            trainingsForDay.put(timeOfDay, trainingSessions);
        }

        trainingSessions.add(trainingSession);
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        TreeMap<TimeOfDay, List<TrainingSession>> trainingsForDay = timetable.get(dayOfWeek);
        if (trainingsForDay == null) {
            trainingsForDay = new TreeMap<>();
        }
        return trainingsForDay;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, List<TrainingSession>> trainingsForDay = timetable.get(dayOfWeek);
        if (trainingsForDay == null) {
            return Collections.emptyList();
        }
        return trainingsForDay.getOrDefault(timeOfDay, Collections.emptyList());
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        Map<Coach, Integer> counterForCoaches = new HashMap<>();

        for (TreeMap<TimeOfDay, List<TrainingSession>> trainingsForDay : timetable.values()) {
            for (List<TrainingSession> trainingsForTime : trainingsForDay.values()) {
                for (TrainingSession trainingSession : trainingsForTime) {
                    Coach coach = trainingSession.getCoach();
                    if (!counterForCoaches.containsKey(coach)) {
                        counterForCoaches.put(coach, 1);
                    } else {
                        counterForCoaches.put(coach, counterForCoaches.get(coach) + 1);
                    }
                }
            }
        }

        List<CounterOfTrainings> counterOfTrainings = new ArrayList<>();
        for (Coach coach : counterForCoaches.keySet()) {
            counterOfTrainings.add(new CounterOfTrainings(coach, counterForCoaches.get(coach)));
        }

        Collections.sort(counterOfTrainings, Collections.reverseOrder());
        //Collections.reverse(counterOfTrainings);

        return counterOfTrainings;
    }
}
