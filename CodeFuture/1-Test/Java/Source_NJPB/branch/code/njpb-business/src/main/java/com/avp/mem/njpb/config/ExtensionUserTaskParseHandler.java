/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.config;

import com.avp.mem.njpb.util.BpmnConstants;
import com.avp.mem.njpb.util.StringPool;
import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.bpmn.model.ExtensionElement;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.handler.UserTaskParseHandler;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Amber Wang on 2017-08-01 下午 02:59.
 * <p>
 * 暂时使用不到该方法，方法本意自定义标签属性
 */
@Deprecated
@Component
public class ExtensionUserTaskParseHandler extends UserTaskParseHandler {

    /**
     * 流程定义文件扩展之后，扩展的属性如何存储呢？我们知道在Activiti中ActivitiImpl对象存储的是活动节点的定义，那么我们即可在流程文件解析时将扩展属性通过setProperty方式存储到 ActivitiImpl对象中。实现过程如下：
     * 自定义用户任务解析控制类，该类实现将流程定义文件中的扩展属性设置给ActivitiImpl
     * 流程引擎配置中将自定义的用户任务解析控制类，覆盖掉默认的用户任务解析控制类
     *
     * @param bpmnParse
     * @param userTask
     */
    @Override
    protected void executeParse(BpmnParse bpmnParse, UserTask userTask) {
        //调用上层的解析
        super.executeParse(bpmnParse, userTask);

        ActivityImpl activity = bpmnParse.getCurrentScope().findActivity(userTask.getId());
//        Map<String, ExtensionOperation> operationMap = parseUserTaskOperations(bpmnParse, userTask);


        Map<String, ExtensionOperation> operationMap = new HashMap<String, ExtensionOperation>();
        //获取扩展属性标签元素
        Map<String, List<ExtensionElement>> extensionElementMap = userTask.getExtensionElements();

        if (!extensionElementMap.isEmpty()) {

            List<ExtensionElement> operationsElementOperations = extensionElementMap.get(BpmnConstants.EXTENSION_ELEMENT_OPERATIONS);

            for (ExtensionElement extensionElement : operationsElementOperations) {
                //设置一个key，后续通过key直接查找人员可以操作的角色和操作列表
                String operationId = userTask.getId() + "_" + extensionElement.getName();

                List<ExtensionElement> operationsElementOperation = extensionElement.getChildElements().get(BpmnConstants.EXTENSION_ELEMENT_OPERATION);

                List<ExtensionOperation> extensionOperations = new ArrayList<>();

                for (ExtensionElement attributeElement : operationsElementOperation) {
                    ExtensionOperation userTaskOperation = new ExtensionOperation();

                    Map<String, List<ExtensionAttribute>> attributeMap = attributeElement.getAttributes();

                    userTaskOperation.setRole(attributeMap.get(BpmnConstants.EXTENSION_ELEMENT_OPERATION_PROPERTY_ROLE).get(0).getValue());
                    userTaskOperation.setOperation(Arrays.asList(attributeMap.get(BpmnConstants.EXTENSION_ELEMENT_OPERATION_PROPERTY_ACTION).get(0).getValue().split(StringPool.SPLIT)));

                    extensionOperations.add(userTaskOperation);

//                userTaskOperation.setRole();
//                userTaskOperation.setOperation();
//                addProperty(attributeElement.getName(), attributeElement.getValue());
                }

//            operationMap.put(extensionElement.getName(), userTaskOperation);

                activity.setProperty(operationId, extensionOperations);
            }
        }


        //将扩展属性设置给activity
//        activity.setProperty(BpmnConstants.PROPERTY_OPERATIONS_TEST, "2017年8月2日14:16:43");
    }

}
