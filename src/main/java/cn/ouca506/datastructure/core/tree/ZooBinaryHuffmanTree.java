package cn.ouca506.datastructure.core.tree;

import lombok.Data;
import lombok.Getter;

import java.io.*;
import java.util.*;

/**
 * @author windmill666
 * @date 2020/4/16 8:27
 */

public class ZooBinaryHuffmanTree {

    @Getter
    private Node huffmanTree;

    @Getter
    private String encode;

    @Getter
    private Map<Byte,String> huffmanCodeMap = new HashMap<>();

    private byte[] zip;

    public ZooBinaryHuffmanTree() {

    }

    public ZooBinaryHuffmanTree(int[] arr) {
        this.huffmanTree = built(arr);
    }

    public ZooBinaryHuffmanTree(String encode) {
        this.encode = encode;
        this.huffmanTree = built(getNodes(encode.getBytes()));
        builtCodeMap(huffmanTree, "", new StringBuilder());
        System.out.println("压缩前");
        System.out.println(Arrays.toString(encode.getBytes()));
    }

    public void prePrint() {
        if (huffmanTree != null) {
            huffmanTree.prePrint();
        } else {
            System.out.println("哈夫曼树为空");
        }
    }

    public void zip(String srcFile, String destFile) throws IOException {
        if (huffmanTree == null) {
            FileInputStream inputStream = new FileInputStream(srcFile);
            byte[] srcBytes = new byte[inputStream.available()];
            inputStream.read(srcBytes);
            inputStream.close();

            this.huffmanTree = built(getNodes(srcBytes));
            builtCodeMap(huffmanTree, "", new StringBuilder());
            byte[] huffmanBytes = zip(srcBytes);

            FileOutputStream outputStream = new FileOutputStream(destFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(huffmanBytes);

            objectOutputStream.close();
            outputStream.close();
        }
    }

    public byte[] zip() {
        byte[] encodeBytes = encode.getBytes();
        return zip(encodeBytes);
    }

    public byte[] zip(byte[] encodeBytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : encodeBytes) {
            stringBuilder.append(huffmanCodeMap.get(b));
        }
        int len;
        if (stringBuilder.length() % 8 == 0) {
            len = stringBuilder.length()/8;
        } else {
            len = stringBuilder.length()/8 + 1;
        }
        byte[] res = new byte[len];
        String str;
        int index = 0;
        for (int i = 0; i < stringBuilder.length(); i+=8) {
            if (i+8 > stringBuilder.length()) {
                str = stringBuilder.substring(i);
            } else {
                str = stringBuilder.substring(i, i+8);
            }
            res[index] = (byte) Integer.parseInt(str, 2);
            index++;
        }
        this.zip = res;
        return res;
    }

    public void unzip(String srcFile, String destFile) throws IOException, ClassNotFoundException {
        FileInputStream inputStream = new FileInputStream(srcFile);
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        byte[] huffmanBytes = (byte[]) objectInputStream.readObject();
        byte[] unzipBytes = unzip(huffmanBytes);
        objectInputStream.close();
        inputStream.close();

        FileOutputStream outputStream = new FileOutputStream(destFile);
        outputStream.write(unzipBytes);
        outputStream.close();
    }

    public String unzip() {
        return new String(unzip(zip));
    }

    public byte[] unzip(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < bytes.length - 1; i++) {
            //按位或，将11100(28)和1 0000 0000(256)
            //1 0001 1100再截取后八位
            //0001 1100
            int temp = bytes[i];
            String str = Integer.toBinaryString(temp |= 256);
            stringBuilder.append(str.substring(str.length() - 8));
        }
        //注意：最后一个字节，不需要补高位
        byte last = bytes[bytes.length - 1];
        if (last >= 0) {
            stringBuilder.append(Integer.toBinaryString(bytes[bytes.length - 1]));
        } else {
            String str = Integer.toBinaryString(last);
            stringBuilder.append(str.substring(str.length() - 8));
        }

