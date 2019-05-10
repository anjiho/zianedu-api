package com.zianedu.api.config;

import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

public class ConfigHolder {

    private static ConfigHolder configHolder;

    @Value("#{config['logger.root']}")
    private String loggerRoot;
    @Value("#{config['logger.level']}")
    private String loggerLevel;
    @Value("#{config['logger.name']}")
    private String loggerName;
    @Value("#{config['board.file.upload.path']}")
    private String boardFileUploadPath;

    public static String getBoardFileUploadPath() {
        return configHolder.boardFileUploadPath;
    }

    @PostConstruct
    private ConfigHolder init() {
        configHolder = this;
        return this;
    }
}
