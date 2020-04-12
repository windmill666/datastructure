package cn.ouca506.datastructure.smart;

import java.io.*;

/**
 * 稀疏数组
 * @author windmill666
 * @date 2020/3/31 17:11
 */

public class SparseArray {

    public static int[][] zip(int[][] source) {
        int len = 0;
        for (int[] row : source) {
            for (int data : row) {
                if (data == 0) {
                    continue;
                }
                len++;
            }
        }
        int[][] res = new int[len + 1][3];
        res[0][0] = source.length;
        res[0][1] = source[0].length;
        res[0][2] = len;
        int count = 1;
        for (int i = 0; i < source.length; i++) {
            for (int j = 0; j < source[0].length; j++) {
                if (source[i][j] == 0) {
                    continue;
                }
                res[count][0] = i;
                res[count][1] = j;
                res[count][2] = source[i][j];
                count++;
            }
        }
        return res;
    }

    public static int[][] unzip(int[][] source) {
        int[][] res = new int[source[0][0]][source[0][1]];
        if (source[0][2] != source.length - 1) {
            return res;
        }
        for (int i = 1; i < source.length; i++) {
            int row = source[i][0];
            int col = source[i][1];
            int val = source[i][2];
            res[row][col] = val;
        }
        return res;
    }

    public static void zipToFile(int[][] source, String path) {
        int[][] sourceZip = zip(source);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(path));
            for (int[] row : sourceZip) {
                for (int data : row) {
                    writer.write(String.valueOf(data));
                    writer.write(" ");
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static int[][] unzipFromFile(String path) {
        int[][] res = null;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(path));
            String str;
            while ((str = reader.readLine()) !=null ) {
                String[] split = str.split(" ");
                if (res == null) {
                    int row = Integer.parseInt(split[0]);
                    int col = Integer.parseInt(split[1]);
                    res = new int[row][col];
                } else {
                    int row = Integer.parseInt(split[0]);
                    int col = Integer.parseInt(split[1]);
                    int val = Integer.parseInt(split[2]);
                    res[row][col] = val;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return res;
    }

    public static void print(int[][] source) {
        for (int[] row : source) {
            for (int data : row) {
                System.out.printf("%d\t",data);
            }
            System.out.println();
        }
    }

}
