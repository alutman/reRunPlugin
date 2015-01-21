/*
 * Copyright 2000-2015 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nab.rerun.extension;

import jetbrains.buildServer.serverSide.BuildPromotion;
import jetbrains.buildServer.serverSide.SBuildServer;
import jetbrains.buildServer.web.openapi.PagePlaces;
import jetbrains.buildServer.web.openapi.ViewBuildTab;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;

public class RerunTabExtension extends ViewBuildTab {

    public RerunTabExtension(@NotNull String title, @NotNull String code, @NotNull PagePlaces pagePlaces, @NotNull String includeUrl, @NotNull SBuildServer server) {
        super(title, code, pagePlaces, includeUrl, server);
    }

    @Override
    protected void fillModel(@NotNull Map<String, Object> model, @NotNull HttpServletRequest request, @NotNull BuildPromotion promotion) {
        ArrayList<String> messages = new ArrayList<String>();
        ArrayList<Map.Entry<String, String>> parameters = new ArrayList<Map.Entry<String, String>>();
        model.put("buildId", promotion.getAssociatedBuildId());

        try {
            for (Map.Entry<String, String> entry : promotion.getParameters().entrySet()) {
                parameters.add(entry);
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
        //TODO Check user can actually run builds
        return true;
    }


}
