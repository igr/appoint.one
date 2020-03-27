<template>
  <div id="c-number">
    Preostalo je {{count}} slobodnih termina.
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import api from '@/api/Api';

@Component
export default class NumberOfTimeslots extends Vue {
  private countOfTimeslots = 1;

  get count(): number {
    return this.countOfTimeslots;
  }

  created() {
    api.doctors.get().then((response) => {
      this.countOfTimeslots = response.data;
    });
  }
}
</script>


<style>
</style>
