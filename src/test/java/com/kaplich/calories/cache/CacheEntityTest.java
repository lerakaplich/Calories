package com.kaplich.calories.cache;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class CacheEntityTest {

    private CacheEntity cacheEntity;

    @BeforeEach
    void setup() {
        cacheEntity = new CacheEntity();
    }

    @Test
    void testPutAndGet() {
        String key = "key";
        String value = "value";

        cacheEntity.put(key, value);
        Object retrievedValue = cacheEntity.get(key);

        Assertions.assertEquals(value, retrievedValue);
    }
    @Test
    void testRemoveOldestEntry_NonEmptyMap() {
        // Создаем непустую карту данных
        Map<String, Object> dataCacheMap = new HashMap<>();
        dataCacheMap.put("key1", "value1");
        dataCacheMap.put("key2", "value2");
        cacheEntity.setHashMap(dataCacheMap);

        // Удаляем самый старый элемент
        cacheEntity.removeOldestEntry();
    }
    @Test
    void testRemove() {
        String key = "key";
        String value = "value";

        cacheEntity.put(key, value);
        cacheEntity.remove(key);

        Object retrievedValue = cacheEntity.get(key);
        Assertions.assertNull(retrievedValue);
    }

    @Test
    void testClear() {
        String key1 = "key1";
        String value1 = "value1";
        String key2 = "key2";
        String value2 = "value2";

        cacheEntity.put(key1, value1);
        cacheEntity.put(key2, value2);
        cacheEntity.clear();

        Object retrievedValue1 = cacheEntity.get(key1);
        Object retrievedValue2 = cacheEntity.get(key2);

        Assertions.assertNull(retrievedValue1);
        Assertions.assertNull(retrievedValue2);
    }

    @Test
    void testSetHashMap() {
        String key = "key";
        String value = "value";

        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put(key, value);

        cacheEntity.setHashMap(hashMap);

        Object retrievedValue = cacheEntity.get(key);

        Assertions.assertEquals(value, retrievedValue);
    }
}
