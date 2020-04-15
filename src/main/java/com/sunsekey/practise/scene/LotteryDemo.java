package com.sunsekey.practise.scene;

import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 注意，区间总长度在抽奖过程中一直在变，不中奖的区间也得跟着变才能保证一个固定的抽奖率
 */
public class LotteryDemo {
    public static final BigDecimal WIN_RATE = new BigDecimal(20);
    public static final BigDecimal FULL_RATE = new BigDecimal(100);
    public static final int COUNT = 1000;
    public static AtomicInteger winCount = new AtomicInteger();
    public static AtomicInteger loseCount = new AtomicInteger();
    public static CountDownLatch countDownLatch = new CountDownLatch(COUNT);

    static CopyOnWriteArrayList<Prize> prizeList = new CopyOnWriteArrayList<>();
    static ConcurrentHashMap<Integer,Prize> prizeMap;

    static{
        prizeList.add(Prize.builder().balance(150).qty(150).prizeNum(1).isRealPrize(true).build());
        prizeList.add(Prize.builder().balance(400).qty(400).prizeNum(2).isRealPrize(true).build());
        prizeList.add(Prize.builder().balance(250).qty(250).prizeNum(3).isRealPrize(true).build());
        prizeList.add(Prize.builder().prizeNum(4).isRealPrize(false).build());
        prizeMap = new ConcurrentHashMap<>(prizeList.stream().collect(Collectors.toMap(Prize::getPrizeNum, p -> p)));
    }

    @SneakyThrows
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        int count = COUNT;
        while (count > 0 ) {
            executorService.execute(LotteryDemo::play);
            count--;
        }
        countDownLatch.await();
        BigDecimal winCalCount = new BigDecimal(winCount.intValue());
        BigDecimal loseCalCount = new BigDecimal(loseCount.intValue());
        System.out.println("real win rate is " + winCalCount.divide(winCalCount.add(loseCalCount), 2, BigDecimal.ROUND_HALF_UP));
        executorService.shutdown();
    }

    public static synchronized void play() {
        BigDecimal noPriceRate = FULL_RATE.subtract(WIN_RATE);
        Map<Integer, Integer> balancePrizeMap = new HashMap<>();
        List<Integer> noPrizeNumList = new ArrayList<>();
        int prizeBalanceCount = 0;
        int prizeTotalCount = 0;
        for (Integer prizeNum : prizeMap.keySet()) {
            Prize prize = prizeMap.get(prizeNum);
            if (prize.isRealPrize()) {
                if (prize.getBalance() > 0) {
                    prizeBalanceCount = prizeBalanceCount + (prize.getBalance());
                    balancePrizeMap.put(prizeBalanceCount, prize.getPrizeNum());
                }
                prizeTotalCount = prizeTotalCount + (prize.getQty());
            } else {
                noPrizeNumList.add(prize.getPrizeNum());
            }
        }
        int getPriceNum;
        if (prizeBalanceCount > 0) {
//            int allChance = getAllChance(noPriceRate, prizeTotalCount);
            int allChance = getAllChanceByNewStrategy(noPriceRate, prizeBalanceCount);
            Random random = new Random();
            int randomChance = random.nextInt(allChance);
            if (randomChance > prizeBalanceCount) {
                Random innerRandom = new Random();
                getPriceNum = innerRandom.nextInt(noPrizeNumList.size());
            }else{
                int getMinKey = -1;
                for (int balanceRangeValue : balancePrizeMap.keySet()) {
                    if (randomChance <= balanceRangeValue) {
                        // 已经中奖
                        if (balanceRangeValue < getMinKey || getMinKey < 0) {
                            getMinKey = balanceRangeValue;
                        }
                    }
                }
                if (getMinKey < 0) {
                    getMinKey = 0;
                }
                getPriceNum = balancePrizeMap.get(getMinKey);
            }
            Prize winPrize = prizeMap.get(getPriceNum);
            if (winPrize != null && winPrize.isRealPrize) {
                System.out.println(Thread.currentThread().getName() + " win price " + winPrize.getPrizeNum());
                winPrize.setBalance(winPrize.getBalance() - 1);
            winCount.incrementAndGet();
            }else{
                System.out.println(Thread.currentThread().getName() + " win no prize..");
            loseCount.incrementAndGet();
            }
        }else{
            System.out.println("prize ran out..");
        }
        countDownLatch.countDown();
    }

    private static int getAllChance(BigDecimal noPriceRate, int prizeTotalCount) {
        int noPriceChance = noPriceRate
                .multiply(new BigDecimal(prizeTotalCount).divide(WIN_RATE, 2, BigDecimal.ROUND_HALF_UP))
                .setScale(0, BigDecimal.ROUND_HALF_UP)
                .intValue();
        return prizeTotalCount + noPriceChance;
    }


    private static int getAllChanceByNewStrategy(BigDecimal noPriceRate, int prizeBalanceCount) {
        int noPriceChance = noPriceRate
                .multiply(new BigDecimal(prizeBalanceCount).divide(WIN_RATE, 2, BigDecimal.ROUND_HALF_UP))
                .setScale(0, BigDecimal.ROUND_HALF_UP)
                .intValue();
        return prizeBalanceCount + noPriceChance;
    }

    @Data
    @Builder
    public static class Prize{
        private Integer prizeNum = 0;
        private Integer qty = 0;
        private Integer balance = 0;
        private boolean isRealPrize;
    }
}
