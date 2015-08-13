# Version history

## 1.1.0
Date 13/07/2015
'+' default logLevel can be configured with `System.setProperty("wlog.logLevel", "verbose|debug|info|error")`
'+' added method `a(T[],ArrayFormatter)` to log arrays with custom formatter
'+' ArrayFormatter: possibility to set custom prefix for first item in array
'*' static methods, for example `Log.i(TAG,MESSAGE, THROWABLE)` now are just shortcuts to 'Log.get(tag).append(message).throwable(throwable).info().release()'


## 1.0.0
Date: 31/07/2015
'+' Initial version, basic functionality, build process setup for upload to jcenter and mavenCentral
---
## Legend:
'+' new feature
'-' removed feature
'*' changed behavior
'!' possible compatibility break
'#' fixed bug