# 🌡 Moodpic Backend

> 그에 대한 해소 방안을 강구할 수 있는 문답형식의 감정기록 서비스 📝

<br/>
 <메인 컨셉 이미지>

<br/>

<b>[무드픽 접속하기 ✏️](https://www.moodpic.kr/)</b>

<details><summary><b>서버 실행 방법</b></summary>

```bash
$  ./gradlew :applications:app-demo:bootRun -PkakaoClientId="{저거}" -PkakaoRedirectUrl="{그거}"
```

</details>
<details><summary><b>api 명세서</b></summary>
- https://www.api.moodpic.kr/docs/index.html
</details>

<hr>

## 🌡 Tech Stacks

### ⚒️ Development Tools

<br/>

<div align="center">
 <img src="https://img.shields.io/badge/Java-11-F7DF1E?logo=java"/>
<img src="https://img.shields.io/badge/Spring Boot-v2.6.3-43853D?logo=spring"/> 
<img src="https://img.shields.io/badge/Spring Data JPA-43853D?logo=spring"/> 
  <img src="https://img.shields.io/badge/Spring Batch-43853D?logo=spring"/> 
</br>
<img src="https://img.shields.io/badge/gradle-232F3E?logo=gradle"/>
<img src="https://img.shields.io/badge/Junit5-232F3E?logo=Junit5"/>
<img src="https://img.shields.io/badge/MySQL-v8.0-316192?logo=mysql"/>
</div>
<br/>

### ⚒️ Infra

<div align="center">
<img src="https://img.shields.io/badge/AWS-ECS-232F3E?logo=AWS"/> <img src="https://img.shields.io/badge/AWS-EC2-232F3E?logo=AWS"/>
<img src="https://img.shields.io/badge/AWS-S3-232F3E?logo=aws"/> <img src="https://img.shields.io/badge/AWS-RDS-232F3E?logo=AWS"/>
<img src="https://img.shields.io/badge/AWS-ELB-232F3E?logo=AWS"/> <img src="https://img.shields.io/badge/AWS-Route53-232F3E?logo=AWS"/>
<img src="https://img.shields.io/badge/GithubActions-CI/CD-232F3E?logo=githubactions"/>
</div>
<br/>

<hr>

## 🌡 Feature

<스크린 샷 이미지> 및 설명

<details><summary>1. 가가가</summary>설명 1</details>
<details><summary>2. 나나나</summary>설명 2</details>
<details><summary>3. 다다다</summary>설명 3</details>

<hr>
<br/>

## 🌡 Contributors

| ```Lead``` 이 건 | 박수호 | 양형욱 | 이솔 |
| :-: |  :-: | :-: | :-: |
| [@zkdlu](https://github.com/zkdlu) |  [@grand7070u](https://github.com/grand7070u)  | [@rere950303](https://github.com/rere950303)  |  [@soleu](https://github.com/soleu) |
| <img src="https://user-images.githubusercontent.com/76844556/176010120-a2f4aded-3098-42c7-a5b5-70ea73518294.png" width="250"> | <img src="https://user-images.githubusercontent.com/76844556/176010323-31729511-f080-448a-98c7-3415c6f8469a.png" width="250"> | <img src="https://user-images.githubusercontent.com/76844556/176010379-cae874e1-b2df-42a8-a87b-85d825012d11.png" width="250"> | <img src="https://user-images.githubusercontent.com/76844556/176010399-5b89fe77-1417-46b1-9d66-7d4bba4b7cb9.png" width="250"> |

<br/>
<hr/>
<br/>

## 🌡 Server Architecture

<img width="2490" alt="Frame 24225" src="https://user-images.githubusercontent.com/76844556/176011081-18697c24-f7d3-41a5-a228-5c9ad5d24536.png">


<hr/>
<br/>

## 🌡 Database Architecture

![5gzoo](https://user-images.githubusercontent.com/76844556/176010880-6d519336-bb06-458e-aed2-412874f19e2a.png)


<hr/>
<br/>

## 🌡 Project Foldering

```
🗂 11th_5team
    🗂 applications
        🗂 app-demo
    🗂 components
        🗂 category
        🗂 member
        🗂 module-category
        🗂 module-folder
        🗂 module-member
        🗂 module-member
        🗂 module-oauth
        🗂 module-post-folder
        🗂 module-posts
        🗂 module-search      
        🗂 module-sharing
        🗂 postEvent
        🗂 support-feign
        🗂 support-jpa      
        🗂 support-jwt
        🗂 support-security
    🗂 gradle
    - build.gradle
    - Dockerfile
    - settings.gradle
```

<hr>
</br>

## 🌡 Module dependencies

```java
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath "org.springframework.boot:spring-boot-gradle-plugin:2.6.3"
    }
}

subprojects {
    apply plugin: 'java-library'
    apply plugin: 'org.springframework.boot'
    apply plugin: 'io.spring.dependency-management'

    group = 'depromeet.ohgzoo'
    version = '0.0.1-SNAPSHOT'
    sourceCompatibility = '11'

    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }

    dependencies {
        implementation platform('org.springframework.cloud:spring-cloud-dependencies:2021.0.1')
        implementation 'org.springframework.boot:spring-boot-starter-web'
        implementation 'org.springframework.boot:spring-boot-starter'
        implementation group: 'org.springframework.boot', name: 'spring-boot-starter-validation', version: '2.5.2'
        testImplementation 'org.springframework.boot:spring-boot-starter-test'
        compileOnly 'org.projectlombok:lombok'
        annotationProcessor 'org.projectlombok:lombok'
    }

    test {
        useJUnitPlatform()
    }

    if (!project.name.contains("app-")) {
        bootJar { enabled = false }
    }
}
```
