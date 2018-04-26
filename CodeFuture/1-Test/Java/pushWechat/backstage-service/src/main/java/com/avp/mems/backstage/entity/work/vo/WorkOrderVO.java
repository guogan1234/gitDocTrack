package com.avp.mems.backstage.entity.work.vo;

import com.avp.mems.backstage.entity.work.WorkOrderView;

import java.io.Serializable;

/**
 * Created by zhoujs on 2017/6/12.
 */
public class WorkOrderVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private WorkOrderView workOrderView;

	private String fixApproachNameCn;

	private String faultTypeNameCn;

	public WorkOrderView getWorkOrderView() {
		return workOrderView;
	}

	public void setWorkOrderView(WorkOrderView workOrderView) {
		this.workOrderView = workOrderView;
	}

	public String getFixApproachNameCn() {
		return fixApproachNameCn;
	}

	public void setFixApproachNameCn(String fixApproachNameCn) {
		this.fixApproachNameCn = fixApproachNameCn;
	}

	public String getFaultTypeNameCn() {
		return faultTypeNameCn;
	}

	public void setFaultTypeNameCn(String faultTypeNameCn) {
		this.faultTypeNameCn = faultTypeNameCn;
	}
}
