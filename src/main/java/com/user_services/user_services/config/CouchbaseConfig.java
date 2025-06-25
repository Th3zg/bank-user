package com.user_services.user_services.config;

import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Collection;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(CouchbaseProperties.class)
public class CouchbaseConfig {
  private final CouchbaseProperties couchbaseProperties;
  private Cluster cluster;

  @Bean
  public Bucket couchbaseBucket(Cluster cluster) {
    return cluster.bucket(couchbaseProperties.getBucketName());
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