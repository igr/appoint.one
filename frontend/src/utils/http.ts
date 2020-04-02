import axios, { AxiosRequestConfig } from 'axios';
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
  errorHandle: false,
  validateStatus: (status) => status < 500,
});

// Request interceptors
http.interceptors.request.use(
  (config) => config,
  (error) => {
    console.error('Error:', error);
    Promise.reject(error);
  },
);

// Response interceptors
http.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.config.errorHandle) {
      return Promise.reject(error);
    }
    if (error.response && error.response.status >= 500) {
      AppModule.setAlertMessage(error.message);
    }
    return Promise.reject(error);
  },
);

export default http;


export function http2(request: AxiosRequestConfig, statusHandlers: {[key: number]: () => boolean}) {
  return http(request).then((res) => {
    const statusCode = res.status;
    let handler = statusHandlers[statusCode];

    if (!handler) {
      // try x00 handler instead
      if (statusCode % 100 > 0) {
        // if statusCode ends with not '00'
        const newStatusCode = Math.floor(statusCode / 100) * 100;
        handler = statusHandlers[newStatusCode];
      }
    }

    if (!handler) {
      if (statusCode < 300) {
        // indicate that status code was not processed
        return true;
      }
      const msg = `${statusCode}: ${res.statusText}`;
      AppModule.setAlertMessage(msg);
      throw Error(msg);
    }
    return !handler();
  });
}
