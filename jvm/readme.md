# gradle配置全局maven库

* 在路径/gradle-5.5.1/init.d 下新建文件init.gradle
  写入内容
    
  buildscript {
      repositories {
          maven{ url 'http://maven.aliyun.com/nexus/content/groups/public/'}
      }
  }
   
  allprojects {
      repositories {
          maven{ url 'http://maven.aliyun.com/nexus/content/groups/public/'}
      }
  }
