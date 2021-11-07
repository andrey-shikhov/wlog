# Version history
## 3.5.0 07/11/2021
'*' updated dependencies, gradle migration to version catalogs, kotlin 1.5.30
'*' a lot of optimizations for Logger instance used in the main thread
'*' CheckResult annotations for some methods to help to not forget to use created Log instance
'*' internal StringJuggler was rewritten to be quicker and unnecessary operations were removed
'*' deprecated Log[TAG] to unify with new lob builder block, added method Log(TAG)

## 2.3.1
Date 07/06/2020
'-' removed append methods in favor of short a methods
'+' added support for primitive arrays and objects in log block
'+' added handy way to log properties in format name=value
'+' added line method in log block to insert horizontal line with chars
'-' removed proguard rules

## 2.0.0
Date 05/03/2020
'+' migrated to kotlin language
'+' special handling of UI thread Logger for speedup
'+' new way to create log using dsl builder
'-' removed logLevel modification methods in favor of parameter of release method  

## 1.3.1
Date 02/02/2016
'+' handling of primitive arrays logging
'+' SequenceFormatter improved interface and now publicly accessible for any purposes

## 1.2.4
Date 01/09/2015  
'+' Added ```Log.newLine()``` or ```Log.nl()``` methods which appends '\n' to the log. This string can be configured by property ```"wlog.newLine"```<br>
'+' Added support for logging java collections and as special case - maps(output will be in format ```[{'key'='value'],['key'='value'])```<br>
'+' improved documentation<br>
'#' fixed array output formatter(first item suffix and last item prefix were not added to output)<br>
'#' rewritten build file to remove dependency on network scripts and fix javadoc errors<br>

## 1.1.0
Date 13/07/2015<br>
'+' default logLevel can be configured with `System.setProperty("wlog.logLevel", "verbose|debug|info|error")`<br>
'+' added method `a(T[],ArrayFormatter)` to log arrays with custom formatter<br>
'+' ArrayFormatter: possibility to set custom prefix for first item in array<br>
'*' static methods, for example `Log.i(TAG,MESSAGE, THROWABLE)` now are just shortcuts to 'Log.get(tag).append(message).throwable(throwable).info().release()'<br>

## 1.0.0
Date: 31/07/2015  
'+' Initial version, basic functionality, build process setup for upload to jcenter and mavenCentral<br>

## Legend
'+' new feature<br>
'-' removed feature<br>
'*' changed behavior<br>
'!' possible compatibility break<br>
'#' fixed bug<br>
<br>  
Date format: dd/mm/yyyy  