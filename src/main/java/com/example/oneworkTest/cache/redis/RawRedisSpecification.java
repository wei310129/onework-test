package com.example.oneworkTest.cache.redis;

import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;

public interface RawRedisSpecification<T> extends Specification<T>, Serializable {
}
