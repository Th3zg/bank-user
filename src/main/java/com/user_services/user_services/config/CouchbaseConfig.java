package com.user_services.user_services.config;

import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Collection;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CouchbaseConfig {

  private Cluster cluster;
  @Value("${couchbase.connection-string}")
  private String connectionString;
  @Value("${couchbase.username}")
  private String username;
  @Value("${couchbase.password}")
  private String password;
  @Value("${couchbase.bucket.name}")
  private String bucketName;

  @Bean
  public Cluster couchbaseCluster() {
    return Cluster.connect("localhost", "admin", "password");
  }

  @Bean
  public Bucket couchbaseBucket(Cluster cluster) {
    Bucket bucket = cluster.bucket(bucketName);
    bucket.waitUntilReady(Duration.ofSeconds(10));
    return bucket;
  }

  @Bean
  public Collection couchbaseCollection(Bucket bucket) {
    return bucket.defaultCollection();
  }

  @PreDestroy
  public void closeCluster() {
    if (cluster != null) {
      cluster.disconnect();
      System.out.println("Couchbase cluster closed correctly");
    }
  }
}