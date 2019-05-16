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

    public static String getFileDomainUrl() {
        return configHolder.fileDomainUrl;
    }

    @PostConstruct
    private ConfigHolder init() {
        configHolder = this;
        return this;
    }

}
