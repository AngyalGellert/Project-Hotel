package hu.progmasters.hotel.service;

import hu.progmasters.hotel.domain.OauthToken;
import hu.progmasters.hotel.dto.response.ResponseToken;
import hu.progmasters.hotel.repository.OauthTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;


@Slf4j
@Service
public class OauthTokenService {

    private static final String PAYPAL_URL = "https://api-m.sandbox.paypal.com/v1/oauth2/token";
    private static final String CLIENT_ID = "AXY6W2sSf9g9WH0Zygz4Lka_SaUCJO50n62y8D9LAYDU48EfPnYJ8-5Bs3oB8vYxfdF_2biF4xoBhTf4";
    private static final String CLIENT_SECRET_KEY = "ELInfzF024w_cEOoFNUj1RKf1NoimjhHpXJelOSAP3bSkucDPaM7bu4urEhjxpcAIZP0_vIcVdbkZy9J";
    private static final String GRANT_TYPE = "grant_type=client_credentials";
    private OauthTokenRepository oauthTokenRepository;
    private CloseableHttpClient httpClient = null;

    private ModelMapper modelMapper;

    @Autowired
    public OauthTokenService(OauthTokenRepository oauthTokenRepository) {
        this.oauthTokenRepository = oauthTokenRepository;
        this.modelMapper = new ModelMapper();
    }


    public ResponseToken requestToken() {
        ResponseToken responseToken = new ResponseToken();

        HttpPost request = new HttpPost(PAYPAL_URL);
        JSONObject responseObject = new JSONObject();
        String base64InputText = CLIENT_ID + ":" + CLIENT_SECRET_KEY;
        String base64EncodedText = Base64.getEncoder().encodeToString(base64InputText.getBytes());
        request.addHeader("Authorization", "Basic " + base64EncodedText);
        request.addHeader("Content-Type", "application/x-www-form-urlencoded");
        StringEntity body = null;
        CloseableHttpResponse response = null;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            body = new StringEntity(GRANT_TYPE);
            body.setContentType("application/x-www-form-urlencoded");
            if (body == null) {
                throw new RuntimeException("Baj van faszom");
            }
            request.setEntity(body);
            response = httpClient.execute(request);
            responseObject = responseEval(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        OauthToken oauthToken = new OauthToken();
        oauthToken.setAccess_token(responseObject.getString("access_token"));
        oauthToken.setToken_type(responseObject.getString("token_type"));
        oauthToken.setApp_id(responseObject.getString("app_id"));
        oauthToken.setExpires_in(responseObject.getInt("expires_in"));
        oauthToken.setCreatedTime(LocalDateTime.now());
        oauthToken.setExperiedTime(LocalDateTime.now().plusSeconds(oauthToken.getExpires_in()));
        OauthToken lastToken = oauthTokenRepository.findTopByOrderByIdDesc();
        if (lastToken != null) {
            if (!lastToken.getAccess_token().equals(oauthToken.getAccess_token())) {
                oauthTokenRepository.save(oauthToken);
            }
        }else{
            oauthTokenRepository.save(oauthToken);
        }
        responseToken = modelMapper.map(oauthToken, ResponseToken.class);
        responseToken.setExpires_in(oauthToken.getExperiedTime());

        return responseToken;
    }


    private JSONObject responseEval(CloseableHttpResponse response) {
        ResponseToken responseToken = null;
        JSONObject responseObject = new JSONObject();
        if (response != null) {
            int statusCode = response.getStatusLine().getStatusCode();
            String responseString;
            try {
                responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
                if (statusCode >= 400) {
                    throw new RuntimeException("Error while executing request, statusCode: " + statusCode + "message: " + responseString);
                }
                responseObject = new JSONObject(responseString);
                log.info("Token: {}", responseObject.getString("token_type"));
            } catch (IOException e) {
                log.error("", e);
            }
        }
        return responseObject;
    }

    public String getLastToken(){
        OauthToken lastToken = oauthTokenRepository.findTopByOrderByIdDesc();
        if(lastToken != null){
            if(lastToken.getExperiedTime().isAfter(LocalDateTime.now())){
            return lastToken.getAccess_token();
            }else{
                ResponseToken responseToken = requestToken();
                return responseToken.getAccess_token();
            }
        }else{
            ResponseToken responseToken = requestToken();
            return responseToken.getAccess_token();
        }
    }

}


