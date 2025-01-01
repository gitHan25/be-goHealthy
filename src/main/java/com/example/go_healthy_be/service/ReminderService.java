package com.example.go_healthy_be.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
    private JavaMailSender javaMailSender;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Scheduled(fixedRate = 60000) // Jalankan setiap 60 detik
    public void sendReminder() {
        try {
            // Gunakan waktu UTC untuk menyimpan dan mengambil data dari database
            LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
            LocalDateTime reminderWindowStart = now.plusMinutes(1);
            LocalDateTime reminderWindowEnd = now.minusMinutes(1);

            List<Schedule> schedules = scheduleRepository.findAllByScheduleTimeBetween(reminderWindowEnd, reminderWindowStart);
            for (Schedule schedule : schedules) {
                sendReminder(schedule);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void sendReminder(Schedule schedule) {
        // Tentukan zona waktu pengguna, misalnya Asia/Jakarta
        ZoneId userZoneId = ZoneId.of("Asia/Jakarta");

        // Konversi waktu server (UTC) ke zona waktu pengguna
        ZonedDateTime userScheduleTime = schedule.getScheduleTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(userZoneId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedScheduleTime = userScheduleTime.format(formatter);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(schedule.getUser().getEmail());
        message.setSubject("📅 Reminder: " + schedule.getScheduleName());
        message.setText(
            "Halo " + schedule.getUser().getName() + ",\n\n" +
            "Kami ingin mengingatkan kegiatan penting yang telah kamu jadwalkan:\n\n" +
            "📌 **" + schedule.getScheduleName() + "**\n" +
            "🕒 **Waktu:** " + formattedScheduleTime + "\n\n" +
            "Selamat beraktivitas dan semoga harimu menyenangkan! 😊\n\n" +
            "Salam hangat,\n" +
            "Tim Go Healthy"
        );
        javaMailSender.send(message);
    }
}