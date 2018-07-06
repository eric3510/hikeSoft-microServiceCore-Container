package org.springframework.boot.container.core;

import java.util.ArrayList;
import java.util.LinkedList;

/***
 * @author 王强 Email : 
 * @version 创建时间：2018/7/5
 * Main
 */
public class Main{
    private final static long LENGTH = 100000;
    public static void main(String[] args){
        System.out.println(120000d/57d);
//        Main main = new Main();
//        PerformanceTesting performanceTesting = new PerformanceTesting();
//        performanceTesting.runTestSegment(main, "linkedListLast");
//        performanceTesting.runTestSegment(main, "arrayList");
//        performanceTesting.runTestSegment(main, "linkedList");
    }

    public void arrayList(){
        ArrayList<Object> list = new ArrayList<>();
        for(int i = 0; i < LENGTH; i++){
            list.add("123");
        }
    }
    //3292
    //8507
    public void linkedList(){
        LinkedList<Object> list = new LinkedList<>();
        for(int i = 0; i < LENGTH; i++){
            list.add("123");
        }
    }

    public void linkedListLast(){
        LinkedList list = new LinkedList();
        for(int i = 0; i < LENGTH; i++){
            list.addLast("123");
        }
    }
}
