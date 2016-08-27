package com.example;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;

/**
 * @author Joe Kutner on 8/29/16.
 *         Twitter: @codefinger
 */
public class HelloWorldVertx {

  public static void main(String[] args) {
    ClusterManager mgr = new HazelcastClusterManager();

    VertxOptions options = new VertxOptions().setClusterManager(mgr);

    // Create an HTTP server which simply returns "Hello World!" to each request.
    Vertx.clusteredVertx(options, res -> {
      if (res.succeeded()) {
        res.result()
            .createHttpServer()
            .requestHandler(req -> req.response().end("Hello World!"))
            .listen(Integer.getInteger("http.port"), System.getProperty("http.address", "0.0.0.0"), handler -> {
              if (handler.succeeded()) {
                System.out.println("Vert.x Started!");
              } else {
                System.err.println("Failed to start Vert.x");
              }
            });
      } else {
        // failed!
        System.err.println("Failed to create Vert.x cluster!");
      }
    });
  }

}


