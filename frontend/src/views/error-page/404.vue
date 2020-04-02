<template>
  <v-row
    align="content-start"
  >
    <v-col
      cols="4"
      class="offset-1"
    >
      <img
        src="@/assets/images/404.gif"
        alt="404"
      >
    </v-col>
    <v-col
      cols="6"
      align-self="center"
    >
      <h1 class="headline">{{ message }}</h1>

      <p>
        Please check that the URL you entered is correct,
        or click the button below to return to the homepage.
      </p>
      <a
        href="/"
      >Back to home</a>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
// eslint-disable-next-line no-unused-vars
import { Route } from 'vue-router';

@Component({
  name: 'Page404',
})
export default class extends Vue {
  private message = '404 Page Not Found'

  public beforeRouteEnter(to: Route, from: Route, next: any): void {
    console.log(from);
    next();
  }
}
</script>

<style lang="scss" scoped>
</style>
