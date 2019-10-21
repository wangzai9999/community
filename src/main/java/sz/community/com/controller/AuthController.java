package sz.community.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sz.community.com.dto.AccessTokenDTO;
import sz.community.com.dto.GithubUser;
import sz.community.com.provider.GithubProvider;

@Controller
public class AuthController {

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private GithubProvider githubProvider;
    @GetMapping("/callback")
    public String callBack(@RequestParam(name = "code") String code
    ,@RequestParam(name = "state") String state){
        AccessTokenDTO accessTokenDTO=new AccessTokenDTO();

        githubProvider.getAccessToken(accessTokenDTO);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(redirectUri);
        String accessToken=githubProvider.getAccessToken(accessTokenDTO);
        System.out.println(accessToken);
        GithubUser user=githubProvider.getUser(accessToken);
        System.out.println(user.getLogin());
        return "index";

    }

}

