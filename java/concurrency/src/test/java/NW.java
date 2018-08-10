/**
 * Created by Jikai Zhang on 2017/4/30.
 */
public class NW {
    static int M = 0;
    static int I = -1;
    static int G = -1;
    static int TOP = 1;
    static int LEFT = 2;
    static int DIAG = 3;
    static int edit[][];
    static int path[][];
    static int cal_edit(String a, String b) {
        int m = b.length();
        System.out.println(m);
        int n = a.length();
        int i, j;
        edit = new int[m + 1][n + 1];
        path = new int[m + 1][n + 1];
        for (i = 0; i <= n; i++) {
            edit[0][i] = G * i;
            edit[i][0] = G * i;
        }
        int x1, x2, x3, temp;
        for (j = 1; j <= n; j++) {
            //System.out.println(a.charAt(j - 1));
            for (i = 1; i <= m; i++) {
                x1 = edit[i - 1][j] + G;
                x2 = edit[i][j - 1] + G;
                if (b.charAt(i - 1) == a.charAt(j - 1)) {
                    temp = M;
                    //System.out.println(i + " " + j);
                } else {
                    temp = I;
                }
                x3 = edit[i - 1][j - 1] + temp;
                if (x1 >= x2 && x1 >= x3) {
                    edit[i][j] = x1;
                    path[i][j] = TOP;
                } else if (x2 >= x1 && x2 >= x3) {
                    path[i][j] = LEFT;
                    edit[i][j] = x2;
                } else {
                    path[i][j] = DIAG;
                    edit[i][j] = x3;
                }
            }
        }
        System.out.println(edit[m][n]);
        for (i = 0; i <= n; i++) {
            if (i > 0) {
                System.out.print(a.charAt(i - 1) + "\t\t ");
            } else {
                System.out.print(" \t\t\t ");
            }
        }
        System.out.println();
        for (i = 0; i <= n; i++) {
            if (i > 0) {
                System.out.print(b.charAt(i - 1) + "\t");
            } else {
                System.out.print(" \t");
            }
            for (j = 0; j <= m; j++) {
                System.out.printf("%-3d\t\t", edit[i][j]);
            }
            System.out.println();
        }
        System.out.println();
        /*for (i = 0; i <= n; i++) {
            if (i > 0) {
                System.out.print(a.charAt(i - 1) + "\t\t");
            } else {
                System.out.print(" \t");
            }
        }
        System.out.println();
        for (i = 1; i <= n; i++) {
            if (i > 0) {
                System.out.print(b.charAt(i - 1) + "\t");
            } else {
                System.out.print(" \t");
            }
            for (j = 1; j <= m; j++) {
                System.out.printf("%d ", path[i][j]);
            }
            System.out.println();
        }
        System.out.println();
        for (i = 0; i < n; i++) {
            for (j = n - 1; j >= 0; j--) {
                if (b.charAt(i) == a.charAt(j)) {
                    System.out.print(1);
                } else {
                    System.out.print(0);
                }
            }
            System.out.println();
        }*/
        return 0;
    }
    public static void main(String[] args) {
        // ref
        //String a = "CGTGATGTCCCAGACACCCTAGTTAAATGAGATCTTTTGGCGAGTCGTCATTATTGATTGAGTGCGAAAAATGCGGACCTTAAAAAAGGAACAATCAACC";
        // read
        //String b = "TATCGCTCATACGTCCAATAAAACGTGACAGTATGTGTCATACATTCGCGTGCATGGTCCAAACAAGGTAACATGCACTCCGAAGCTTACTTGGTGCCAG";
        String a = "ACGGTCGG";
        String b = "AGGCTCAG";
        cal_edit(b, a);
    }
}
