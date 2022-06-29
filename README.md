# 🌡 Moodpic Backend

> 나만의 감정 기록 보관소, moodpic
당신의 해소하고 싶은 감정들을 기록하고 공유해보세요. 마음이 한결 편안해질거예요! 📝

<br/>
<img width="1016" alt="스크린샷 2022-06-29 오후 9 50 31" src="https://user-images.githubusercontent.com/29244798/176440486-a98d6aad-b62b-4246-9253-e03ac5cc21e3.png">

## <b>[무드픽 접속하기 ✏️](https://www.moodpic.kr/)</b>

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

<img width="1045" alt="스크린샷 2022-06-29 오후 9 51 47" src="https://user-images.githubusercontent.com/29244798/176440677-63e21223-0592-4140-8137-f9530be079b1.png">

``` 
 1. 감정을 선택하여 글 기록하기
 2. 폴더별/감정별로 글 확인하기
 3. 다른 사람의 글 둘러보기
 4. 편지로 감정 공유하기 
 ```
<br/>
<hr>

## 🌡 Contributors

| ```Lead``` 이 건 | 박수호 | 양형욱 | 이솔 |
| :-: |  :-: | :-: | :-: |
| [@zkdlu](https://github.com/zkdlu) |  [@grand7070u](https://github.com/grand7070u)  | [@rere950303](https://github.com/rere950303)  |  [@soleu](https://github.com/soleu) |
| <img src="https://user-images.githubusercontent.com/76844556/176010120-a2f4aded-3098-42c7-a5b5-70ea73518294.png" width="250"> | <img src="https://user-images.githubusercontent.com/76844556/176010323-31729511-f080-448a-98c7-3415c6f8469a.png" width="250"> | <img src="https://user-images.githubusercontent.com/76844556/176010379-cae874e1-b2df-42a8-a87b-85d825012d11.png" width="250"> | <img src="https://user-images.githubusercontent.com/76844556/176010399-5b89fe77-1417-46b1-9d66-7d4bba4b7cb9.png" width="250"> |

<br/>

## 🌡 Front-end Repository

https://github.com/depromeet/11th_5team_fe

<hr/>

## 🌡 Server Architecture

<img width="2490" alt="Frame 24225" src="https://user-images.githubusercontent.com/76844556/176011081-18697c24-f7d3-41a5-a228-5c9ad5d24536.png">

<br/>
<hr/>

## 🌡 Database Schema

![5gzoo](https://user-images.githubusercontent.com/76844556/176010880-6d519336-bb06-458e-aed2-412874f19e2a.png)

<br/>
<hr/>

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
<br/>
<hr>

## 🌡 Module dependencies

```java
dependencies {
    implementation project(':components:module-oauth')
    implementation project(':components:support-security')
    implementation project(':components:module-member')
    implementation project(':components:module-folder')
    implementation project(':components:module-posts')
    implementation project(':components:module-post-folder')
    implementation project(':components:module-search')
    implementation project(':components:support-jwt')
    implementation project(':components:support-jpa')
    implementation project(':components:module-category')
    implementation project(':components:module-sharing')
    implementation project(':components:member')

    asciidoctorExt 'org.springframework.restdocs:spring-restdocs-asciidoctor'
    testImplementation 'org.springframework.restdocs:spring-restdocs-mockmvc'

    implementation 'com.github.zkdlu:api-response-spring-boot-starter:1.0.8'

    runtimeOnly 'com.h2database:h2'

    runtimeOnly 'mysql:mysql-connector-java'
    implementation 'mysql:mysql-connector-java'
}
```
