package com.namle.socialsharing.controllers;

import com.fasterxml.jackson.core.JsonParser;
import com.namle.socialsharing.dto.KeyParserDTO;
import com.namle.socialsharing.util.AESAlgorithmUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.google.gson.*;


import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@Slf4j
public class ReferralController {
    @Autowired
    private AESAlgorithmUtils aesAlgorithmUtils;

    @RequestMapping(value = "/referral/share", method = RequestMethod.GET)
    @CrossOrigin(origins = "*")
    public String handleOpenGraphRequest(
            @RequestHeader(value = "User-Agent") String userAgent,
            @RequestParam("key") String key, Model model
    ) throws UnsupportedEncodingException {
//        String[] data = aesAlgorithmUtils.decodeToGetUserNameAndShareKey(key);
//        String keyWithEncoded = URLEncoder.encode(key, StandardCharsets.UTF_8.toString());
        String userName = "";//data[0];
        String referralKey = "";//data[1];
        //  if (userAgent.contains("facebookexternalhit")) {//_zbot
        model.addAttribute("url", "zalopay.vn");
        model.addAttribute("title", "User-Agent" + userAgent);
        model.addAttribute("text", key);
        model.addAttribute("description","key  = "+ key);
        log.info(userName + " đã mời bạn tham gia chương trình" + referralKey);
        return "referral";
        //     } else {
        //      return "redirect:" + "https://github.com/lehoangnam97/react-native-anchor-carousel";
        //    }
    }
}
