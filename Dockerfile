# 베이스 이미지
FROM amazoncorretto:21
# 작성자 라벨 생성
#LABEL maintainer "NLGZ<edas96@naver.com>"
LABEL maintainer "seungbeom4382<jsd940@naver.com>"
# 버전 라벨 생성
LABEL version "1.0.0"
# build시에만 사용할 변수 선언
ARG JAR_FILE_PATH=build/libs/*.jar
ARG PROD_DB_PASSWORD
ARG PROD_DB_URL
ARG PROD_DB_USERNAME
ARG PROD_REDIS_HOST
ARG PROD_REDIS_PORT
ARG PROD_REDIS_PASSWORD
# root 디렉토리에 app.jar복사
COPY ${JAR_FILE_PATH} app.jar
# 지파일을 루트하위에 복사
COPY wallets/Wallet_VLAOHSDEXLPS1JCQ /Wallet_VLAOHSDEXLPS1JCQ
# docker 이미지에 저장할 환경변수
ENV PROD_DB_PASSWORD=${PROD_DB_PASSWORD}
ENV PROD_DB_URL=${PROD_DB_URL}
ENV PROD_DB_USERNAME=${PROD_DB_USERNAME}
ENV PROD_REDIS_HOST=${PROD_REDIS_HOST}
ENV PROD_REDIS_PORT=${PROD_REDIS_PORT}
ENV PROD_REDIS_PASSWORD=${PROD_REDIS_PASSWORD}
# 컨테이너 실행 시 app.jar 자동 실행 (프로필 설정)
ENTRYPOINT ["java", "-jar", "/app.jar"]