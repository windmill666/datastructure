package cn.ouca506.datastructure.sort;

import java.util.Arrays;

/**
 * @author windmill666
 * @date 2020/4/10 8:00
 */

public class MySort {

    /**
     * 初级冒泡排序
     * 从小到大，如果需要从大到小只需把if中的大于号改成小于号
     * 两层for循环：相邻的两个元素相比较，最大的沉在下面
     * @param arr 一维数组
     */
    public void bubble(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i; j++) {
                if (arr[j] < arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
//            System.out.println("第"+i+"趟："+ Arrays.toString(arr));
        }
    }

    /**
     * 高级冒泡排序
     * 从小到大，如果需要从大到小只需把if中的大于号改成小于号
     * 记录每一趟是否发生过交换，如果未发生交换则说明数组已经有序
     * @param arr 一维数组
     */
    public void bubbleOpt(int[] arr) {
        boolean opt = false;//记录每一趟是否发生过交换
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    opt = true;
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
//            System.out.println("第"+i+"趟："+ Arrays.toString(arr));
            if (opt) {
                opt = false;
            } else {
                break;
            }
        }
    }

    /**
     * 快速排序
     * 以最后一个数或者第一个数为基准，进行排序
     * 如果需要随机一个数做基准，只需将随机到的数与最后一个数或者第一个数做交换
     * @param arr 一维数组
     * @param left 第一次递归时：left = 0
     * @param right 第一次递归时：right = arr.length - 1
     */
    public void quick(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int partitionIndex = partition(arr, left, right);
//        int partitionIndex = partition2(arr, left, right);
        quick(arr, left, partitionIndex - 1);
        quick(arr, partitionIndex + 1, right);
    }

    private int partition(int[] arr, int left, int right) {
        //选取第一个为坑位
        int basicIndex = left;
        int pivot = arr[basicIndex];

        //挖坑填数法
        while (left != right) {
            while (left < right && arr[right] >= pivot) {
                right--;
            }
            //出来之后arr[right] < pivot
            if (left < right) {
                arr[basicIndex] = arr[right];
                basicIndex = right;
                left++;
            }
            while (left < right && arr[left] <= pivot) {
                left++;
            }
            //出来之后arr[left] > pivot
            if (left < right) {
                arr[basicIndex] = arr[left];
                basicIndex = left;
                right--;
            }
        }
        //循环结束：left == right
        arr[basicIndex] = pivot;

        return basicIndex;
    }

    private int partition2(int[] arr, int left, int right) {
        //选取第一个为基准值
        int basicIndex = left;
        int pivot = arr[basicIndex];

        //双指针法
        while (left != right) {
            while (left < right && arr[right] >= pivot) {
                right--;
            }
            //出来之后arr[right] < pivot
            while (left < right && arr[left] <= pivot) {
                left++;
            }
            //出来之后arr[left] > pivot
            if (left < right) {
                int temp = arr[left];
                arr[left] = arr[right];
                arr[right] = temp;
            }
        }

        //循环结束：left == right，说明两个指针碰撞，原因如下
        //可能是right找到小于pivot，但是left没有找到大于pivot的，导致left与right重合
        //可能是right没有找到大于pivot的，导致right与left重合
        //重合后需要与基准值交换
        int temp = arr[basicIndex];
        arr[basicIndex] = arr[left];
        arr[left] = temp;

        return left;
    }

    /**
     * 直接选择排序
     * 从小到大，如果需要从大到小只需把if中的小于号改成大于号
     * 从arr[0]到arr[n-1]找出一个最小值与arr[0]交换
     * 从arr[1]到arr[n-1]找出一个最小值与arr[1]交换
     * 以此类推
     * @param arr 一维数组
     */
    public void select(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int min = arr[i];
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < min) {
                    min = arr[j];
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
        }
    }

    /**
     * 直接插入排序
     * 从小到大，如果需要从大到小只需把if中的小于号改成大于号
     * 把n个待排序的元素看成为一个有序表和一个无序表
     * 把无序表第一个元素插入到有序表中合适位置
     * 如果temp比arr[j]小就把数组往后挪，如果大或者等于arr[j]就把temp插入到arr[j+1]
     * @param arr 一维数组
     */
    public void insert(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i];
            int j = i - 1;
            for (; j >= 0; j--) {
               if (temp < arr[j]) {
                   arr[j + 1] = arr[j];
               }else {
                   break;
               }
            }
            if (j + 1 != i) {
                arr[j + 1] = temp;
            }
        }
    }

    /**
     * 希尔排序——交换法
     * 从小到大
     * - 先分组len/2=5，分成5组
     * - 再分组5/2=2，分成2组
     * - 再分组2/2，分成1组
     * @param arr 一维数组
     */
    public void shell(int[] arr) {
        for (int gap = arr.length/2; gap > 0; gap /= 2) {
            for (int i = gap; i < arr.length; i++) {
                for (int j = i - gap; j >= 0; j -= gap) {
                    if (arr[j] > arr[j + gap]) {
                        int temp = arr[j];
                        arr[j] = arr[j + gap];
                        arr[j + gap] = temp;
                    }
                }
            }
        }
    }

    /**
     * 希尔排序——插入法
     * 从小到大
     * - 先分组len/2=5，分成5组
     * - 再分组5/2=2，分成2组
     * - 再分组2/2，分成1组
     * @param arr 一维数组
     */
    public void shellInsert(int[] arr) {
        for (int gap = arr.length/2; gap > 0; gap /= 2) {
            for (int i = gap; i < arr.length; i++) {
                int temp = arr[i];
                int j = i - gap;
                for (; j >= 0; j -= gap) {
                    if (temp < arr[j]) {
                        arr[j + gap] = arr[j];
                    }else {
                        break;
                    }
                }
                if (j + gap != i) {
                    arr[j + gap] = temp;
                }
            }
        }
    }

    /**
     * 归并排序
     * 使用分而治之算法
     * 和快速排序速度差不多
     * @param arr 一维数组
     */
    public void merge(int[] arr) {
        mergeDivide(arr, 0, arr.length - 1, new int[arr.length]);
    }

    private void mergeDivide(int[] arr, int left, int right, int[] temp) {
        if (left == right) {
            return;
        }
        mergeDivide(arr, left, (left + right)/2, temp);
        mergeDivide(arr, (left + right)/2 + 1, right, temp);
        mergeConquer(arr, left, (left + right)/2, right, temp);
    }

    private void mergeConquer(int[] arr, int left, int middle, int right, int[] temp) {
        int tempIndex = left;
        int leftPin = left;
        int rightPin = middle + 1;

        while (leftPin <= middle && rightPin <= right) {
            if (arr[leftPin] < arr[rightPin]) {
                temp[tempIndex++] = arr[leftPin++];
            } else {
                temp[tempIndex++] = arr[rightPin++];
            }
        }

        while (leftPin <= middle) {
            temp[tempIndex++] = arr[leftPin++];
        }
        while (rightPin <= right) {
            temp[tempIndex++] = arr[rightPin++];
        }

        tempIndex = left;
        while (left <= right) {
            arr[left++] = temp[tempIndex++];
        }
    }

    /**
     * 基数排序
     * 桶排序的扩展
     * 经典的以空间换时间的算法——需要额外的存储空间：桶
     * @param arr 一维数组
     */
    public void radix(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int value : arr) {
            if (value > max) {
                max = value;
            }
        }
        int d = (max+"").length();

        int[][] bucket = new int[10][arr.length];
        int[] bucketIndex = new int[10];
        int base = 1;
        for (int i = 0; i < d; i++,base*=10) {
            Arrays.fill(bucketIndex, 0);
            // 将元素放入桶中
            for (int value : arr) {
                int digit = (value / base) % 10;
                int tempIndex = bucketIndex[digit];
                bucket[digit][tempIndex] = value;
                bucketIndex[digit]++;
            }
            // 将元素从桶中取出
            int index = 0;
            for (int j = 0; j < bucket.length; j++) {
                for (int k = 0; k < bucketIndex[j]; k++) {
                    arr[index++] = bucket[j][k];
                }
            }
        }
    }

    /**
     * 堆排序
     * 从小到大——大顶堆
     * 从大到小——小顶堆
     * @param arr 一维数组
     */
    public void heap(int[] arr) {
        //从小到大排序，初始建堆
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            adjustHeapOrder(arr, i, arr.length);
        }
        //堆顶下沉与最后一个节点交换
        //同时做相应调整
        for (int i = arr.length - 1; i > 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            adjustHeapOrder(arr, 0, i);
        }
