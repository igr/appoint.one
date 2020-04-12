<template>
  <v-row
    justify="center"
    align="center"
  >
    <v-sheet
      color="green"
      class="white--text font-weight-black text-center pa-6 col-md-6"
    >
      Ukupno slobodnih termina: <span id="count">{{ count }}</span>
    </v-sheet>
  </v-row>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import TimeslotApi from '@/api/TimeslotApi';

@Component
export default class NumberOfTimeslots extends Vue {
  private countOfTimeslots = 0;

  get count(): number {
    return this.countOfTimeslots;
  }

  created() {
    this.fetchData();
  }

  private async fetchData() {
    const { data } = await TimeslotApi.count();
    this.countOfTimeslots = data;
  }
}
</script>

<style lang="scss">
#count {
  font-size: 1.2em;
  position: relative;
  top: 2px;
}
</style>
