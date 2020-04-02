<template>
  <div>
    <v-app-bar
      app
      color="primary"
      dark
      :fixed="true"
    >
      <div class="d-flex align-center">
        <router-link to="/">
          <v-img
            alt="Vuetify Logo"
            class="shrink mr-2"
            contain
            src="https://cdn.vuetifyjs.com/images/logos/vuetify-logo-dark.png"
            transition="scale-transition"
            width="40"
          />
        </router-link>
      </div>
      <v-spacer />
      <v-btn
        icon
        to="/login"
      >
        <v-icon>mdi-account-box</v-icon>
      </v-btn>
    </v-app-bar>
    <v-content>
      <info />
      <v-container
        fluid
        fill-height
      >
        <router-view :key="key" />
      </v-container>
    </v-content>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import Info from '@/components/Info/index.vue';

@Component({
  name: 'Layout',
  components: {
    Info,
  },
})
export default class extends Vue {
  get key() {
    return this.$route.path;
  }
}
</script>

<style>
</style>
