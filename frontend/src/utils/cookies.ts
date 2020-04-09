import Cookies from 'js-cookie';

const tokenKey = 'vue_typescript_access_token';
const languageKey = 'language';

class AppCookies {
  /**
   * Auth Token.
   */
  getToken = () => Cookies.get(tokenKey);

  setToken = (token: string) => Cookies.set(tokenKey, token);

  removeToken = () => Cookies.remove(tokenKey);

  /**
   * Language.
   */
  getLanguage = () => Cookies.get(languageKey);

  setLanguage = (language: string) => Cookies.set(languageKey, language);
}

export default new AppCookies();
