package com.cherrypick.backend.infrastructure.bookmark;

import com.cherrypick.backend.domain.bookmark.Bookmark;
import com.cherrypick.backend.domain.bookmark.BookmarkStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookmarkStoreImpl implements BookmarkStore {

  private final BookmarkRepository bookmarkRepository;


  @Override
  public void store(Bookmark bookmark) {
    bookmarkRepository.save(bookmark);
  }
}
