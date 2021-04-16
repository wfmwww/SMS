package cn.kgc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "sms")
public class SmsConfig {
    private String accessKeyId;//主账号AccessKey的ID
    private String accessKeySecret;//和secret
    private String regionId;//服务地址
    private String domain;//服务域名
    private String version;//版本号
    private String action;//系统规定参数
    private String signName;//签名
    private String templateCode;//模板id
}
