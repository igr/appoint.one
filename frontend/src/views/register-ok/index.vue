<template>
  <v-row
    align="center"
    justify="center"
  >
    <v-col
      cols="12"
      md="8"
    >
      <h1>Uspešno ste registrovani.</h1>
      <p>
        Sačuvajte vašu lozinku na sigurnom mestu.
      </p>
      <router-link to="/home">
        Nazad
      </router-link>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
@Component({
  name: 'RegisterOK',
})
export default class extends Vue {
}
</script>

<style lang="scss">
</style>
