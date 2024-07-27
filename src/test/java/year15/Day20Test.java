//package year15;
//
//import java.io.IOException;
//import java.util.HashMap;
//
//import org.junit.jupiter.api.Test;
//
//import common.Day;
//
//public class Day20Test implements Day {
//
//    @Override
//    @Test
//    public void partOne() throws IOException {
//        var solution = getSolution(29000000);
//    }
//
////    return ElfDivisorFunctions.findLowestHouseNoForSumOfPresents(input, Integer.MAX_VALUE, 10);
//
//    @Override
//    @Test
//    public void partTwo() throws IOException {
//
//    }
//
//    private int[] getSolution(int input) {
//        int maxHouses = input / 9;
//        int[] presents = new int[maxHouses];
//
//        for (int elf = 1; elf <= maxHouses; elf++) {
//            for (int divisor = 1; divisor <= house; divisor++) {
//                if (house % divisor == 0) {
//                    presents[house - 1] += divisor * 10;
//                    System.out.println(presents);
//                }
//            }
//        }
//
//        return presents;
//    }
//
//}
