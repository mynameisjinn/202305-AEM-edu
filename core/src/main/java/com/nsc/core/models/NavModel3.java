package com.nsc.core.models;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.nsc.core.annotation.RequestParameter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;

import static org.apache.sling.api.resource.ResourceResolver.PROPERTY_RESOURCE_TYPE;

@Model(adaptables = {SlingHttpServletRequest.class,Resource.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class NavModel3 {

    @ValueMapValue(name=PROPERTY_RESOURCE_TYPE, injectionStrategy=InjectionStrategy.OPTIONAL)
    @Default(values="No resourceType")
    protected String resourceType;

    @SlingObject
    private Resource currentResource;
    @SlingObject
    private ResourceResolver resourceResolver;

    public String title;

    public String text;

    public String image;

    @PostConstruct
    protected void init() {
        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        String currentPagePath = Optional.ofNullable(pageManager)
                .map(pm -> pm.getContainingPage(currentResource))
                .map(Page::getPath).orElse("");


        // .getChildren 의 NullPointException 잡기
        // Contents 가 만들어지지 않은 상태에서 getChildren() 을 호출할 수 없기 때문

        if (currentResource.getChild("Contents") != null) {
            Iterable<Resource> children = currentResource.getChild("Contents").getChildren();
            for (Resource child : children) {
                text = child.getValueMap().get("text", String.class);
                title = child.getValueMap().get("title", String.class);
                image = child.getValueMap().get("image", String.class);

            }
        }
    }

    // getter 생성 -> Alt + Ins



    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getImage() {
        return image;
    }
}
