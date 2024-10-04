package cloud.facets.cross_control_plane.vcs;

public record OneTimePayload<X>(String webhookId, X payload) {}
