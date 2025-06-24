package com.air.quality.service;

import com.air.quality.dto.city_notes.NoteAddDto;
import com.air.quality.dto.city_notes.NoteDto;
import com.air.quality.dto.city_notes.NoteEditDto;
import com.air.quality.entity.Note;
import com.air.quality.repository.NoteRepositoryImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NoteService {

    final NoteRepositoryImpl noteRepository;

    public List<NoteDto> getNotes(final UUID cityId) {
        return noteRepository.getNotesByCityId(cityId).stream()
                .map(note -> NoteDto.of(
                        note.getUuid(),
                        note.getTopic(),
                        note.getDateAdd().toString(),
                        note.getDateMod().toString(),
                        note.getUserMod(),
                        note.getText()))
                .sorted(Comparator.comparing(NoteDto::getDateAdd).reversed())
                .toList();
    }

    @Transactional
    public boolean editNote(final NoteEditDto editNote) {
        return noteRepository.getNoteByUuid(editNote.getUuid())
                .map(note -> {
                    note.setText(editNote.getText());
                    noteRepository.save(note);
                    return true;
                })
                .orElseGet(() -> {
                    log.error("Note with UUID [{}] not found", editNote.getUuid());
                    return false;
                });
    }

    @Transactional
    public boolean addNote(final NoteAddDto addNote) {
        var time = LocalDateTime.now();
        var note = Note.of(
                UUID.randomUUID(),
                addNote.getCityId(),
                addNote.getTopic(),
                time,
                time,
                addNote.getUser(),
                addNote.getText());
        noteRepository.save(note);
        return true;
    }
}
