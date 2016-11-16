package com.yahoo.elide.contrib.swagger;

import com.google.gson.annotations.SerializedName;

enum Scheme {
    @SerializedName("wss")
    WSS, 

    @SerializedName("ws")
    WS, 

    @SerializedName("http")
    HTTP,

    @SerializedName("https")
    HTTPS
};

class Swagger {
    // This is specified to be this value. 
    // TODO: Should I somehow externalize these values so that
    // they are easier to increment?
    public String swagger = "2.0";
    public Info info;
    public String host;
    public String basePath = "/";
    public Scheme[] schemes;
    public MimeType[] consumes;
    public MimeType[] produces;
    // There's going to be a Paths type and a Path type. I'm just going by the spec.
    public Paths paths;
    public Definitions definitions;
    // I haven't the slightest idea how we're to implement this. I think one could make the case that 
    // this is for the benefit of whatever poor soul has to write this by hand, but the computer doesn't 
    // care, so we could just leave it out.
    public ParametersDefinitions parameters;
    // I don't know how to do this for the reasons above and also java can only return 
    // one thing, so it might not be able to have many responses.
    public ResponsesDefinitions responses;
    // May God help us with this one.
    public SecurityDefinitions securityDefinitions;
    public SecurityRequirement[] security;
    public Tag[] tags;
    public ExternalDocumentation externalDocs;
}
