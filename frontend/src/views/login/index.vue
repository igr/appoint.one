<template>
  <div class="login-container">
    >>>
    <a
      href="#"
      @click="handleLogin"
    >login here</a>
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
    username: 'admin',
    password: '111111',
  };

  private redirect?: string;

  private async handleLogin() {
    await UserModule.Login(this.loginForm);
    console.log('login');
    this.$router.push({
      path: this.redirect || '/',
    });
  }
}
</script>

<style lang="scss">
</style>
