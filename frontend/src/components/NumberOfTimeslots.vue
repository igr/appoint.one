<template>
  <div id="c-number">
    Preostalo je {{ count }} slobodnih termina.
  </div>
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

<style>
</style>
