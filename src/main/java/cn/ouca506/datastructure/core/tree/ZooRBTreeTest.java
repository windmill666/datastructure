package cn.ouca506.datastructure.core.tree;

import java.util.Scanner;

/**
 * @author windmill666
 * @date 2020/4/24 11:20
 */

public class ZooRBTreeTest {

    public static void main(String[] args) {
        ZooRBTree<String,Object> rbt = new ZooRBTree<>();
        Scanner scanner = new Scanner(System.in);
        boolean exit = true;
        while (exit) {
            System.out.println("请输入key：");
            String s = scanner.nextLine();
            if (s.equals("exit")) {
                exit = false;
            } else {
                rbt.insert(s, null);
                rbt.infixPrint();
            }
        }
    }
}
