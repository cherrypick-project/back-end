package com.cherrypick.backend.domain.bookmark;

import com.cherrypick.backend.common.exception.BusinessException;
import com.cherrypick.backend.common.exception.ErrorCode;
import com.cherrypick.backend.domain.lecture.Lecture;
import com.cherrypick.backend.domain.lecture.LectureReader;
import com.cherrypick.backend.domain.user.User;
import com.cherrypick.backend.domain.user.UserReader;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

  private final LectureReader lectureReader;
  private final UserReader userReader;
  private final BookmarkStore bookmarkStore;

  @Override
  public void createBookmark(String loginId, Long lectureId) {
    Optional<Lecture> lectureOptional = lectureReader.findByLectureId(lectureId);
    Optional<User> userOptional = userReader.findByProviderId(loginId);

    Bookmark bookmark = new Bookmark(
      userOptional.orElseThrow(() -> new BusinessException(lectureId + " 사용자를 찾지 못했습니다.",
        ErrorCode.NOT_FOUND_USER)),
      lectureOptional.orElseThrow(() -> new BusinessException(loginId + " 강의를 찾지 못했습니다.",
        ErrorCode.NOT_FOUND_LECTURE))
    );
    bookmarkStore.store(bookmark);
  }
}
