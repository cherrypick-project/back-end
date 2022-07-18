package com.cherrypick.backend.infrastructure.bookmark;

import com.cherrypick.backend.domain.bookmark.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

}
