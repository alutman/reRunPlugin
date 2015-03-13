TeamCity ReRun Plugin
=====================

This is a teamcity plugin that allows you to re run previous builds with the same parameters.

Setup
-----
A few TeamCity libraries are needed to develop this plugin. Install TeamCity locally and specify the install path in
`build.properties`. This should auto resolve the libraries location (including source and documentation).
Otherwise you can copy the libraries manually from another TeamCity install and import them yourself.

The Following paths are taken from `$TEAMCITY_HOME`

#### Agent API ####
* devPackage/agent-api.jar

#### Common API ####
* devPackage/common-api.jar
* devPackage/runtime/runtime-util.jar

#### Server API ####
* devPackage/server-api.jar

#### Third Party API (Spring etc.) ####
* webapps/ROOT/WEB-INF/lib/annotations.jar
* webapps/ROOT/WEB-INF/lib/jdom.jar
* webapps/ROOT/WEB-INF/lib/openapi.jar
* webapps/ROOT/WEB-INF/lib/spring-webmvc.jar
* webapps/ROOT/WEB-INF/lib/spring.jar
* webapps/ROOT/WEB-INF/lib/util.jar

##### Tomcat #####
* lib/servlet-api.jar

#### Docs/Source ####
* devPackage/src/openApi-source.jar
* devPackage/javadoc/openApi-help.jar


You may need to set your path variable 'TeamCityDistribution' in IntelliJ's settings to wherever you have TC installed

Deploy
------
Run the Ant task 'dist'. This will produce a zip file under `dist/`. Copy this zip file into your TeamCity's plugin directory
(`$TEAMCITY_DATA_PATH/plugins`) and restart TeamCity to install the plugin.


Notes
-----
Spring does a bit of magic when it comes to the beans (`src/META-INF/build-server-plugin-rerun.xml`)

* Omitted constructor arguments will attempt to be autowired. This works for some arguments but not others, have fun working it out
* Accessing includeUrl files use a path of format `/plugins/reRunPlugin/rerun.jsp` where `rerun.jsp` is actually located under
`resources/buildServerResources/rerun.jsp`
* Setting up resource paths that don't end with `.html` must begin with `/app` in both the controller and references to it.
* Controllers handle the server's context path behind the scene but references to that path will need to factor it in.
Use `${pageContext.request.contextPath}` before your controller's path to accommodate for it
(e.g `action="${pageContext.request.contextPath}/app/rerun"` where the controller path is `/app/rerun`)
* The current user can be accessed via `SessionUser.getUser(request)` where `request` is a `HttpServletRequest`

Todo
----
* Show a notification if a run started successfully

Links
-----
* [Developing TeamCity Plugins](https://confluence.jetbrains.com/display/TCD8/Developing+TeamCity+Plugins)
* [Web UI Extensions for TeamCity](https://confluence.jetbrains.com/display/TCD8/Web+UI+Extensions)