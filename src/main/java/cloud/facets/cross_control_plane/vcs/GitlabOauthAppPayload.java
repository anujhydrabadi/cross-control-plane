package cloud.facets.cross_control_plane.vcs;

public record GitlabOauthAppPayload(String code, String redirectUrl, String accountName) {}
