import { HttpHeaders } from "@angular/common/http";

export const environment = {
    production: true,
    apiUrl: '',
    httpOptions: {
        headers: new HttpHeaders({
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*',
            'Access-Control-Allow-Credentials': 'true',
            'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE',
            'Access-Control-Allow-Headers': 'Origin, Content-Type, Accept, X-Custom-Header, Upgrade-Insecure-Requests',
        })
    }
}