        //倒置密码本
        HashMap<String,Byte> decode = new HashMap<>();
        huffmanCodeMap.forEach((key,value) -> {
            decode.put(value, key);
        });
//        System.out.println("倒置密码本");
//        System.out.println(decode);

        int start = 0;
        List<Byte> bytesDecode = new ArrayList<>();
        for (int i = 0; i < stringBuilder.length(); i++) {
            String key = stringBuilder.substring(start, i + 1);
            if (decode.containsKey(key)) {
                bytesDecode.add(decode.get(key));
                start = i + 1;
            }
        }
//        System.out.println("解压后");
//        System.out.println(bytesDecode);

        byte[] res = new byte[bytesDecode.size()];
        for (int i = 0; i < bytesDecode.size(); i++) {
            res[i] = bytesDecode.get(i);
        }

        return res;
    }

    private Node built(int[] arr) {
        if (arr == null) {
            return null;
        }
        List<Node> arrList = new ArrayList<>();
        for (int priority : arr) {
            arrList.add(new Node(priority));
        }
        return built(arrList);
    }

    private Node built(List<Node> arrList) {
        if (arrList == null || arrList.size() < 2) {
            return null;
        }
        Node node = null;
        while (arrList.size() > 1) {
            arrList.sort(Comparator.comparingInt(Node::getPriority));
            Node first = arrList.get(0);
            Node second = arrList.get(1);
            //取二相加
            node = new Node(first.priority + second.priority);
            node.leftNode = first;
            node.rightNode = second;

            //删二加一
            arrList.remove(0);
            arrList.remove(0);
            arrList.add(node);
        }
        return node;
    }

    private List<Node> getNodes(byte[] bytes) {
        List<Node> nodes = new ArrayList<>();
        Map<Byte,Integer> map = new HashMap<>();
        for (byte b : bytes) {
            map.merge(b, 1, Integer::sum);
        }
        map.forEach((key, value) -> {
            nodes.add(new Node(key, value));
        });
        return nodes;
    }

    private void builtCodeMap(Node node, String binary, StringBuilder stringBuilder) {
        StringBuilder stringBuilderNew = new StringBuilder(stringBuilder);
        stringBuilderNew.append(binary);
        if (node != null) {
            //这里的node其实不会为null，因为只要一开始的头节点不为空
            //在遍历的时候，如果是非叶子节点则向左右递归，如果是叶子节点则放入Map并结束方法
            if (node.data == null) {
                builtCodeMap(node.leftNode, "0", stringBuilderNew);
                builtCodeMap(node.rightNode, "1", stringBuilderNew);
            } else {
                huffmanCodeMap.put(node.data, stringBuilderNew.toString());
            }
        }
    }

    @Data
    static class Node {
        private Byte data;
        private int priority;
        private Node leftNode;
        private Node rightNode;

        public Node(int priority) {
            this.priority = priority;
        }

        public Node(Byte data, int priority) {
            this.data = data;
            this.priority = priority;
        }

        private void prePrint() {
            System.out.println(this.priority + " " + this.data);
            if (this.leftNode != null) {
                this.leftNode.prePrint();
            }
            if (this.rightNode != null) {
                this.rightNode.prePrint();
            }
        }

        private void infixPrint() {
            if (this.leftNode != null) {
                this.leftNode.infixPrint();
            }
            System.out.print(this.priority + " ");
            if (this.rightNode != null) {
                this.rightNode.infixPrint();
            }
        }

        private void postPrint() {
            if (this.leftNode != null) {
                this.leftNode.postPrint();
            }
            if (this.rightNode != null) {
                this.rightNode.postPrint();
            }
            System.out.print(this.priority + " ");
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    ", priority=" + priority +
                    ", leftNode=" + leftNode +
                    ", rightNode=" + rightNode +
                    '}';
        }
    }
}
