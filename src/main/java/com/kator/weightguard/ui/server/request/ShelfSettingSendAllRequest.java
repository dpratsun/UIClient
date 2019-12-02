package com.kator.weightguard.ui.server.request;

import com.kator.weightguard.ui.entity.ShelfSetting;
import org.springframework.http.HttpMethod;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ShelfSettingSendAllRequest extends BasicRequest {
    public ShelfSettingSendAllRequest(String destinationIpAddr, List<ShelfSetting> shelfSettingsList) {
        super("");
        URL = "http://" + destinationIpAddr + ":80/" + makeSendSettingResponse(shelfSettingsList);
        httpMethod = HttpMethod.GET;
    }

    private String makeSendSettingResponse(List<ShelfSetting> shelfSettingsList){
        int listSize = shelfSettingsList.size();
        String str = "?";

        for(int i = 0; i < listSize; i++){
            str += "shwop" + (i + 1) + "=" + shelfSettingsList.get(i).getWeightOfOnePosition() + "&";
            str += "shwd" + (i + 1) + "=" + shelfSettingsList.get(i).getWeightDifferense() + "&";
            str += "shdea" + (i + 1) + "=" + shelfSettingsList.get(i).getEventAlarmDifference();
            if(i < listSize - 1) str += "&";
        }

        return str;
    }
}
