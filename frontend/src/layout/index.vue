<template>
  <div class="fill-height">
    <v-app-bar
      app
      color="primary"
      dark
      :fixed="true"
    >
      <v-btn
        icon
        to="/"
      >
        <v-icon>
          mdi-home
        </v-icon>
      </v-btn>
      <v-spacer />
      <v-toolbar-title>
        podrška psihoterapeuta
      </v-toolbar-title>
      <v-spacer />
      <v-btn
        v-if="!isLoggedIn"
        to="login"
      >
        <v-icon>mdi-account</v-icon>
        Prijava
      </v-btn>
      <v-menu
        v-if="isLoggedIn"
        offset-y
      >
        <template v-slot:activator="{ on }">
          <v-btn
            icon
            v-on="on"
          >
            <v-avatar color="indigo">
              <v-icon>mdi-account-circle</v-icon>
            </v-avatar>
          </v-btn>
        </template>
        <v-list>
          <v-list-item to="/my">
            <v-list-item-title>
              <v-icon>mdi-cog</v-icon>
              Profil
            </v-list-item-title>
          </v-list-item>
          <v-list-item @click="logout()">
            <v-list-item-title>
              <v-icon>mdi-logout-variant</v-icon>
              Odjava
            </v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
    </v-app-bar>
    <v-content class="fill-height">
      <v-container
        fluid
        class="fill-height"
      >
        <router-view :key="key" />
      </v-container>
    </v-content>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { UserModule } from '@/store';

@Component({
  name: 'Layout',
  components: {
  },
})
export default class extends Vue {
  get key() {
    return this.$route.path;
  }

  get isLoggedIn() {
    return UserModule.isUserLoggedIn;
  }

  logout() {
    UserModule.LogOut();
    this.$router.push('/');
  }
}
</script>

<style>
</style>
