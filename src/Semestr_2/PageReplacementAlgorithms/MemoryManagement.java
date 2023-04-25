package Semestr_2.PageReplacementAlgorithms;

import Semestr_2.PageReplacementAlgorithms.Algorithms.*;

import java.util.Random;

public class MemoryManagement {
    private final static int numberOfPages = 10000;
    private final static int rangeOfPages = 100;
    private final static int physicalMemorySize = 50;
    private final static int step = 5;

    public MemoryManagement(){
        measurement();
    }

    private void measurement(){
        Page[] pages = generateRandomNatualPages();
        Algorithm[] algorithms = {
                new FIFO(pages, physicalMemorySize),
                new OPT(pages, physicalMemorySize),
                new LRU(pages, physicalMemorySize),
                new ALRU(pages, physicalMemorySize),
                new RAND(pages, physicalMemorySize)
        };

        for (Algorithm algorithm : algorithms)
            algorithm.execute();
    }

    private Page[] generateRandomPages(){
        Page[] pages = new Page[numberOfPages];
        Random random = new Random();
        for (int i = 0; i < pages.length; i++)
            pages[i] = new Page(random.nextInt(rangeOfPages));
        return pages;
    }

    private Page[] generateRandomNatualPages() {
        Page[] pages = new Page[numberOfPages];

        Random random = new Random();
        for (int i = 0; i < numberOfPages; i++){
            int tmp = random.nextInt(rangeOfPages);
            for (int j = 0; j < step; j++){
                pages[i] = new Page(Math.min(rangeOfPages, tmp + (int) (random.nextExponential() * step)));
            }
        }

        return pages;
    }

    public static void main(String[] args) {new MemoryManagement();}
}
