package com.zianedu.api.define.datasource;

import com.zianedu.api.dto.ConsultTimeDTO;

import java.util.ArrayList;
import java.util.List;

public enum ConsultReserveTimeType {

    AM_NINE_CLOCK(1, "9:00"),
    AM_TEN_HALF(2, "9:30"),
    AM_TEN_CLOCK(3, "10:00"),
    AM_ELEVEN_CLOCK(4, "11:00"),
    AM_ELEVEN_HALF(5, "11:30"),
    PM_TWELVE_CLOCK(6, "12:00"),
    PM_ONE_HALF(7, "12:30"),
    PM_THREE_HALF(8, "2:30"),
    PM_THREE_CLOCK(9, "3:00"),
    PM_FOUR_HALF(10, "3:30"),
    PM_FOUR_CLOCK(11, "4:00"),
    PM_FIVE_HALF(12, "4:30"),
    PM_FIVE_CLOCK(13, "5:00");

    int consultReserveTimeTypeKey;

    String consultReserveTimeTypeName;

    ConsultReserveTimeType(int consultReserveTimeTypeKey, String consultReserveTimeTypeName) {
        this.consultReserveTimeTypeKey = consultReserveTimeTypeKey;
        this.consultReserveTimeTypeName = consultReserveTimeTypeName;
    }

    public static String getConsultReserveTimeName(int consultReserveTimeTypeKey) {
        for (ConsultReserveTimeType timeType : ConsultReserveTimeType.values()) {
            if (consultReserveTimeTypeKey == timeType.consultReserveTimeTypeKey) {
                return timeType.consultReserveTimeTypeName;
            }
        }
        return null;
    }

    public static List<ConsultTimeDTO> getConsultTimeList() {
        List<ConsultTimeDTO>list = new ArrayList<>();
        for (ConsultReserveTimeType consultReserveTimeType : ConsultReserveTimeType.values()) {
            ConsultTimeDTO consultTimeDTO = new ConsultTimeDTO();
            consultTimeDTO.setReserveTimeKey(consultReserveTimeType.consultReserveTimeTypeKey);
            consultTimeDTO.setReserveTimeName(consultReserveTimeType.consultReserveTimeTypeName);
            consultTimeDTO.setReservedYn(false);

            list.add(consultTimeDTO);
        }
        return list;
    }
}
