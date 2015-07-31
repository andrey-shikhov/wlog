[ ![Download](https://api.bintray.com/packages/andrey-shikhov/maven/wlog/images/download.svg) ](https://bintray.com/andrey-shikhov/maven/wlog/_latestVersion)

# wlog
Ultimate replacement for android android.util.Log class, no proguard leftovers, convenient way to log basic types, arrays, exceptions

# Why to use wlog instead of built in log?
* No proguard leftovers(one rule to follow - no string concatenation, but using built in append() or a() methods)
* decreased garbage generation due to reuse of StringBuilder objects which are stored in pool
* excellent support of primitive types(for example, methods to append number in hexademical format)
* customizable output of arrays and java collections
* possibility to write log into file or any user defined LogWriter
* no StringBuilders required to log complex data structures which requires iteration
 
# Migration
For the sake of the smooth migration, there are all the methods of the original Log class such as 
`Log.d(tag, message,  throwable)` 
and so on. So, for quick migration replace all `import android.util.Log;` to `import me.shikhov.wlog.Log;` and all must work as before. But remember this is just legacy methods and they must be replaced with more appropriate ones.

# Installation
Library is in `jcenter` and `mavenCentral` repositories, so you only need to declare it in dependencies in your `build.gradle`
```
dependencies {
    compile 'me.shikhov:wlog:1.0.0'
}
```

# Intoduction
1. Log object reference can be received by calling `Log.get("TAG")`
2. Log object can be recycled by calling `release()` or `r()` method
3. Log object can't be used after `release()` call, this will produce IllegalStateException
4. For most types use overloaded `append()` method, or `a()` to decrease length of statement.
5. Call `flush()` or `f()` method to flush data to the log output(default is logcat)
6. `flush()` is not mandatory if `release()` method called.

# Usage examples
```
int value = 10;
int[] array = new int[] {1,2,3,4,5,6,7,8,9,10}
Log.get("TEST").a(value).a(",").a(array).release();
```
# Change log
[See here](./changelog.md)

# History
When I thought about name,I decided to use log with one char prefix for simplicity.
Some goals were to not intersect with already existed log libraries and to find some cool abbreviation which has meaning.
I found it, and this is mathematical "without loss of generality", so I stopped here :)