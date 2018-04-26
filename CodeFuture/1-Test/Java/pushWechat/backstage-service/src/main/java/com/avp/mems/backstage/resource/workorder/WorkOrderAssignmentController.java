package com.avp.mems.backstage.resource.workorder;

import com.avp.mems.backstage.entity.work.Assignment;
import com.avp.mems.backstage.entity.work.WorkOrder;
import com.avp.mems.backstage.repositories.basic.ComponentRepository;
import com.avp.mems.backstage.repositories.basic.EquipmentRepository;
import com.avp.mems.backstage.repositories.basic.LocationRepository;
import com.avp.mems.backstage.repositories.push.PushInfoWechatRepository;
import com.avp.mems.backstage.repositories.user.UserRepository;
import com.avp.mems.backstage.repositories.work.AssignmentRepository;
import com.avp.mems.backstage.repositories.work.WorkOrderRepository;
import com.avp.mems.backstage.repositories.work.WorkOrderViewRepository;
import com.avp.mems.backstage.rest.ResponseBuilder;
import com.avp.mems.backstage.rest.ResponseCode;
import com.avp.mems.backstage.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by Amber Wang on 2017-06-05 下午 04:38.
 */
@RestController
public class WorkOrderAssignmentController {


    @Autowired
    private WorkOrderRepository workOrderRepository;
    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorkOrderViewRepository workOrderViewRepository;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    EquipmentRepository equipmentRepository;
    @Autowired
    ComponentRepository componentRepository;
    @Autowired
    PushInfoWechatRepository pushInfoWechatRepository;


    @RequestMapping(value = "/workOrderAssignments", method = RequestMethod.POST)
    @Transactional
    ResponseEntity saveWorkOrderAssignment(@RequestBody Assignment assignment) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        if (Validator.isNotNull(assignment)) {
            assignment.setAssignTime(new Date());

            assignment = assignmentRepository.save(assignment);

            WorkOrder workOrder = workOrderRepository.findOne(assignment.getWorkOrderId());
            workOrder.setAssignEmployee(assignment.getAssignEmployee());
            workOrder.setStatusId(4L);

            workOrderRepository.save(workOrder);

            builder.setResultEntity(assignment, ResponseCode.CREATE_SUCCEED);

        } else {
            builder.setErrorCode(ResponseCode.PARAM_MISSING);
        }
        return builder.getResponseEntity();
    }

//
//    private void pushToWechat(WorkOrder workOrder, Assignment assignment) {
//        Message message = new Message();
//        Integer locationId = new Long(workOrder.getLocationId()).intValue();
//        message.setContent("站点:" + locationRepository.findOne(locationId).getNameCn()
//                + "设备名称:" + equipmentRepository.findOne(workOrder.getEquipmentId()).getNameCn() + "\n"
//                + "模块:" + componentRepository.findOne((long) workOrder.getBadComponentId()).getNameCn() + "\n"
//                + "故障描述" + workOrder.getDescription());
//
//        List<String> pushUsers = new ArrayList<>();
//        pushUsers.add(assignment.getRepairEmployee());
//        List<PushInfoWechat> PushInfoWechats = pushInfoWechatRepository.findByUsernameInAndWechatidIsNotNull(pushUsers);
//        List<String> pushWechatUsers = new ArrayList<>();
//        for (PushInfoWechat temp : PushInfoWechats) {
//            pushWechatUsers.add(temp.getWechatid());
//        }
//        weChatMessageService.pushTextToWeChatUsers(message, pushWechatUsers);
//    }
}
