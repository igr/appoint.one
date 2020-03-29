<template>
  <div>
    <img
      src="@/assets/images/404.gif"
      alt="404"
    >
    {{ message }}

    Please check that the URL you entered is correct,
    or click the button below to return to the homepage.
    <a
      href="/"
    >Back to home</a>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component({
  name: 'Page404',
})
export default class extends Vue {
  private message = '404 Page Not Found'
}
</script>

<style lang="scss" scoped>
</style>
