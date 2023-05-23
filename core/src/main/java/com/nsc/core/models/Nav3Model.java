package com.nsc.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.*;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Nav3Model {

    @SlingObject
    private Resource current;

    @ValueMapValue
    private String color;

    List<ValueMap> contents;

//    @ChildResource
//    List<Nav3ContentModel> multifield;
    @PostConstruct
    protected void init() {
        Resource Contents = current.getChild("Contents");
        contents = new ArrayList<ValueMap>();

        if(Objects.nonNull(Contents)){
            for(Resource child : Contents.getChildren()){
                contents.add(child.getValueMap());
            }
        }
    }

    public List<ValueMap> getContents() {
        return contents;
    }

    public String getColor() {
        return color;
    }

//    public List<Nav3ContentModel> getMultifield() {
//        return multifield;
//    }
}
