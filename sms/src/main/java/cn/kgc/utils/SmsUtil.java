package cn.kgc.utils;

import cn.kgc.config.SmsConfig;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SmsUtil {
    Logger log = LoggerFactory.getLogger(SmsUtil.class);
    @Autowired
    private SmsConfig smsConfig;

    /**
     *单个短信验证码发送
     * @param phone//电话
     * @param code//验证码
     * @return
     */
    public String send(String phone, String code) {
        DefaultProfile profile = DefaultProfile.getProfile(smsConfig.getRegionId(), smsConfig.getAccessKeyId(), smsConfig.getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);
        String result = null;
        //构造请求的request
        CommonRequest request = new CommonRequest();

        request.setSysMethod(MethodType.POST);
        request.setSysDomain(smsConfig.getDomain());//阿里短信服务域名，固定值不变
        request.setSysVersion(smsConfig.getVersion());//版本号，固定值不变
        request.setSysAction(smsConfig.getAction());//系统规定参数，固定值不变
        request.putQueryParameter("RegionId", smsConfig.getRegionId());//固定参数，填写default默认值即可，同第一行参数
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", smsConfig.getSignName());
        request.putQueryParameter("TemplateCode", smsConfig.getTemplateCode());
        Map<String,String> map = new HashMap<>();
        map.put("code",code);
        request.putQueryParameter("TemplateParam", JSON.toJSONString(map));//短信模板变量对应的实际值，JSON格式，例如：{"code":"1111"}
        System.out.println(code);
        try {
            //发送请求，接收相应
            CommonResponse response = client.getCommonResponse(request);
            log.info("阿里云发送短信返回值：{}", response.getData());
            JSONObject jsonObject = JSON.parseObject(response.getData());
            result = jsonObject.get("Message").toString();
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
            log.error("阿里云发送短信失败，服务端错误：{}", e);
        } catch (ClientException e) {
            e.printStackTrace();
            log.error("阿里云发送短信失败，客户端错误：{}", e);
        }
        return result;
    }

}
