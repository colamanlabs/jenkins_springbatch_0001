
https://markdownlivepreview.com/

# 젠킨스 설치 및 연동 삽질기 1/2


"자바 프로젝트 필수 유틸리티" 8장 참고


젠킨스는 Java 버전에 의존성을 갖는다. \
책에서는 Java 9 를 쓰지 말고 8 로 하라고 가이드 하고 있다.


## 1. 설치절차

### 1.1. Java 버전 확인 
```
colaman@vm-dev-00:~$ java -version
openjdk version "17.0.9" 2023-10-17
OpenJDK Runtime Environment (build 17.0.9+9-Ubuntu-122.04)
OpenJDK 64-Bit Server VM (build 17.0.9+9-Ubuntu-122.04, mixed mode, sharing)
colaman@vm-dev-00:~$ 
```


### 1.2. Jenkins 다운로드

```
https://www.jenkins.io/
```

apt 로 검색하면 뭔가 많이 나오는데, 공홈에서 다운로드 받아 설치하자.


```
colaman@vm-dev-00:~$ apt search jenkins
정렬 중... 완료
전체 텍스트 검색... 완료
...

jenkins-debian-glue/jammy,jammy 0.21.0 all
  Jenkins Debian glue scripts

jenkins-debian-glue-buildenv/jammy,jammy 0.21.0 all
  Jenkins Debian glue scripts - dependency package

jenkins-job-builder/jammy,jammy 3.11.0-2 all
  Configure Jenkins using YAML files - metapackage

jenkins-job-builder-doc/jammy,jammy 3.11.0-2 all
  Configure Jenkins using YAML files - doc
...
colaman@vm-dev-00:~$ 
```


### 1.3. 공홈에서 다운로드


Stable (LTS) 버전으로 받는다.
리눅스 배포판을 확인한다.


```
colaman@vm-dev-00:~$ lsb_release -a
No LSB modules are available.
Distributor ID:	Ubuntu
Description:	Ubuntu 22.04.4 LTS
Release:	22.04
Codename:	jammy
colaman@vm-dev-00:~$ 
```

```
Download Jenkins 2.426.3 LTS for:
Generic Java package (.war)
SHA-256: ab439243a6a07e2e78fe7c3408c59609f7be3bf268947ac214657af96abad106 

```

우분투 선택한다.

https://pkg.jenkins.io/debian-stable/


**사전에 Java 환경이 설치되어 있어야 한다.**


```
sudo wget -O /usr/share/keyrings/jenkins-keyring.asc https://pkg.jenkins.io/debian-stable/jenkins.io-2023.key

echo deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc] https://pkg.jenkins.io/debian-stable binary/ | sudo tee /etc/apt/sources.list.d/jenkins.list > /dev/null

apt update

apt install fontconfig

apt install jenkins

```


**설치로그**
```
root@vm-dev-00:/home/colaman# 
root@vm-dev-00:/home/colaman# sudo wget -O /usr/share/keyrings/jenkins-keyring.asc https://pkg.jenkins.io/debian-stable/jenkins.io-2023.key
--2024-02-17 18:03:59--  https://pkg.jenkins.io/debian-stable/jenkins.io-2023.key
pkg.jenkins.io (pkg.jenkins.io) 해석 중... 146.75.50.133, 2a04:4e42:7c::645
다음으로 연결 중: pkg.jenkins.io (pkg.jenkins.io)|146.75.50.133|:443... 연결했습니다.
HTTP 요청을 보냈습니다. 응답 기다리는 중... 200 OK
길이: 3175 (3.1K) [application/pgp-keys]
저장 위치: ‘/usr/share/keyrings/jenkins-keyring.asc’

/usr/share/keyrings 100%[===================>]   3.10K  --.-KB/s    / 0s       

2024-02-17 18:04:01 (40.8 MB/s) - ‘/usr/share/keyrings/jenkins-keyring.asc’ 저장함 [3175/3175]

root@vm-dev-00:/home/colaman# 
root@vm-dev-00:/home/colaman# echo deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc] https://pkg.jenkins.io/debian-stable binary/ | sudo tee /etc/apt/sources.list.d/jenkins.list > /dev/null
root@vm-dev-00:/home/colaman# 
root@vm-dev-00:/home/colaman# 
root@vm-dev-00:/home/colaman# apt install jenkins
패키지 목록을 읽는 중입니다... 완료
의존성 트리를 만드는 중입니다... 완료
상태 정보를 읽는 중입니다... 완료        
다음 새 패키지를 설치할 것입니다:
  jenkins
0개 업그레이드, 1개 새로 설치, 0개 제거 및 9개 업그레이드 안 함.
89.0 M바이트 아카이브를 받아야 합니다.
이 작업 후 89.6 M바이트의 디스크 공간을 더 사용하게 됩니다.
받기:1 https://pkg.jenkins.io/debian-stable binary/ jenkins 2.426.3 [89.0 MB]
내려받기 89.0 M바이트, 소요시간 21분 15초 (69.8 k바이트/초)                    
Selecting previously unselected package jenkins.
(데이터베이스 읽는중 ...현재 220107개의 파일과 디렉터리가 설치되어 있습니다.)
Preparing to unpack .../jenkins_2.426.3_all.deb ...
Unpacking jenkins (2.426.3) ...
jenkins (2.426.3) 설정하는 중입니다 ...
sed: -e 표현식 1번째 행, 65번째 문자: 부적절한 대조 문자
Created symlink /etc/systemd/system/multi-user.target.wants/jenkins.service → /l
ib/systemd/system/jenkins.service.
root@vm-dev-00:/home/colaman# 
```


