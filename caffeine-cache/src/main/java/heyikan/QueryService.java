package heyikan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = {"query-result", "demo"})
public class QueryService {
    private static Logger LOG = LoggerFactory.getLogger(QueryService.class);

    @Cacheable(unless = "#result.length() > 20")
    public String query(String keyWord) {
        LOG.info("do query by keyWord: {}", keyWord);
        String queryResult = doQuery(keyWord);
        return queryResult;
    }

    private String doQuery(String keyWord) {
        try {
            Thread.sleep(3000L);
            String result = "result of " + keyWord;
            return result;
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
