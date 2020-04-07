/**
 * Returns true if path is external.
 */
export const isExternal = (path: string) => /^(https?:|mailto:|tel:)/.test(path);

/**
 * Returns true if argument is an array.
 */
export const isArray = (arg: any) => {
  if (typeof Array.isArray === 'undefined') {
    return Object.prototype.toString.call(arg) === '[object Array]';
  }
  return Array.isArray(arg);
};

/**
 * Simple URL validator.
 */
export const isValidURL = (url: string) => {
  const reg = /^(https?|ftp):\/\/([a-zA-Z0-9.-]+(:[a-zA-Z0-9.&%$-]+)*@)*((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]?)(\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])){3}|([a-zA-Z0-9-]+\.)*[a-zA-Z0-9-]+\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(:[0-9]+)*(\/($|[a-zA-Z0-9.,?'\\+&%$#=~_-]+))*$/;
  return reg.test(url);
};

export const isValidEmail = (email: string) => {
  const reg = /.+@.+\..+/;
  return reg.test(email);
};

export const isValidDate = (date: string) => {
  const reg = /^202[0-9][//](0?[1-9]|1[012])[//](0?[1-9]|[12][0-9]|3[01])$/;
  return reg.test(date);
};

export const isValidTime = (time: string) => {
  const reg = /^(0?[0-9]|1[0-9]|2[0123])[:](0?[0-9]|[1-5][0-9])$/;
  return reg.test(time);
};

export const isValidPhoneNumber = (phone: string) => {
  let phoneNumbers = phone.replace('-', '');
  phoneNumbers = phoneNumbers.replace('/', '');
  phoneNumbers = phoneNumbers.replace(' ', '');
  const reg = /^(\+381|\+385|\+387)([0-9]{8}|[0-9]{9})$/;
  return reg.test(phoneNumbers);
};

export const isValidZoomID = (zoom: number) => {
  const reg = /^([0-9]{9}|[0-9]{10}|[0-9]{11})$/;
  return reg.test(zoom.toString());
};
