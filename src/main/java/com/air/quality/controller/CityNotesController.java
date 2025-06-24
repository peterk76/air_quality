package com.air.quality.controller;

import com.air.quality.dto.city_notes.NoteAddDto;
import com.air.quality.dto.city_notes.NoteDto;
import com.air.quality.dto.city_notes.NoteEditDto;
import com.air.quality.service.CityNotesService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("city")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class CityNotesController {

    final CityNotesService cityNotesService;

    @GetMapping(value = "{cityId}/notes")
    public List<NoteDto> getCityNotes(@PathVariable String cityId) {
        log.info("[Controller] CityNotesController.getCityNotes: cityId [{}]", cityId);
        return cityNotesService.getNotes(cityId);
    }

    @PostMapping(value = "note/edit")
    public void editNote(@RequestBody NoteEditDto editNote) {
        log.info("[Controller] CityNotesController.editNote: note [{}]", editNote.toString());
        cityNotesService.editNote(editNote);
    }

    @PostMapping(value = "note/add")
    public void addNote(@RequestBody NoteAddDto addNote) {
        log.info("[Controller] CityNotesController.addNote: note [{}]", addNote.toString());
        cityNotesService.addNote(addNote);
    }
}
