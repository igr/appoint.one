<template>
  <div>
    <pre>
      Spisak ponuđenih termina.
    </pre>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import TimeslotApi from '@/api/TimeslotApi';
import AppCookies from '@/utils/cookies';

@Component
export default class AvailableTimeslots extends Vue {
  private timeslotList: any;

  created() {
    this.fetchData();
  }

  private async fetchData() {
    const country = AppCookies.getCountry() || '1';
    const city = AppCookies.getCity() || '1';
    const { data } = await TimeslotApi.listNextTimeslots(country, city);
    this.timeslotList = data;
  }
}
</script>

<style>
</style>
