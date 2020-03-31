<template>
  <v-row align="center" justify="center">
    <v-col cols="12" sm="8" md="4">
      <v-card class="elevation-12">
        <v-toolbar color="#34558b" dark flat>
          <v-toolbar-title>Login</v-toolbar-title>
          <v-spacer />
        </v-toolbar>
        <v-card-text>
          <v-form
            ref="form"
            v-model="valid"
          >
            <v-text-field
              v-model="email"
              label="email"
              prepend-icon="person"
              :rules="rules.email"
              autocomplete="email"
            />
            <v-text-field
              v-model="password"
              label="Password"
              prepend-icon="lock"
              type="password"
              :rules="rules.password"
              autocomplete="current-password"
            />
          </v-form>
        </v-card-text>
        <v-card-actions>
          <v-spacer />
          <v-btn
            name="registre"
            to="/register"
          >
            REGISTER
          </v-btn>
          <v-btn
            name="submit"
            :disabled="!valid"
            @click="handleLogin"
            color="primary"
          >
            LOGIN
          </v-btn>
        </v-card-actions>
      </v-card>
      <v-alert v-if="error" type="error" dismissible>
        {{ $t('login.error') }}
      </v-alert>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { UserModule } from '@/store/modules/user';

@Component({
  name: 'Login',
})
export default class extends Vue {
  private email = '';

  private valid = true;

  private password = '';

  private redirect?: string;

  private error: boolean = false;

  private rules = {
    email: [
      (v: string) => !!v || 'E-mail is required',
      (v: string) => /.+@.+\..+/.test(v) || 'E-mail must be valid',
      (v: string) => !!v && /.+@.+\..+/.test(v),
    ],
    password: [
      (v: string) => !!v || 'Password is required',
    ],
  };

  private async handleLogin() {
    const success = await UserModule.Login({
      email: this.email,
      password: this.password,
    });
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
