package com.info.web.controller.wf;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.domain.TTask;
import com.info.service.WfObjectCompare;
import com.info.service.WfProcessUtils;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;

@Controller
public class Demo extends BaseController {
	@Autowired
	WfProcessUtils wf;
	@Autowired
	WfObjectCompare compare;
	@RequestMapping(value = "/wf/demo/{action}/")
	public void demo(@PathVariable("action") int action,
			HttpServletResponse response) {
		CurrentResponse =response;
		message = new ResultMessage();
		switch (action) {
		case 1:// 起草
			result = add();
			break;
		case 2:// 保存
			result = save();
			break;
		case 3:// 提交
			result = commit();
			break;
		case 4:// 催办
			result = urge();
			break;
		case 5:// 打回
			result = returnActive();
			break;
		case 6:// 办结
			result = complete();
			break;
		case 7://归档
			result = archivedProcess();
		case 8://实体对象保存前后对比
			objectCompare();
		default:
			break;
		}
		writeJsonString(result);
	}
	private void objectCompare() {
		TTask task = new TTask();
		task.setFId(115);
		//电力,绿化,通信
		task.setFIndustryCategoryName("电力,绿化,通信1");
		//竣工决算编制
		task.setFServiceCategory("竣工决算编制1");
		//竣工决算编制
		task.setFBusinessCategory("竣工决算编制");
		try {
			String info =compare.objectCompare(task);
			System.out.println(info);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	public String add() {
		// 起草
		try {
			int typeId = getInt("typeid", -1);
			int processId = wf.addNewProcess(typeId);
			message.setSuccess(true);
			message.setRoot(processId);
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return getJsonFromObj(message);
	}

	public String save() {
		// 更新
		try {
			int processId = getInt("processId", -1);
			int formPKID = 0;
			String title = "业务过程demo测试标题";
			message.setSuccess(wf.setProcessTitle(processId, formPKID, title));
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return getJsonFromObj(message);
	}

	public String commit() {
		// 提交给下一个办理人
		try {
			int processId = getInt("processId", -1);
			int acceptUserId = getInt("acceptUserId", -1);
			int aboveActId = getInt("aboveActId", -1);
			message.setSuccess(true);
			message.setRoot(wf.addProcessActiveItem(processId, acceptUserId,
					aboveActId,"业务提交说明备注"));
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return getJsonFromObj(message);
	}

	public String urge() {
		// 催办
		try {
			int activeId = getInt("activeId", -1);
			message.setSuccess(wf.activeUrge(activeId,"催办业务说明"));
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return getJsonFromObj(message);
	}

	public String returnActive() {
		// 打回
		try {
			int activeId = getInt("activeId", -1);
			message.setSuccess(wf.activeReturn(activeId,"业务打回说明备注"));
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return getJsonFromObj(message);
	}

	public String complete() {
		// 业务办理完成
		try {
			int activeId = getInt("activeId", -1);
			message.setSuccess(wf.activeComplet(activeId));
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return getJsonFromObj(message);
	}

	public String archivedProcess() {
		// 业务过程归档
		try {
			int processId = getInt("processId", -1);
			message = wf.archivedProcess(processId);
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("业务过程归档失败！");
			e.printStackTrace();
		}
		return getJsonFromObj(message);
	}
}
