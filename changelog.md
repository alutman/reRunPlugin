WIP
---

Unversioned
-----------

v1.6
----
* Fixed defect caused by v1.5 where rerun would simply use default values for params
* Hid rerun tab if the build is still in queue

v1.5
----
* Textarea height detection is all done in JS world now
* Permission checking checks on a project basis rather than global
* Redirect after run accounts for context path and url used to connect
* Fixed rerun with no parameters failing
* Textarea resize is done once on window load

v1.4
----
* Input area now autosizes
* Fixed certain characters not being escaped (`"` and `\n`)

v1.3
----
* Added editable parameters
* Parameters shown in the re-run tab match the build type params

v1.2
----
* Redirect to new build page on successful queueing of the re-run
* Tab only shows if the user can run builds
* New run shows the user who did it

v1.1
----
* Support context paths
* Fix re-run not picking up VCS changes

v1.0
----
* Initial release