### 1.4. 설치 가이드(리눅스)

https://www.jenkins.io/doc/book/installing/



#### 젠킨스 서비스 등록관리
```
You can enable the Jenkins service to start at boot with the command:

sudo systemctl enable jenkins
You can start the Jenkins service with the command:

sudo systemctl start jenkins
You can check the status of the Jenkins service using the command:

sudo systemctl status jenkins
```


#### 젠킨스 서비스 상태 확인
```
root@vm-dev-00:/home/colaman# sudo systemctl status jenkins
● jenkins.service - Jenkins Continuous Integration Server
     Loaded: loaded (/lib/systemd/system/jenkins.service; enabled; vendor preset: enabled)
     Active: active (running) since Sat 2024-02-17 18:41:59 KST; 6min ago
   Main PID: 707 (java)
      Tasks: 48 (limit: 19091)
     Memory: 3.0G
        CPU: 38.303s
     CGroup: /system.slice/jenkins.service
             └─707 /usr/bin/java -Djava.awt.headless=true -jar /usr/share/java/jenkins.war --webroot=/var/cache/jenkins/war --httpPort=8080

 2월 17 18:41:28 vm-dev-00 jenkins[707]: Jenkins initial setup is required. An admin user has been created and a password generated.
 2월 17 18:41:28 vm-dev-00 jenkins[707]: Please use the following password to proceed to installation:
 2월 17 18:41:28 vm-dev-00 jenkins[707]: 55b47565a82c4b0f9f231693c4abd101
 2월 17 18:41:28 vm-dev-00 jenkins[707]: This may also be found at: /var/lib/jenkins/secrets/initialAdminPassword
 2월 17 18:41:28 vm-dev-00 jenkins[707]: *************************************************************
 2월 17 18:41:28 vm-dev-00 jenkins[707]: *************************************************************
 2월 17 18:41:28 vm-dev-00 jenkins[707]: *************************************************************
 2월 17 18:41:59 vm-dev-00 jenkins[707]: 2024-02-17 09:41:59.829+0000 [id=32]        INFO        jenkins.InitReactorRunner$1#onAttained: Completed initialization
 2월 17 18:41:59 vm-dev-00 jenkins[707]: 2024-02-17 09:41:59.860+0000 [id=25]        INFO        hudson.lifecycle.Lifecycle#onReady: Jenkins is fully up and running
 2월 17 18:41:59 vm-dev-00 systemd[1]: Started Jenkins Continuous Integration Server.
root@vm-dev-00:/home/colaman# 
```


#### 젠킨스 서비스 포트 변경
디폴트 포트가 8080 이다. 변경하자.

