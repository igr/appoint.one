<template>
  <v-row justify="center">
    <v-col
      cols="12"
      md="8"
    >
      <h1 class="headline">
        ZAKAZANI TERMIN {{ id }}
      </h1>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';

@Component({
  name: 'Appointment',
})
export default class extends Vue {
  @Prop({ default: -1 })
  readonly id: number | undefined
}
</script>

<style scoped>
</style>
