package com.dplot.common.service.util;

import com.dplot.common.SOMap;
import com.dplot.mapper.SerialNoMapper;
import com.dplot.util.NumberMakerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : ywm2004
 * @discription : 번호 생성 Service
 * @fileName : NumberMakerService.java
 * @date : 2022-01-12
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022-01-12	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
@Service
public class NumberMakerService {

    @Autowired
    SerialNoMapper serialNoMapper;

    public String getSerialNo() {
        String serialNo = NumberMakerUtil.executeSerialNoGenerate();

        // 중복 확인
        SOMap dupParam = new SOMap();
        List<String> serialNoList = new ArrayList<>();
        serialNoList.add(serialNo);
        dupParam.put("serialnolist", serialNoList);
        // 중복될 경우
        if (!serialNoMapper.selectAdminSerialNoList(dupParam).isEmpty()) {
            serialNo = getSerialNo();
        }

        return serialNo;
    }

    public List<String> getSerialNoList(int makeCount) {
        List<String> serialNoList = NumberMakerUtil.executeSerialNoListGenerate(makeCount);
        while (true) {
            // 중복 검사
            boolean isDup = isDupSerialNo(serialNoList);
            if (!isDup) {
                break;
            }
        }

        return serialNoList;
    }

    private boolean isDupSerialNo(List<String> serialNoList) {
        boolean isDup = false;

        // 중복된 시리얼 번호
        SOMap dupParams = new SOMap();
        dupParams.put("serialnolist", serialNoList);
        List<String> dupSerialNoList = serialNoMapper.selectAdminSerialNoList(dupParams).stream().map(obj -> obj.getStr("serialno")).collect(Collectors.toList());
        if (!dupSerialNoList.isEmpty()) {
            isDup = true;
            int size = dupSerialNoList.size();
            List<String> addSerialNoList = NumberMakerUtil.executeSerialNoListGenerate(size); // 중복된 수만큼 번호 새로 발급
            serialNoList.removeAll(dupSerialNoList); // 중복 제거
            serialNoList.addAll(addSerialNoList); // 새로운 번호 추가
        }

        return isDup;
    }

}
