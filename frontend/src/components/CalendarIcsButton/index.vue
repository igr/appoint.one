<template>
  <v-btn
    dark
    x-large
    class="ma-2 col-10 col-md-4"
    @click="downloadIcs"
  >
    <v-icon
      dark
      class="mr-2"
    >
      mdi-calendar-month
    </v-icon>
    dodaj u kalendar
  </v-btn>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';
import AppoitmentApi from '@/api/AppoitmentApi';
import { Timeslot } from '@/model/Timeslot';

@Component
export default class CalendarIcsButton extends Vue {
  @Prop() timeslot!: Timeslot;

  async downloadIcs() {
    await AppoitmentApi.downloadIcal(this.timeslot.id);
  }
}
</script>
