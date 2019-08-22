package com.zianedu.api.config;

import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

/**
 * Created by jihoan on 2017. 11. 2..
 */
public class ConfigHolder {

    private static ConfigHolder configHolder;

    @Value("#{config['logger.root']}")
    private String loggerRoot;
    @Value("#{config['logger.level']}")
    private String loggerLevel;
    @Value("#{config['logger.name']}")
    private String loggerName;
    @Value("#{config['file.domain.url']}")
    private String fileDomainUrl;
    @Value("#{config['file.upload.path']}")
    private String fileUploadPath;

    @Value("#{config['mail.smtp.host']}")
    private String mailSmtpHost;
    @Value("#{config['mail.smtp.port']}")
    private int mailSmtpPort;
    @Value("#{config['mail.user.id']}")
    private String mailUserId;
    @Value("#{config['mail.user.pass']}")
    private String mailUserPass;
    @Value("#{config['mail.send.address']}")
    private String mailSendAddress;


    public static String getFileDomainUrl() {
        return configHolder.fileDomainUrl;
    }

    public static String getFileUploadPath() {
        return configHolder.fileUploadPath;
    }

    public static String getMailSmtpHost() {
        return configHolder.mailSmtpHost;
    }

    public static int getMailSmtpPort() {
        return configHolder.mailSmtpPort;
    }

    public static String getMailUserId() {
        return configHolder.mailUserId;
    }

    public static String getMailUserPass() {
        return configHolder.mailUserPass;
    }

    public static String getMailSendAddress() {
        return configHolder.mailSendAddress;
    }

    @PostConstruct
    private ConfigHolder init() {
        configHolder = this;
        return this;
    }

}
