package com.zianedu.api.define.datasource;

public enum ZianAcademyType {

    NUMBER_1(1, "지안공무원 1관"),
    NUMBER_2(2, "지안공무원 2관");

    int zianAcademyTypeKey;

    String zianAcademyTypeName;

    ZianAcademyType(int zianAcademyTypeKey, String zianAcademyTypeName) {
        this.zianAcademyTypeKey = zianAcademyTypeKey;
        this.zianAcademyTypeName = zianAcademyTypeName;
    }
}
