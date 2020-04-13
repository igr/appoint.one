import axios, { AxiosResponse } from 'axios';
import { AppModule, UserModule } from '@/store';
import router from '@/router';

const http = axios.create({
  baseURL: process.env.VUE_APP_BASE_API,
  timeout: 5000,
  // withCredentials: true // send cookies when cross-domain requests
  headers: {
    'Content-Type': 'application/json',
  },
  validateStatus: (status) => status < 400,
});

// Request interceptors
http.interceptors.request.use(
  (config) => {
    // eslint-disable-next-line no-param-reassign
    config.headers.common.Authorization = `Bearer ${UserModule ? UserModule.token : ''}`;
    return config;
  },
  (error) => {
    // console.error('>> Error:', error);
    Promise.reject(error);
  },
);

// Response interceptors
http.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && error.response.status >= 400) {
      if (error.response.status === 401) {
        AppModule.setInfo({ message: 'Ulogujte se ponovo.', type: 'error' });
        UserModule.LogOut().then(() => router.push('/login'));
      }
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

// const http2 = axios.create({
//   baseURL: '/',
//   timeout: 3000,
//   headers: {
//     'Content-Type': 'application/json',
//   },
//   validateStatus: (status) => status < 400,
// });
//
// export const httpLocal = http2;
