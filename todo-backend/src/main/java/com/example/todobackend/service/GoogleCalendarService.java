package com.example.todobackend.service;

import com.example.todobackend.model.Todo;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;

/**
 * ✅ PRAVA Google Calendar integracija
 *
 * SETUP INSTRUKCIJE:
 * 1. Idi na https://console.cloud.google.com/
 * 2. Kreiraj novi projekat ili izaberi postojeći
 * 3. Aktiviraj Google Calendar API
 * 4. Idi na "Credentials" -> "Create Credentials" -> "Service Account"
 * 5. Kreiraj Service Account i preuzmi JSON key fajl
 * 6. Stavi JSON fajl u src/main/resources/google-credentials.json
 * 7. U application.properties dodaj:
 *    google.calendar.credentials.path=src/main/resources/google-credentials.json
 *    google.calendar.id=primary (ili email kalendara)
 */
@Service
public class GoogleCalendarService {

    private static final String APPLICATION_NAME = "Todo App Calendar Sync";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    @Value("${google.calendar.credentials.path:src/main/resources/google-credentials.json}")
    private String credentialsPath;

    @Value("${google.calendar.id:primary}")
    private String calendarId;

    /**
     * Kreira Google Calendar klijent
     */
    private Calendar getCalendarService() throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        GoogleCredential credential = GoogleCredential.fromStream(
                new FileInputStream(credentialsPath)
        ).createScoped(Collections.singleton("https://www.googleapis.com/auth/calendar"));

        return new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /**
     * ✅ DODAJ Todo kao Google Calendar event
     */
    public String createCalendarEvent(Todo todo) throws IOException, GeneralSecurityException {
        Calendar service = getCalendarService();

        Event event = new Event()
                .setSummary(todo.getName())
                .setDescription(buildDescription(todo));

        // Postavi datum i vreme
        EventDateTime startDateTime;
        EventDateTime endDateTime;

        if (todo.getReminderAt() != null) {
            // Ako postoji reminder, koristi njega
            ZonedDateTime zonedStart = todo.getReminderAt().atZone(ZoneId.systemDefault());
            startDateTime = new EventDateTime()
                    .setDateTime(new DateTime(zonedStart.toInstant().toEpochMilli()))
                    .setTimeZone("Europe/Ljubljana");

            // Trajanje 1 sat
            ZonedDateTime zonedEnd = zonedStart.plusHours(1);
            endDateTime = new EventDateTime()
                    .setDateTime(new DateTime(zonedEnd.toInstant().toEpochMilli()))
                    .setTimeZone("Europe/Ljubljana");
        } else if (todo.getDueDate() != null) {
            // Ako nema reminder, koristi due date (ceo dan)
            startDateTime = new EventDateTime()
                    .setDate(new DateTime(todo.getDueDate().toString()));
            endDateTime = new EventDateTime()
                    .setDate(new DateTime(todo.getDueDate().toString()));
        } else {
            // Ako nema ni reminder ni due date, postavi za danas
            LocalDateTime now = LocalDateTime.now();
            ZonedDateTime zonedStart = now.atZone(ZoneId.systemDefault());
            startDateTime = new EventDateTime()
                    .setDateTime(new DateTime(zonedStart.toInstant().toEpochMilli()))
                    .setTimeZone("Europe/Ljubljana");

            ZonedDateTime zonedEnd = zonedStart.plusHours(1);
            endDateTime = new EventDateTime()
                    .setDateTime(new DateTime(zonedEnd.toInstant().toEpochMilli()))
                    .setTimeZone("Europe/Ljubljana");
        }

        event.setStart(startDateTime);
        event.setEnd(endDateTime);

        // Kreiraj event
        Event createdEvent = service.events().insert(calendarId, event).execute();

        System.out.println("✅ Google Calendar event kreiran: " + createdEvent.getHtmlLink());
        return createdEvent.getId();
    }

    /**
     * ✅ AŽURIRAJ postojeći Google Calendar event
     */
    public void updateCalendarEvent(String eventId, Todo todo) throws IOException, GeneralSecurityException {
        Calendar service = getCalendarService();

        Event event = service.events().get(calendarId, eventId).execute();

        event.setSummary(todo.getName());
        event.setDescription(buildDescription(todo));

        // Ažuriraj datum i vreme
        if (todo.getReminderAt() != null) {
            ZonedDateTime zonedStart = todo.getReminderAt().atZone(ZoneId.systemDefault());
            EventDateTime startDateTime = new EventDateTime()
                    .setDateTime(new DateTime(zonedStart.toInstant().toEpochMilli()))
                    .setTimeZone("Europe/Ljubljana");

            ZonedDateTime zonedEnd = zonedStart.plusHours(1);
            EventDateTime endDateTime = new EventDateTime()
                    .setDateTime(new DateTime(zonedEnd.toInstant().toEpochMilli()))
                    .setTimeZone("Europe/Ljubljana");

            event.setStart(startDateTime);
            event.setEnd(endDateTime);
        } else if (todo.getDueDate() != null) {
            EventDateTime dateTime = new EventDateTime()
                    .setDate(new DateTime(todo.getDueDate().toString()));
            event.setStart(dateTime);
            event.setEnd(dateTime);
        }

        service.events().update(calendarId, eventId, event).execute();
        System.out.println("✅ Google Calendar event ažuriran: " + event.getHtmlLink());
    }

    /**
     * ✅ OBRIŠI Google Calendar event
     */
    public void deleteCalendarEvent(String eventId) throws IOException, GeneralSecurityException {
        Calendar service = getCalendarService();
        service.events().delete(calendarId, eventId).execute();
        System.out.println("✅ Google Calendar event obrisan: " + eventId);
    }

    /**
     * ✅ OZNAČI event kao completed (dodaj COMPLETED u naslov)
     */
    public void markEventAsCompleted(String eventId) throws IOException, GeneralSecurityException {
        Calendar service = getCalendarService();
        Event event = service.events().get(calendarId, eventId).execute();

        if (!event.getSummary().startsWith("✅ ")) {
            event.setSummary("✅ " + event.getSummary());
            service.events().update(calendarId, eventId, event).execute();
            System.out.println("✅ Event označen kao completed: " + event.getHtmlLink());
        }
    }

    /**
     * Kreira detaljniji opis za Google Calendar event
     */
    private String buildDescription(Todo todo) {
        StringBuilder desc = new StringBuilder();

        if (todo.getDescription() != null && !todo.getDescription().isEmpty()) {
            desc.append(todo.getDescription()).append("\n\n");
        }

        desc.append("📋 Kategorija: ").append(todo.getCategory()).append("\n");
        desc.append("⚡ Prioritet: ").append(todo.getPriority()).append("\n");

        if (todo.getDueDate() != null) {
            desc.append("📅 Rok: ").append(todo.getDueDate()).append("\n");
        }

        desc.append("\n🔗 Sinhronizovano iz Todo aplikacije");

        return desc.toString();
    }
}