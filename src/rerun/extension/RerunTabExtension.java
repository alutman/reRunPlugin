/**
 * Created by alutman on 21-Jan-15.
 */
package rerun.extension;

import jetbrains.buildServer.serverSide.BuildPromotion;
import jetbrains.buildServer.serverSide.SBuildServer;
import jetbrains.buildServer.serverSide.auth.Permission;
import jetbrains.buildServer.users.SUser;
import jetbrains.buildServer.web.openapi.PagePlaces;
import jetbrains.buildServer.web.openapi.ViewBuildTab;
import jetbrains.buildServer.web.util.SessionUser;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class RerunTabExtension extends ViewBuildTab {

    public RerunTabExtension(@NotNull String title, @NotNull String code, @NotNull PagePlaces pagePlaces, @NotNull String includeUrl, @NotNull SBuildServer server) {
        super(title, code, pagePlaces, includeUrl, server);
    }

    @Override
    protected void fillModel(@NotNull Map<String, Object> model, @NotNull HttpServletRequest request, @NotNull BuildPromotion promotion) {
        ArrayList<String> messages = new ArrayList<String>();
        ArrayList<ReRunParameter> parameters = new ArrayList<ReRunParameter>();
        model.put("buildId", promotion.getAssociatedBuildId());

        try {
            for (Map.Entry<String, String> entry : buildParams(promotion.getBuildType().getParameters(), promotion.getParameters()).entrySet()) {
                parameters.add(new ReRunParameter(entry.getKey(), entry.getValue()));
            }
        } catch (Exception e) {
            messages.add("Exception " + e.getClass() + " : " + e.getMessage());
        } finally {
            model.put("parameters", parameters);
            model.put("messages", messages);
        }
    }

    @Override
    public boolean isAvailable(@NotNull HttpServletRequest request, @NotNull BuildPromotion promotion) {
        SUser user = SessionUser.getUser(request);
        return user.getPermissionsGrantedForProject(promotion.getBuildType().getProjectId()).contains(Permission.RUN_BUILD);
//        return user.getGlobalPermissions().contains(Permission.RUN_BUILD);
    }

    //Return only parameters that exist in the build type. Use re-run values over defaults if possible.
    private Map<String, String> buildParams (Map<String, String> defaultParams, Map<String, String> reRunParams){
        Map<String, String> newMap = new HashMap<String, String>(defaultParams);
        Iterator<Map.Entry<String, String>> iterator = reRunParams.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            if(newMap.containsKey(next.getKey())) {
                newMap.put(HtmlUtils.htmlEscape(next.getKey()), HtmlUtils.htmlEscape(next.getValue()));
            }
        }
        return newMap;
    }
}
