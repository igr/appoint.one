import axios from 'axios';
import { Message } from 'element-ui';

const http = axios.create({
  baseURL: process.env.VUE_APP_BASE_API,
  timeout: 5000,
  // withCredentials: true // send cookies when cross-domain requests
  headers: {
    'Content-Type': 'application/json',
  },
  errorHandle: false,
});

// Request interceptors
http.interceptors.request.use(
  (config) => config,
  (error) => {
    console.error('error:', error);
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
    if (error.response) {
      Message({
        showClose: true,
        message: error.message,
        type: 'error',
        duration: 5 * 1000,
      });
    }
    return Promise.reject(error);
  },
);

export default http;
