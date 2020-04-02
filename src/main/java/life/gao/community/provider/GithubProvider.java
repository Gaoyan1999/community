package life.gao.community.provider;


import com.alibaba.fastjson.JSON;
import life.gao.community.dto.AccessTokenDTO;
import life.gao.community.dto.GitHubUser;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;
import okhttp3.RequestBody;

import java.io.IOException;
import java.sql.SQLOutput;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string= response.body().string();

            String token = string.split("&")[0].split("=")[1];
            System.out.println(token);
            return token ;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public GitHubUser getUser(String accessToken){
        //This program downloads a URL and prints its contents as a string
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GitHubUser gitHubUser = JSON.parseObject(string,GitHubUser.class);//把String对象自动解析成了java的类对象
            return gitHubUser;
        }catch (IOException e){

        }
        return null;
    }


}
