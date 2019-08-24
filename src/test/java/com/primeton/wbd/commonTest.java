package com.primeton.wbd;

import org.junit.Test;

import java.util.Random;

public class commonTest
{
    @Test
    public void testRandom() {
        Random random = new Random();
        // 0-99
        String num = String.valueOf(random.nextInt(9000)+1000);

        System.out.println(num);
    }
}
