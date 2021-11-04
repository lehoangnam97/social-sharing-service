package com.namle.socialsharing.controllers;

import com.namle.socialsharing.dto.GenerateShareDataReferralRequestDTO;
import com.namle.socialsharing.util.AESAlgorithmUtils;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/mega/d11/api")
@Slf4j
public class GenerateCodeController {

  @Autowired
  private AESAlgorithmUtils aesAlgorithmUtils;

  @GetMapping(value = "get-info")
  public String getInfo(HttpServletResponse response,
      @CookieValue("zalo_id") String zaloId,
      @CookieValue("zalopay_id") String zalopayId,
      @CookieValue("zlp_token") String zlpToken) {
    Cookie cookieZaloId = new Cookie("zalo_id", zaloId);
    cookieZaloId.setHttpOnly(true);
    cookieZaloId.setDomain("herokuapp.com");
    cookieZaloId.setPath("/");

    Cookie cookieZaloPayId = new Cookie("zalopay_id", zalopayId);
    cookieZaloPayId.setHttpOnly(true);
    cookieZaloPayId.setDomain("herokuapp.com");
    cookieZaloPayId.setPath("/");

    Cookie cookieZlpToken = new Cookie("zlp_token", zlpToken);
    cookieZlpToken.setHttpOnly(true);
    cookieZlpToken.setDomain("herokuapp.com");
    cookieZlpToken.setPath("/");
    //add cookie to response
    System.out.println("getInfo" + zaloId + zalopayId + zlpToken);
    return "get information ok!";
  }

  @PostMapping(value = "/gen-key")
  public String generateKey(@RequestBody GenerateShareDataReferralRequestDTO requestDTO)
      throws UnsupportedEncodingException {
    String code = aesAlgorithmUtils
        .processGenEncryptedKey(requestDTO.getUserName(), requestDTO.getReferralKey());
    String encrypJson = aesAlgorithmUtils.encodeNam(
        "\"{\"userName\":\"Lê Hoàng Nam\",\"referralKey\":\"UmmpbhNB+SThBxfL9lcny258ojyd44no52tH6IUtxaPc\"}\"");
    System.out.println("------------------------");
    System.out.println(requestDTO.getUserName());
    System.out.println(requestDTO.getReferralKey());
    System.out.println("------------------------");
    System.out.println("");
    return "Normal: " + URLEncoder.encode(code, StandardCharsets.UTF_8.toString()) + " and " + code
        + "\n"
        + "JSON: " + URLEncoder.encode(encrypJson, StandardCharsets.UTF_8.toString()) + " and "
        + encrypJson;
  }
}
