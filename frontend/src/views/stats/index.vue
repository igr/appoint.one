<template>
  <v-row
    cols="12"
    md="6"
    justify="center"
  >
    <v-col cols="6">
      <div
        v-for="s in stats"
        :key="s.status"
      >
        {{ name(s.status) }} {{ s.count }}
      </div>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import StatsApi from '@/api/StatsApi';
import { TimeslotStatusCount } from '@/model/Stats';
import { TimeslotStatus, timeslotStatusName } from '@/model/Timeslot';

@Component({
  name: 'Doctor',
})
export default class extends Vue {
  private stats: TimeslotStatusCount[] = [];

  name(timeslotStatus: TimeslotStatus) {
    return timeslotStatusName(timeslotStatus);
  }

  async created() {
    const res = await StatsApi.get();
    this.stats = res.data;
  }
}
</script>

<style scoped>
</style>
