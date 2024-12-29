package com.example.go_healthy_be.service;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.example.go_healthy_be.entity.Schedule;
import com.example.go_healthy_be.repository.ScheduleRepository;

@Service
public class ReminderService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired

    private JavaMailSender javaMailSender;

    @Scheduled(fixedRate=60000)
    public void sendReminder(){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reminderWindowStart = now.plusMinutes(1);
        LocalDateTime reminderWindowEnd = now.minusMinutes(1);
        List<Schedule> schedules = scheduleRepository.findAllByScheduleTimeBetween(reminderWindowEnd, reminderWindowStart);
        for (Schedule schedule : schedules) {
            sendReminder(schedule);
        }
    }
    private void sendReminder(Schedule schedule) {
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd;HH:mm");
        String formattedScheduleTime = schedule.getScheduleTime().format(formatter);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(schedule.getUser().getEmail());
        message.setSubject("Reminder: " + schedule.getScheduleName());
        message.setText("Ini adalah pesan pengingat kegiatan anda: " + schedule.getScheduleName() + " pada waktu " + formattedScheduleTime);
        javaMailSender.send(message);
    }

}

