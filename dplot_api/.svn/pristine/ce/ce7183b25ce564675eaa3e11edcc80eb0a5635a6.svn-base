package com.dplot.util;

import java.util.*;

/**
 * @author : ywm2004
 * @discription : 번호 생성 Util
 * @fileName : SerialUtil.java
 * @date : 2022-01-12
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022-01-12	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
public class NumberMakerUtil {

    // 시리얼 번호 길이
    private static final int SerialNoLength = 20;

    // 문자열 셋
    private static final char[] characterTable = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};

    /**
     * 쿠폰 번호 리스트 생성
     *
     * @param makeCount 만들 갯수
     * @return
     */
    public static List<String> executeSerialNoListGenerate(int makeCount) {
        Set<String> resultSet = new HashSet<>();
        Random random = new Random(System.currentTimeMillis());
        int tableLength = characterTable.length;
        StringBuilder buf = new StringBuilder();

        while (resultSet.size() != makeCount) {
            for (int i = 0; i < SerialNoLength; i++) {
                buf.append(characterTable[random.nextInt(tableLength)]);
            }
            resultSet.add(buf.toString());
            buf.delete(0, SerialNoLength);
        }

        return new ArrayList<>(resultSet);
    }

    /**
     * 쿠폰 번호 생성
     *
     * @return
     */
    public static String executeSerialNoGenerate() {
        Random random = new Random(System.currentTimeMillis());
        int tableLength = characterTable.length;
        StringBuilder buf = new StringBuilder();

        for (int i = 0; i < SerialNoLength; i++) {
            buf.append(characterTable[random.nextInt(tableLength)]);
        }

        return buf.toString();
    }
}
