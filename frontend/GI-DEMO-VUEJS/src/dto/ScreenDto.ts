export class ScreenDto {
    private screenCd: string;
    private screenName: string;
    private path: string;
    private asMenu: boolean;
    private moduleImport: (() => Promise<Object>) | undefined;
    private children: ScreenDto[] | undefined;

    constructor(screenCd: string, screenName: string, path: string | undefined | null, asMenu: boolean, moduleImport: (() => Promise<Object>) | undefined | null, children?: ScreenDto[]) {
        this.screenCd = screenCd;
        this.screenName = screenName;
        this.path = path == undefined || path == null ? '#' : path;
        this.asMenu = asMenu;
        this.moduleImport = moduleImport == undefined || moduleImport == null ? undefined : moduleImport;
        this.children = children;
    }

    get getScreenCd() {
        return this.screenCd;
    }

    get getScreenName() {
        return this.screenName;
    }

    get getPath() {
        return this.path;
    }

    get getAsMenu() {
        return this.asMenu;
    }

    get getModuleImport() {
        return this.moduleImport;
    }

    get getChildren() {
        return this.children;
    }
}