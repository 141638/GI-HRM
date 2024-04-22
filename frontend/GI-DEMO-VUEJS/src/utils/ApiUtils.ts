import { ApiConstant } from "@/assets/Constants";
import axios, { type AxiosInstance } from "axios";

const create = (): AxiosInstance | undefined => {
    const token = localStorage.getItem("jwtToken");
    if (!token) {
        throw new Error('No token found');
    }
    return axios.create({
        baseURL: `${ApiConstant.GT_HOST}${ApiConstant.GT_SANDBOX_URL}`,
        headers: {
            Authorization: `Bearer ${token}`
        }
    })
}

const get = (url: string, signal?: AbortSignal, params?: Map<string, any>) => {
    const urlParams = paramBuilder(url, false, params);
    try {
        return create()?.get(urlParams, { signal: signal });
    } catch (e) {
        console.log('Error when calling axios.get: ', e);
    }
}


const put = (url: string, payload: any, signal?: AbortSignal, params?: Map<string, any>) => {
    const urlParams = paramBuilder(url, false, params);
    try {
        return create()?.put(urlParams, payload, { signal: signal });
    } catch (e) {
        console.log('Error when calling axios.put: ', e);
    }
}
const eventSource = (url: string, params?: Map<string, string | number | Date | null>) => {
    return new EventSource(paramBuilder(`${ApiConstant.GT_HOST}${ApiConstant.GT_SANDBOX_URL}${url}`, true, params));
};

const paramBuilder = (url: string, hasTokenAsParam: boolean, params?: Map<string, string | number | Date | null>) => {
    if (params) {
        if (hasTokenAsParam) {
            params.set('Authorization', localStorage.getItem('jwtToken'));
        }
        url += '?';
        params.forEach((value, key) => {
            url += `${key}=${value}&`
        });
        url = url.substring(0, url.length - 1);
    } else {
        if (hasTokenAsParam) {
            url += `?Authorization=${localStorage.getItem('jwtToken')}`
        }
    }
    return url;
}

export { create, get, put, eventSource, paramBuilder };