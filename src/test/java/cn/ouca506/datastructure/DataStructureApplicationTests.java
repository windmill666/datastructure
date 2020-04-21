package cn.ouca506.datastructure;

import cn.ouca506.datastructure.core.api.ZooLinkedList;
import cn.ouca506.datastructure.core.api.ZooQueue;
import cn.ouca506.datastructure.core.api.ZooStack;
import cn.ouca506.datastructure.core.linkedlist.ZooSingleCircleLinkedList;
import cn.ouca506.datastructure.core.linkedlist.ZooSingleLinkedList;
import cn.ouca506.datastructure.core.queue.ZooArrayQueue;
import cn.ouca506.datastructure.core.stack.ZooStringArrayStack;
import cn.ouca506.datastructure.core.tree.*;
import cn.ouca506.datastructure.search.MySearch;
import cn.ouca506.datastructure.smart.EightQueenProblem;
import cn.ouca506.datastructure.smart.ReversePolishExpression;
import cn.ouca506.datastructure.smart.SparseArray;
import cn.ouca506.datastructure.sort.MySort;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
class DataStructureApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testSparseArray() {
        int[][] chess = new int[11][11];
        chess[1][2] = 1;
        chess[2][3] = 2;
        chess[8][3] = 1;
        System.out.println("原始数组");
        SparseArray.print(chess);
        int[][] chessZip = SparseArray.zip(chess);
        System.out.println("稀疏数组");
        SparseArray.print(chessZip);
        int[][] chessUnzip = SparseArray.unzip(chessZip);
        System.out.println("恢复数组");
        SparseArray.print(chessUnzip);
    }

    @Test
    void testSparseArrayIO() {
        int[][] chess = new int[11][11];
        chess[1][2] = 1;
        chess[2][3] = 2;
        chess[8][3] = 1;
        System.out.println("原始数组");
        SparseArray.print(chess);
        SparseArray.zipToFile(chess, "map.data");
        int[][] unzipChess = SparseArray.unzipFromFile("map.data");
        System.out.println("恢复数组");
        SparseArray.print(unzipChess);
    }

    @Test
    void testZooArrayQueue() {
        ZooQueue zooQueue = new ZooArrayQueue(3);
//        ZooQueue zooQueue = new ZooLinkedQueue(3);
        System.out.println("zooQueue.offer(1) = " + zooQueue.offer(1));
        System.out.println("zooQueue.offer(2) = " + zooQueue.offer(2));
        System.out.println("zooQueue.offer(3) = " + zooQueue.offer(3));
        zooQueue.showQueue();
        System.out.println("zooQueue.peek() = " + zooQueue.peek());

        System.out.println("zooQueue.offer(4) = " + zooQueue.offer(4));
        System.out.println("zooQueue.offer(5) = " + zooQueue.offer(5));
        zooQueue.showQueue();

        System.out.println("zooQueue.poll() = " + zooQueue.poll());
        zooQueue.showQueue();
        System.out.println("zooQueue.poll() = " + zooQueue.poll());
        zooQueue.showQueue();

        System.out.println("zooQueue.offer(4) = " + zooQueue.offer(4));
        zooQueue.showQueue();
        System.out.println("zooQueue.offer(5) = " + zooQueue.offer(5));
        zooQueue.showQueue();
        System.out.println("zooQueue.offer(6) = " + zooQueue.offer(6));
        zooQueue.showQueue();

        System.out.println("zooQueue.poll() = " + zooQueue.poll());
        zooQueue.showQueue();
        System.out.println("zooQueue.poll() = " + zooQueue.poll());
        zooQueue.showQueue();
        System.out.println("zooQueue.poll() = " + zooQueue.poll());
        zooQueue.showQueue();
        System.out.println("zooQueue.poll() = " + zooQueue.poll());
    }

    @Test
    void testZooLinkedList() {
        ZooLinkedList<String> linkedList = new ZooSingleLinkedList<>();
//        ZooLinkedList<String> linkedList = new ZooRoundLinkedList<>();
        linkedList.reverse();
        linkedList.update("空", 0);
        linkedList.addFirst("刘备");
        linkedList.reverse();
        linkedList.addFirst("关羽");
        linkedList.addFirst("张飞");
        System.out.println(linkedList.getSize());
        linkedList.showLinkedList();

        linkedList.add("赵云");
        linkedList.addLast("黄忠");
        linkedList.add("马超", 1);
        linkedList.add("赵云");
        linkedList.add("诸葛亮");
        System.out.println(linkedList.getSize());
        linkedList.showLinkedList();

        linkedList.removeFirst();
        linkedList.remove(1);
        linkedList.remove();
        linkedList.removeLast();
        System.out.println(linkedList.getSize());
        linkedList.showLinkedList();

        System.out.println(linkedList.contains("赵云"));
        linkedList.add("赵云");
        linkedList.remove("赵云");
        System.out.println(linkedList.getSize());
        linkedList.showLinkedList();

        linkedList.update("吕布", 0);
        linkedList.update("常山赵子龙", linkedList.getSize() - 1);
        System.out.println(linkedList.getSize());
        linkedList.showLinkedList();

        linkedList.reverse();
        System.out.println(linkedList.getSize());
        linkedList.showLinkedList();
    }

    @Test
    void testZooLinkedListMerge() {
        ZooSingleLinkedList<Integer> linkedList1 = new ZooSingleLinkedList<>();
        linkedList1.add(1);
        linkedList1.add(2);
        linkedList1.add(4);
        linkedList1.add(5);
        linkedList1.showLinkedList();
        ZooSingleLinkedList<Integer> linkedList2 = new ZooSingleLinkedList<>();
        linkedList2.add(1);
        linkedList2.add(3);
        linkedList2.add(3);
        linkedList2.add(6);
        linkedList2.add(7);
        linkedList2.showLinkedList();
        ZooSingleLinkedList<Integer> linkedListMerge = new ZooSingleLinkedList<>();
        ZooSingleLinkedList<Integer>.Node cur1 = linkedList1.getHead();
        ZooSingleLinkedList<Integer>.Node cur2 = linkedList2.getHead();
        while (cur1 != null && cur2 != null) {
            if (cur1.getValue() <= cur2.getValue()) {
                linkedListMerge.add(cur1.getValue());
                cur1 = cur1.getNext();
                continue;
            }
            if (cur2.getValue() < cur1.getValue()) {
                linkedListMerge.add(cur2.getValue());
                cur2 = cur2.getNext();
            }
        }
        while (cur1 != null) {
            linkedListMerge.add(cur1.getValue());
            cur1 = cur1.getNext();
        }
        while (cur2 != null) {
            linkedListMerge.add(cur2.getValue());
            cur2 = cur2.getNext();
        }
        linkedListMerge.showLinkedList();
    }

    @Test
    void testZooLinkedReverseOnly() {
        ZooSingleLinkedList<String> linkedList = new ZooSingleLinkedList<>();
        linkedList.add("刘备");
        linkedList.add("关羽");
        linkedList.add("张飞");
        linkedList.showLinkedList();
        ZooStack stack = new ZooStringArrayStack(linkedList.getSize());
//        ZooStack stack = new ZooStringLinkedStack(linkedList.getSize());
        ZooSingleLinkedList<String>.Node cur = linkedList.getHead();
        while (cur != null) {
            stack.push(cur.getValue());
            cur = cur.getNext();
        }
        stack.push("测试");
        for (int i = 0; i < linkedList.getSize(); i++) {
            System.out.print(stack.pop() + "\t");
        }
        System.out.println();
        stack.pop();
    }

    @Test
    void testJosephusProblem() {
        // 2 4 1 5 3
        // 对于重复
//        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        List<String> list = new ArrayList<>(Arrays.asList("刘备","关羽","张飞","黄忠","马超"));
        int size = list.size();
        int out = 0;
        int point = 1;
        int index = 0;
        int sizeTemp = size;
        List<String> temp = new ArrayList<>();
        while (out < size) {
            if (point%2 == 0) {
                System.out.print(list.get(index) + " ");
                out++;
                temp.add(list.get(index));
            }
            index++;
            point++;
            if (index == sizeTemp) {
                index = 0;
                for (String value : temp) {
                    list.remove(value);
                }
                sizeTemp = list.size();
                temp.clear();
            }
        }

    }

    @Test
    void testZooCircleLinkedList() {
        ZooSingleCircleLinkedList<Integer> linkedList = new ZooSingleCircleLinkedList<>();
//        ZooRoundCircleLinkedList<Integer> linkedList = new ZooRoundCircleLinkedList<>();
        linkedList.add(1);
        linkedList.add(2);
        linkedList.add(3);
        linkedList.add(4);
        linkedList.add(5);
        linkedList.showCircleLinkedList();
//        linkedList.showCircleLinkedListRe();
        int temp = 1;
        ZooSingleCircleLinkedList<Integer>.Node cur = linkedList.getHead();
//        ZooRoundCircleLinkedList<Integer>.Node cur = linkedList.getHead();
        while (linkedList.getSize() > 0) {
            if (temp%2 == 0) {
                int out = cur.getValue();
                System.out.print(out + "\t");
                cur = cur.getNext();
                linkedList.remove(out);
                temp++;
            } else {
                cur = cur.getNext();
                temp++;
            }
        }
        System.out.println();
    }

    @Test
    void testJS() {
        String script = "7+2-4*5+45+9/3+2+4*0-7";
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        try {
            Object eval = engine.eval(script);
            System.out.println(eval);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        System.out.println("自制计算器");
        List<String> num = new ArrayList<>();
        List<String> symbol = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(script);
        while (matcher.find()) {
            num.add(matcher.group());
        }
        Pattern patternSymbol = Pattern.compile("[+\\-*/]");
        Matcher matcherSymbol = patternSymbol.matcher(script);
        while (matcherSymbol.find()) {
            symbol.add(matcherSymbol.group());
        }
        ZooStringArrayStack numStack = new ZooStringArrayStack(num.size());
        ZooStack numSymbol = new ZooStringArrayStack(symbol.size());
        // 扫描
        for (int i = 0; i < symbol.size(); i++) {
            numStack.push(num.get(i));
            if (numSymbol.isEmpty()) {
                numSymbol.push(symbol.get(i));
                continue;
            }
            while (!numSymbol.isEmpty() && ZooStringArrayStack.symbolPropPriority(symbol.get(i)) <= ZooStringArrayStack.symbolPropPriority(numSymbol.peek())) {
                int a = Integer.parseInt(numStack.pop());
                int b = Integer.parseInt(numStack.pop());
                String c = numSymbol.pop();
                switch (c) {
                    case "+": {
                        numStack.push(String.valueOf(b+a));
                        break;
                    }
                    case "-": {
                        numStack.push(String.valueOf(b-a));
                        break;
                    }
                    case "*": {
                        numStack.push(String.valueOf(b*a));
                        break;
                    }
                    case "/": {
                        numStack.push(String.valueOf(b/a));
                        break;
                    }
                }
            }
            numSymbol.push(symbol.get(i));
        }
        numStack.push(num.get(num.size()-1));

        int a = Integer.parseInt(numStack.pop());
        int b = Integer.parseInt(numStack.pop());
        String c = numSymbol.pop();
        switch (c) {
            case "+": {
                numStack.push(String.valueOf(b+a));
                break;
            }
            case "-": {
                numStack.push(String.valueOf(b-a));
                break;
            }
        }
        System.out.println(numStack.peek());
    }

    @Test
    void testPolishExp() {
        String middleExp = "1+((2+3)*4)-5";

        int calculate = ReversePolishExpression.calculate(middleExp);
        System.out.println("中缀表达式：" + middleExp + " :" + calculate);

        String suffix = ReversePolishExpression.middleConvertSuffix(middleExp);
        int calculateRPE = ReversePolishExpression.calculateRPE(suffix);
        System.out.println("后缀表达式：" + suffix + " :" + calculateRPE);

        String prefix = ReversePolishExpression.middleConvertPrefix(middleExp);
        int calculatePE = ReversePolishExpression.calculatePE(prefix);
        System.out.println("前缀表达式：" +prefix + " :" + calculatePE);
    }

    @Test
    void testEightQueenProblem() {
        EightQueenProblem problem = new EightQueenProblem();
        problem.run();
    }

    @Test
    void testSort() {
        MySort sort = new MySort();
        int size = 80000;
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = (int) (Math.random()*size);
        }

        int[] bubble = new int[size];
        System.arraycopy(arr, 0, bubble, 0, arr.length);
        int[] bubbleOpt = new int[size];
        System.arraycopy(arr, 0, bubbleOpt, 0, arr.length);
        int[] select = new int[size];
        System.arraycopy(arr, 0, select, 0, arr.length);
        int[] insert = new int[size];
        System.arraycopy(arr, 0, insert, 0, arr.length);
        int[] shell = new int[size];
        System.arraycopy(arr, 0, shell, 0, arr.length);
        int[] shellInsert = new int[size];
        System.arraycopy(arr, 0, shellInsert, 0, arr.length);
        int[] quick = new int[size];
        System.arraycopy(arr, 0, quick, 0, arr.length);
        int[] merge = new int[size];
        System.arraycopy(arr, 0, merge, 0, arr.length);
        int[] radix = new int[size];
        System.arraycopy(arr, 0, radix, 0, arr.length);

        long start = System.currentTimeMillis();
        sort.bubble(bubble);
        long bubbleEnd = System.currentTimeMillis();
        sort.bubbleOpt(bubbleOpt);
        long bubbleOptEnd = System.currentTimeMillis();
        sort.select(select);
        long selectEnd = System.currentTimeMillis();
        sort.insert(insert);
        long insertEnd = System.currentTimeMillis();
        sort.shell(shell);
        long shellEnd = System.currentTimeMillis();
        sort.shellInsert(shellInsert);
        long shellInsertEnd = System.currentTimeMillis();
        sort.quick(quick, 0 , quick.length - 1);
        long quickEnd = System.currentTimeMillis();
        sort.merge(merge);
        long mergeEnd = System.currentTimeMillis();
        sort.radix(radix);
        long radixEnd = System.currentTimeMillis();

        System.out.println("初级冒泡排序："+(bubbleEnd - start));
        System.out.println("高级冒泡排序："+(bubbleOptEnd - bubbleEnd));
        System.out.println("直接选择排序："+(selectEnd - bubbleOptEnd));
        System.out.println("直接插入排序："+(insertEnd - selectEnd));
        System.out.println("希尔排序-交换法："+(shellEnd - insertEnd));
        System.out.println("希尔排序-插入法："+(shellInsertEnd - shellEnd));
        System.out.println("快速排序："+(quickEnd - shellInsertEnd));
        System.out.println("归并排序："+(mergeEnd - quickEnd));
        System.out.println("基数排序："+(radixEnd - mergeEnd));

//        int[] shellArr = new int[]{8,9,1,7,2,3,5,4,6,0};
//        sort.shell(shellArr);
//        sort.shellInsert(shellArr);
//        int[] quickArr = new int[]{9,8};
//        int[] quickArr = new int[]{8,9,1,7,2,3,5,4,6,0};
//        int[] quickArr = new int[]{4,7,6,5,3,2,8,1};
//        int[] quickArr = new int[]{12,45,23,67,7,1,5,21};
//        sort.quick(quickArr, 0, quickArr.length - 1);
//        System.out.println(Arrays.toString(quickArr));

    }

    @Test
    void testSearch() {
        MySearch search = new MySearch();
        int[] arr = new int[]{8,4,67,23,5,7,32};
        System.out.println(search.sequenceSearch(arr, 5));
        System.out.println(search.binarySearch(arr, 5));//注意是有序数组
        int[] arrList = new int[]{1,1,1,0,1,1};
        System.out.println(search.binarySearchMultiple(arrList, 1));//注意是有序数组
        int[] arrInsertList = new int[]{1,2,3,4,5,6,7};
        System.out.println(search.insertSearchMultiple(arrInsertList, 1));
        int[] fibonacci = new int[]{1,2,3,4,4,5,6,6,6,6};
        System.out.println(search.fibonacciSearchMultiple(fibonacci, 6));
        System.out.println(search.binarySearchStandard(fibonacci, 6));
    }

    @Test
    void testHeapSort() {
        MySort sort = new MySort();
        int[] arr = new int[]{91, 60, 96, 13, 35, 65, 46, 65, 10, 30, 20, 31, 77, 81,22};
        sort.heap(arr);
        System.out.println(Arrays.toString(arr));
    }

    @Test
    void testBinaryTree() {
        //普通二叉树
        ZooBinaryTree<String> tree = new ZooBinaryTree<>(1, "1");
        tree.getRoot().setLeftNode(2, "2");
        tree.getRoot().setRightNode(3, "3");
        tree.getRoot().getLeftNode().setLeftNode(5, "5");
        tree.getRoot().getLeftNode().setRightNode(6, "6");
        tree.getRoot().getRightNode().setRightNode(4, "4");
        tree.prePrint();
        tree.infixPrint();
        tree.postPrint();
        System.out.println("tree.preSearch(\"6\") = " + tree.preSearch("6"));
        System.out.println("tree.infixSearch(\"6\") = " + tree.infixSearch("6"));
        System.out.println("tree.postSearch(\"6\") = " + tree.postSearch("6"));

        //顺序存储二叉树
        ZooBinaryTree<String> treeOrder = new ZooBinaryTree<>(new String[]{"1","2","3","4","5","6","7"});
        treeOrder.prePrintOrder(0);
        System.out.println();
        treeOrder.infixPrintOrder(0);
        System.out.println();
        treeOrder.postPrintOrder(0);
    }

    @Test
    void testBinaryHuffmanTree() {
        ZooBinaryHuffmanTree tree = new ZooBinaryHuffmanTree(new int[]{13,7,8,3,29,6,1});
        tree.prePrint();

        ZooBinaryHuffmanTree huffmanTree = new ZooBinaryHuffmanTree("i like like like java do you like a java");
        System.out.println("密码本");
        System.out.println(huffmanTree.getHuffmanCodeMap());
        System.out.println("压缩后");
        byte[] zip = huffmanTree.zip();
        System.out.println(Arrays.toString(zip));
        String unzip = huffmanTree.unzip();
        System.out.println("明文");
        System.out.println(unzip);

        ZooBinaryHuffmanTree zooBinaryHuffmanTree = new ZooBinaryHuffmanTree();
        try {
            zooBinaryHuffmanTree.zip("D:\\test.bmp", "D:\\test.zip");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            zooBinaryHuffmanTree.unzip("D:\\test.zip", "D:\\unzip.bmp");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Test
    void testBinaryThreadTree() {
        ZooBinaryThreadTree<String> tree = new ZooBinaryThreadTree<>(1, "1");
        tree.getRoot().setLeftNode(2, "2");
        tree.getRoot().setRightNode(3, "3");
        tree.getRoot().getLeftNode().setLeftNode(5, "5");
//        tree.getRoot().getLeftNode().getLeftNode().setLeftNode(8,"8");
//        tree.getRoot().getLeftNode().getLeftNode().setRightNode(9,"9");
        tree.getRoot().getLeftNode().setRightNode(6, "6");
        tree.getRoot().getRightNode().setRightNode(4, "4");
        tree.getRoot().getRightNode().setLeftNode(7, "7");
        tree.prePrint();
        tree.infixPrint();
        tree.postPrint();

//        tree.getRoot().setParentNode(null);
//        tree.getRoot().getLeftNode().setParentNode(tree.getRoot());
//        tree.getRoot().getLeftNode().getRightNode().setParentNode(tree.getRoot().getLeftNode());
//        tree.getRoot().getLeftNode().getLeftNode().setParentNode(tree.getRoot().getLeftNode());
//        tree.getRoot().getRightNode().setParentNode(tree.getRoot());
//        tree.getRoot().getRightNode().getRightNode().setParentNode(tree.getRoot().getRightNode());
//        tree.getRoot().getRightNode().getLeftNode().setParentNode(tree.getRoot().getRightNode());

        tree.preThread(tree.getRoot());
        System.out.println("1 2 5 6 3 4：prePrint不能再使用");
//        tree.infixThread(tree.getRoot());
//        System.out.println("5 2 6 1 3 4：infixPrint不能再使用");
//        tree.postThread(tree.getRoot());
//        System.out.println("5 6 2 4 3 1：postPrint不能再使用");

        System.out.println("节点5");
        System.out.print(tree.getRoot().getLeftNode().getLeftNode().getLeftNodeType()+"\t");
        System.out.println(tree.getRoot().getLeftNode().getLeftNode().getLeftNode());
        System.out.print(tree.getRoot().getLeftNode().getLeftNode().getRightNodeType()+"\t");
        System.out.println(tree.getRoot().getLeftNode().getLeftNode().getRightNode());

        System.out.println("节点6");
        System.out.print(tree.getRoot().getLeftNode().getRightNode().getLeftNodeType()+"\t");
        System.out.println(tree.getRoot().getLeftNode().getRightNode().getLeftNode());
        System.out.print(tree.getRoot().getLeftNode().getRightNode().getRightNodeType()+"\t");
        System.out.println(tree.getRoot().getLeftNode().getRightNode().getRightNode());

        tree.prePrintThread();
//        tree.infixPrintThread();
//        tree.postPrintThread();
    }

    @Test
    void testBinarySortThreadTree() {
//        int[] arr = new int[]{7,3,10,15,5,1,9,2,13,11,12};
        int[] arr = new int[]{7,3,10,12,5,1,9};

        ZooBinarySortTree tree = new ZooBinarySortTree(arr);
        tree.infixPrint();
        tree.delete(10);
        tree.infixPrint();
    }

    @Test
    void testBinaryBalanceTree() {
//        int[] arr = new int[]{4,3,6,5,7,8};
//        int[] arr = new int[]{10,12,8,9,7,6};
//        int[] arr = new int[]{4,3,6,5,8,9};
//        int[] arr = new int[]{8,4,2,5,9,1};

        int[] arr = new int[]{10,11,7,6,8,9};
        ZooBinaryBalanceTree tree = new ZooBinaryBalanceTree(arr);
        System.out.println(tree.getRoot().height());
        System.out.println(tree.getRoot().leftHeight());
        System.out.println(tree.getRoot().rightHeight());
        System.out.println(tree.getRoot());
    }
}
