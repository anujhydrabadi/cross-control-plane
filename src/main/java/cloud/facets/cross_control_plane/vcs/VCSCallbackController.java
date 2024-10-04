package cloud.facets.cross_control_plane.vcs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/vcs/callback")
@RequiredArgsConstructor
@Slf4j
public class VCSCallbackController {
  private final ObjectMapper objectMapper;

  private final ControlPlaneClient controlPlaneClient;

  @GetMapping
  public RedirectView processGithubCallback(
      @RequestParam String code,
      @RequestParam("installation_id") long installationId,
      @RequestParam String state) {
    log.info("Code: {}, installationId: {}, state: {}", code, installationId, state);
    String stateJson = new String(Base64.getDecoder().decode(state));
    CallbackStateDTO callbackStateDTO;
    try {
      callbackStateDTO = objectMapper.readValue(stateJson, CallbackStateDTO.class);
    } catch (JsonProcessingException e) {
      log.error("Error converting state json into CallbackStateDTO", e);
      return new RedirectView();
    }

    controlPlaneClient.linkGithubAccount(code, installationId, callbackStateDTO);
    return new RedirectView(
        String.format(
            "https://%s/capc/settings/overlay/organization-accounts",
            callbackStateDTO.controlPlaneUrl()));
  }
}
