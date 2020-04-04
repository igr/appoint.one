<template>
  <v-row
    align="center"
    justify="center"
  >
    <v-col
      cols="12"
      md="8"
    >
      <router-link
        to="/admin/doctors"
      >
        All doctors
      </router-link>
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
