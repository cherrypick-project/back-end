package com.cherrypick.backend.domain.category;

public interface CategoryStore {

  void save(CategoryCommand.RegisterCategory request);
}
