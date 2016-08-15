package com.example;

import com.hazelcast.config.NetworkConfig;
import com.hazelcast.logging.ILogger;
import com.hazelcast.spi.discovery.AbstractDiscoveryStrategy;
import com.hazelcast.spi.discovery.DiscoveryNode;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

public class HazelcastHerokuDiscoveryStrategy extends AbstractDiscoveryStrategy {

  private static final String HAZELCAST_SERVICE_PORT = "hazelcast-service-port";

  private final EndpointResolver endpointResolver;

  public HazelcastHerokuDiscoveryStrategy(ILogger logger, Map<String, Comparable> properties) {
    super(logger, properties);

    String serviceDns = properties.get("service-dns").toString();

    this.endpointResolver = new DnsEndpointResolver(logger, serviceDns);
  }

  public void start() {
    endpointResolver.start();
  }

  public Iterable<DiscoveryNode> discoverNodes() {
    return endpointResolver.resolve();
  }

  public void destroy() {
    endpointResolver.destroy();
  }

  static abstract class EndpointResolver {
    private final ILogger logger;

    EndpointResolver(ILogger logger) {
      this.logger = logger;
    }

    abstract List<DiscoveryNode> resolve();

    void start() {
    }

    void destroy() {
    }

    protected InetAddress mapAddress(String address) {
      if (address == null) {
        return null;
      }
      try {
        return InetAddress.getByName(address);
      } catch (UnknownHostException e) {
        logger.warning("Address '" + address + "' could not be resolved");
      }
      return null;
    }

    protected int getServicePort(Map<String, Object> properties) {
      int port = NetworkConfig.DEFAULT_PORT;
      if (properties != null) {
        String servicePort = (String) properties.get(HAZELCAST_SERVICE_PORT);
        if (servicePort != null) {
          port = Integer.parseInt(servicePort);
        }
      }
      return port;
    }
  }

}
