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

# Stage 1, based on Nginx, to have only the compiled app, ready for production with Nginx
FROM nginx:1.18
#Copy ci-dashboard-dist
COPY --from=build-stage /app/dist/web-ui/ /usr/share/nginx/html
#Copy default nginx configuration
COPY ./nginx-custom.conf /etc/nginx/conf.d/default.conf

#docker build -t site:dev .