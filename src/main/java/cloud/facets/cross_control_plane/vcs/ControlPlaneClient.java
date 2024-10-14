package cloud.facets.cross_control_plane.vcs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class ControlPlaneClient {
  private final RestTemplate restTemplate;

  public void linkGithubAccount(
      String code, long installationId, CallbackStateDTO callbackStateDTO) {
    OneTimePayload<GithubAppInstallationPayload> payload =
        new OneTimePayload<>(
            callbackStateDTO.webhookId(),
            new GithubAppInstallationPayload(
                code,
                installationId,
                callbackStateDTO.accountName(),
                callbackStateDTO.enterpriseHostName()));
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<OneTimePayload<GithubAppInstallationPayload>> entity =
        new HttpEntity<>(payload, headers);

    try {
      String controlPlaneUrl = callbackStateDTO.controlPlaneUrl();
      if (controlPlaneUrl.endsWith("/")) {
        controlPlaneUrl = controlPlaneUrl.substring(0, controlPlaneUrl.length() - 1);
      }
      ResponseEntity<Void> response =
          restTemplate.exchange(
              String.format("https://%s/public/v1/link-github", controlPlaneUrl),
              HttpMethod.POST,
              entity,
              Void.class);
      if (response.getStatusCode().is2xxSuccessful()) {
        log.info("Successfully linked GitHub account");
      } else {
        log.error(
            "Failed to link GitHub account, received status code: {}", response.getStatusCode());
      }
    } catch (Exception e) {
      log.error("Error while linking GitHub account: {}", e.getMessage(), e);
    }
  }

  public void linkBitbucketAccount(String code, CallbackStateDTO callbackStateDTO) {
    OneTimePayload<BitbucketOauthAppPayload> payload =
        new OneTimePayload<>(
            callbackStateDTO.webhookId(),
            new BitbucketOauthAppPayload(code, callbackStateDTO.accountName()));
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<OneTimePayload<BitbucketOauthAppPayload>> entity =
        new HttpEntity<>(payload, headers);

    try {
      String controlPlaneUrl = callbackStateDTO.controlPlaneUrl();
      if (controlPlaneUrl.endsWith("/")) {
        controlPlaneUrl = controlPlaneUrl.substring(0, controlPlaneUrl.length() - 1);
      }
      ResponseEntity<Void> response =
          restTemplate.exchange(
              String.format("https://%s/public/v1/link-bitbucket", controlPlaneUrl),
              HttpMethod.POST,
              entity,
              Void.class);
      if (response.getStatusCode().is2xxSuccessful()) {
        log.info("Successfully linked Bitbucket account");
      } else {
        log.error(
            "Failed to link Bitbucket account, received status code: {}", response.getStatusCode());
      }
    } catch (Exception e) {
      log.error("Error while linking Bitbucket account: {}", e.getMessage(), e);
    }
  }

  public void linkGitlabAccount(String code, CallbackStateDTO callbackStateDTO) {
    OneTimePayload<GitlabOauthAppPayload> payload =
        new OneTimePayload<>(
            callbackStateDTO.webhookId(),
            new GitlabOauthAppPayload(
                code,
                "https://cross.723633d8e9cebc8c481a.console.facets.cloud/vcs/callback/gitlab",
                callbackStateDTO.accountName()));
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<OneTimePayload<GitlabOauthAppPayload>> entity = new HttpEntity<>(payload, headers);

    try {
      String controlPlaneUrl = callbackStateDTO.controlPlaneUrl();
      if (controlPlaneUrl.endsWith("/")) {
        controlPlaneUrl = controlPlaneUrl.substring(0, controlPlaneUrl.length() - 1);
      }
      ResponseEntity<Void> response =
          restTemplate.exchange(
              String.format("https://%s/public/v1/link-gitlab", controlPlaneUrl),
              HttpMethod.POST,
              entity,
              Void.class);
      if (response.getStatusCode().is2xxSuccessful()) {
        log.info("Successfully linked Gitlab account");
      } else {
        log.error(
            "Failed to link Gitlab account, received status code: {}", response.getStatusCode());
      }
    } catch (Exception e) {
      log.error("Error while linking Gitlab account: {}", e.getMessage(), e);
    }
  }
}
