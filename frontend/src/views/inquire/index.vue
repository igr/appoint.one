<template>
  <v-row justify="center">
    <v-col
      cols="12"
      md="8"
    >
      <h1 class="heading text-center">
        IZBOR TERMINA
      </h1>
      <NumberOfTimeslots />
      <v-divider class="ma-6" />
      <AvailableTimeslots />
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import AvailableTimeslots from '@/components/AvailableTimeslots/index.vue';
import NumberOfTimeslots from '@/components/NumberOfTimeslots/index.vue';

@Component({
  name: 'Inquiry',
  components: {
    AvailableTimeslots,
    NumberOfTimeslots,
  },
})
export default class extends Vue {
}
</script>

<style scoped>
</style>
