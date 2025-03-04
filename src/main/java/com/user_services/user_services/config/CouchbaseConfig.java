package com.user_services.user_services.config;

import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Collection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CouchbaseConfig {

  @Bean
  public Cluster couchbaseCluster() {
    return Cluster.connect("localhost", "username", "password");
  }

  @Bean
  public Bucket couchbaseBucket(Cluster cluster) {
    return cluster.bucket("user_services_bucket");
  }

  @Bean
  public Collection couchbaseCollection(Bucket bucket) {
    return bucket.defaultCollection();
  }
}