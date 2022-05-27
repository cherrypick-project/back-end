package com.cherrypick.backend.domain.lecture;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class SeparatorConverter implements AttributeConverter<List<String>, String> {

  @Override
  public String convertToDatabaseColumn(List<String> attribute) {
    if (attribute == null) {
      return null;
    }
    return String.join(",", attribute);
  }

  @Override
  public List<String> convertToEntityAttribute(String dbData) {
    if (dbData == null) {
      return null;
    }
    String[] hashtags = dbData.split(",");
    return Arrays.stream(hashtags)
      .collect(Collectors.toList());
  }
}