```
/lib/systemd/system/jenkins.service 파일에 정의한다.

기존 파일을 백업하고 
root@vm-dev-00:/lib/systemd/system# cp -p jenkins.service jenkins.service.20240217_191500
root@vm-dev-00:/lib/systemd/system# 


수정한다.
     63 # Port to listen on for HTTP requests. Set to -1 to disable.
     64 # To be able to listen on privileged ports (port numbers less than 1024),
     65 # add the CAP_NET_BIND_SERVICE capability to the AmbientCapabilities
     66 # directive below.
     67 Environment="JENKINS_PORT=8080"

젠킨스 서비스를 정지시키고 
root@vm-dev-00:/lib/systemd/system# systemctl stop jenkins
root@vm-dev-00:/lib/systemd/system# ps -ef | grep java
root        3245    2508  0 19:18 pts/1    00:00:00 grep --color=auto java
root@vm-dev-00:/lib/systemd/system# 
root@vm-dev-00:/lib/systemd/system# 

젠킨스 서비스를 다시 기동시키고
root@vm-dev-00:/lib/systemd/system# systemctl start jenkins

젠킨스 서비스를 확인한다.
root@vm-dev-00:/lib/systemd/system# 
root@vm-dev-00:/lib/systemd/system# ps -ef | grep java
jenkins     3248       1 87 19:18 ?        00:00:33 /usr/bin/java -Djava.awt.headless=true -jar /usr/share/java/jenkins.war --webroot=/var/cache/jenkins/war --httpPort=18080
root        3373    2508  0 19:19 pts/1    00:00:00 grep --color=auto java
root@vm-dev-00:/lib/systemd/system# 
root@vm-dev-00:/lib/systemd/system# netstat -an | grep 18080
tcp6       0      0 :::18080                :::*                    LISTEN     
root@vm-dev-00:/lib/systemd/system# 

```

#### 다 완료가 되면, 웹 브라우져로 확인한다.

```
http://localhost:18080
```

첫 화면
```
Unlock Jenkins
To ensure Jenkins is securely set up by the administrator, a password has been written to the log (not sure where to find it?) and this file on the server:

/var/lib/jenkins/secrets/initialAdminPassword

Please copy the password from either location and paste it below.
Administrator password

cat 으로 /var/lib/jenkins/secrets/initialAdminPassword 파일을 읽어 패스워드를 입력하면 넘어간다.

```


#### 이후 내용은 "자바프로젝트 필수유틸리티 p) 451 이후를 참고한다.
진행순서

```
1.
Customize Jenkins
- "Install sggested plugins" 선택

2.
플러그인 설치시 처음에는 부분 실패가 있었는데, "retry" 하니 두번째는 모두 성공하였고,
자동으로 Create First Admin User 화면으로 넘어갔다.

3. 
Create First Admin User
어드민 계정 생성을 위한 정보를 입력한다.

4.
Instance Configuration
Jenkins URL:	
http://localhost:18080/
The Jenkins URL is used to provide the root URL for absolute links to various Jenkins resources. That means this value is required for proper operation of many Jenkins features including email notifications, PR status updates, and the BUILD_URL environment variable provided to build steps.
The proposed default value shown is not saved yet and is generated from the current request, if possible. The best practice is to set this value to the URL that users are expected to use. This will avoid confusion when sharing or viewing links.

"Save and Finish" 버튼을 선택한다.

5.
Jenkins is ready!
Your Jenkins setup is complete.

"Start using Jenjins" 버튼을 선택한다.

```



### 2. 젠킨스 기본구성
1. 네비게이션 바 (상단 계정명 부분)

2. 메뉴바(좌측 메뉴 패널영역)

3. 작업공간(메뉴바 우측 패널)

4. 상태바(맨 아래 "REST API Jenkins 2.426.3" 부분)



### 3. 젠킨스 활용하기


#### 3.1 깃허브 프로젝트로 빌드시 깃허브 토큰을 요구하므로, 먼저 깃허브 토큰을 만든다.

참고 URL https://velog.io/@rungoat/CICD-Jenkins%EC%99%80-GitHub-%EC%97%B0%EB%8F%99%ED%95%98%EA%B8%B0


GitHub Repository에서 Token 발급
Settings -> Developer settings -> Personal access tokens -> Tokens (classic)


```
0. 깃허브 로그인 

1. 우상단 계정 마크 클릭

2. Settings

3. Developer settings

4. Personal access tokens -> Tokens (classic)

5. 프로젝트 선택 

6. repo, admin:org, admin:repo_hook 을 선택

7. "Update token" 선택(발급 받은 token은 다시 볼 수 없기에 따로 저장해둔다.)

8. 상단 "Regenerate personal access token (classic)" 선택

9. Expiration 지정
```


