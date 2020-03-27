import axios from 'axios';
import { Message } from 'element-ui';

const http = axios.create({
  baseURL: process.env.API_ENDPOINT,
  timeout: 5000,
  // withCredentials: true // send cookies when cross-domain requests
  headers: {
    'Content-Type': 'application/json',
  },
});

// Request interceptors
http.interceptors.request.use(
  (config) => config,
  (error) => {
    Promise.reject(error);
  },
);

// Response interceptors
http.interceptors.response.use(
  (response) => response,
  (error) => {
    // error handling may be disabled by `errorHandle` option
    if (Object.prototype.hasOwnProperty.call(error.config, 'errorHandle') && error.config.errorHandle === false) {
      return Promise.reject(error);
    }
    if (error.response) {
      Message({
        message: error.message,
        type: 'error',
        duration: 5 * 1000,
      });
    }
    return Promise.reject(error);
  },
);

export default http;
