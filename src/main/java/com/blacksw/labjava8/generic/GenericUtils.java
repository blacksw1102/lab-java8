package com.blacksw.labjava8.generic;

import java.util.List;

public class GenericUtils {

    public static <T> void swap(T[] arr, int idx1, int idx2) {
        T temp = arr[idx1];
        arr[idx1] = arr[idx2];
        arr[idx2] = temp;
    }

    public static String toString(List<? extends Number> list) {
        StringBuilder sb = new StringBuilder();
        for (Number value : list) {
            sb.append(value).append(" ");
        }
        return sb.toString().trim();
    }

    public static void printList(List<? extends Number> list) {
        System.out.println("Extends : " + list);
    }

    public static void printList2(List<? super Integer> list) {
        System.out.println("Super : " + list);
    }

}
