package hill.postcodecrimechecker;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EhCacheLogger implements CacheEventListener<Object, Object> {

    private static final Logger LOG = LoggerFactory.getLogger(EhCacheLogger.class);

    @Override
    public void onEvent(CacheEvent<?, ?> cacheEvent) {
        LOG.info("Element Modified Key: {}, New: {}, Old: {}", cacheEvent.getKey(), cacheEvent.getNewValue(), cacheEvent.getOldValue());
    }
}
