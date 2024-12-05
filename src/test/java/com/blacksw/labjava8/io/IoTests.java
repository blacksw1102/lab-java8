package com.blacksw.labjava8.io;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class IoTests {

    @ParameterizedTest
    @ValueSource(ints = {10_000, 100_000, 1_000_000, 3_000_000})
    void Io_입력_성능테스트(int value) throws IOException {
        ////////// Scanner //////////
        // given
        Scanner scan = new Scanner(new ByteArrayInputStream(getDummyBytes(value)));

        // when
        long start1 = System.currentTimeMillis();
        List<Integer> list1 = new ArrayList<>();
        for (int i = 0; i < value; i++) {
            list1.add(Integer.valueOf(scan.nextLine()));
        }
        long end1 = System.currentTimeMillis();
        long elapsedTime1 = end1 - start1;

        ////////// BufferReader //////////
        // given
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(getDummyBytes(value))));

        // when
        long start2 = System.currentTimeMillis();
        List<Integer> list2 = new ArrayList<>();
        for (int i = 0; i < value; i++) {
            list2.add(Integer.valueOf(br.readLine()));
        }
        long end2 = System.currentTimeMillis();
        long elapsedTime2 = end2 - start2;

        ////////// total //////////
        // then
        System.out.println("Scanner case : " + elapsedTime1);
        System.out.println("BufferReader case : " + elapsedTime2);
        assertEquals(value, list1.size());
        assertEquals(value, list2.size());
    }

    @ParameterizedTest
    @ValueSource(ints = {100_000})
    void Io_출력_성능테스트(int value) throws IOException {
        ////////// Scanner //////////

        // when
        long start1 = System.currentTimeMillis();
        for (int i = 0; i < value; i++) {
            System.out.println(i);
        }
        long end1 = System.currentTimeMillis();
        long elapsedTime1 = end1 - start1;

        ////////// BufferedWriter //////////
        // given
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        for (int i = 0; i < value; i++) {
            bw.append(String.valueOf(i)).append("\n");
        }

        // when
        long start2 = System.currentTimeMillis();
        bw.flush();
        long end2 = System.currentTimeMillis();
        long elapsedTime2 = end2 - start2;

        ////////// StringBuffer //////////
        // given
        StringBuffer stringbuffer = new StringBuffer();
        for (int i = 0; i < value; i++) {
            stringbuffer.append(i).append("\n");
        }

        // when
        long start3 = System.currentTimeMillis();
        System.out.println(stringbuffer);
        long end3 = System.currentTimeMillis();
        long elapsedTime3 = end3 - start3;

        ////////// StringBuilder //////////
        // given
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < value; i++) {
            stringBuilder.append(i).append("\n");
        }

        // when
        long start4 = System.currentTimeMillis();
        System.out.println(stringBuilder);
        long end4 = System.currentTimeMillis();
        long elapsedTime4 = end4 - start4;

        ////////// total //////////
        // then
        System.out.println("Scanner case : " + elapsedTime1);
        System.out.println("BufferedWriter case : " + elapsedTime2);
        System.out.println("StringBuffer case : " + elapsedTime3);
        System.out.println("StringBuilder case : " + elapsedTime4);
    }

    private byte[] getDummyBytes(int value) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < value; i++) {
            sb.append(i).append("\n");
        }
        return sb.toString().getBytes();
    }

}
