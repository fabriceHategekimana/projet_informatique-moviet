# WebUi

## install nodjs (v14)

`curl -sL https://deb.nodesource.com/setup_14.x | sudo -E bash -`\
`sudo apt-get install -y nodejs`\
`sudo n latest`\
`npm cache clean -f`\
`npm install -g n`\
`sudo n stable`

## Development server

`cd web-ui`\
`npm install`

Run `ng serve` (or `ng serve --poll=2000` if live-refresh does not work) for a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.

If it doesn't work:

`npm audit fix`

If it still doesn't work:

`npm uninstall -g @angular/cli`\
`npm cache verify`\
`npm install -g @angular/cli@next`\
 `npm update`

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory. Use the `--prod` flag for a production build.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via [Protractor](http://www.protractortest.org/).

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI Overview and Command Reference](https://angular.io/cli) page.
