package com.mossle.dashboard.web;

import java.util.*;

import javax.annotation.Resource;

import com.mossle.api.humantask.HumanTaskConnector;
import com.mossle.api.humantask.HumanTaskDTO;
import com.mossle.bpm.persistence.domain.BpmCategory;
import com.mossle.bpm.persistence.domain.BpmProcess;
import com.mossle.bpm.persistence.manager.BpmProcessManager;

import com.mossle.cms.domain.*;
import com.mossle.cms.manager.*;

import com.mossle.security.util.SpringSecurityUtils;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.history.*;
import org.activiti.engine.repository.*;
import org.activiti.engine.runtime.*;
import org.activiti.engine.task.*;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("dashboard")
public class DashboardController {
    private ProcessEngine processEngine;
    private BpmProcessManager bpmProcessManager;
    private CmsArticleManager cmsArticleManager;
    private HumanTaskConnector humanTaskConnector;

    @RequestMapping("dashboard")
    public String list(Model model) {
        String userId = SpringSecurityUtils.getCurrentUserId();
        List<HistoricProcessInstance> historicProcessInstances = processEngine
                .getHistoryService().createHistoricProcessInstanceQuery()
                .startedBy(userId).unfinished().list();
        List<BpmProcess> bpmProcesses = bpmProcessManager.find("from BpmProcess where CATEGORY_ID = 1");
        List<CmsArticle> cmsArticles = cmsArticleManager.getAll();
        model.addAttribute("personalTasks",
                humanTaskConnector.findPersonalTasks(userId, 1, 10));
        model.addAttribute("historicProcessInstances", historicProcessInstances);
        model.addAttribute("bpmProcesses", bpmProcesses);
        model.addAttribute("cmsArticles", cmsArticles);

        return "dashboard/dashboard";
    }

    // ~ ==================================================
    @Resource
    public void setProcessEngine(ProcessEngine processEngine) {
        this.processEngine = processEngine;
    }

    @Resource
    public void setBpmProcessManager(BpmProcessManager bpmProcessManager) {
        this.bpmProcessManager = bpmProcessManager;
    }

    @Resource
    public void setCmsArticleManager(CmsArticleManager cmsArticleManager) {
        this.cmsArticleManager = cmsArticleManager;
    }

    @Resource
    public void setHumanTaskConnector(HumanTaskConnector humanTaskConnector) {
        this.humanTaskConnector = humanTaskConnector;
    }
}
