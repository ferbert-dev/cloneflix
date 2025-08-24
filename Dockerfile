FROM node:latest AS frontend_build
WORKDIR /frontend 
COPY /frontend/package*.json .
RUN npm ci
COPY /frontend/public ./public/
COPY /frontend/src ./src/
RUN npm run build

FROM maven:latest AS spring_build
COPY pom.xml .
COPY src ./src
COPY --from=frontend_build /frontend/build ./src/main/resources/static/
COPY --from=frontend_build /frontend/build/index.html ./src/main/resources/templates/
RUN ["mvn", "clean", "package", "-Dmaven.test.skip=true"]

FROM openjdk:11
COPY --from=spring_build /target/cloneflix-1.0.0.jar ./app.jar
COPY ./wait-for-it.sh ./
RUN chmod +x wait-for-it.sh
