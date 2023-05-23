package com.nsc.core.models;

import com.day.cq.wcm.api.Page;
import com.nsc.core.annotation.RequestParameter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.annotation.PostConstruct;

@Model(adaptables = {SlingHttpServletRequest.class,Resource.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class NavModel {
    @ValueMapValue
    private String menuname;

    // 현재 컴포넌트 resource
    @SlingObject
    private Resource current;

    @SlingObject
    private SlingHttpServletRequest request;

    @ScriptVariable
    private Page currentPage;

    @SlingObject
    private ResourceResolver resolver;

    @RequestParameter
    private String param;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PostConstruct
    protected void init() {
//        PageManager pm = resolver.adaptTo(PageManager.class);
//        currentPage = pm.getContainingPage(current);
//        System.out.println("1111111");
        String pParam = request.getParameter("param");
//        System.out.println(pParam);
        logger.debug("*************** {} *********",pParam);
    }

    // getter 생성 -> Alt + Ins
    public String getMenuname() {
        return menuname;
    }

    public Page getCurrentPage() {
        return currentPage;
    }
}
