package life.gao.community.controller;

import life.gao.community.dto.AccessTokenDTO;
import life.gao.community.dto.GitHubUser;
import life.gao.community.mapper.UserMapper;
import life.gao.community.model.User;
import life.gao.community.provider.GithubProvider;
import life.gao.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 授权
 */

@Controller
@Slf4j
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;


    @Autowired
    private UserService userService;

    @Value("${github.client.id}") //去配置文件读这个值，然后赋给它
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;


    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setState(state);
        GitHubUser gitHubUser = githubProvider.getUser(githubProvider.getAccessToken(accessTokenDTO));
        if (gitHubUser != null) {
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(gitHubUser.getName());
            user.setAccountId((String.valueOf(gitHubUser.getId())));
            user.setAvatarUrl(gitHubUser.getAvatarUrl());
            userService.createOrUpdate(user);

            response.addCookie(new Cookie("token", token));


            return "redirect:/";
        } else {
            //登录失败
            log.error("callback get github error");
            return "redirect:/";
        }


    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }

}