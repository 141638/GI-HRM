package com.gi.hrm.utils.bean;

import com.gi.hrm.utils.ObjectUtils;
import com.google.gson.reflect.TypeToken;
import jakarta.annotation.Nullable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static com.gi.hrm.utils.ObjectUtils.parseJSON;
import static com.gi.hrm.utils.ObjectUtils.toJSON;

@Service
@RequiredArgsConstructor
public class CacheService {
    /** Spring cache manager */
    private final CacheManager cacheManager;

    /**
     * Register/update cache value
     *
     * @param cacheName cache name
     * @param key key
     * @param value value
     */
    public void cachePut(@NonNull String cacheName, @NonNull Object key, @NonNull Object value) {
        this.getCache(cacheName).put(key, value);
    }

    /**
     * Register/update cache value as JSON object
     *
     * @param cacheName cache name
     * @param key key
     * @param value value
     */
    public void putJSON(@NonNull String cacheName, @NonNull Object key, @NonNull Object value) {
        this.getCache(cacheName).put(key, toJSON(value));
    }

    /**
     * Retrieve cache value with the Object class as the default type
     *
     * @param cacheName cache name
     * @param key key
     * @return cache value by key
     */
    public Object cacheGet(@NonNull String cacheName, @NonNull Object key) {
        return this.cacheGet(cacheName, key, Object.class);
    }

    /**
     * Retrieve cache value with the defined class
     *
     * @param cacheName cache name
     * @param key key
     * @param clazz defined class for value
     * @param <S> generic type of value
     * @return cache value by key
     */
    public <S> S cacheGet(@NonNull String cacheName, @NonNull Object key, @NonNull Class<S> clazz) {
        return this.getCache(cacheName).get(key, clazz);
    }

    /**
     * Retrieve cache value with and cast the return type
     *
     * @param cacheName cache name
     * @param key key
     * @param <S> generic type of value
     * @return cache value by key
     */
    public <S> S cacheGetCast(@NonNull String cacheName, @NonNull Object key) {
        return ObjectUtils.cast(this.getCache(cacheName).get(key));
    }

    /**
     * Retrieve cache value as an array(required the cache to be firstly put as a JSON object)
     *
     * @param cacheName cache name
     * @param key key
     * @param clazz class to map
     * @param <S> generic type of value
     * @return cache value by key
     */
    @Nullable
    @SuppressWarnings("PMD.ReturnEmptyCollectionRatherThanNull")
    // note: null is used to differentiate an empty array and the result of cache retrieve failed
    public <S> List<S> cacheGetArray(String cacheName, String key, Class<S> clazz) {
        final String cacheValue = this.getCache(cacheName).get(key, String.class);
        if (!StringUtils.hasText(cacheValue)) {
            return null;
        }
        return parseJSON(cacheValue, TypeToken.getParameterized(List.class, clazz).getType());
    }

    /**
     * Remove a pair key-value from the cache
     *
     * @param cacheName cache name
     * @param key key of the pair to remove from cache
     * @return whether the remove action is successful or not
     */
    public boolean cacheRemove(String cacheName, String key) {
        return getCache(cacheName).evictIfPresent(key);
    }

    /**
     * Retrieve cache by name
     *
     * @param cacheName cache name
     * @return cache
     */
    public Cache getCache(@NonNull String cacheName) {
        return Optional.ofNullable(cacheManager.getCache(cacheName))
                .orElseThrow(() -> new IllegalArgumentException("Invalid cache name"));
    }
}
