package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        TreeMap<TimeOfDay, List<TrainingSession>> mondayTrainingSession = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(1, mondayTrainingSession.size());
        Assertions.assertEquals(1, mondayTrainingSession.sequencedValues().getFirst().size());

        //Проверить, что за вторник не вернулось занятий
        TreeMap<TimeOfDay, List<TrainingSession>> tuesdayTrainingSession = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertEquals(0, tuesdayTrainingSession.size());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        TreeMap<TimeOfDay, List<TrainingSession>> mondayTrainingSession = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(1, mondayTrainingSession.size());
        Assertions.assertEquals(1, mondayTrainingSession.sequencedValues().getFirst().size());

        //Проверить, что это то самое занятие
        Assertions.assertEquals(mondayChildTrainingSession, mondayTrainingSession.get(
                new TimeOfDay(13, 0)).getFirst());

        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        TreeMap<TimeOfDay, List<TrainingSession>> thursdayTrainingSession = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        Assertions.assertEquals(2, thursdayTrainingSession.size());

        int index = 0;
        for (TimeOfDay entry : thursdayTrainingSession.navigableKeySet()) {
            Assertions.assertEquals(1, thursdayTrainingSession.get(entry).size());
            if (index == 0) {
                Assertions.assertEquals(new TimeOfDay(13, 0), entry);
            } else {
                Assertions.assertEquals(new TimeOfDay(20, 0), entry);
            }
            index++;
        }

        // Проверить, что за вторник не вернулось занятий
        TreeMap<TimeOfDay, List<TrainingSession>> tuesdayTrainingSession = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertEquals(0, tuesdayTrainingSession.size());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        List<TrainingSession> trainingsForMondayAt13 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        Assertions.assertEquals(1, trainingsForMondayAt13.size());

        //Проверить, что за понедельник в 14:00 не вернулось занятий
        List<TrainingSession> trainingsForMondayAt14 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        Assertions.assertEquals(0, trainingsForMondayAt14.size());
    }

    @Test
    void testGetTrainingSessionsForDayAndTimeMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach acrobaticCoach = new Coach("Петрова", "Ирина", "Ивановна");
        Coach boxingCoach = new Coach("Николаев", "Сергей", "Васильевич");

        Group groupChildAcrobatic = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTAcrobaticSession = new TrainingSession(groupChildAcrobatic, acrobaticCoach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession wednesdayChildTAcrobaticSession = new TrainingSession(groupChildAcrobatic, acrobaticCoach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(mondayChildTAcrobaticSession);
        timetable.addNewTrainingSession(wednesdayChildTAcrobaticSession);

        Group groupChildBoxing = new Group("Бокс для детей", Age.CHILD, 60);
        TrainingSession wednesdayChildBoxingSession = new TrainingSession(groupChildBoxing, boxingCoach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(9, 0));

        timetable.addNewTrainingSession(wednesdayChildBoxingSession);

        Group groupAdultBoxing = new Group("Бокс для взрослых", Age.ADULT, 90);
        TrainingSession wednesdayAdultBoxingSession = new TrainingSession(groupAdultBoxing, boxingCoach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(wednesdayAdultBoxingSession);

        //Проверить, что в понедельник в 13:00 проходит только одно занятие
        List<TrainingSession> trainingsForMondayAt13 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        Assertions.assertEquals(1, trainingsForMondayAt13.size());

        // Проверить, что в среду в 9:00 проходит только одно занятие
        List<TrainingSession> trainingsForWednesdayAt9 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.WEDNESDAY, new TimeOfDay(9, 0));
        Assertions.assertEquals(1, trainingsForWednesdayAt9.size());

        //В 9:00 бокс для детей
        Assertions.assertEquals(wednesdayChildBoxingSession, trainingsForWednesdayAt9.getFirst());

        // Проверить, что в среду в 20:00 проходит два занятия
        List<TrainingSession> trainingsForWednesdayAt20 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.WEDNESDAY, new TimeOfDay(20, 0));
        Assertions.assertEquals(2, trainingsForWednesdayAt20.size());

        //В 20:00 акробатика для детей и бокс для взрослых
        Set<String> sessionsName = new HashSet<>();
        sessionsName.add("Акробатика для детей");
        sessionsName.add("Бокс для взрослых");
        for (TrainingSession trainingSession : trainingsForWednesdayAt20) {
            sessionsName.remove(trainingSession.getGroup().getTitle());
        }
        Assertions.assertEquals(0, sessionsName.size());
    }

    @Test
    void testGetTrainingSessionsForDayAddSession() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group group = new Group("Бокс для взрослых", Age.ADULT, 30);
        TrainingSession trainingSessionAt1910= new TrainingSession(group, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(19, 10));
        TrainingSession trainingSessionAt2030 = new TrainingSession(group, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 30));

        timetable.addNewTrainingSession(trainingSessionAt1910);
        timetable.addNewTrainingSession(trainingSessionAt2030);

        TreeMap<TimeOfDay, List<TrainingSession>> trainingSessions = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        Assertions.assertEquals(2, trainingSessions.size());

        int index = 0;
        for (TimeOfDay entry : trainingSessions.navigableKeySet()) {
            Assertions.assertEquals(1, trainingSessions.get(entry).size());
            switch (index) {
                case 0 -> Assertions.assertEquals(new TimeOfDay(19, 10), entry);
                case 1 -> Assertions.assertEquals(new TimeOfDay(20, 30), entry);
            }
            index++;
        }

        //Проверить, что после добавления занятия сортировка сохраняется
        TrainingSession trainingSessionAt1950 = new TrainingSession(group, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(19, 50));
        timetable.addNewTrainingSession(trainingSessionAt1950);

        trainingSessions = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        Assertions.assertEquals(3, trainingSessions.size());

        index = 0;
        for (TimeOfDay entry : trainingSessions.navigableKeySet()) {
            //Проверить, что количество одновременных занятий не изменилось
            Assertions.assertEquals(1, trainingSessions.get(entry).size());

            //Проверить сортировку
            switch (index) {
                case 0 -> Assertions.assertEquals(new TimeOfDay(19, 10), entry);
                case 1 -> Assertions.assertEquals(new TimeOfDay(19, 50), entry);
                case 2 -> Assertions.assertEquals(new TimeOfDay(20, 30), entry);
            }
            index++;
        }
    }

    @Test
    void testGetTrainingSessionsForDayAndTimeAddSessionSameTimes() {
        Timetable timetable = new Timetable();

        Coach acrobaticCoach = new Coach("Петрова", "Ирина", "Ивановна");
        Coach boxingCoach = new Coach("Николаев", "Сергей", "Васильевич");

        Group acrobaticGroup = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession acrobaticTrainingSession = new TrainingSession(acrobaticGroup, acrobaticCoach,
                DayOfWeek.THURSDAY, new TimeOfDay(19, 0));

        timetable.addNewTrainingSession(acrobaticTrainingSession);

        List<TrainingSession> trainingSessionsAt19 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.THURSDAY, new TimeOfDay(19, 0));
        Assertions.assertEquals(1, trainingSessionsAt19.size());

        //Проверить, что новое занятие назначено на то же время
        Group boxingGroup = new Group("Бокс для взрослых", Age.ADULT, 40);
        TrainingSession boxingTrainingSession = new TrainingSession(boxingGroup, boxingCoach,
                DayOfWeek.THURSDAY, new TimeOfDay(19, 0));
        timetable.addNewTrainingSession(boxingTrainingSession);

        trainingSessionsAt19 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.THURSDAY, new TimeOfDay(19, 0));
        Assertions.assertEquals(2, trainingSessionsAt19.size());

        //Проверить, что на 18:00 не вернулось занятий
        List<TrainingSession> trainingsForMondayAt18 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.THURSDAY, new TimeOfDay(18, 0));
        Assertions.assertEquals(0, trainingsForMondayAt18.size());

    }

    @Test
    public void testGetCountByCoachesSingleSessions() {
        Timetable timetable = new Timetable();

        //Проверить, что у тренеров нет занятий
        Assertions.assertEquals(0, timetable.getCountByCoaches().size());

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что есть только одно занятие для одного тренера
        List<CounterOfTrainings> counterOfTrainings = timetable.getCountByCoaches();
        Assertions.assertEquals(1, counterOfTrainings.size());
        Assertions.assertEquals(coach, counterOfTrainings.getFirst().getCoach());
        Assertions.assertEquals(1, counterOfTrainings.getFirst().getCount());
    }

    @Test
    public void testGetCountByCoachesMultipleSessions() {
        Timetable timetable = new Timetable();

        //Проверить, что у тренеров нет занятий
        Assertions.assertEquals(0, timetable.getCountByCoaches().size());

        Coach coachChildAcrobatics = new Coach("Розова", "Любовь", "Андреевна");
        Group groupChildAcrobatics = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTAcrobaticSession = new TrainingSession(groupChildAcrobatics, coachChildAcrobatics,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession wednesdayAt9ChildTAcrobaticSession = new TrainingSession(groupChildAcrobatics, coachChildAcrobatics,
                DayOfWeek.WEDNESDAY, new TimeOfDay(9, 0));
        TrainingSession wednesdayAt20ChildTAcrobaticSession = new TrainingSession(groupChildAcrobatics, coachChildAcrobatics,
                DayOfWeek.WEDNESDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(mondayChildTAcrobaticSession);
        timetable.addNewTrainingSession(wednesdayAt9ChildTAcrobaticSession);
        timetable.addNewTrainingSession(wednesdayAt20ChildTAcrobaticSession);

        Coach coachAdultBoxing = new Coach("Николаев", "Сергей", "Васильевич");
        Group groupAdultBoxing = new Group("Бокс для взрослых", Age.ADULT, 90);
        TrainingSession wednesdayAt9AdultBoxingSession = new TrainingSession(groupAdultBoxing, coachAdultBoxing,
                DayOfWeek.WEDNESDAY, new TimeOfDay(9, 0));
        TrainingSession saturdayAt9AdultBoxingSession = new TrainingSession(groupAdultBoxing, coachAdultBoxing,
                DayOfWeek.SATURDAY, new TimeOfDay(9, 0));

        timetable.addNewTrainingSession(wednesdayAt9AdultBoxingSession);
        timetable.addNewTrainingSession(saturdayAt9AdultBoxingSession);

        //Проверить, что кол-во занятий посчитано верно
        List<CounterOfTrainings> counterOfTrainings = timetable.getCountByCoaches();
        Assertions.assertEquals(2, counterOfTrainings.size());

        Set<Coach> coaches = new HashSet<>();
        for ( CounterOfTrainings counterOfTraining : counterOfTrainings ) {
            Coach coach = counterOfTraining.getCoach();
            coaches.add(coach);
            if (coach.equals(coachChildAcrobatics)) {
                Assertions.assertEquals(3, counterOfTraining.getCount());
            } else if (coach.equals(coachAdultBoxing)) {
                Assertions.assertEquals(2, counterOfTraining.getCount());
            }
        }

        //Проверить, что количество тренеров посчитано верно
        Assertions.assertEquals(2, coaches.size());
    }

    @Test
    public void testGetCountByCoachesSorting() {
        Timetable timetable = new Timetable();

        //Николаев Сергей: 2 занятия по боксу + 1 занятие по шахматам
        Coach coachAdultBoxing = new Coach("Николаев", "Сергей", "Васильевич");
        Group groupAdultBoxing = new Group("Бокс для взрослых", Age.ADULT, 60);

        TrainingSession wednesdayAt9AdultBoxingSession = new TrainingSession(groupAdultBoxing, coachAdultBoxing,
                DayOfWeek.WEDNESDAY, new TimeOfDay(9, 0));
        TrainingSession saturdayAt9AdultBoxingSession = new TrainingSession(groupAdultBoxing, coachAdultBoxing,
                DayOfWeek.MONDAY, new TimeOfDay(9, 0));

        Group groupAdultChess = new Group("Шахматы для взрослых", Age.ADULT, 90);
        TrainingSession saturdayAt10AdultChessSession = new TrainingSession(groupAdultChess, coachAdultBoxing,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 30));

        timetable.addNewTrainingSession(wednesdayAt9AdultBoxingSession);
        timetable.addNewTrainingSession(saturdayAt9AdultBoxingSession);
        timetable.addNewTrainingSession(saturdayAt10AdultChessSession);

        //Розова Любовь: 2 занятия по детской акробатике
        Coach coachChildAcrobatics = new Coach("Розова", "Любовь", "Андреевна");
        Group groupChildAcrobatics = new Group("Акробатика для детей", Age.CHILD, 60);

        TrainingSession wednesdayAt9ChildAcrobaticSession = new TrainingSession(groupChildAcrobatics, coachChildAcrobatics,
                DayOfWeek.WEDNESDAY, new TimeOfDay(9, 0));
        TrainingSession wednesdayAt20ChildAcrobaticSession = new TrainingSession(groupChildAcrobatics, coachChildAcrobatics,
                DayOfWeek.WEDNESDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(wednesdayAt9ChildAcrobaticSession);
        timetable.addNewTrainingSession(wednesdayAt20ChildAcrobaticSession);

        //Боев Юрий>: 6 занятий по взрослой акробатике
        Coach coachAdultAcrobatics = new Coach("Боев", "Юрий", "Павлович");
        Group groupAdultAcrobatics = new Group("Акробатика для взрослых", Age.ADULT, 60);

        TrainingSession tuesdayAt9AdultAcrobaticSession = new TrainingSession(groupAdultAcrobatics, coachAdultAcrobatics,
                DayOfWeek.TUESDAY, new TimeOfDay(9, 0));
        TrainingSession tuesdayAt20AdultAcrobaticSession = new TrainingSession(groupAdultAcrobatics, coachAdultAcrobatics,
                DayOfWeek.TUESDAY, new TimeOfDay(20, 0));
        TrainingSession wednesdayAt9AdultAcrobaticSession = new TrainingSession(groupAdultAcrobatics, coachAdultAcrobatics,
                DayOfWeek.WEDNESDAY, new TimeOfDay(9, 0));
        TrainingSession fridayAt9AdultAcrobaticSession = new TrainingSession(groupAdultAcrobatics, coachAdultAcrobatics,
                DayOfWeek.FRIDAY, new TimeOfDay(9, 0));
        TrainingSession fridayAt17AdultAcrobaticSession = new TrainingSession(groupAdultAcrobatics, coachAdultAcrobatics,
                DayOfWeek.FRIDAY, new TimeOfDay(17, 0));
        TrainingSession sunday10AdultAcrobaticSession = new TrainingSession(groupAdultAcrobatics, coachAdultAcrobatics,
                DayOfWeek.SUNDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(tuesdayAt9AdultAcrobaticSession);
        timetable.addNewTrainingSession(tuesdayAt20AdultAcrobaticSession);
        timetable.addNewTrainingSession(wednesdayAt9AdultAcrobaticSession);
        timetable.addNewTrainingSession(fridayAt9AdultAcrobaticSession);
        timetable.addNewTrainingSession(fridayAt17AdultAcrobaticSession);
        timetable.addNewTrainingSession(sunday10AdultAcrobaticSession);

        //Проверить сортировку
        int index = 0;
        List<CounterOfTrainings> counterOfTrainings = timetable.getCountByCoaches();
        Assertions.assertEquals(3, counterOfTrainings.size());
        for (CounterOfTrainings counterOfTraining : counterOfTrainings) {
            switch (index) {
                case 0 -> Assertions.assertEquals(coachAdultAcrobatics, counterOfTraining.getCoach());
                case 1 -> Assertions.assertEquals(coachAdultBoxing, counterOfTraining.getCoach());
                case 2 -> Assertions.assertEquals(coachChildAcrobatics, counterOfTraining.getCoach());
            }
            index++;
        }
    }


}
