/**
 * Created by alutman on 21-Jan-15.
 */

package rerun.controller;

import jetbrains.buildServer.controllers.BaseFormXmlController;
import jetbrains.buildServer.serverSide.*;
import jetbrains.buildServer.users.SUser;
import jetbrains.buildServer.web.openapi.WebControllerManager;
import jetbrains.buildServer.web.util.SessionUser;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RerunTabController extends BaseFormXmlController {

    public RerunTabController(@NotNull SBuildServer server,
                              @NotNull WebControllerManager webControllerManager) {
        super(server);
        webControllerManager.registerController("/app/rerun", this);
    }

    @Override
    @Nullable
    protected ModelAndView doGet(@NotNull final HttpServletRequest request, @NotNull final HttpServletResponse response) {
        return null;
    }


    @Override
    protected void doPost(@NotNull final HttpServletRequest request, @NotNull final HttpServletResponse response, @NotNull final Element xmlResponse) {
        Long buildId = Long.parseLong(request.getParameter("buildId"));
        String returnUrl = request.getHeader("Referer");
        try {
            SBuildType buildType = myServer.findBuildInstanceById(buildId).getBuildType();


            SUser user = SessionUser.getUser(request);
            BuildCustomizerFactory factory = myServer.findSingletonService(BuildCustomizerFactory.class);
            BuildCustomizer customizer = factory.createBuildCustomizer(buildType, user);
            setParameters(customizer, request.getParameterValues("paramNames"), request.getParameterValues("paramValues"));

            SQueuedBuild sQueuedBuild = customizer.createPromotion().addToQueue(user.getUsername());
            returnUrl = request.getContextPath() + "/viewQueued.html?itemId=" + sQueuedBuild.getItemId();
            //TODO Send success or failure result
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            response.sendRedirect(returnUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setParameters(BuildCustomizer customizer, String[] names, String[] values) {
        if(isValidArray(names) && isValidArray(values)) {
            Map<String, String> newParams = createParameterMap(names, values);
            customizer.setParameters(newParams);
        }
    }
    private boolean isValidArray(Object[] arr) {
        return arr != null && arr.length > 0;
    }

    private Map<String, String> createParameterMap(String[] names, String[] values) {
        Map<String, String> paramMap = new HashMap<String, String>();
        for(int i = 0; i < names.length; i++) {
            paramMap.put(names[i], values[i]);
        }
        return paramMap;
    }
}