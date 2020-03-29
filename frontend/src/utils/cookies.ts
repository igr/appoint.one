import Cookies from 'js-cookie';

const tokenKey = 'vue_typescript_access_token';
const sizeKey = 'size';
const languageKey = 'language';
const countryKey = 'country';
const cityKey = 'city';

class AppCookies {
  /**
   * Application size modifier.
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
   * Language.
   */
  getLanguage = () => Cookies.get(languageKey);

  setLanguage = (language: string) => Cookies.set(languageKey, language);


  /**
   * Country.
   */
  getCountry = () => Cookies.get(countryKey);

  setCountry = (country: string) => Cookies.set(countryKey, country);


  /**
   * City.
   */
  getCity = () => Cookies.get(cityKey);

  setCity = (city: string) => Cookies.set(cityKey, city);
}

export default new AppCookies();
