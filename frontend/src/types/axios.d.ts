import * as axios from 'axios';

declare module 'axios' {
  export interface AxiosRequestConfig {
    errorHandle?: boolean;
  }
}
