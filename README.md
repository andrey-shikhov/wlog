# ABOUT
Android Log wrapper with handy DSL(with extra features like line dividers) and control of message size, internal StringBuilder pool for memory pollution minimization 
_________________
# CURRENT VERSION
Release: ![Maven Central](https://img.shields.io/maven-central/v/me.shikhov/wlog)
Built with: ![Kotlin](https://img.shields.io/badge/kotlin-1.4.31-blue)
Requires kotlin stdlib 1.4.31
 
# Migration
For the sake of the smooth migration, there are all the methods of the original Log class such as 
`Log.d(tag, message,  throwable)` and so on.<br> So, for quick migration replace all `import android.util.Log;` to `import me.shikhov.wlog.Log;` and all must work as before.
But remember if a string concatenation is used in such methods it should be replaced with `append()` or 'a()' calls

# Installation
Library is in `mavenCentral` repository, so you only need to declare it in dependencies in your `build.gradle` for groovy or `build.gradle.kts` for kotlin dsl build scripts
```
// groovy
dependencies {
    implementation 'me.shikhov:wlog:3.0.0'
}

// kotlin
dependencies {
   implementation("me.shikhov:wlog:3.0.0")
}
```

# Usage examples
```
  WILL BE UPDATED SOON
```

# Change log
[See here](./changelog.md)

# History
When I thought about name,I decided to use log with one char prefix for simplicity.
Some goals were to not intersect with already existed log libraries and to find some cool abbreviation which has meaning.
I found it, and this is mathematical "without loss of generality", so I stopped here :)

# License

    Copyright 2021 Andrew Shikhov

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
