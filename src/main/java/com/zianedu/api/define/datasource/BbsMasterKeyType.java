package com.zianedu.api.define.datasource;

public enum BbsMasterKeyType {

    LEARNING_REFERENCE_ROOM(10023, "학습자료실"),
    LEARNING_QNA(10025, "학습Q/A")
    ;

    int bbsMasterKey;

    String bbsMasterKeyStr;

    BbsMasterKeyType(int bbsMasterKey, String bbsMasterKeyStr) {
        this.bbsMasterKey = bbsMasterKey;
        this.bbsMasterKeyStr = bbsMasterKeyStr;
    }

    public int getBbsMasterKey() {
        return this.bbsMasterKey;
    }
}
