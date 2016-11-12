package com.yahoo.elide.contrib.swagger;

import java.util.HashSet;
import java.lang.IllegalArgumentException;

public class Operation extends SwaggerComponent {
    private static HashSet<String> usedOperationIds = new HashSet<>();
    private static final String[] REQUIRED = {"responses"};

    public String[] tags;
    public String summary;
    public String description;
    public ExternalDocumentation externalDocs;
    private String operationId;
    public MimeType[] consumes;
    public MimeType[] produces;
    public Parameter[] parameters;
    public Responses responses;
    public Enums.Scheme[] schemes;
    public boolean deprecated = false;
    public SecurityRequirement[] security;

    public Operation()
    {
        required = REQUIRED;
    }
    public boolean checkRequired()
    {
        if(!super.checkRequired())
            return false;
        if(Util.hasDuplicates(parameters))
            return false;
        boolean foundBody = false;
        for(int i = 0; i < parameters.length; i++)
        {
            if(parameters[i].in == Enums.Location.BODY)
                if(foundBody)
                    return false;
                else
                    foundBody = true;
        }
        return true;
    }

    public void setOperationId(String id) throws IllegalArgumentException
    {
        if(operationId.equals(id) || !usedOperationIds.contains(id))
            operationId = id;
        else
            throw new IllegalArgumentException("You can't recycle an operationId!");
    }
    public String getOperationId()
    {
        return operationId;
    }
}
