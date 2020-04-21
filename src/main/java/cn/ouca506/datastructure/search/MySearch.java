package cn.ouca506.datastructure.search;

import cn.ouca506.datastructure.sort.MySort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author windmill666
 * @date 2020/4/12 15:40
 */

public class MySearch {

    /**
     * 线性查找/顺序查找
     * 从头依次比对，有就返回数组元素下标，没有返回-1
     * @param arr 一维数组
     * @param element 要查找的元素
     * @return 查找到的数组元素下标，如果没有就返回-1
     */
    public int sequenceSearch(int[] arr, int element) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == element) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 二分查找/折半查找
     * 单个结果
     * 使用递归
     * @param arr 一维数组
     * @param element 要查找的元素
     * @return 查找到的数组元素下标，如果没有就返回-1
     */
    public int binarySearch(int[] arr, int element) {
        int[] arrCopy = new int[arr.length];
        System.arraycopy(arr, 0, arrCopy, 0, arr.length);
        // 先从小到大排序
        MySort mySort = new MySort();
        mySort.quick(arrCopy, 0 , arrCopy.length - 1);
        return recursionBinarySearch(arrCopy, 0, arrCopy.length - 1, element);
    }

    private int recursionBinarySearch(int[] arr, int left, int right, int element) {
        if (left > right) {
            return -1;
        }
        int middle = (left + right) / 2;
        if (element == arr[middle]) {
            return middle;
        } else if (element < arr[middle]) {
            return recursionBinarySearch(arr, left, middle - 1, element);
        } else {
            return recursionBinarySearch(arr, middle + 1, right, element);
        }
    }

    /**
     * 二分查找/折半查找
     * 多个结果
     * 使用递归
     * @param arr 一维数组
     * @param element 要查找的元素
     * @return 查找到的数组元素下标，如果没有就返回null
     */
    public List<Integer> binarySearchMultiple(int[] arr, int element) {
        int[] arrCopy = new int[arr.length];
        System.arraycopy(arr, 0, arrCopy, 0, arr.length);
        // 先从小到大排序
        MySort mySort = new MySort();
        mySort.quick(arrCopy, 0 , arrCopy.length - 1);
        return recursionBinarySearchMultiple(arrCopy, 0, arrCopy.length - 1, element);
    }

    private List<Integer> recursionBinarySearchMultiple(int[] arr, int left, int right, int element) {
        if (left > right) {
            return null;
        }
        int middle = (left + right) / 2;
        if (element == arr[middle]) {
            int tempLeft = middle - 1;
            while (tempLeft >= 0 && arr[tempLeft] == arr[middle]) {
                tempLeft--;
            }
            int tempRight = middle + 1;
            while (tempRight < arr.length && arr[tempRight] == arr[middle]) {
                tempRight++;
            }
            List<Integer> res = new ArrayList<>();
            for (int i = tempLeft + 1; i < tempRight; i++) {
                res.add(i);
            }
            return res;
        } else if (element < arr[middle]) {
            return recursionBinarySearchMultiple(arr, left, middle - 1, element);
        } else {
            return recursionBinarySearchMultiple(arr, middle + 1, right, element);
        }
    }

    /**
     * 二分查找/折半查找
     * 多个结果
     * 非递归，使用循环
     * @param arr 一维数组
     * @param element 要查找的元素
     * @return 查找到的数组元素下标，如果没有就返回null
     */
    public List<Integer> binarySearchStandard(int[] arr, int element) {
        int[] arrCopy = new int[arr.length];
        System.arraycopy(arr, 0, arrCopy, 0, arr.length);
        // 先从小到大排序
        MySort mySort = new MySort();
        mySort.quick(arrCopy, 0 , arrCopy.length - 1);
        int left = 0;
        int right = arrCopy.length - 1;
        int middle;
        while (left <= right) {
            middle = (left + right) / 2;
            if (element < arrCopy[middle]) {
                right = middle - 1;
            } else if (element > arrCopy[middle]) {
                left = middle + 1;
            } else {
                int tempLeft = middle - 1;
                while (tempLeft >= 0 && arr[tempLeft] == arr[middle]) {
                    tempLeft--;
                }
                int tempRight = middle + 1;
                while (tempRight < arr.length && arr[tempRight] == arr[middle]) {
                    tempRight++;
                }
                List<Integer> res = new ArrayList<>();
                for (int i = tempLeft + 1; i < tempRight; i++) {
                    res.add(i);
                }
                return res;
            }
        }
        return null;
    }

    /**
     * 插值查找
     * 多个结果
     * 将middle的索引进行了优化
     * @param arr 一维数组
     * @param element 要查找的元素
     * @return 查找到的数组元素下标，如果没有就返回null
     */
    public List<Integer> insertSearchMultiple(int[] arr, int element) {
        int[] arrCopy = new int[arr.length];
        System.arraycopy(arr, 0, arrCopy, 0, arr.length);
        // 先从小到大排序
        MySort mySort = new MySort();
        mySort.quick(arrCopy, 0 , arrCopy.length - 1);
        return recursionInsertSearchMultiple(arr, 0, arr.length - 1, element);
    }

    private List<Integer> recursionInsertSearchMultiple(int[] arr, int left, int right, int element) {
        //由于element与middle的计算相关，所以要加限定，防止数组越界
        if (left > right || element < arr[0] || element > arr[arr.length - 1]) {
            return null;
        }
        int middle = left + (element - arr[left])*(right - left)/(arr[right] - arr[left]);
        if (element == arr[middle]) {
            int tempLeft = middle - 1;
            while (tempLeft >= 0 && arr[tempLeft] == arr[middle]) {
                tempLeft--;
            }
            int tempRight = middle + 1;
            while (tempRight < arr.length && arr[tempRight] == arr[middle]) {
                tempRight++;
            }
            List<Integer> res = new ArrayList<>();
            for (int i = tempLeft + 1; i < tempRight; i++) {
                res.add(i);
            }
            return res;
        } else if (element < arr[middle]) {
            return recursionInsertSearchMultiple(arr, left, middle - 1, element);
        } else {
            return recursionInsertSearchMultiple(arr, middle + 1, right, element);
        }
    }

    /**
     * 斐波那契查找
     * 利用斐波那契数列对数组进行划分
     * F(k)-1-1=F(k-1)-1+F(k-2)-1
     * 将数组扩容到F(k)-1的大小，middle会占用一个位置，这样数组会分成两块
     * middle=F(k-1)-1
     * 一块是[0,F(k-1)-1-1]共F(k-1)-1个元素
     * 另一块是[F(k-1)-1+1,F(k)-1-1]共F(k-2)-1个元素
     * @param arr 一维数组
     * @param element 要查找的元素
     * @return 查找到的数组元素下标，如果没有就返回null
     */
    public List<Integer> fibonacciSearchMultiple(int[] arr, int element) {
        //先创建斐波那契数组
        int[] fib = new int[20];
        fib[0] = 0;
        fib[1] = 1;
        for (int i = 2; i < fib.length; i++) {
            fib[i] = fib[i - 1] + fib[i - 2];
        }
        //找到最接近数组长度的斐波那契数
        int k = 0;
        while (arr.length > fib[k] - 1) {
            //这里为什么是n=F(k)-1，n为扩容后的数组长度
            //因为表中的数据是F(k)-1个，使用mid值进行分割又用掉一个，那么剩下F(k)-2个
            //正好分给两个子序列，每个子序列的个数分别是F(k-1)-1与F(k-2)-1个
            k++;
        }
        //对原数组进行扩容
        int[] temp = Arrays.copyOf(arr, fib[k] - 1);
        //将arr最后一个数填充到temp扩容后的数组中
        for (int i = arr.length; i < temp.length; i++) {
            temp[i] = arr[arr.length - 1];
        }
        //寻找element目标值所在索引
        int left = 0;
        int right = temp.length - 1;
        while (left <= right) {
            //F(k)=F(k-1)+F(k-2)
            int middle = left + fib[k - 1] - 1;
            if (element < temp[middle]) {
                right = middle - 1;
                //要找的值在左侧F(k-1)=F(k-2)+F(k-3)
                k--;
            } else if (element > temp[middle]) {
                left = middle + 1;
                //要找的值在右侧F(k)=F(k-1)+[F(k-3)+F(k-4)]
                k-=2;
            } else {
                if (middle < arr.length) {
                    //找到的是在arr边界内的数据
                    int tempLeft = middle - 1;
                    while (tempLeft >= 0 && temp[tempLeft] == temp[middle]) {
                        tempLeft--;
                    }
                    int tempRight = middle + 1;
                    while (tempRight < arr.length && temp[tempRight] == temp[middle]) {
                        tempRight++;
                    }
                    List<Integer> res = new ArrayList<>();
                    for (int i = tempLeft + 1; i < tempRight; i++) {
                        res.add(i);
                    }
                    return res;
                } else {
                    //找到的是填充后的数据
                    int tempLeft = arr.length - 1;
                    while (tempLeft >= 0 && temp[tempLeft] == temp[middle]) {
                        tempLeft--;
                    }
                    List<Integer> res = new ArrayList<>();
                    for (int i = tempLeft + 1; i < arr.length; i++) {
                        res.add(i);
                    }
                    return res;
                }
            }
        }
        return null;
    }
}
