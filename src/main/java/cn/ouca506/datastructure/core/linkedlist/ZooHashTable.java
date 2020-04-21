package cn.ouca506.datastructure.core.linkedlist;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author windmill666
 * @date 2020/4/13 8:56
 */

public class ZooHashTable {

    public static List<ZooSingleLinkedList<User>> hashTable;

    public static int autoIncrement;

    public static int size;

    public static void main(String[] args) {
        //创建“数据库表”，使用哈希表进行存储
        size = 10;
        hashTable = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            hashTable.add(new ZooSingleLinkedList<>());
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("---------------------------------菜单---------------------------------");
        System.out.println("增加：add    删除：delete    更新：update    查找：select    退出：exit");
        System.out.println("显示：show   显示存储结构：showHashTable     快速生成数据：quick");
        while (!scanner.hasNext("exit")) {
            String input = scanner.nextLine();
            switch (input) {
                case "add": {
                    System.out.print("用户名：");
                    String username = scanner.nextLine();
                    System.out.print("地址：");
                    String address = scanner.nextLine();
                    System.out.print("电话：");
                    String phone = scanner.nextLine();
                    add(username, address, phone);
                    break;
                }
                case "delete": {
                    System.out.print("请输入要删除的id：");
                    int id = Integer.parseInt(scanner.nextLine());
                    delete(id);
                    break;
                }
                case "update": {
                    System.out.print("请输入要更新的id：");
                    int id = Integer.parseInt(scanner.nextLine());
                    System.out.print("用户名：");
                    String username = scanner.nextLine();
                    System.out.print("地址：");
                    String address = scanner.nextLine();
                    System.out.print("电话：");
                    String phone = scanner.nextLine();
                    User user = new User(username, address, phone);
                    update(id, user);
                    break;
                }
                case "select": {
                    System.out.print("请输入要查找的id：");
                    int id = Integer.parseInt(scanner.nextLine());
                    select(id);
                    break;
                }
                case "show": {
                    show();
                    break;
                }
                case "showHashTable": {
                    showHashTable();
                    break;
                }
                case "quick": {
                    System.out.println("请输入生成数据的条数：");
                    int recordNum = Integer.parseInt(scanner.nextLine());
                    quick(recordNum);
                    break;
                }
                default: {
                    System.out.println("输入有误");
                }
            }
        }
    }

    private static void add(String username, String address, String phone) {
        User user = new User(username, address, phone);
        user.setId(autoIncrement);
        hashTable.get((autoIncrement) % size).add(user);
        autoIncrement++;
        System.out.println("添加成功！");
    }

    private static void delete(int id) {
        //id=10的记录在第一行的第二个
        int hashRow = id % size;
        int hashCol = id / size;
        User user = hashTable.get(hashRow).get(hashCol);
        if (user != null) {
            user.setDelete(1);
            System.out.println("删除成功！");
        } else {
            System.out.println("删除失败！");
        }
    }

    private static void update(int id, User userNew) {
        //id=10的记录在第一行的第二个
        int hashRow = id % size;
        int hashCol = id / size;
        User user = hashTable.get(hashRow).get(hashCol);
        if (user != null && user.getDelete() == 0) {
            user.setUsername(userNew.getUsername());
            user.setAddress(userNew.getAddress());
            user.setPhone(userNew.getPhone());
            System.out.println("更新成功！");
        } else {
            System.out.println("更新失败！");
        }
    }

    private static void select(int id) {
        //id=10的记录在第一行的第二个
        int hashRow = id % size;
        int hashCol = id / size;
        User user = hashTable.get(hashRow).get(hashCol);
        if (user != null && user.getDelete() == 0) {
            System.out.println("查询结果：" + user.toString());
        } else {
            System.out.println("未查到！");
        }
    }

    private static void show() {
        System.out.println("------------------------------------------------");
        System.out.println( "id" + "\t" +  "username" + "\t" +  "address" + "\t" +  "phone" + "\t" );
        int lastRow = (autoIncrement - 1) % 10;
        int lastCol = (autoIncrement - 1) / 10;
        for (int col = 0; col < lastCol; col++) {
            for (int row = 0; row < size; row++) {
                User user = hashTable.get(row).get(col);
                if (user.getDelete() == 0) {
                    System.out.println(user.getId()+"\t"+user.getUsername()+"\t"+user.getAddress()+"\t"+user.getPhone()+"\t");
                }
            }
        }
        for (int row = 0; row <= lastRow; row++) {
            User user = hashTable.get(row).get(lastCol);
            if (user.getDelete() == 0) {
                System.out.println(user.getId()+"\t"+user.getUsername()+"\t"+user.getAddress()+"\t"+user.getPhone()+"\t");
            }
        }
        System.out.println("------------------------------------------------");
    }

    private static void showHashTable() {
        for (int i = 0; i < size; i++) {
            System.out.print("链表 " + i + ": ");
            hashTable.get(i).showLinkedList();
        }
    }

    private static void quick(int recordNum) {
        String[] username = new String[recordNum];
        String[] address = new String[recordNum];
        String[] phone = new String[recordNum];
        String[] names1 = {"赵","钱","孙","李","张","吴"};
        String[] names2 = {"乐","广","月","圣","龙","天"};
        for (int i = 0; i < recordNum; i++) {
            username[i] = names1[(int) (Math.random()*names1.length)] + names2[(int) (Math.random()*names2.length)] + names2[(int) (Math.random()*names2.length)];
        }
        for (int i = 0; i < recordNum; i++) {
            address[i] = "北京市"+(i+1)+"区";
        }
        for (int i = 0; i < recordNum; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("1");
            for (int j = 0; j < recordNum; j++) {
                stringBuilder.append((int) (Math.random() * 10));
            }
            phone[i] = stringBuilder.toString();
        }
        for (int i = 0; i < recordNum; i++) {
            User user = new User(username[i], address[i], phone[i]);
            user.setId(autoIncrement);
            hashTable.get((autoIncrement) % size).add(user);
            autoIncrement++;
        }
        System.out.println("成功生成"+recordNum+"条数据！");
    }

    @Data
    static class User{
        private int id;
        private String username;
        private String address;
        private String phone;
        private int delete;

        public User(String username, String address, String phone) {
            this.username = username;
            this.address = address;
            this.phone = phone;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", username='" + username + '\'' +
                    ", address='" + address + '\'' +
                    ", phone='" + phone + '\'' +
                    ", delete=" + delete +
                    '}';
        }
    }
}
