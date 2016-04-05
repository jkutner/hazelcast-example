package com.example;

import com.hazelcast.core.Hazelcast;

/**
 * TODO
 *
 * @author Viktor Gamov on 4/5/16.
 *         Twitter: @gamussa
 * @since 0.0.1
 */
public class HazelcastCluster {
    public static void main(String[] args) {
        Hazelcast.newHazelcastInstance();
    }
}
