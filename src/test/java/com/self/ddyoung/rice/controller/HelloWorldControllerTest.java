package com.self.ddyoung.rice.controller;

import net.minidev.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

/**
 * @author <a href="mailto:zdan68@163.com">Sanbian</a>
 * @version 1.0
 * @since 17/8/1 下午5:59
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MockServletContext.class)
@WebAppConfiguration
public class HelloWorldControllerTest {
    private MockMvc mvc;
    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new HelloWorldController()).build();
    }

    @Test
    public void index() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/hello").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void testIndex() throws Exception {
        assignTask();
    }

    private List<String> getTasks(){
//        List<String> tasks = client.listChildren("/bcp/task");
        List<String> tasks = new ArrayList<String>();
        for (int i = 0; i < 22; i++) {
            tasks.add("task" + i);
        }
        return tasks;
    }

    private Integer getTaskCount(String task){
        //  int taskCount = Integer.valueOf(new String(client.getData("/bcp/task/" + task)));
        int taskCount = 1;
        if (task.equals("task20")) taskCount = 2;
        if (task.equals("task21")) taskCount = 3;
        return taskCount;
    }

    private List<String> getProviders(){
//        List<String> providers = client.listChildren("/bcp/providers");
        List<String> providers = new ArrayList<String>();
        providers.add("node0");
        providers.add("node1");
        providers.add("node2");
        providers.add("node3");
        return providers;
    }

    private List<String> getRunnings(){
        //正在运行的jvm
//        List<String> runnings = client.listChildren("/bcp/running");
        List<String> runnings = new ArrayList<String>();
        runnings.add("node0");
        runnings.add("node2");
        runnings.add("node3");
        return runnings;
    }

    private List<String> getRunningNodes(String node){
//        List<String> runningNodes = client.listChildren("/bcp/running/" + node);
        List<String> runningNodes = new ArrayList<String>();
        if (node.equals("node0")){
            runningNodes.add("task0");
            runningNodes.add("task10");
        }else if (node.equals("node2")){
            runningNodes.add("task1");
            runningNodes.add("task2");
            runningNodes.add("task3");
            runningNodes.add("task4");
            runningNodes.add("task5");
            runningNodes.add("task6");
        }else if(node.equals("node3")){
            runningNodes.add("task11");
            runningNodes.add("task12");
            runningNodes.add("task13");
            runningNodes.add("task14");
            runningNodes.add("task15");
            runningNodes.add("task16");
            runningNodes.add("task17");
            runningNodes.add("task18");
        }
        return runningNodes;
    }

    /**
     * 将任务分配到各个机器上
     */
    private void assignTask() throws Exception {
        List<String> tasks = getTasks();
        Map<String, Integer> taskMap = new HashMap<String, Integer>();
        //总任务数
        int totalTask = 0;
        for (String task : tasks) {
            //每个规则的任务数
            int taskCount = getTaskCount(task);
            taskMap.put(task, taskCount);
            totalTask += taskCount;
        }
        //提供服务的jvm
        List<String> providers = getProviders();
        //正在运行的jvm
        List<String> runnings = getRunnings();
        //要补的jvm
        List<String> added = getLeft(providers, runnings);
        //要退的jvm
        List<String> deleted = getRight(providers, runnings);
        List<String> exists = getCenter(providers, runnings);
        for (String add : added) {
//            client.createDirectory("/bcp/running/" + add, ZkClient.CreateMode.PERSISTENT);
        }
        for (String delete : deleted) {
//            client.deleteQuietly("/bcp/running/" + delete);
        }
        //目前存在的节点与节点分配的任务
        Map<String, List<String>> existTasks = new HashMap<String, List<String>>();
        for (String node : exists) {
            List<String> runningNodes = getRunningNodes(node);
            for (String runningNodeTask : new ArrayList<String>(runningNodes)) {
                if (!taskMap.containsKey(runningNodeTask)){
                    runningNodes.remove(runningNodeTask);
                }else {
                    //该规则未分配的数量
                    Integer taskCount = taskMap.get(runningNodeTask);
                    taskCount --;
                    taskMap.put(runningNodeTask, taskCount);
                }

            }
            existTasks.put(node, new ArrayList<String>(runningNodes));
        }
        //全部节点任务分配情况
        for (String add : added) {
            existTasks.put(add, new ArrayList<String>());
        }
        int perTaskCount = totalTask / providers.size();
        int leftCount = totalTask % providers.size();


        //然后通过比较器来实现排序
        List<Map.Entry<String,Integer>> taskList = new ArrayList<Map.Entry<String,Integer>>(taskMap.entrySet());
        Collections.sort(taskList,new Comparator<Map.Entry<String,Integer>>() {
            //升序排序
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }

        });

        Map<String, Integer> taskOrderMap = new LinkedHashMap<String, Integer>();
        Iterator<Map.Entry<String, Integer>> iter = taskList.iterator();
        Map.Entry<String, Integer> tmpEntry = null;
        while (iter.hasNext()) {
            tmpEntry = iter.next();
            taskOrderMap.put(tmpEntry.getKey(), tmpEntry.getValue());
        }

        //遍历待分配的节点
        for (String node : existTasks.keySet()) {
            //该节点在running的的任务
            List<String> nodeTasks = existTasks.get(node);
            //该节点已分配的任务数
            int nodeTaskCount = nodeTasks.size();
            int nodeTaskLeftCount = perTaskCount - nodeTaskCount;

            if (nodeTaskLeftCount < 0){
                assignDelete(existTasks,node,nodeTaskLeftCount,taskOrderMap, perTaskCount);
            }else if (nodeTaskLeftCount > 0){
                assignAdd(taskOrderMap, nodeTaskLeftCount, nodeTasks, node, perTaskCount);
            }
        }

        if (leftCount > 0){
            for (String node : existTasks.keySet()){
                //该节点在running的的任务
                List<String> nodeTasks = existTasks.get(node);
                //该节点已分配的任务数
                int nodeTaskCount = nodeTasks.size();
                int nodeTaskLeftCount = perTaskCount + 1 - nodeTaskCount;
                if (leftCount > 0){
                    assignAdd(taskOrderMap, nodeTaskLeftCount, nodeTasks, node, leftCount);
                }
            }
        }

        System.out.println(JSONObject.toJSONString(taskOrderMap));
        System.out.println(JSONObject.toJSONString(existTasks));

    }

    private void assignDelete(Map<String, List<String>> existTasks, String node, int nodeTaskLeftCount, Map<String, Integer> taskMap, int perTaskCount){
        for (String nodeTask : new ArrayList<String>(existTasks.get(node))){
            if (nodeTaskLeftCount >= 0) {
                break;
            }
            // TODO: 2017/8/9 删除该节点的该任务
            if (!taskMap.containsKey(nodeTask)){
                taskMap.put(nodeTask, 0);
            }
            int taskCount = taskMap.get(nodeTask);
            taskCount ++;
            taskMap.put(nodeTask, taskCount);
            existTasks.get(node).remove(nodeTask);
            int nodeTaskCount = existTasks.get(node).size();
            nodeTaskLeftCount = perTaskCount - nodeTaskCount;
        }
    }

    private void assignAdd(Map<String, Integer> taskMap, int nodeTaskLeftCount, List<String> nodeTasks, String node, int leftCount) throws Exception {
        for (String task : taskMap.keySet()){
            if (nodeTaskLeftCount <= 0 || leftCount <= 0){
                break;
            }
            //该规则未分配的数量
            Integer taskCount = taskMap.get(task);
            if (taskCount > 0 && !nodeTasks.contains(task)){
                //如果不存在这个任务，就分配进去
                if (!nodeTasks.contains(task)){
                    createDirectory();
                    nodeTasks.add(task);
                    nodeTaskLeftCount--;
                    taskCount --;
                    taskMap.put(task, taskCount);
                    leftCount --;
                }
            }
        }

        if (nodeTaskLeftCount > 0 && leftCount > 0){
            for (String task : taskMap.keySet()){
                if (nodeTaskLeftCount <= 0){
                    break;
                }
                //该规则未分配的数量
                Integer taskCount = taskMap.get(task);
                if (taskCount > 0){
                    createDirectory();
                    nodeTasks.add(task);
                    nodeTaskLeftCount--;
                    taskCount --;
                    taskMap.put(task, taskCount);
                    leftCount --;
                }
            }
        }
    }

    private void createDirectory(){
//        client.createDirectory("/bcp/running/" + node, ZkClient.CreateMode.PERSISTENT);
//        // TODO: 2017/8/9 任务保存到节点
//        client.saveData("/bcp/task/" + task, null, ZkClient.CreateMode.PERSISTENT);
    }



    private static List<String> getLeft(List<String> list1, List<String> list2) {
        List<String> left = new ArrayList<String>(list1);
        left.removeAll(list2);
        return left;
    }

    private static List<String> getRight(List<String> list1, List<String> list2) {
        List<String> right = new ArrayList<String>(list2);
        right.removeAll(list1);
        return right;
    }

    private static List<String> getCenter(List<String> list1, List<String> list2) {
        List<String> center = new ArrayList<String>(list2);
        center.retainAll(list1);
        return center;
    }

}