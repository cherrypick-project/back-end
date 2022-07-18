package com.cherrypick.backend.application;

import com.cherrypick.backend.domain.bookmark.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkFacade {

  private final BookmarkService bookmarkService;

  public void createBookmark(String loginId, Long lectureId) {
    bookmarkService.createBookmark(loginId, lectureId);
  }
}
