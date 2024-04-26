# stage 1: build angular project
FROM node:16.19.0-alpine as node
WORKDIR /usr/local/app
COPY . .

# install dependecies
RUN npm install -f
RUN npm install --save @types/chart.js -f
COPY . .
RUN npm run build --prod

# stage 2: server app nginx
FROM nginx:latest
COPY --from=node /usr/local/app/dist/gi-hrm-fe /user/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Expose port 80
EXPOSE 80

