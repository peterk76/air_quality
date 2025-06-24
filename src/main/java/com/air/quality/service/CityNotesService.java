package com.air.quality.service;

import com.air.quality.dto.city_notes.NoteAddDto;
import com.air.quality.dto.city_notes.NoteDto;
import com.air.quality.dto.city_notes.NoteEditDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class CityNotesService {

    public List<NoteDto> getNotes(final String cityId) { // TODO
        return List.of(NoteDto.of("111", "1", "Note 1", "2023-10-01", "usr1", "qqq1"),
                       NoteDto.of("112", "2", "Note 2", "2023-10-02", "usr2", "qqq2"),
                       NoteDto.of("113", "3", "Note 3", "2023-10-03", "usr3", "qqq3"));
    }

    public void editNote(NoteEditDto editNote) {
        // TODO
    }

    public void addNote(NoteAddDto addNote) {
        // TODO
    }
}
