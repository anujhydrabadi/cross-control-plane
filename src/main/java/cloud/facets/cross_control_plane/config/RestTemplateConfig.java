package cloud.facets.cross_control_plane.config;

import java.time.Duration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
  @Bean
  public RestTemplate getRestTemplate() {
    return new RestTemplateBuilder()
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
        .setReadTimeout(Duration.ofSeconds(30))
        .setConnectTimeout(Duration.ofSeconds(30))
        .build();
  }
}