//        //从大到小排序，初始建堆
//        for (int i = arr.length / 2 - 1; i >= 0; i--) {
//            adjustHeapDesc(arr, i, arr.length);
//        }
//        //堆顶下沉与最后一个节点交换
//        //同时做相应调整
//        for (int i = arr.length - 1; i > 0; i--) {
//            int temp = arr[0];
//            arr[0] = arr[i];
//            arr[i] = temp;
//            adjustHeapDesc(arr, 0, i);
//        }
    }

    /**
     * 生成(调整成)大顶堆
     * @param arr 一维数组
     * @param index 表示非叶子节点再数组中的索引
     * @param length 还有多少节点需要调整，逐渐减少
     */
    private void adjustHeapOrder(int[] arr, int index, int length) {
        int temp = arr[index];
        //循环的作用是让下面的不断往上冒
        for (int i = index * 2 + 1; i < length; i = i * 2 + 1) {
            //i此时是index节点下的左子节点，i+1是右子节点
            if (i + 1 < length && arr[i] < arr[i + 1]) {
                i++;
            }
            if (arr[i] > temp) {
                arr[index] = arr[i];
                index = i;//index的作用是接替下一轮的扫描，作为父节点
            } else {
                break;
            }
        }
        //将局部顶端元素放到下面
        arr[index] = temp;
    }

    /**
     * 生成(调整成)小顶堆
     * @param arr 一维数组
     * @param index 表示非叶子节点再数组中的索引
     * @param length 还有多少节点需要调整，逐渐减少
     */
    private void adjustHeapDesc(int[] arr, int index, int length) {
        int temp = arr[index];
        //循环的作用是让下面的不断往上冒，初始建堆的时候需要从下至上，从右至左调整才可以
        for (int i = index * 2 + 1; i < length; i = i * 2 + 1) {
            //i此时是index节点下的左子节点，i+1是右子节点
            if (i + 1 < length && arr[i] > arr[i + 1]) {
                i++;
            }
            if (arr[i] < temp) {
                arr[index] = arr[i];
                index = i;//index的作用是接替下一轮的扫描，作为父节点
            } else {
                break;
            }
        }
        //将局部顶端元素放到下面
        arr[index] = temp;
    }

}
