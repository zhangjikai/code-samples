package com.zhangjikai.enums;

import org.junit.Test;

import java.util.EnumSet;
import java.util.Set;

import static com.zhangjikai.enums.EnumElement.*;

/**
 * @author Jikai Zhang
 * @date 2018-08-18
 */
public class EnumTest {
    public static void main(String[] args) {
        System.out.println(A.ordinal());
        System.out.println(B.ordinal());
        System.out.println(C.ordinal());
    }
    
    private void setEnums(Set<EnumElement> enums) {
        System.out.println(enums);
    }
    @Test
    public void testEnumSet() {
        setEnums(EnumSet.of(A, B));
    }
    
    @Test
    public void testEnumMap() {
        for (EnumElement enumElement : EnumElement.values()) {
            System.out.println(enumElement);
        }
    }
}
