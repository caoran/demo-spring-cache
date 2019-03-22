package heyikan;

import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
public class QueryController {
    @Autowired
    private QueryService queryService;

    @GetMapping("/query")
    public ResponseEntity<?> query(String keyWord) {
        String result = queryService.query(keyWord);
        return ResponseEntity.ok(result);
    }

    @Autowired
    @SuppressWarnings("all")
    private CacheManager cacheManager;

    @GetMapping("/caches")
    public ResponseEntity<?> getCache() {
        Map<String, ConcurrentMap> cacheMap = cacheManager.getCacheNames().stream()
                .collect(Collectors.toMap(Function.identity(), name -> {
                    Cache cache = (Cache) cacheManager.getCache(name).getNativeCache();
                    return cache.asMap();
                }));
        return ResponseEntity.ok(cacheMap);
    }
}
