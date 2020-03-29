<template>
  <div class="login-container">
    >>>
    <a
      href="#"
      @click="handleLogin"
    >login here</a>
    <div v-if="error">
      {{ $t('login.error') }}
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { UserModule } from '@/store/modules/user';

@Component({
  name: 'Login',
})
export default class extends Vue {
  private loginForm = {
    email: 'admin2',
    password: '111111',
  };

  private redirect?: string;

  private error: boolean = false;

  private async handleLogin() {
    const success = await UserModule.Login(this.loginForm);
    if (!success) {
      this.error = true;
      return;
    }

    this.$router.push({
      path: this.redirect || '/',
    });
  }
}
</script>

<style lang="scss">
</style>
