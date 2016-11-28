package test;

import junit.framework.*;

import com.yahoo.elide.contrib.swagger.*;

public class SwaggerTest extends TestCase {

    private Swagger getFunctionalSwagger()
    {
        Swagger retval = new Swagger();
        retval.info = new Info();
        retval.paths = new Paths();
        return retval;
    }

    public void testRequired() throws SwaggerValidationException
    {
        Swagger s = new Swagger();
        try {
            s.checkRequired();
            fail("Something isn't working right; there should be an exception here");
        }
        catch (SwaggerValidationException e)
        {}
        s.info = new Info();
        try {
            s.checkRequired();
            fail("Something isn't working right; there should be an exception here");
        }
        catch (SwaggerValidationException e)
        {
        }
        s.paths = new Paths();
        s.checkRequired();
    }
    /*
     * The point of this method is to make sure
     * that the checkAllRequired() method goes all the way down the stack
     */
    public void testCheckAllRequired() throws SwaggerValidationException
    {
        Swagger s = new Swagger();
        try {
            Swagger.checkAllRequired(s);
            fail("Something isn't working right; there should be an exception here");
        }
        catch (SwaggerValidationException e)
        {
        }
        s.info = new Info();
        s.paths = new Paths();
        try {
            Swagger.checkAllRequired(s);
            fail("Something isn't working right; there should be an exception here");
        }
        catch (SwaggerValidationException e)
        {
        }
        s.info.title = "Title";
        s.info.version = "15.52.1.5";
        Swagger.checkAllRequired(s);
        s.info.contact = new Contact();
        Swagger.checkAllRequired(s);
        s.info.contact.name = "Dewey Finn";
        s.info.contact.url = "the internet";
        try {
            Swagger.checkAllRequired(s);
            fail("Something isn't working right; there should be an exception here");
        }
        catch (SwaggerValidationException e)
        {
        }
        s.info.contact.url = "http://xkcd.com";
        Swagger.checkAllRequired(s);
        s.info.contact.email = "1600 Pennsylvania Avenue";
        try {
            Swagger.checkAllRequired(s);
            fail("Something isn't working right; there should be an exception here");
        }
        catch (SwaggerValidationException e)
        {
        }
        s.info.contact.email = "fred@television.tv";
        Swagger.checkAllRequired(s);


    }
    public void testPathsRejectsBadPath() {
        Paths paths = new Paths();
        try {
            paths.put("test", new Path());
            fail("Something isn't working right; there should be an exception here");
        }
        catch (IllegalArgumentException e)
        {}
    }

    public void testPathValidator() throws SwaggerValidationException
    {
        Path dummyPath = new Path();
        dummyPath.checkRequired();
        dummyPath.ref = "apples";
        try {
            dummyPath.checkRequired();
            fail("Something isn't working right; there should be an exception here");
        }
        catch (SwaggerValidationException e)
        {
        }
        dummyPath.ref = "http://i.imgur.com/foWGjVK.gifv";
        dummyPath.checkRequired();
        dummyPath.parameters = new Parameter[1];
        dummyPath.parameters[0] = new Parameter();
        dummyPath.parameters[0].name = "This is the name of a parameter";
        dummyPath.parameters[0].in = Enums.Location.BODY;
        dummyPath.checkRequired();

        dummyPath.parameters = new Parameter[2];

        dummyPath.parameters[0] = new Parameter();
        dummyPath.parameters[0].name = "This is the name of a parameter";
        dummyPath.parameters[0].in = Enums.Location.BODY;

        dummyPath.parameters[1] = new Parameter();
        dummyPath.parameters[1].name = "This is the name of a parameter";
        dummyPath.parameters[1].in = Enums.Location.BODY;
        try {
            dummyPath.checkRequired();
            fail("Something isn't working right; there should be an exception here");
        }
        catch (SwaggerValidationException e)
        {}
    }

    public void testParameterValidator() throws SwaggerValidationException
    {
        Parameter p = new Parameter();
        try {
            p.checkRequired();
            fail("Something isn't working right; there should be an exception here");
        }
        catch (SwaggerValidationException e)
        {}
        p.name = "This is a test name";
        try {
            p.checkRequired();
            fail("Something isn't working right; there should be an exception here");
        }
        catch (SwaggerValidationException e)
        {}
        p.in = Enums.Location.BODY;
        try {
            p.checkRequired();
            fail("Something isn't working right; there should be an exception here");
        }
        catch (SwaggerValidationException e)
        {}
    }
}
