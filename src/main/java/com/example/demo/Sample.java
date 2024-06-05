package com.example.demo;

import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import com.hazelcast.internal.util.Sha256Util;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StopWatch;

public class Sample {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        Collections.emptyMap().forEach((e, v) -> System.out.println(e + ":" + v));
    }

    private static void measurePerformance(String input) {
        double size = (input.getBytes().length * 1.0) / (1024);
        int iterations = 5; // Number of iterations for averaging
        StopWatch watch = new StopWatch();
        for (int i = 0; i < iterations; i++) {
            watch.start();
            DigestUtils.sha256Hex(input);
            watch.stop();// Average time per iteration
        }

        System.out.println("Average time for computing SHA-256: " + watch.getTotalTime(
                TimeUnit.MILLISECONDS) / iterations + " milliseconds of size " + size);
        System.out.println(watch.prettyPrint(TimeUnit.MILLISECONDS));
    }

    private static void measureHazelcastPerformance(String input) throws NoSuchAlgorithmException {
        double size = (input.getBytes().length * 1.0) / (1024);
        int iterations = 1_00_000; // Number of iterations for averaging
        StopWatch watch = new StopWatch();
        for (int i = 0; i < iterations; i++) {
            watch.start();
            Sha256Util.calculateSha256Hex(input.getBytes());
            // DigestUtils.sha256Hex(input);
            watch.stop();// Average time per iteration
        }

        System.out.println("Average time for computing SHA-256 (hazelcast): " + watch.getTotalTime(
                TimeUnit.MILLISECONDS) / iterations + " milliseconds of size " + size);
    }
}
