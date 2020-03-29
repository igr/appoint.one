import Cookies from 'js-cookie';

const sizeKey = 'size';
const tokenKey = 'vue_typescript_access_token';
const languageKey = 'language';

class AppCookies {
  /**
   * Application size modificator.
   */
  getSize = () => Cookies.get(sizeKey);

  setSize = (size: string) => Cookies.set(sizeKey, size);


  /**
   * Auth Token.
   */
  getToken = () => Cookies.get(tokenKey);

  setToken = (token: string) => Cookies.set(tokenKey, token);

  removeToken = () => Cookies.remove(tokenKey);

  /**
   * Language,
   */
  getLanguage = () => Cookies.get(languageKey);

  setLanguage = (language: string) => Cookies.set(languageKey, language);
}

export default new AppCookies();
