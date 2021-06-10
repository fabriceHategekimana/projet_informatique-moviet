# Stage 0, "build-stage", based on Node.js, to build and compile the frontend
FROM node:12.8.1 as build-stage
WORKDIR /app
COPY package*.json /app/
# install npm 7:
RUN npm install -g npm@7
# fix error "An unhandled exception occurred: PostCSS plugin postcss-discard-comments requires PostCSS 8."
# RUN npm install postcss --save-dev
RUN npm install
RUN npm install -g @angular/cli@11.2.8
# RUN npm audit fix --force

COPY ./ /app/
# ARG configuration=production
# RUN npm run build -- --output-path=./dist/web-ui --configuration $configuration
RUN ng build --prod

CMD ng serve --host 0.0.0.0 --port 4200 --disable-host-check --prod

#docker build -t site:dev .