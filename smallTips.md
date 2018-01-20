---
title:Hiahiahia~
---

## Android Studio中Make Project，Clean Project，Rebuild Project区别
1. Make Project：编译Project下所有Module，一般是自上次编译后Project下有更新的文件，不生成apk。
2. Make Selected Modules：编译指定的Module，一般是自上次编译后Module下有更新的文件，不生成apk。
3. Clean Project：删除之前编译后的编译文件，并重新编译整个Project，比较花费时间，不生成apk。
4. Rebuild Project：先执行Clean操作，删除之前编译的编译文件和可执行文件，然后重新编译新的编译文件，不生成apk，这里效果其实跟Clean Project是一致的，这个不知道Google搞什么鬼～～
5. Build APK：前面4个选项都是编译，没有生成apk文件，如果想生成apk，需要点击Build APK。
6. Generate Signed APK：生成有签名的apk


vysor

记得分包：activity(活动类),utils(工具类),base(基类),fragment,bean(实体类),da/database(数据库)
