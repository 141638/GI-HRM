import { CommonPaginatorRequest } from "../common-paginator-request";

export class EmployeeListSearchRequest extends CommonPaginatorRequest {
    name: string | undefined;
    email: string | undefined;
    insystemRole: number | undefined;
    department: number | undefined;
    employeeCode: string | undefined;
    entryDate: Date | undefined;
    generalKeys: string | undefined;
}