package com.avp.mem.njpb.service.activiti;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 调整报修内容处理器
 *
 * @author Amber
 */
@Component
@Transactional
public class AfterModifyReportContentProcessor implements TaskListener {

    private static final long serialVersionUID = 1L;

    @Autowired
    RuntimeService runtimeService;

    /* (non-Javadoc)
     * @see org.activiti.engine.delegate.TaskListener#notify(org.activiti.engine.delegate.DelegateTask)
     */
    public void notify(DelegateTask delegateTask) {
        String processInstanceId = delegateTask.getProcessInstanceId();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        //TODO 可以在调整报修内容的时候对原有工单信息进行修改，但是如果调整报修内容接口自己写的话就不需要在流程里面加上处理了。所以后续需要再思考
        /*Leave leave = leaveManager.getLeave(new Long(processInstance.getBusinessKey()));

        leave.setLeaveType((String) delegateTask.getVariable("leaveType"));
        leave.setStartTime((Date) delegateTask.getVariable("startTime"));
        leave.setEndTime((Date) delegateTask.getVariable("endTime"));
        leave.setReason((String) delegateTask.getVariable("reason"));

        leaveManager.saveLeave(leave);*/
    }

}
