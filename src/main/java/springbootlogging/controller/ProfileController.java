package springbootlogging.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/profile/")
public class ProfileController {

    @Autowired
    private Environment environment;

    @GetMapping("get-active-profile")
    public void getActiveProfile() {
        for (String activeProfile : environment.getActiveProfiles()) {
            System.out.println("Current active profile: " + activeProfile);
        }
    }

    @GetMapping("get-default-profile")
    public void getDefaultProfile() {
        for (String defaultProfile : environment.getDefaultProfiles()) {
            System.out.println("Default active profile: " + defaultProfile);
        }
    }
}
