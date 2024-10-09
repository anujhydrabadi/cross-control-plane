package cloud.facets.cross_control_plane.vcs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class VCSCallbackService {
  private final ControlPlaneClient controlPlaneClient;

  private final ObjectMapper objectMapper;

  public String linkGithubAccount(String code, String state, long installationId) {
    CallbackStateDTO callbackStateDTO = extractCallbackStateDTO(state);
    if (callbackStateDTO == null) {
      return null;
    }

    controlPlaneClient.linkGithubAccount(code, installationId, callbackStateDTO);
    return String.format(
        "https://%s/capc/settings/overlay/organization-accounts",
        callbackStateDTO.controlPlaneUrl());
  }

  public String linkBitbucketAccount(String code, String state) {
    CallbackStateDTO callbackStateDTO = extractCallbackStateDTO(state);
    if (callbackStateDTO == null) {
      return null;
    }

    controlPlaneClient.linkBitbucketAccount(code, callbackStateDTO);
    return String.format(
        "https://%s/capc/settings/overlay/organization-accounts",
        callbackStateDTO.controlPlaneUrl());
  }

  public String linkGitlabAccount(String code, String state) {
    CallbackStateDTO callbackStateDTO = extractCallbackStateDTO(state);
    if (callbackStateDTO == null) {
      return null;
    }

    controlPlaneClient.linkGitlabAccount(code, callbackStateDTO);
    return String.format(
        "https://%s/capc/settings/overlay/organization-accounts",
        callbackStateDTO.controlPlaneUrl());
  }

  private CallbackStateDTO extractCallbackStateDTO(String state) {
    String stateJson = new String(Base64.getDecoder().decode(state));
    CallbackStateDTO callbackStateDTO;
    try {
      callbackStateDTO = objectMapper.readValue(stateJson, CallbackStateDTO.class);
    } catch (JsonProcessingException e) {
      log.error("Error converting state json into CallbackStateDTO", e);
      return null;
    }
    return callbackStateDTO;
  }
}
