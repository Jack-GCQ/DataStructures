package com.atguigu.binarysorttree;

import java.awt.print.Pageable;

/**
 * Created by ChengQian on 2020/6/1 12:41
 */
public class BinarySortTreeDemo {
    public static void main(String[] args) {
        int[] arr = {7, 3, 10, 12, 5, 1, 9, 2};
        BinarySortTree binarySortTree = new BinarySortTree();
        //循环将数组添加到二叉树中
        for (int i = 0; i < arr.length; i++){
            binarySortTree.add(new Node(arr[i]));
        }
        //中序遍历二叉排序树
        System.out.println("中序遍历二叉排序树");
        binarySortTree.infixOrder();


        binarySortTree.delNode(7);
        System.out.println("删除结点后 中序遍历二叉排序树");
        binarySortTree.infixOrder();
    }
}
class BinarySortTree{
    private Node root;

    /**
     *  1.返回的以 node为根结点 的二叉排序树的最小结点的值
     *  2.删除node 为根结点的二叉树的最小结点
     * @param node 传入的结点 当作二叉排序树的根结点
     * @return 返回的是以node 为根结点的二叉排序树的最小结点值
     */
    public int delRightTreeMin(Node node) {
        Node target = node; //临时变量保存
        //循环查找左节点 就会找到最小值
        while (target.left != null) { //一直往左找 最小值 直到左边为空退出
            target = target.left;
        }
        //这时target指向了最小结点
        //删除最小结点
        delNode(target.value);
        return target.value;
    }

    //要删除的结点
    private Node search(int value){
        if (root == null){
            return null;
        } else {
            return root.search(value);
        }
    }
    //父节点
    public Node searchParent(int value){
        if (root == null){
            return null;
        } else {
            return root.searchParent(value);
        }
    }


    public void delNode(int value){
        if (root == null){
            return;
        } else {
            //找要删除的结点 targetNode
            Node targetNode = search(value);
            //没有找到
            if (targetNode == null){
                return;
            }
            //如果我们发现targetNode 没有父节点
            if (root.left == null && root.right == null){
                    root = null;
                    return;
            }
            //去找到targetNode的父结点
            Node parent = searchParent(value);
            //如果要删除的结点是叶子结点
            if (targetNode.left == null && targetNode.right == null){
                //判断targetNode是父结点的左结点 还有右子结点
                if (parent.left != null && parent.left.value == value){//左
                    parent.left = null;
                } else if (parent.right != null && parent.right.value == value) { //右子结点
                    parent.right = null;
                }
            } else if (targetNode.left != null && targetNode.right != null) { //删除两颗子树的节点
                int minVal = delRightTreeMin(targetNode.right);
                targetNode.value = minVal;
            } else {//删除只有一棵子树的节点
                //如果要删除的结点有左子结点
                if (targetNode.left != null ) {
                    //如果targetNode是parent的左子结点
                    if (parent.left.value == value){
                        parent.left = targetNode.left;
                    } else { //targetNode 是parent的右子结点
                        parent.right = targetNode.left;
                    }
                } else { //如果要删除的结点有右子结点
                    //如果targetNode 是parent的右子结点
                    if (parent.left.value == value) {
                        parent.left = targetNode.right;
                    } else {//如果targetNode是 parent的右子结点
                        parent.right = targetNode.right;
                    }
                }
            }

        }
    }

    //添加结点方法
    public void add(Node node){
        if (root == null){
            root = node;//root为空 直接让node指向root
        } else {
            root.add(node);
        }
    }

    //中序遍历
    public void infixOrder(){
        if (root != null) {
            root.infixOrder();
        } else {
            System.out.println("二叉排序树为空,不能遍历");
        }
    }
}
//创建Node结点
class Node{
    int value;
    Node left;
    Node right;

    public Node(int value) {
        this.value = value;
    }
    //查找要删除的结点
    /**
     *
     * @param value 如果找到返回改结点 否则返回null
     * @return
     */
    public Node search(int value){
        if (value == this.value){//和当前结点一致
                return this;
        } else if (value < this.value){ //如果查找的值小于当前结点 向左子树递归查找
            //如果左子结点为空
            if (this.left == null){
                return null;
            }
            return  this.left.search(value);
        } else { //查找的值 不小于当前结点 向右子树递归查找
            if (this.right == null){
                return null;
            }
            //向有递归查找
            return this.right.search(value);
        }
    }
    //查找要删除结点的父结点
    /**
     *
     * @param value 要找到的结点的值
     * @return 返回的是要删除的结点的父节点 如果没有就返回null
     */
    public Node searchParent(int value){
        //如果当前结点时要删除结点的父节点 就返回
        if ((this.left != null && this.left.value == value) ||
                (this.right != null && this.right.value == value)){
            return this;
        } else {
            //如果查找的值小于当前结点的值 并且当前结点的左子结点不为空
            if (value < this.value && this.left != null) {
                return this.left.searchParent(value);
            } else if (value >= this.value && this.right != null){
                return this.right.searchParent(value);//向右子树递归查找
            } else {
                return null;//没有父结点
            }
        }
    }

    //添加结点的方法
    public void add(Node node){
        if (node == null){
            return;
        }
        //判断传入的结点值 和当前子树的根结点的值关系
        if (node.value < this.value){//添加的值小于当前值 往左子结点添加
            //如果当前左子节点为null
            if (this.left == null){
                this.left = node;
            } else {
                //递归的向左子树添加
                    this.left.add(node);
            }
        } else {//添加的结点的值 大于当前结点的值 往右边添加
            if (this.right == null){
                this.right = node;
            }else{
                //递归的向左子树添加
                this.right.add(node);
            }
        }
    }

    //中序遍历
    public void infixOrder(){
        if (this.left != null){
            this.left.infixOrder();
        }
        System.out.println(this);
        if (this.right != null){
            this.right.infixOrder();
        }
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }
}