package cn.kgc.controller;

import cn.kgc.utils.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private SmsUtil smsUtil;

    @GetMapping("/send")
    public String sms() {
        String send = smsUtil.send("18255780520", "0000");
        return send;
    }
}
