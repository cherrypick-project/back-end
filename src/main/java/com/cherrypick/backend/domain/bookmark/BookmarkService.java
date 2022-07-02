package com.cherrypick.backend.domain.bookmark;

public interface BookmarkService {

  void createBookmark(String loginId, Long lectureId);
}
