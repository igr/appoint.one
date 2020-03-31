<template>
  <v-row
    align="center"
    justify="center"
  >
    <v-col
      cols="12"
      md="8"
    >
      <v-card
        ref="card"
        class="elevation-12"
        :loading="loading"
      >
        <v-toolbar
          color="#34558b"
          dark
          flat
        >
          <v-toolbar-title>{{ $t('login.title') }}</v-toolbar-title>
          <v-spacer />
        </v-toolbar>
        <v-card-text>
          <v-form
            ref="form"
            v-model="valid"
          >
            <v-text-field
              v-model="name"
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
            {{ $t('login.register') }}
          </v-btn>
          <v-btn
            name="submit"
            :disabled="!valid"
            color="primary"
            @click="handleLogin"
          >
            {{ $t('login.submit') }}
          </v-btn>
        </v-card-actions>
      </v-card>
      <v-alert
        v-model="error"
        type="error"
        transition="slide-y-transition"
        dismissible
      >
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
  private name = '';

  private valid = true;

  private password = '';

  private redirect?: string;

  private error: boolean = false;

  private loading: boolean = false;

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
    this.loading = true;

    const success = await UserModule.Login({
      name: this.name,
      password: this.password,
    });
    if (!success) {
      this.error = true;
      this.loading = false;
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
