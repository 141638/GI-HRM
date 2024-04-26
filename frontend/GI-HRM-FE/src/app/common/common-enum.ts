export enum PageSize {
    PAGE_SIZE = 10,
    PAGE_LINK_SIZE = 6,
}

export enum HttpStatusCodeConstants {
    HTTP_STATUS_200 = 200,
    HTTP_STATUS_500 = 500,
    HTTP_STATUS_400 = 400,
    HTTP_STATUS_401 = 401,
    HTTP_STATUS_404 = 404,
}

export enum MediaType {
    ERROR_ = "Error_",
    TEXT_CSV = "text/csv",
    DOT_CSV = ".csv"
}

export enum eRole {
    GUEST = "GUEST",
    EMPLOYEE = "EMPLOYEE",
    LEADER = "LEADER",
    PROJECT_MANAGER = "PROJECT_MANAGER",
    HR_RECRUITER = "RECRUITER",
    HR_STAFF = "STAFF",
    HR_SOURCER = "SOURCER",
    HR_ACCOUNTANT = "ACCOUNTANT",
    HR = "HR",
}

export enum NavRouter {
    LOGIN = "login",
    FORGOT_PASSWORD = "forgot-password",
    OTHER = "other",
    HOME = "/home",
    PROFILE = "/profile"
}

export enum NavChildrenRouter {
    HRM_SYSTEM = "system",
    HRM_USER = "user",
    HRM_GUEST = "guest",
}

export enum NavSystemRouter {
    ACCOUNTANT = "accounting",
    RESOURCER = "resource",
    RECRUITER = "recruit",
    STAFF = "staff",
    TASKLOG = "tasklog"
}

export enum SearchAfter {
    CREATE = 0,
    UPDATE = 1,
    DELETE = 2
}

export enum ActionStatus {
    SUCCESS = 0,
    FAILED = 1
}

export enum ConfirmActionType {
    DEFAULT = 0,
    UPDATE = 1,
    DELETE = 2
}




export enum LocalStorageKey {
    WORKSPACE_ID = 'workspace_id',
    AUTH_TOKEN = "auth-token"
}
