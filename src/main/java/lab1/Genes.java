package lab1;

import java.util.Arrays;


public class Genes {
    public static final int GENES_SIZE = 32;
    public static final int GENES_AMOUNT = 8;
    private final int[] genesTab;

    public Genes(int[] genesTab) {
        this.genesTab = genesTab;
    }

    public int[] genesCounter(){
        return genesCounter(genesTab);
    }

    public static int[] genesCounter(int[] arr) { // counts single genes
        int[] counter = new int[GENES_AMOUNT];
        for (int i = 0; i < arr.length; i++) {
            counter[arr[i]]++;
        }
        return counter;
    }

    public static Genes ofMixed(int[] arr) { // add missing arguments
        int[] genesCounter = genesCounter(arr);
        for (int i = 0; i < genesCounter.length; i++) {
            if (genesCounter[i] == 0) {
                for (int j = 0; j < arr.length; j++) {
                    if (genesCounter[arr[j]] > 1) {
                        genesCounter[arr[j]]--;
                        arr[j] = i;
                        break;
                    }
                }
            }
        }
        Arrays.sort(arr);
        return new Genes(arr);
    }

    public static Genes ofRandom() { //creates random geneTab
        int[] newTab = new int[GENES_SIZE];
        for (int i = 0; i < GENES_SIZE; i++) {
            newTab[i] = (int) ((Math.random() * (GENES_AMOUNT - 1)));
        }
        return ofMixed(newTab);
    }

    @Override
    public int hashCode() {
        int[] c = genesCounter(this.genesTab);
        return Long.hashCode(((long) c[0] << 0) | ((long) c[1] << 5) | ((long) c[2] << 10) |
                ((long) c[3] << 15) | ((long) c[4] << 20) | ((long)c[5] << 25) | ((long)c[6] << 30) | ((long) c[7] << 35));
    }

    @Override
    public boolean equals(Object o) {
        Genes genes = (Genes) o;
        for (int i = 0; i < GENES_SIZE; i++) {
            if (this.genesTab[i] != genes.genesTab[i]) {
                return false;
            }
        }
        return true;
    }

    public int[] getGenesTab() {
        return genesTab;
    }
}
