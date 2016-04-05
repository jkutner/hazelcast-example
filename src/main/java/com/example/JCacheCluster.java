package com.example;

import com.hazelcast.cache.impl.HazelcastServerCachingProvider;
import com.hazelcast.core.Hazelcast;

import javax.cache.configuration.MutableConfiguration;

/**
 * TODO
 *
 * @author Viktor Gamov on 4/5/16.
 *         Twitter: @gamussa
 * @since 0.0.1
 */
public class JCacheCluster {
    public static void main(String[] args) {
        final MutableConfiguration<String, String> configuration = new MutableConfiguration<>();
        configuration.setTypes(String.class, String.class);
        HazelcastServerCachingProvider.createCachingProvider(Hazelcast.newHazelcastInstance())
            .getCacheManager().createCache("city", configuration);

    }
}
