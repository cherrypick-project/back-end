package com.cherrypick.backend.infrastructure.redis;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisRepository {

  private final RedisTemplate<String, String> redisTemplate;

  public void setValue(String key, String value, Long timeout, TimeUnit unit) {
    ValueOperations<String, String> values = redisTemplate.opsForValue();
    values.set(key, value, timeout, unit);
  }

  public Optional<String> getValue(String key) {
    ValueOperations<String, String> values = redisTemplate.opsForValue();
    String result = values.get(key);
    return Optional.ofNullable(result);
  }

  public void delValue(String key) {
    redisTemplate.delete(key);
  }
}
