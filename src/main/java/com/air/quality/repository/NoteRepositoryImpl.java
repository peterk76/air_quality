package com.air.quality.repository;

import com.air.quality.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NoteRepositoryImpl extends JpaRepository<Note, String> {

    List<Note> getNotesByCityId(final UUID cityId);

    Optional<Note> getNoteByUuid(final UUID uuid);

}
