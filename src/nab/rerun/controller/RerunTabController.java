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

package nab.rerun.controller;

import jetbrains.buildServer.controllers.BaseFormXmlController;
import jetbrains.buildServer.serverSide.SBuildServer;
import jetbrains.buildServer.web.openapi.WebControllerManager;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        Long buildId = Long.parseLong(request.getParameter("promoid"));
        try {
            myServer.findBuildInstanceById(buildId).getBuildPromotion().copy(true).addToQueue("automated");
            //TODO Send success or failure result
        } catch(Exception e) {
            e.printStackTrace();
        }
        try {
            response.sendRedirect(request.getHeader("Referer"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}