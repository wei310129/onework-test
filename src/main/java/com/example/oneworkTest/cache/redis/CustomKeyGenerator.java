package com.example.oneworkTest.cache.redis;

import com.example.oneworkTest.station.request.RawRequest;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

@Component
public class CustomKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        // 假設 params[0] 是 @RequestBody 參數
        if (params.length > 0 && params[0] instanceof RawRequest) {
            // 根據 RawRequest @Data hashCode 生成 ID
            return params[0].hashCode();
        }
        return Arrays.hashCode(params);
    }
}
