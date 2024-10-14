package cloud.facets.cross_control_plane.vcs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CallbackStateDTO(
    String accountName, String controlPlaneUrl, String webhookId, String enterpriseHostName) {}
