import Vue from 'vue';
import VueI18n from 'vue-i18n';

import AppCookies from '@/utils/cookies';
import rsLocale from './rs';

Vue.use(VueI18n);

const messages = {
  rs: {
    ...rsLocale,
  },
};

export const getLocale = () => {
  const cookieLanguage = AppCookies.getLanguage();
  if (cookieLanguage) {
    return cookieLanguage;
  }

  const language = navigator.language.toLowerCase();
  const locales = Object.keys(messages);
  for (const locale of locales) {
    if (language.indexOf(locale) > -1) {
      return locale;
    }
  }

  // default language
  return 'rs';
};

const i18n = new VueI18n({
  locale: getLocale(),
  messages,
});

export default i18n;
