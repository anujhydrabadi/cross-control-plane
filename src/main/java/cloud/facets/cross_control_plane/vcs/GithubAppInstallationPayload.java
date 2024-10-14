package cloud.facets.cross_control_plane.vcs;

public record GithubAppInstallationPayload(
    String code, long installationId, String accountName, String enterpriseHostName) {}
