# ๐ก Moodpic Backend

> ๋๋ง์ ๊ฐ์  ๊ธฐ๋ก ๋ณด๊ด์, moodpic
๋น์ ์ ํด์ํ๊ณ  ์ถ์ ๊ฐ์ ๋ค์ ๊ธฐ๋กํ๊ณ  ๊ณต์ ํด๋ณด์ธ์. ๋ง์์ด ํ๊ฒฐ ํธ์ํด์ง๊ฑฐ์์! ๐

<br/>
<img width="1016" alt="แแณแแณแแตแซแแฃแบ 2022-06-29 แแฉแแฎ 9 50 31" src="https://user-images.githubusercontent.com/29244798/176440486-a98d6aad-b62b-4246-9253-e03ac5cc21e3.png">

## <b>[๋ฌด๋ํฝ ์ ์ํ๊ธฐ โ๏ธ](https://www.moodpic.kr/)</b>

<details><summary><b>์๋ฒ ์คํ ๋ฐฉ๋ฒ</b></summary>

```bash
$  ./gradlew :applications:app-demo:bootRun -PkakaoClientId="{์ ๊ฑฐ}" -PkakaoRedirectUrl="{๊ทธ๊ฑฐ}"
```

</details>
<details><summary><b>api ๋ช์ธ์</b></summary>
- https://www.api.moodpic.kr/docs/index.html
</details>

<hr>

## ๐ก Tech Stacks

### โ๏ธ Development Tools

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

### โ๏ธ Infra

<div align="center">
<img src="https://img.shields.io/badge/AWS-ECS-232F3E?logo=AWS"/> <img src="https://img.shields.io/badge/AWS-EC2-232F3E?logo=AWS"/>
<img src="https://img.shields.io/badge/AWS-S3-232F3E?logo=aws"/> <img src="https://img.shields.io/badge/AWS-RDS-232F3E?logo=AWS"/>
<img src="https://img.shields.io/badge/AWS-ELB-232F3E?logo=AWS"/> <img src="https://img.shields.io/badge/AWS-Route53-232F3E?logo=AWS"/>
<img src="https://img.shields.io/badge/GithubActions-CI/CD-232F3E?logo=githubactions"/>
</div>
<br/>

<hr>

## ๐ก Feature

<img width="1045" alt="แแณแแณแแตแซแแฃแบ 2022-06-29 แแฉแแฎ 9 51 47" src="https://user-images.githubusercontent.com/29244798/176440677-63e21223-0592-4140-8137-f9530be079b1.png">

``` 
 1. ๊ฐ์ ์ ์ ํํ์ฌ ๊ธ ๊ธฐ๋กํ๊ธฐ
 2. ํด๋๋ณ/๊ฐ์ ๋ณ๋ก ๊ธ ํ์ธํ๊ธฐ
 3. ๋ค๋ฅธ ์ฌ๋์ ๊ธ ๋๋ฌ๋ณด๊ธฐ
 4. ํธ์ง๋ก ๊ฐ์  ๊ณต์ ํ๊ธฐ 
 ```
<br/>
<hr>

## ๐ก Contributors

| ```Lead``` ์ด ๊ฑด | ๋ฐ์ํธ | ์ํ์ฑ | ์ด ์ |
| :-: |  :-: | :-: | :-: |
| [@zkdlu](https://github.com/zkdlu)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |  [@grand7070u](https://github.com/grand7070u)  | [@rere950303](https://github.com/rere950303)  |  [@soleu](https://github.com/soleu)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |
| <img src="https://user-images.githubusercontent.com/76844556/176010120-a2f4aded-3098-42c7-a5b5-70ea73518294.png" width=100%> | <img src="https://user-images.githubusercontent.com/76844556/176010323-31729511-f080-448a-98c7-3415c6f8469a.png" width=100%> | <img src="https://user-images.githubusercontent.com/76844556/176010379-cae874e1-b2df-42a8-a87b-85d825012d11.png" width=100%> | <img src="https://user-images.githubusercontent.com/76844556/176010399-5b89fe77-1417-46b1-9d66-7d4bba4b7cb9.png" width=100%> |

<br/>

## ๐ก Front-end Repository

https://github.com/depromeet/11th_5team_fe

<hr/>

## ๐ก Server Architecture

<img width="2490" alt="Frame 24225" src="https://user-images.githubusercontent.com/76844556/176011081-18697c24-f7d3-41a5-a228-5c9ad5d24536.png">

<br/>
<hr/>

## ๐ก Database Schema

![5gzoo](https://user-images.githubusercontent.com/76844556/176010880-6d519336-bb06-458e-aed2-412874f19e2a.png)

<br/>
<hr/>

## ๐ก Project Foldering

```
๐ 11th_5team
    ๐ applications
        ๐ app-demo
    ๐ components
        ๐ category
        ๐ member
        ๐ module-category
        ๐ module-folder
        ๐ module-member
        ๐ module-member
        ๐ module-oauth
        ๐ module-post-folder
        ๐ module-posts
        ๐ module-search      
        ๐ module-sharing
        ๐ postEvent
        ๐ support-feign
        ๐ support-jpa      
        ๐ support-jwt
        ๐ support-security
    ๐ gradle
    - build.gradle
    - Dockerfile
    - settings.gradle
```
<br/>
<hr>

## ๐ก Module dependencies

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
