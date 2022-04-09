# 11th_5team

## Quick Start

```
git clone https://github.com/depromeet/11th_5team
$ cd 11th_5team/applications/app-front
$ npm i
$ npm run dev
```


## 데모 서버 실행 방법

### api document

```bash
$ ./gradlew :applications:app-demo:bootJar
```

### 서버 실행

```bash
$  ./gradlew :applications:app-demo:bootRun -PkakaoClientId="{저거}" -PkakaoRedirectUrl="{그거}"
```

### api 문서

- http://localhost:8080/docs/index.html