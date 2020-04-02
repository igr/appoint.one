import axios, { AxiosRequestConfig, AxiosResponse } from 'axios';
import { AppModule } from '@/store/modules/app';
import { UserModule } from '@/store/modules/user';

const http = axios.create({
  baseURL: process.env.VUE_APP_BASE_API,
  timeout: 5000,
  // withCredentials: true // send cookies when cross-domain requests
  headers: {
    Authorization: `Bearer ${UserModule ? UserModule.token : ''}`,
    'Content-Type': 'application/json',
  },
  validateStatus: (status) => status < 400,
});

// Request interceptors
http.interceptors.request.use(
  (config) => config,
  (error) => {
    console.error('>> Error:', error);
    Promise.reject(error);
  },
);

// Response interceptors
http.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && error.response.status >= 500) {
      AppModule.setAlertMessage(error.message);
    }
    return Promise.reject(error);
  },
);

export default http;

export function isSuccess(response: AxiosResponse): boolean {
  return response.status < 300;
}

export function isError(response: AxiosResponse): boolean {
  return response.status >= 300;
}

export function isStatus(response: AxiosResponse, status: number): boolean {
  if (status < 300) {
    return false;
  }
  return response.status === status;
}

export function isError4xx(response: AxiosResponse): boolean {
  const hundreds = Math.floor(response.data.status / 100);
  return (hundreds === 4);
}

export function isError5xx(response: AxiosResponse): boolean {
  const hundreds = Math.floor(response.data.status / 100);
  return (hundreds === 5);
}
