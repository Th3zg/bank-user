package com.user_services.user_services.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.couchbase")
@Getter
@Setter
public class CouchbaseProperties {
  private String connectionString;
  private String username;
  private String password;
  private String bucketName;
}
