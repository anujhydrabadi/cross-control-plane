package cloud.facets.cross_control_plane.vcs;

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
  private final VCSCallbackService vcsCallbackService;

  @GetMapping("/github")
  public RedirectView processGithubCallback(
      @RequestParam String code,
      @RequestParam("installation_id") long installationId,
      @RequestParam String state) {
    log.info("Github Code: {}, installationId: {}, state: {}", code, installationId, state);
    return new RedirectView(vcsCallbackService.linkGithubAccount(code, state, installationId));
  }

  @GetMapping("/gitlab")
  public RedirectView processGitlabCallback(@RequestParam String code, @RequestParam String state) {
    log.info("Gitlab Code: {}, state: {}", code, state);
    return new RedirectView(vcsCallbackService.linkGitlabAccount(code, state));
  }

  @GetMapping("/bitbucket")
  public RedirectView processBitBucketCallback(
      @RequestParam String code, @RequestParam String state) {
    log.info("Bitbucket Code: {}, state: {}", code, state);
    return new RedirectView(vcsCallbackService.linkBitbucketAccount(code, state));
  }
}
