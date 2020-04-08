<template>
  <v-row
    align="center"
    justify="center"
  >
    <v-col
      cols="12"
      md="8"
    >
      <h1>Administracija</h1>
      <v-btn
        x-large
        color="red"
        style="color: white;"
        class="ma-2"
        to="/admin/doctors"
      >
        All doctors
      </v-btn>
      <v-btn
        x-large
        color="red"
        style="color: white;"
        class="ma-2"
        to="/logout"
      >
        Logout
      </v-btn>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component({
  name: 'Admin',
})
export default class extends Vue {
}
</script>

<style lang="scss">
</style>
