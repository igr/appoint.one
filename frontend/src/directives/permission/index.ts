import { DirectiveOptions } from 'vue';
import { UserModule } from '@/store/modules/user';

export const permission: DirectiveOptions = {
  inserted(el, binding) {
    const { value } = binding;

    if (value && value instanceof Array && value.length > 0) {
      const hasPermission = UserModule.hasAccess(value);
      if (!hasPermission) {
        el.parentNode && el.parentNode.removeChild(el);
      }
    } else {
      throw new Error('Roles missing! Use v-permission="[\'admin\',\'editor\']"');
    }
  },
};
