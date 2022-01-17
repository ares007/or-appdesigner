
# Angular Frontend Server

## Environment Variables

In `src/environments/environment.ts` you can change settings of the designer.
You will find the following:
| Variable Name  | Possible values | Default |  Explanation |
|--|--|--|--|
| pruduction | true / false | false |Angular's default setting to clarify whether it has to run in production.  |
| useLocalStorage | true / false | true | Whether it has to uses your browsers' LocalStorage.
| useDatabase | true / false | false | If false, the backend nor the database will be used. If true, it saves a SINGLE design in the database.
| previewUrl | website URL | http://localhost:4200/preview | The URL that the QR Code for Phone Mirroring will have.
| backendUrl | website URL | http://localhost:8000 | The endpoint URL where the backend is hosted.
| openremoteUrl | website URL | https://localhost | The endpoint URL where the OpenRemote Manager is hosted. **The Manager has to be up and running to work correctly.**
| debugComponents | true / false | false | Boolean for debugging purposes; provides borders and other visual info when working on Widgets/Components.



## Development
> For running the Frontend Server. an hosted OpenRemote Manager is required,
> with the `openremoteUrl` variable set correctly.

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. <br />
The app will automatically reload if you change any of the source files.

Run `ng generate component <component-name>` to generate a new component.<br />
You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory.

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI Overview and Command Reference](https://angular.io/cli) page.