#### 3.2 첫 잡 만들어보기

```
1. 
"+ 새로운 item" 선택

2.
아이템 명에 적당한 이름을 넣는다.
ex) First Job (책 따라함)

이름을 넣고, "Freestyle project" 를 선택한다.

아래 "OK" 버튼을 선택한다.
General 페이지로 전환된다.

3. General
깃허브 소스 빌드를 가정한다.


- 설명 
  - 특이사항 없음
  
- 소스코드 관리
  - "Git" 선택
    - "Repository URL" 에 깃허브 URL 입력
    https://github.com/colamanlabs/SPRINGBOOT_STUDY/tree/main/springboot_study_0001
	https://github.com/colamanlabs/SPRINGBOOT_STUDY.git
	apt install git 으로 사전 git 설치 필요

  - 크리덴셜 옆 add 버튼 선택
  - treat username as secret 체크
  - username 은 깃허브 ID
  - PWD 는 깃허브에서 받은 토큰
  - ID 는 "github-token" 입력
  - Description 은 비운다.
  

```

** 젠킨스에서 git 명령어를 사용하므로 git 이 먼저 설치 되어 있어야 한다. **
```
Failed to connect to repository : Error performing git command: git ls-remote -h https://github.com/colamanlabs/SPRINGBOOT_STUDY.git HEAD
```
에러났다.

콘솔창에서도 안된다. 
```
git ls-remote -h https://github.com/colamanlabs/SPRINGBOOT_STUDY.git HEAD
git 설치한다
apt install git
```

build now 했는데 실패했다.

리포지토리 URL 에는 "https://github.com/colamanlabs/SPRINGBOOT_STUDY.git" 로 하면 넘어가나,\
현재 프로젝트 구조상 각 파일이 하위에 있어 빌드 파일이 없고,

실제 경로인 "https://github.com/colamanlabs/SPRINGBOOT_STUDY/tree/main/springboot_study_0001" 로 하면\
젠킨스가 찾지 못한다.



```
콘솔 출력
Started by user colaman Mason
Running as SYSTEM
Building in workspace /var/lib/jenkins/workspace/First Job
The recommended git tool is: NONE
No credentials specified
 > git rev-parse --resolve-git-dir /var/lib/jenkins/workspace/First Job/.git # timeout=10
Fetching changes from the remote Git repository
 > git config remote.origin.url https://github.com/colamanlabs/SPRINGBOOT_STUDY.git # timeout=10
Fetching upstream changes from https://github.com/colamanlabs/SPRINGBOOT_STUDY.git
 > git --version # timeout=10
 > git --version # 'git version 2.34.1'
 > git fetch --tags --force --progress -- https://github.com/colamanlabs/SPRINGBOOT_STUDY.git +refs/heads/*:refs/remotes/origin/* # timeout=10
 > git rev-parse refs/remotes/origin/master^{commit} # timeout=10
 > git rev-parse origin/master^{commit} # timeout=10
ERROR: Couldn't find any revision to build. Verify the repository and branch configuration for this job.
Finished: FAILURE
```


**결국 깃허브에 최상위 루트로 빌드되는 프로젝트를 만들어야 한다.**





# 젠킨스 설치 및 연동 삽질기 2/2


젠킨스 연동 삽질기 1 결과, 최종 연동 까지는 추가 작업이 필요함을 알았다.


```
TO-DO 1)
깃허브 에 빌드대상 프로젝트의 리포지토리를 만들어야 한다.


TO-DO 2)
해당 리포지토리의 프로젝트 소스를 구현해야 한다.
구현을 마치면 해당 리포지토리에 push 해야 한다.


TO-DO 3)
젠킨스에 설정해야 한다.
```


## 1. 깃허브 에 빌드대상 프로젝트의 리포지토리 생성
깃허브에 새 리포지토리를 만들었다.

```
https://github.com/colamanlabs/jenkins_springbatch_0001.git
```

## 2. 프로젝트 소스 구현
깃허브와 젠킨스 연동 확인이 목적이므로, STS 가 만든 최초 기본 소스를 그대로 활용한다.\
깃허브에 소스를 push 한다.


## 3. 젠킨스 설정하기
깃허브 소스를 가져오도록 하는 설정(리포지토리 설정)을 적용하고,
메이븐으로 clean, package 하도록 설정한다.



